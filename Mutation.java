package astroid;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Mutation extends GameObject{

    private int acceleration = 0;
    public static int daley=30;
    private int passTime=0;
    
    public Mutation() {
        
    }
    
    public Mutation(int radius) {
        super(new Circle(radius,radius,radius,Color.BLUE));
        super.setRadius(radius);
    }
    
    public void velocityUpdate() {
        acceleration++;
        if(acceleration >=60){
//            if(super.getVelocity().getX()>0){ // X เป็นบวก
//                if(super.getVelocity().getY()>0){ // Y เป็นบวก
//                    if(super.getVelocity().getX()>=2 ){
//                        if(super.getVelocity().getY()>=2) super.setVelocity(new Point2D(super.getVelocity().getX()-2,super.getVelocity().getY()-2));
//                        else super.setVelocity(new Point2D(super.getVelocity().getX()-2, 0));
//                    }
//                    else{
//                        if(super.getVelocity().getY()>=2) super.setVelocity(new Point2D(0, super.getVelocity().getY()-2));
//                        else super.setVelocity(Point2D.ZERO);
//                    }
//                } //Y เป็นบวก
//                else{ // Y ติดลบ
//                    if(super.getVelocity().getX()>=2 ){
//                        if(super.getVelocity().getY()<=-2) super.setVelocity(new Point2D(super.getVelocity().getX()-2,super.getVelocity().getY()+2));
//                        else super.setVelocity(new Point2D(super.getVelocity().getX()-2,0));
//                    }
//                    else{
//                        if(super.getVelocity().getY()<=-2) super.setVelocity(new Point2D(0,super.getVelocity().getY()+2));
//                        else super.setVelocity(new Point2D(0,0));
//                    }
//                }
//            } // X เป็นบวก
//            else if(super.getVelocity().getX() <0){
//                if(super.getVelocity().getY()>0){
//                    if(super.getVelocity().getX()<=-2 ){
//                        if(super.getVelocity().getY()>=2) super.setVelocity(new Point2D(super.getVelocity().getX()+2,super.getVelocity().getY()-2));
//                        else super.setVelocity(new Point2D(super.getVelocity().getX()+2,0));
//                    }
//                    else{
//                        if(super.getVelocity().getY()>=2) super.setVelocity(new Point2D(0,super.getVelocity().getY()-2));
//                        else super.setVelocity(new Point2D(0,0));
//                    }
//                }
//                else{
//                    if(super.getVelocity().getX()<=-2){
//                        if(super.getVelocity().getY()<=-2) super.setVelocity(new Point2D(super.getVelocity().getX()+2,super.getVelocity().getY()+2));
//                        else super.setVelocity(new Point2D(super.getVelocity().getX()+2,0));
//                    }
//                    else{
//                        if(super.getVelocity().getY()<=-2) super.setVelocity(new Point2D(0,super.getVelocity().getY()+2));
//                        else super.setVelocity(new Point2D(0,0));
//                    }
//                }
//            }
//            acceleration=0;
            super.setVelocity(new Point2D(super.getVelocity().getX()/2,super.getVelocity().getY()/2));
        }
    }
    
    public void passTimeUpdate(){
        passTime++;
    }

    public int getPassTime() {
        return passTime;
    }
}
