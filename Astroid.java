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
    private List<GameObject> enemies = new ArrayList<>();

    private int score=0;
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
        
        for(int i=0;i<3;i++)
            addEnemies(new Enemies(),Math.random() * root.getPrefWidth(), Math.random() * root.getPrefHeight());
        
        for(int i=0;i<30;i++){
            addFood(new Food(), Math.random() * root.getPrefWidth(), Math.random() * root.getPrefHeight());
            GameObject.foodsCount++;
        }
        
        
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


    private void addFood(GameObject enemy, double x, double y) {
        foods.add(enemy);
        addGameObject(enemy, x, y);
    }
    
    private void addVirus(GameObject virus, double x, double y) {
        viruses.add(virus);
        addGameObject(virus, x, y);
    }
    
    private void addEnemies(GameObject enemie, double x, double y){
        enemies.add(enemie);
        addGameObject(enemie,x,y);
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
                    double x= player.getView().getTranslateX(),y = player.getView().getTranslateY();
                    root.getChildren().removeAll(player.getView());
                    player.radiusUpdate();
                    addGameObject(player,x,y);
                    food.setAlive(false);
                    root.getChildren().removeAll(food.getView());
                    GameObject.foodsCount--;
                }
            }
        
            for (GameObject virus : viruses) { 
                if (player.isColliding(virus)) {
                    double x= player.getView().getTranslateX(),y = player.getView().getTranslateY();
                    root.getChildren().removeAll(player.getView());
                    player.setRadius(player.getRadius()/2);
                    player.radiusUpdate();
                    addGameObject(player,x,y);
                    virus.setAlive(false);
                    root.getChildren().removeAll(virus.getView());
                    GameObject.virusesCount--;
                }
            }
            
            for(GameObject enemy : enemies){
                for(GameObject food : foods){
                    if (enemy.isColliding(food)) {
                    double x= enemy.getView().getTranslateX(),y = enemy.getView().getTranslateY();
                    root.getChildren().removeAll(enemy.getView());
                    enemy.enemyRadiusUpdate();
                    addGameObject(enemy,x,y);
                    food.setAlive(false);
                    root.getChildren().removeAll(food.getView());
                    GameObject.foodsCount--;
                    }
                }
                if(enemy.isColliding(player)){
                    if(enemy.getRadius() > player.getRadius() && player.isAlive()){
                    double x= enemy.getView().getTranslateX(),y = enemy.getView().getTranslateY();
                    root.getChildren().removeAll(enemy.getView());
                    enemy.setRadius(enemy.getRadius() + enemy.getRadius()/2);
                    enemy.enemyRadiusUpdate();
                    addGameObject(enemy,x,y);
                    player.setAlive(false);
                    root.getChildren().removeAll(player.getView());
                    }
                    else if(player.getRadius() > enemy.getRadius()){
                        double x= player.getView().getTranslateX(),y = player.getView().getTranslateY();
                        root.getChildren().removeAll(player.getView());
                        player.setRadius(player.getRadius() + enemy.getRadius()/2);
                        player.radiusUpdate();
                        addGameObject(player,x,y);
                        enemy.setAlive(false);
                        root.getChildren().removeAll(enemy.getView());
                    }
                }
                for(GameObject anotherEnemy : enemies){
                    if(enemy.isColliding(anotherEnemy)){
                        if(enemy.getRadius() > anotherEnemy.getRadius()){
                        double x= enemy.getView().getTranslateX(),y = enemy.getView().getTranslateY();
                        root.getChildren().removeAll(enemy.getView());
                        enemy.setRadius(enemy.getRadius() + anotherEnemy.getRadius()/2);
                        enemy.enemyRadiusUpdate();
                        addGameObject(enemy,x,y);
                        anotherEnemy.setAlive(false);
                        root.getChildren().removeAll(anotherEnemy.getView());
                        }
                    }
                }
                for(GameObject virus : viruses){
                    if (enemy.isColliding(virus)) {
                    double x= enemy.getView().getTranslateX(),y = enemy.getView().getTranslateY();
                    root.getChildren().removeAll(enemy.getView());
                    enemy.setRadius(enemy.getRadius()/2);
                    enemy.enemyRadiusUpdate();
                    addGameObject(enemy,x,y);
                    virus.setAlive(false);
                    root.getChildren().removeAll(virus.getView());
                    GameObject.virusesCount--;
                    }
                }
            }
            
            foods.removeIf(GameObject::isDead);
            
            for(GameObject enemy : enemies){
                double c=2000,x=1,y=0;
                if(enemy.radiusCompare(player.getRadius())){
                c = Math.sqrt(Math.pow((player.getView().getTranslateX()-enemy.getView().getTranslateX()) , 2) + Math.pow(player.getView().getTranslateY() - enemy.getView().getTranslateY(), 2) );
                x=(player.getView().getTranslateX() - enemy.getView().getTranslateX())/c;
                y=(player.getView().getTranslateY() - enemy.getView().getTranslateY())/c;
                }
                else{
                for(GameObject food : foods){
                    double temp = Math.sqrt(Math.pow((food.getView().getTranslateX()-enemy.getView().getTranslateX()) , 2) + Math.pow(food.getView().getTranslateY() - enemy.getView().getTranslateY(), 2) );
                    if(Math.abs(temp) <= Math.abs(c)){
                        c = temp;
                        x = (food.getView().getTranslateX()-enemy.getView().getTranslateX())/c;
                        y = (food.getView().getTranslateY() - enemy.getView().getTranslateY())/c;
                        }
                    }
                }
                enemy.setVelocity(new Point2D(3*x,3*y));
            }

        foods.removeIf(GameObject::isDead);
        viruses.removeIf(GameObject::isDead);
        enemies.removeIf(GameObject::isDead);

        if(player.isAlive()){
        foods.forEach(GameObject::update);
        viruses.forEach(GameObject::update);
        enemies.forEach(GameObject::update);

       
       player.update();
        

        if (randomWithRange(0,100) < 4 && GameObject.foodsCount<40) {
            addFood(new Food(), Math.random() * root.getPrefWidth(), Math.random() * root.getPrefHeight());
            GameObject.foodsCount++;
        }
        if (randomWithRange(0,3000) < 3 && GameObject.virusesCount<3) {
            addVirus(new Virus(), Math.random() * root.getPrefWidth(), Math.random() * 855.0);
            GameObject.virusesCount++;
        }
        
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

    private class Food extends GameObject {
        Food() {
            super();
            int colorRand = randomWithRange(0, 5);
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
    
    private static class Virus extends GameObject {
        Virus() {
            super(new Rectangle(40, 40, Color.GREEN));
        }
    }
    
    private static class Enemies extends GameObject {
        Enemies(){
            super(new Circle(15, 15 ,15,Color.BLACK));
        }
    }
    
//    private class HPbar extends GameObject {
//        HPbar(){
//            super(new Rectangle(100*HP,30, Color.RED));
//        }
//    }
//    
//    private class PowerBar extends GameObject {
//        PowerBar(){
//            super(new Rectangle(100*power,30, Color.YELLOW));
//        }
//    }

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
