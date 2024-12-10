import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameBoard extends JPanel implements ActionListener {
    private final int TILE_SIZE = 20;
    private final int BOARD_WIDTH = 800;
    private final int BOARD_HEIGHT = 600;

    private AbstractSnake snake;
    private Point food;
    private int direction;
    private boolean isGameOver;

    private int score;

    private Timer timer;

    public GameBoard(Color snakeColor) {
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);

        snake = new Snake(snakeColor);
        direction = 1;

        spawnFood();

        score = 0;

        timer = new Timer(100, this);
        timer.start();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!isGameOver) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                            if (direction != 2)
                                direction = 0;
                            break;
                        case KeyEvent.VK_RIGHT:
                            if (direction != 3)
                                direction = 1;
                            break;
                        case KeyEvent.VK_DOWN:
                            if (direction != 0)
                                direction = 2;
                            break;
                        case KeyEvent.VK_LEFT:
                            if (direction != 1)
                                direction = 3;
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
        snake.move(direction, TILE_SIZE, BOARD_WIDTH, BOARD_HEIGHT);
        Point head = snake.getBody().getFirst();

        if (head.equals(food)) {
            spawnFood();
            score += 1;
        } else {
            snake.getBody().removeLast();
        }

        if (snake.checkCollision(BOARD_WIDTH, BOARD_HEIGHT)) {
            isGameOver = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isGameOver) {
            moveSnake();
        }
        repaint();
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

        g.setColor(snake.getColor());
        for (Point p : snake.getBody()) {
            g.fillRect(p.x, p.y, TILE_SIZE, TILE_SIZE);
        }

        g.setColor(Color.RED);
        g.fillRect(food.x, food.y, TILE_SIZE, TILE_SIZE);

        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 20);
    }

    public void restartGame() {
        snake = new Snake(snake.getColor());
        direction = 1;
        spawnFood();
        score = 0;
        isGameOver = false;
        timer.restart();
    }

    public boolean isGameOver() {
        return isGameOver;
    }
}
