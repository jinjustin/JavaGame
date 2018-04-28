
package astroid;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

class Player extends GameObject{
        
        Player() {
            super(new Circle(15, 15 ,15,new ImagePattern(new Image(astroid.Player.class.getResource("player.png").toExternalForm()))));
        }
        
    }
