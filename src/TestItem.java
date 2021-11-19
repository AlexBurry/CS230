import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TestItem {
    
    private double x;
    private double y;
    private double width;
    private double height;
    private Rectangle r;

    public TestItem(double x, double y, double width, double height, Rectangle r) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.r = r;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
    
    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void setColor(Color color) {
        r.setFill(color);
    }

    public void draw() {
        r.setWidth(width);
        r.setHeight(height);
        r.setFill(Color.BLACK);
        r.setTranslateX(x);
        r.setTranslateY(y);
    }
}
