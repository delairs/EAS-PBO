import java.awt.*;

public class Snake extends AbstractSnake {
  public Snake(Color color) {
    super(color);
  }

  @Override
  public void move(int direction, int tileSize, int boardWidth, int boardHeight) {
    Point head = body.getFirst();
    Point newHead = null;

    switch (direction) {
      case 0:
        newHead = new Point(head.x, head.y - tileSize);
        break;
      case 1:
        newHead = new Point(head.x + tileSize, head.y);
        break;
      case 2:
        newHead = new Point(head.x, head.y + tileSize);
        break;
      case 3:
        newHead = new Point(head.x - tileSize, head.y);
        break;
    }

    body.addFirst(newHead);
  }

  @Override
  public boolean checkCollision(int boardWidth, int boardHeight) {
    Point head = body.getFirst();
    return head.x < 0 || head.y < 0 || head.x >= boardWidth || head.y >= boardHeight
        || body.subList(1, body.size()).contains(head);
  }
}