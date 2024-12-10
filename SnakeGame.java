import javax.swing.*;
import java.awt.*;

public class SnakeGame {
  public static void main(String[] args) {
    Color selectedColor = JColorChooser.showDialog(null, "Pilih Warna Snake", Color.GREEN);
    if (selectedColor == null) {
      selectedColor = Color.GREEN;
    }

    JFrame frame = new JFrame("Snake Game");
    GameBoard gameBoard = new GameBoard(selectedColor);

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(gameBoard);
    frame.pack();
    frame.setVisible(true);

    gameBoard.addKeyListener(new java.awt.event.KeyAdapter() {
      @Override
      public void keyPressed(java.awt.event.KeyEvent e) {
        if (e.getKeyCode() == java.awt.event.KeyEvent.VK_R && gameBoard.isGameOver()) {
          gameBoard.restartGame();
        }
      }
    });
  }
}
