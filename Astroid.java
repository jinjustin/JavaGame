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

public class Astroid extends Application { 

    private Pane root; 
    
    private Pane overRoot; 

    private List<GameObject> foods = new ArrayList<>(); 
    private List<GameObject> viruses = new ArrayList<>();
    private List<GameObject> enemies = new ArrayList<>();
    private List<Mutation> mutations = new ArrayList<>();

    private Score score;
    
    private GameObject player;
    
    private Text scoreText; 
    private Text gameOver = new Text("GameOver");
    
    private Difficulties difficulties = new Difficulties('E');
    
    private Parent gameContent() throws Exception { 
        root = new Pane(); 
        root.setPrefSize(1300, 1000);
        
        ReadFile r = new ReadFile();
        
        score = r.readFile();
        score.setZeroScore();
        scoreText = new Text();
        
        //BG
        ImageView iv = new ImageView("https://i.pinimg.com/originals/79/c7/b6/79c7b6a3c85ac3d99581216e35f3938e.png"); //
        iv.setFitHeight(root.getPrefHeight());
        iv.setFitWidth(root.getPrefWidth());
        root.getChildren().add(iv);
        
        player = new Player();
        
        switch(difficulties.getDifficult()){
            case 'E' :
                for(int i=0;i<2;i++){
                addEnemies(new Enemies(),Math.random() * root.getPrefWidth(), Math.random() * root.getPrefHeight());
                Enemies.enemiesCount++;
        }
                break;
            case 'N' :
                for(int i=0;i<3;i++){
                addEnemies(new Enemies(),Math.random() * root.getPrefWidth(), Math.random() * root.getPrefHeight());
                Enemies.enemiesCount++;
        }
                break;
            case 'H' :
                for(int i=0;i<4;i++){
                addEnemies(new Enemies(),Math.random() * root.getPrefWidth(), Math.random() * root.getPrefHeight());
                Enemies.enemiesCount++;
        }
                break;
        }
        
//        for(int i=0;i<3;i++){
//            addEnemies(new Enemies(),Math.random() * root.getPrefWidth(), Math.random() * root.getPrefHeight());
//            Enemies.enemiesCount++;
//        }
        
        for(int i=0;i<30;i++){
            addFood(new Food(), Math.random() * root.getPrefWidth(), Math.random() * root.getPrefHeight());
            Food.foodsCount++;
        }
        
        player.setVelocity(new Point2D(2, 0)); 
        addGameObject(player, 300, 300);     
        
        scoreText.setFont(Font.font("Sans serif",FontWeight.NORMAL,FontPosture.REGULAR,50));
        scoreText.setFill(Color.RED);
        scoreText.setX(1030.0);
        scoreText.setY(940.0);
        root.getChildren().add(scoreText);
        
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };
        timer.start();
        return root;
    }
    
   private Parent gameOverScene(){
        
        overRoot = new Pane(); 
        root.setPrefSize(1300, 1000);  
        
        gameOver.setFont(Font.font("Sans serif",FontWeight.NORMAL,FontPosture.REGULAR,100));
        gameOver.setFill(Color.RED);
        gameOver.setX(500.0);
        gameOver.setY(500.0);
        root.getChildren().add(gameOver);
            
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };
        timer.start();
        
        return overRoot;
    }
    
   public int randomWithRange(int min, int max){ 
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
        root.getChildren().add(object.getView()); 
    }
    
    private void addMutation(Mutation mutation, double x, double y) {
        mutations.add(mutation);
        mutation.getView().setTranslateX(x);
        mutation.getView().setTranslateY(y);
        root.getChildren().add(mutation.getView()); 
    }

    private void onUpdate() {
        
            for (GameObject food : foods) {
                if (player.isColliding(food)) {
                    score.updateScore();
                    double x= player.getView().getTranslateX(),y = player.getView().getTranslateY();
                    root.getChildren().removeAll(player.getView());
                    player.radiusUpdate();
                    addGameObject(player,x,y);
                    food.setAlive(false);
                    root.getChildren().removeAll(food.getView());
                    Food.foodsCount--;
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
                    Virus.virusesCount--;
                }
            }
            
            for(GameObject enemy : enemies){
                for(GameObject food : foods){
                    if (enemy.isColliding(food)) {
                    double x= enemy.getView().getTranslateX(),y = enemy.getView().getTranslateY();
                    root.getChildren().removeAll(enemy.getView());
                    enemy.radiusUpdate();
                    addGameObject(enemy,x,y);
                    food.setAlive(false);
                    root.getChildren().removeAll(food.getView());
                    Food.foodsCount--;
                    }
                }
                if(enemy.isColliding(player)){
                    if(enemy.getRadius() > player.getRadius() && player.isAlive()){
                    double x= enemy.getView().getTranslateX(),y = enemy.getView().getTranslateY();
                    root.getChildren().removeAll(enemy.getView());
                    enemy.setRadius(enemy.getRadius() + enemy.getRadius()/2);
                    enemy.radiusUpdate();
                    addGameObject(enemy,x,y);
                    player.setAlive(false);
                    root.getChildren().removeAll(player.getView());
                    WriteFile w = new WriteFile();
                    try{
                    score.setRanking();
                    w.writeFile(score);
                    score.setZeroScore();
                    for(int i=0;i<5;i++){
                        System.out.println(score.getScore()[i]);
                    }
                    }
                    catch(Exception e){
                        System.out.println("Error");
                    }
                    }
                    else if(player.getRadius() > enemy.getRadius()){
                        double x= player.getView().getTranslateX(),y = player.getView().getTranslateY();
                        root.getChildren().removeAll(player.getView());
                        player.setRadius(player.getRadius() + enemy.getRadius()/2);
                        player.radiusUpdate();
                        addGameObject(player,x,y);
                        enemy.setAlive(false);
                        root.getChildren().removeAll(enemy.getView());
                        score.setCurrentScore(score.getCurrentScore()+10);
                    }
                }
                for(GameObject anotherEnemy : enemies){
                    if(enemy.isColliding(anotherEnemy)){
                        if(enemy.getRadius() > anotherEnemy.getRadius()){
                        double x= enemy.getView().getTranslateX(),y = enemy.getView().getTranslateY();
                        root.getChildren().removeAll(enemy.getView());
                        enemy.setRadius(enemy.getRadius() + anotherEnemy.getRadius()/2);
                        enemy.radiusUpdate();
                        addGameObject(enemy,x,y);
                        anotherEnemy.setAlive(false);
                        root.getChildren().removeAll(anotherEnemy.getView());
                        Enemies.enemiesCount--;
                        }
                    }
                }
                for(GameObject virus : viruses){
                    if (enemy.isColliding(virus)) {
                    double x= enemy.getView().getTranslateX(),y = enemy.getView().getTranslateY();
                    root.getChildren().removeAll(enemy.getView());
                    enemy.setRadius(enemy.getRadius()/2);
                    enemy.radiusUpdate();
                    addGameObject(enemy,x,y);
                    virus.setAlive(false);
                    root.getChildren().removeAll(virus.getView());
                    Virus.virusesCount--;
                    }
                }
                for(Mutation mutation : mutations){
                    if(enemy.isColliding(mutation)){
                        if(enemy.getRadius() > mutation.getRadius()){
                        double x= enemy.getView().getTranslateX(),y = enemy.getView().getTranslateY();
                        root.getChildren().removeAll(enemy.getView());
                        enemy.setRadius(enemy.getRadius() + mutation.getRadius()/2);
                        enemy.radiusUpdate();
                        addGameObject(enemy,x,y);
                        mutation.setAlive(false);
                        root.getChildren().removeAll(mutation.getView());
                        Enemies.enemiesCount--;
                        }
                    }
                }
            }
            
            for(Mutation mutation : mutations){
            mutation.velocityUpdate();
            mutation.passTimeUpdate();
                for(GameObject food : foods){
                    if (mutation.isColliding(food)) {
                    double x= mutation.getView().getTranslateX(),y = mutation.getView().getTranslateY();
                    root.getChildren().removeAll(mutation.getView());
                    mutation.radiusUpdate();
                    addGameObject(mutation,x,y);
                    food.setAlive(false);
                    root.getChildren().removeAll(food.getView());
                    Food.foodsCount--;
                    score.updateScore();
                    }
                }
                if(mutation.isColliding(player)&& mutation.getPassTime()>Mutation.daley){
                        double x= player.getView().getTranslateX(),y = player.getView().getTranslateY();
                        root.getChildren().removeAll(player.getView());
                        player.setRadius(player.getRadius() + mutation.getRadius()/2);
                        player.radiusUpdate();
                        addGameObject(player,x,y);
                        mutation.setAlive(false);
                        root.getChildren().removeAll(mutation.getView());
                }
                for(GameObject enemy : enemies){
                    if(mutation.isColliding(enemy)){
                        if(mutation.getRadius() > enemy.getRadius()){
                        double x= mutation.getView().getTranslateX(),y = mutation.getView().getTranslateY();
                        root.getChildren().removeAll(mutation.getView());
                        mutation.setRadius(mutation.getRadius() + enemy.getRadius()/2);
                        mutation.radiusUpdate();
                        addGameObject(mutation,x,y);
                        enemy.setAlive(false);
                        root.getChildren().removeAll(enemy.getView());
                        Enemies.enemiesCount--;
                        }
                    }
                }
                for(GameObject virus : viruses){
                    if (mutation.isColliding(virus)) {
                    double x= mutation.getView().getTranslateX(),y = mutation.getView().getTranslateY();
                    root.getChildren().removeAll(mutation.getView());
                    mutation.setRadius(mutation.getRadius()/2);
                    mutation.radiusUpdate();
                    addGameObject(mutation,x,y);
                    virus.setAlive(false);
                    root.getChildren().removeAll(virus.getView());
                    Virus.virusesCount--;
                    }
                }
    }
            
            foods.removeIf(GameObject::isDead);
            
            for(GameObject enemy : enemies){
                double c=2000,x=1,y=0;
                if(enemy.radiusCompare(player.getRadius()) ){
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
                switch(difficulties.getDifficult()){
                    case 'E' : enemy.setVelocity(new Point2D(1.5*x,1.5*y));
                    case 'N' : enemy.setVelocity(new Point2D(2*x,2*y));
                    case 'H' : enemy.setVelocity(new Point2D(3*x,3*y));
                }
            }
            
        foods.removeIf(GameObject::isDead);
        viruses.removeIf(GameObject::isDead);
        enemies.removeIf(GameObject::isDead);
        mutations.removeIf(GameObject::isDead);

        if(player.isAlive()){
        foods.forEach(GameObject::update);
        viruses.forEach(GameObject::update);
        enemies.forEach(GameObject::update);
        mutations.forEach(GameObject::update);
       
       player.update();
        
        Food.timeUpdate();
        if (Food.getTime()>=Food.spawnTime && Food.foodsCount<40) {
            addFood(new Food(), Math.random() * root.getPrefWidth(), Math.random() * root.getPrefHeight());
            Food.foodsCount++;
            Food.setTime(0);
        }
        
        Virus.passTimeUpdate();
        if (Virus.getPassTime() >= Virus.spawnTime && Virus.virusesCount<3) {
            addVirus(new Virus(), Math.random() * root.getPrefWidth(), Math.random() * 855.0);
            Virus.virusesCount++;
            Virus.passTime=0;
        }
            }
        
        //คะแนน
        scoreText.setText("Score : " + score.getCurrentScore());

    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(gameContent()));
        stage.getScene().setOnMouseMoved(e -> {
            double c = Math.sqrt(Math.pow((e.getX()-player.getView().getTranslateX()) , 2) + Math.pow(e.getY() - player.getView().getTranslateY(), 2) );
            player.setVelocity(new Point2D(3*(e.getX()-player.getView().getTranslateX())/c,3*(e.getY()-player.getView().getTranslateY())/c));
        });
        stage.getScene().setOnMouseClicked(e -> {
            score.updateScore();
                    double x= player.getView().getTranslateX(),y = player.getView().getTranslateY();
                    root.getChildren().removeAll(player.getView());
                    player.setRadius(player.getRadius()/2);
                    player.radiusUpdate();
                    addGameObject(player,x,y);
                    Mutation m = new Mutation(player.getRadius()/2);
                    m.radiusUpdate();
                    addMutation(m,player.getView().getTranslateX(),player.getView().getTranslateY());
                    m.setVelocity(new Point2D(1.5*player.getVelocity().getX(),1.5*player.getVelocity().getY()));
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
