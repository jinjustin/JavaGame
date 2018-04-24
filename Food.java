package astroid;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

class Food extends GameObject {
    
    public static int foodsCount = 0; 
    public static int time = 0;
    public static final int spawnTime = 20;
    
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

    public static int getFoodsCount() {
        return foodsCount;
    }

    public static void setFoodsCount(int foodsCount) {
        Food.foodsCount = foodsCount;
    }

    public static int getTime() {
        return time;
    }

    public static void setTime(int time) {
        Food.time = time;
    }
    
    public static void timeUpdate(){
        time++;
    }
        
    }
