package astroid;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

class Enemies extends GameObject {
    
    public static int enemiesCount = 0;
    
        Enemies(){
            super(new Circle(15, 15 ,15,new ImagePattern(new Image(astroid.Player.class.getResource("enemy.png").toExternalForm()))));
        }
        
        @Override
        public void radiusUpdate(){
        super.setRadius(super.getRadius()+1);
        super.setView(new Circle(super.getRadius(), super.getRadius(), super.getRadius(), new ImagePattern(new Image(astroid.Player.class.getResource("enemy.png").toExternalForm()))));
    }

    public static int getEnemiesCount() {
        return enemiesCount;
    }

    public static void setEnemiesCount(int enemiesCount) {
        Enemies.enemiesCount = enemiesCount;
    }
        
    }
