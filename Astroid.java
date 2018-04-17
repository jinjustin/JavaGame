package astroid;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Astroid extends Application { //เขียน JavaFx ต้อง extends Application เสมอ

    private Pane root; //หน้าจอเกม
    
    private StackPane menuRoot; //หน้าจอ Menu ตอนเข้าเกม

    private List<GameObject> bullets = new ArrayList<>(); //List ของ GameObject ใช้เป็นกระสุน
    private List<GameObject> foods = new ArrayList<>(); //List ของ GameObject ใช้เป็นศัตรู
    private List<GameObject> viruses = new ArrayList<>();

    private int score=0,power=10,HP=10;
    private int radius=15;
    private int powerCount=0;
    
    private GameObject player;
//    private GameObject hpBar;
//    private GameObject powerBar;
    
    private Text scoreText = new Text("Score : "+ score); //Text ของคะแนน

    private Parent gameContent() { //ยังงงว่า Parent คืออะไร
        root = new Pane(); //กำหนดให้ root เป็น layout อันใหม่
        root.setPrefSize(1300, 1000);
        
        //BG
        ImageView iv = new ImageView("https://i.pinimg.com/originals/79/c7/b6/79c7b6a3c85ac3d99581216e35f3938e.png"); //
        iv.setFitHeight(root.getPrefHeight());
        iv.setFitWidth(root.getPrefWidth());
        root.getChildren().add(iv);
        
        player = new Player();
        
//        hpBar = new HPbar();
//        powerBar = new PowerBar();
        
        player.setVelocity(new Point2D(2, 0)); //ความเร็วเริ่มต้นพอกดเปลี่ยนทิศทางความเร็วเหลือ 1  
        addGameObject(player, 300, 300); //Method อยู่ในคลาสนี้

        
        scoreText.setFont(Font.font("Sans serif",FontWeight.NORMAL,FontPosture.REGULAR,50));
        scoreText.setFill(Color.RED);
        scoreText.setX(1030.0);
        scoreText.setY(940.0);
        root.getChildren().add(scoreText);
        
//        addGameObject(hpBar,20,900);
//        addGameObject(powerBar,20,940);
            
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };
        timer.start();
        return root;
    }
    
    private Parent gameMenu(){ //incompleted
        
        return menuRoot;
    }
    
    private int randomWithRange(int min, int max){ //สุ่มตัวเลข
   int range = (max - min) + 1;     
   return (int)(Math.random() * range) + min;
    }

    private void addBullet(GameObject bullet, double x, double y) {
        bullets.add(bullet); //เพิ่ม Object ลงใน ArrayList
        addGameObject(bullet, x, y);
    }

    private void addFood(GameObject enemy, double x, double y) {
        foods.add(enemy);
        addGameObject(enemy, x, y);
    }
    
    private void addTank(GameObject virus, double x, double y) {
        viruses.add(virus);
        addGameObject(virus, x, y);
    }

    private void addGameObject(GameObject object, double x, double y) {
        object.getView().setTranslateX(x);
        object.getView().setTranslateY(y);
        root.getChildren().add(object.getView()); // ไม่รู้ว่า children คืออะไร 
    }

    private void onUpdate() {
        
            for (GameObject food : foods) {
                if (player.isColliding(food)) {
                    score+=1;
                    System.out.println("HP : "+HP);
                    radius+=1;
                    double x= player.getView().getTranslateX(),y = player.getView().getTranslateY();
                    root.getChildren().removeAll(player.getView());
                    player.radiusUpdate(radius);
                    addGameObject(player,x,y);
                    food.setAlive(false);
                    System.out.println("Score : "+score);
                    root.getChildren().removeAll(food.getView());
                    GameObject.enemiesCount--;
                }
            }
        
            for (GameObject virus : viruses) { 
                if (player.isColliding(virus)) {
                    virus.setAlive(false);
                    if(HP<=5) HP+=5;
                    else HP=10;
                    System.out.println("HP : "+HP);
                    root.getChildren().removeAll(virus.getView());
                    GameObject.tanksCount--;
                }
            }

        bullets.removeIf(GameObject::isDead); //Method isDead อยู่ในคลาส GameObject
        foods.removeIf(GameObject::isDead);
        viruses.removeIf(GameObject::isDead);

        bullets.forEach(GameObject::update); //update อยู่ในคลาส GameObject
        foods.forEach(GameObject::update);
        viruses.forEach(GameObject::update);

       player.update();

        if (randomWithRange(0,100) < 2 && GameObject.enemiesCount<20) {
            Food food = new Food();
            int colorRand = randomWithRange(0,5) ;
            switch(colorRand)
            {
                case 1 : 
            }
            addFood(new Food(), Math.random() * root.getPrefWidth(), Math.random() * root.getPrefHeight());
            GameObject.enemiesCount++;
        }
        if (randomWithRange(0,3000) < 2 && GameObject.tanksCount<2) {
            addTank(new Virus(), Math.random() * root.getPrefWidth(), Math.random() * 855.0);
            GameObject.tanksCount++;
        }
        
        //คะแนน
        scoreText.setText("Score : " + score);
        
//        root.getChildren().removeAll(hpBar.getView());
//        hpBar.hpUpdate(HP);
//        addGameObject(hpBar,20,900);
//        
//        root.getChildren().removeAll(powerBar.getView());
//        powerBar.powerUpdate(power);
//        addGameObject(powerBar,20,940);
        
    }

    private static class Player extends GameObject {
        Player() {
            super(new Circle(15, 15 ,15,Color.BLUE));
        }
    }

    private static class Food extends GameObject {
        Food() {
            super(new Circle(5, 5, 5, Color.RED));
        }
    }
    
    private static class Virus extends GameObject {
        Virus() {
            super(new Rectangle(20, 40, Color.GREEN));
        }
    }
    
    private class HPbar extends GameObject {
        HPbar(){
            super(new Rectangle(100*HP,30, Color.RED));
        }
    }
    
    private class PowerBar extends GameObject {
        PowerBar(){
            super(new Rectangle(100*power,30, Color.YELLOW));
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        
        
        stage.setScene(new Scene(gameContent()));
        stage.getScene().setOnMouseMoved(e -> {
            double c = Math.sqrt(Math.pow((e.getX()-player.getView().getTranslateX()) , 2) + Math.pow(e.getY() - player.getView().getTranslateY(), 2) );
            player.setVelocity(new Point2D(3*(e.getX()-player.getView().getTranslateX())/c,3*(e.getY()-player.getView().getTranslateY())/c));
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
