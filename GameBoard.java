import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;

public class GameBoard extends JPanel implements ActionListener {
    private final int TILE_SIZE = 20;
    private final int BOARD_WIDTH = 800;
    private final int BOARD_HEIGHT = 600;
    private final int MAX_TILES = (BOARD_WIDTH / TILE_SIZE) * (BOARD_HEIGHT / TILE_SIZE);

    private LinkedList<Point> snake;
    private Point food;
    private int direction;
    private boolean isGameOver;

    private Timer timer;

    // Arah ular
    private static final int UP = 0;
    private static final int RIGHT = 1;
    private static final int DOWN = 2;
    private static final int LEFT = 3;

    public GameBoard() {
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);

        snake = new LinkedList<>();
        snake.add(new Point(100, 100)); // Posisi awal ular
        direction = RIGHT;

        spawnFood();

        timer = new Timer(100, this); // Setiap 100ms
        timer.start();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!isGameOver) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                            if (direction != DOWN) direction = UP;
                            break;
                        case KeyEvent.VK_RIGHT:
                            if (direction != LEFT) direction = RIGHT;
                            break;
                        case KeyEvent.VK_DOWN:
                            if (direction != UP) direction = DOWN;
                            break;
                        case KeyEvent.VK_LEFT:
                            if (direction != RIGHT) direction = LEFT;
                            break;
                    }
                }
            }
        });
    }

    private void spawnFood() {
        Random rand = new Random();
        int x = rand.nextInt(BOARD_WIDTH / TILE_SIZE) * TILE_SIZE;
        int y = rand.nextInt(BOARD_HEIGHT / TILE_SIZE) * TILE_SIZE;
        food = new Point(x, y);
    }

    private void moveSnake() {
        Point head = snake.getFirst();
        Point newHead = null;

        switch (direction) {
            case UP: newHead = new Point(head.x, head.y - TILE_SIZE); break;
            case RIGHT: newHead = new Point(head.x + TILE_SIZE, head.y); break;
            case DOWN: newHead = new Point(head.x, head.y + TILE_SIZE); break;
            case LEFT: newHead = new Point(head.x - TILE_SIZE, head.y); break;
        }

        // Jika ular menabrak tubuhnya atau keluar dari papan
        if (newHead.x < 0 || newHead.y < 0 || newHead.x >= BOARD_WIDTH || newHead.y >= BOARD_HEIGHT || snake.contains(newHead)) {
            isGameOver = true;
            return;
        }

        snake.addFirst(newHead); // Tambahkan bagian kepala ular baru

        // Cek jika ular makan makanan
        if (newHead.equals(food)) {
            spawnFood(); // Spawn makanan baru
        } else {
            snake.removeLast(); // Hapus ekor ular jika tidak makan
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isGameOver) {
            moveSnake();
        }
        repaint(); // Gambar ulang tampilan
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (isGameOver) {
            String message = "Game Over! Press R to restart.";
            g.setColor(Color.WHITE);
            g.drawString(message, BOARD_WIDTH / 2 - g.getFontMetrics().stringWidth(message) / 2, BOARD_HEIGHT / 2);
            return;
        }

        g.setColor(Color.GREEN);
        for (Point p : snake) {
            g.fillRect(p.x, p.y, TILE_SIZE, TILE_SIZE);
        }

        g.setColor(Color.RED);
        g.fillRect(food.x, food.y, TILE_SIZE, TILE_SIZE);
    }

    public void restartGame() {
        snake.clear();
        snake.add(new Point(100, 100));
        direction = RIGHT;
        spawnFood();
        isGameOver = false;
        timer.restart();
    }

    public boolean isGameOver() {
        return isGameOver;
    }
}
