package astroid;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class GameObject {

    private Node view;
    private Point2D velocity = new Point2D(0, 0);

    private boolean alive = true;
    public static int enemiesCount = 0; 
    public static int tanksCount = 0;
    
    public GameObject(Node view) {
        this.view = view;
    }

    public void update() {
        view.setTranslateX(view.getTranslateX() + velocity.getX());
        view.setTranslateY(view.getTranslateY() + velocity.getY());
    }
    
    public void radiusUpdate(int value){
        view = new Circle(value, value, value, Color.BLUE);
    }
    
//    public void hpUpdate(int value) {
//        view = new Rectangle(100*value,30, Color.RED);
//    }
//    
//    public void powerUpdate(int value) {
//        view = new Rectangle(100*value,30, Color.YELLOW);
//    }

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    public Point2D getVelocity() {
        return velocity;
    }

    public Node getView() {
        return view;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isDead() {
        return !alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public double getRotate() {
        return view.getRotate();
    }

    public boolean isColliding(GameObject other) {
        return getView().getBoundsInParent().intersects(other.getView().getBoundsInParent());
    }
}