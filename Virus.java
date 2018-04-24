package astroid;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class Virus extends GameObject {
    
    public static int virusesCount = 0;
    public static int passTime=0;
    public static final int spawnTime = 1200;
    public static final int expireTime = 3600;
    private int time=0;
    
        Virus() {
            super(new Rectangle(40, 40, Color.GREEN));
        }

    public static int getVirusesCount() {
        return virusesCount;
    }

    public static void setVirusesCount(int virusesCount) {
        Virus.virusesCount = virusesCount;
    }

    public static int getPassTime() {
        return passTime;
    }

    public static void setPassTime(int time) {
        Virus.passTime = passTime;
    }
    
    public static void passTimeUpdate(){
        passTime++;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
   
    public void timeUpdate(){
        time++;
    }
    
    }
