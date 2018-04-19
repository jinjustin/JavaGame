package astroid;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

class Food extends GameObject {
        Food() {
            super();
            RandomColor();
        }
        
        private void RandomColor(){
            Astroid random = new Astroid();
            int colorRand = random.randomWithRange(0, 5);
            switch(colorRand){
                case 0 : super.setView(new Circle(8, 8 ,8,Color.RED));
                break;
                case 1 : super.setView(new Circle(8, 8 ,8,Color.CORAL));
                break;
                case 2 : super.setView(new Circle(8, 8 ,8,Color.AQUA));
                break;
                case 3 : super.setView(new Circle(8, 8 ,8,Color.DARKVIOLET));
                break;
                case 4 : super.setView(new Circle(8, 8 ,8,Color.MAGENTA));
                break;
                case 5 : super.setView(new Circle(8, 8 ,8,Color.ORANGE));    
            }
        }
    }
