package astroid;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class GameObject {

    private Node view;
    private Point2D velocity = new Point2D(0, 0);
    private int radius = 15;

    private boolean alive = true;
    
    public GameObject(){
        
    }
    
    public GameObject(Node view) {
        this.view = view;
    }

    public void update() {
        view.setTranslateX(view.getTranslateX() + velocity.getX());
        view.setTranslateY(view.getTranslateY() + velocity.getY());
    }
    
    public void radiusUpdate(){
        radius++;
        view = new Circle(radius, radius, radius,new ImagePattern(new Image(astroid.Player.class.getResource("player.png").toExternalForm())));
    }
    
    public boolean radiusCompare(int anotherRadius){
        if(radius - anotherRadius >= 10) return true;
        else return false;
    }
    
    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    public Point2D getVelocity() {
        return velocity;
    }

    public Node getView() {
        return view;
    }
    
    public void setView(Node view){
        this.view = view;
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
    
    public void setRadius(int radius){
        this.radius = radius;
    }
    
    public int getRadius(){
        return radius;
    }
}

    