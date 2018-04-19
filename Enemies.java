package astroid;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

class Enemies extends GameObject {
        Enemies(){
            super(new Circle(15, 15 ,15,Color.BLACK));
        }
      
        @Override
        public void radiusUpdate(){
        super.setRadius(super.getRadius()+1);
        super.setView(new Circle(super.getRadius(), super.getRadius(), super.getRadius(), Color.BLACK));
    }
        
    }