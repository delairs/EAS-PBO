import java.awt.*;
import java.util.LinkedList;

public abstract class AbstractSnake {
    protected LinkedList<Point> body;
    protected Color color;

    public AbstractSnake(Color color) {
        this.color = color;
        body = new LinkedList<>();
        body.add(new Point(100, 100));
    }

    public LinkedList<Point> getBody() {
        return body;
    }

    public Color getColor() {
        return color;
    }

    public abstract void move(int direction, int tileSize, int boardWidth, int boardHeight);

    public abstract boolean checkCollision(int boardWidth, int boardHeight);
}