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

import java.io.IOException;
import java.io.InputStream;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.application.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.text.*;
import javafx.scene.paint.CycleMethod;

public class Astroid extends Application {

    private Pane root;
    private Stage window;
    private Scene scenemenu, sceneplay, scenehighscore, sceneDifficult;
    Media music = new Media(astroid.Astroid.class.getResource("menu.mp3").toExternalForm());
    MediaPlayer song = new MediaPlayer(music);
        

    private AnimationTimer timer;

    private Pane overRoot;

    private List<GameObject> foods = new ArrayList<>();
    private List<GameObject> viruses = new ArrayList<>();
    private List<GameObject> enemies = new ArrayList<>();
    private List<Mutation> mutations = new ArrayList<>();

    private Score score;

    private GameObject player;

    private Text scoreText;
    private Text gameOver = new Text("GameOver");
    
   

    private Difficulties difficulties;

    private Parent createContent() {
        Pane root = new Pane();
        

        root.setPrefSize(1280, 720);
        
        
        song.play();
        ImageView img = new ImageView(new Image(astroid.Astroid.class.getResource("background.png").toExternalForm()));
        img.setFitWidth(1280);
        img.setFitHeight(720);
        root.getChildren().add(img);

        Title title = new Title("Asteroid.io");
        title.setTranslateX(450);
        title.setTranslateY(200);

        MenuBox vbox = new MenuBox(
                new MenuItem("Play"),
                new MenuItem("Highscore"),
                new MenuItem("Exit")
        );
        vbox.setTranslateX(525);
        vbox.setTranslateY(350);

        root.getChildren().addAll(title, vbox);

        return root;

    }

    private Parent highScoreContent() throws Exception {
        Pane root = new Pane();

        root.setPrefSize(1280, 720);

        ImageView img = new ImageView(new Image(astroid.Astroid.class.getResource("background.png").toExternalForm()));

        img.setFitWidth(1280);
        img.setFitHeight(720);
        root.getChildren().add(img);

        Title title = new Title("HighScore");
        title.setTranslateX(430);
        title.setTranslateY(20);

        MenuBox vbox = new MenuBox(
                new MenuItem("Back")
        );
        vbox.setTranslateX(1050);
        vbox.setTranslateY(600);

        ReadFile r = new ReadFile();
        score = r.readFile();

        int[] display = new int[5];
        display = score.getScore();

        Text score1 = new Text();
        score1.setText("1st" + "       " + display[0]);
        Text score2 = new Text();
        score2.setText("2nd" + "       " + display[1]);
        Text score3 = new Text();
        score3.setText("3rd" + "       " + display[2]);
        Text score4 = new Text();
        score4.setText("4th" + "       " + display[3]);
        Text score5 = new Text();
        score5.setText("5th" + "       " + display[4]);

        score1.setFont(Font.font("Sans serif", FontWeight.NORMAL, FontPosture.REGULAR, 30));
        score1.setFill(Color.RED);
        score1.setX(550);
        score1.setY(180);

        score2.setFont(Font.font("Sans serif", FontWeight.NORMAL, FontPosture.REGULAR, 30));
        score2.setFill(Color.RED);
        score2.setX(550);
        score2.setY(280);

        score3.setFont(Font.font("Sans serif", FontWeight.NORMAL, FontPosture.REGULAR, 30));
        score3.setFill(Color.RED);
        score3.setX(550);
        score3.setY(380);

        score4.setFont(Font.font("Sans serif", FontWeight.NORMAL, FontPosture.REGULAR, 30));
        score4.setFill(Color.RED);
        score4.setX(550);
        score4.setY(480);

        score5.setFont(Font.font("Sans serif", FontWeight.NORMAL, FontPosture.REGULAR, 30));
        score5.setFill(Color.RED);
        score5.setX(550);
        score5.setY(580);

        root.getChildren().addAll(score1, score2, score3, score4, score5);

        root.getChildren().addAll(title, vbox);

        return root;
    }

    private Parent selectDifficultContent() {
        Pane root = new Pane();
        

        root.setPrefSize(1280, 720);

        Title title = new Title("Select Level");
        title.setTranslateX(430);
        title.setTranslateY(220);

        ImageView img = new ImageView(new Image(astroid.Astroid.class.getResource("background.png").toExternalForm()));
        img.setFitWidth(1280);
        img.setFitHeight(720);
        root.getChildren().add(img);

        MenuBox vbox = new MenuBox(
                new MenuItem("Easy"),
                new MenuItem("Normal"),
                new MenuItem("Hard")
        );
        vbox.setTranslateX(525);
        vbox.setTranslateY(350);

        root.getChildren().addAll(vbox, title);

        return root;

    }

    private static class Title extends StackPane {

        public Title(String name) {
            Rectangle bg = new Rectangle(400, 80);
            bg.setStroke(Color.WHITE);
            bg.setStrokeWidth(2);
            bg.setFill(null);

            Text text = new Text(name);
            text.setFill(Color.CADETBLUE);
            text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 80));

            setAlignment(Pos.CENTER);
            getChildren().addAll(bg, text);
        }
    }

    private static class MenuBox extends VBox {

        public MenuBox(MenuItem... items) {

            getChildren().add(createSeperator());

            for (MenuItem item : items) {
                getChildren().addAll(item, createSeperator());

            }

        }

        private Line createSeperator() {
            Line sep = new Line();
            sep.setEndX(210);
            sep.setStroke(Color.DARKGREY);
            return sep;
        }

    }

    private class MenuItem extends StackPane {

        public MenuItem(String name) {
            LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop[]{
                new Stop(0, Color.RED),
                new Stop(0.1, Color.BLACK),
                new Stop(0.9, Color.BLACK),
                new Stop(1, Color.RED)

            });

            Rectangle bg = new Rectangle(200, 35);
            bg.setOpacity(0.4);

            Text text = new Text(name);
            text.setFill(Color.DARKGREY);
            text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 25));

            setAlignment(Pos.CENTER);
            getChildren().addAll(bg, text);
            setOnMouseEntered(event -> {
                bg.setFill(gradient);
                text.setFill(Color.WHITE);

            });

            setOnMouseExited(event -> {
                bg.setFill(Color.BLACK);
                text.setFill(Color.DARKGREY);
            });

            setOnMousePressed(event -> {
                bg.setFill(Color.GREEN);

            });

            setOnMouseReleased(event -> {
                bg.setFill(gradient);
                if (name.equals("Play")) {
                    try {
                        sceneDifficult = new Scene(selectDifficultContent());
                    } catch (Exception e) {
                        System.out.println("Error");
                    }
                    window.setScene(sceneDifficult);
                } else if (name.equals("Exit")) {
                    System.exit(0);
                } else if (name.equals("Highscore")) {
                    try {
                        scenehighscore = new Scene(highScoreContent());
                    } catch (Exception e) {
                        System.out.println("Error");
                    }
                    window.setScene(scenehighscore);
                } else if (name.equals("Back")) {
                    try {
                        scenemenu = new Scene(createContent());
                    } catch (Exception e) {
                        System.out.println("Error");
                    }
                    window.setScene(scenemenu);
                } else if (name.equals("Easy")) {
                    difficulties = new Difficulties('E');
                    song.stop();
                    try {
                        sceneplay = new Scene(gameContent());
                        
                    } catch (Exception e) {
                        System.out.println("Error");
                    }
                    window.setScene(sceneplay);
                    window.getScene().setOnMouseMoved(e -> {
                        double c = Math.sqrt(Math.pow((e.getX() - player.getView().getTranslateX()), 2) + Math.pow(e.getY() - player.getView().getTranslateY(), 2));
                        player.setVelocity(new Point2D(3 * (e.getX() - player.getView().getTranslateX()) / c, 3 * (e.getY() - player.getView().getTranslateY()) / c));
                    });
                    window.getScene().setOnMouseClicked(e -> {
                        score.updateScore();
                        double x = player.getView().getTranslateX(), y = player.getView().getTranslateY();
                        root.getChildren().removeAll(player.getView());
                        player.setRadius(player.getRadius() / 2);
                        player.radiusUpdate();
                        addGameObject(player, x, y);
                        Mutation m = new Mutation(player.getRadius() / 2);
                        m.radiusUpdate();
                        addMutation(m, player.getView().getTranslateX(), player.getView().getTranslateY());
                        m.setVelocity(new Point2D(1.5 * player.getVelocity().getX(), 1.5 * player.getVelocity().getY()));
                    });
                    player.setAlive(true);
                } else if (name.equals("Normal")) {
                    difficulties = new Difficulties('N');
                    song.stop();
                    try {
                        sceneplay = new Scene(gameContent());
                    } catch (Exception e) {
                        System.out.println("Error");
                    }
                    window.setScene(sceneplay);
                    window.getScene().setOnMouseMoved(e -> {
                        double c = Math.sqrt(Math.pow((e.getX() - player.getView().getTranslateX()), 2) + Math.pow(e.getY() - player.getView().getTranslateY(), 2));
                        player.setVelocity(new Point2D(3 * (e.getX() - player.getView().getTranslateX()) / c, 3 * (e.getY() - player.getView().getTranslateY()) / c));
                    });
                    window.getScene().setOnMouseClicked(e -> {
                        score.updateScore();
                        double x = player.getView().getTranslateX(), y = player.getView().getTranslateY();
                        root.getChildren().removeAll(player.getView());
                        player.setRadius(player.getRadius() / 2);
                        player.radiusUpdate();
                        addGameObject(player, x, y);
                        Mutation m = new Mutation(player.getRadius() / 2);
                        m.radiusUpdate();
                        addMutation(m, player.getView().getTranslateX(), player.getView().getTranslateY());
                        m.setVelocity(new Point2D(1.5 * player.getVelocity().getX(), 1.5 * player.getVelocity().getY()));
                    });
                    player.setAlive(true);
                } else if (name.equals("Hard")) {
                    difficulties = new Difficulties('H');
                    song.stop();
                    try {
                        sceneplay = new Scene(gameContent());
                    } catch (Exception e) {
                        System.out.println("Error");
                    }
                    window.setScene(sceneplay);
                    window.getScene().setOnMouseMoved(e -> {
                        double c = Math.sqrt(Math.pow((e.getX() - player.getView().getTranslateX()), 2) + Math.pow(e.getY() - player.getView().getTranslateY(), 2));
                        player.setVelocity(new Point2D(3 * (e.getX() - player.getView().getTranslateX()) / c, 3 * (e.getY() - player.getView().getTranslateY()) / c));
                    });
                    window.getScene().setOnMouseClicked(e -> {
                        score.updateScore();
                        double x = player.getView().getTranslateX(), y = player.getView().getTranslateY();
                        root.getChildren().removeAll(player.getView());
                        player.setRadius(player.getRadius() / 2);
                        player.radiusUpdate();
                        addGameObject(player, x, y);
                        Mutation m = new Mutation(player.getRadius() / 2);
                        m.radiusUpdate();
                        addMutation(m, player.getView().getTranslateX(), player.getView().getTranslateY());
                        m.setVelocity(new Point2D(1.5 * player.getVelocity().getX(), 1.5 * player.getVelocity().getY()));
                    });
                    player.setAlive(true);
                }
            });
        }
    }

    private Parent gameContent() throws Exception {
        root = new Pane();
        root.setPrefSize(1300, 1000);
        Media music = new Media(astroid.Astroid.class.getResource("soundbg.wav").toExternalForm());
        MediaPlayer song = new MediaPlayer(music);
        
        song.play();
        

        ReadFile r = new ReadFile();

        score = r.readFile();
        score.setZeroScore();
        scoreText = new Text();

        ImageView iv = new ImageView(new Image(astroid.Astroid.class.getResource("bggame.jpg").toExternalForm()));
        iv.setFitHeight(root.getPrefHeight());
        iv.setFitWidth(root.getPrefWidth());
        root.getChildren().add(iv);

        player = new Player();

        switch (difficulties.getDifficult()) {
            case 'E':
                for (int i = 0; i < 2; i++) {
                    addEnemies(new Enemies(), Math.random() * root.getPrefWidth(), Math.random() * root.getPrefHeight());
                    Enemies.enemiesCount++;
                }
                break;
            case 'N':
                for (int i = 0; i < 3; i++) {
                    addEnemies(new Enemies(), Math.random() * root.getPrefWidth(), Math.random() * root.getPrefHeight());
                    Enemies.enemiesCount++;
                }
                break;
            case 'H':
                for (int i = 0; i < 4; i++) {
                    addEnemies(new Enemies(), Math.random() * root.getPrefWidth(), Math.random() * root.getPrefHeight());
                    Enemies.enemiesCount++;
                }
                break;
        }

        for (int i = 0; i < 30; i++) {
            addFood(new Food(), Math.random() * root.getPrefWidth(), Math.random() * root.getPrefHeight());
            Food.foodsCount++;
        }

        //player.setVelocity(new Point2D(2, 0)); 
        addGameObject(player, 300, 300);

        scoreText.setFont(Font.font("Sans serif", FontWeight.NORMAL, FontPosture.REGULAR, 50));
        scoreText.setFill(Color.RED);
        scoreText.setX(1030);
        scoreText.setY(940);
        root.getChildren().add(scoreText);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
                if(player.isDead()){
            song.stop();
        }
            }
        };
        
        timer.start();
        return root;
    }

    public int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }

    private void addFood(GameObject enemy, double x, double y) {
        foods.add(enemy);
        addGameObject(enemy, x, y);
    }

    private void addVirus(GameObject virus, double x, double y) {
        viruses.add(virus);
        addGameObject(virus, x, y);
    }

    private void addEnemies(GameObject enemie, double x, double y) {
        enemies.add(enemie);
        addGameObject(enemie, x, y);
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
        
        
        Media effect = new Media(astroid.Astroid.class.getResource("Pop.wav").toExternalForm());
        MediaPlayer eat = new MediaPlayer(effect);
        

        for (GameObject food : foods) {
            if (player.isColliding(food)) {
                score.updateScore();
                double x = player.getView().getTranslateX(), y = player.getView().getTranslateY();
                root.getChildren().removeAll(player.getView());
                player.radiusUpdate();
                addGameObject(player, x, y);
                food.setAlive(false);
                root.getChildren().removeAll(food.getView());
                Food.foodsCount--;
                eat.play();
                
            }
        }

        for (GameObject virus : viruses) {
            if (player.isColliding(virus)) {
                double x = player.getView().getTranslateX(), y = player.getView().getTranslateY();
                root.getChildren().removeAll(player.getView());
                player.setRadius(player.getRadius() / 2);
                player.radiusUpdate();
                addGameObject(player, x, y);
                virus.setAlive(false);
                root.getChildren().removeAll(virus.getView());
                Virus.virusesCount--;
                eat.play();
            }
        }

        for (GameObject enemy : enemies) {
            for (GameObject food : foods) {
                if (enemy.isColliding(food)) {
                    double x = enemy.getView().getTranslateX(), y = enemy.getView().getTranslateY();
                    root.getChildren().removeAll(enemy.getView());
                    enemy.radiusUpdate();
                    addGameObject(enemy, x, y);
                    food.setAlive(false);
                    root.getChildren().removeAll(food.getView());
                    Food.foodsCount--;
                    eat.play();
                }
            }
            if (enemy.isColliding(player)) {
                if (enemy.getRadius() > player.getRadius() && player.isAlive()) {
                    double x = enemy.getView().getTranslateX(), y = enemy.getView().getTranslateY();
                    root.getChildren().removeAll(enemy.getView());
                    enemy.setRadius(enemy.getRadius() + enemy.getRadius() / 2);
                    enemy.radiusUpdate();
                    addGameObject(enemy, x, y);
                    player.setAlive(false);
                    root.getChildren().removeAll(player.getView());
                    
                    eat.play();
                } else if (player.getRadius() > enemy.getRadius()) {
                    double x = player.getView().getTranslateX(), y = player.getView().getTranslateY();
                    root.getChildren().removeAll(player.getView());
                    player.setRadius(player.getRadius() + enemy.getRadius() / 2);
                    player.radiusUpdate();
                    addGameObject(player, x, y);
                    enemy.setAlive(false);
                    root.getChildren().removeAll(enemy.getView());
                    score.setCurrentScore(score.getCurrentScore() + 10);
                    Enemies.enemiesCount--;
                    eat.play();
                }
            }
            for (GameObject anotherEnemy : enemies) {
                if (enemy.isColliding(anotherEnemy)) {
                    if (enemy.getRadius() > anotherEnemy.getRadius()) {
                        double x = enemy.getView().getTranslateX(), y = enemy.getView().getTranslateY();
                        root.getChildren().removeAll(enemy.getView());
                        enemy.setRadius(enemy.getRadius() + anotherEnemy.getRadius() / 2);
                        enemy.radiusUpdate();
                        addGameObject(enemy, x, y);
                        anotherEnemy.setAlive(false);
                        root.getChildren().removeAll(anotherEnemy.getView());
                        Enemies.enemiesCount--;
                        eat.play();
                    }
                }
            }
            for (GameObject virus : viruses) {
                if (enemy.isColliding(virus)) {
                    double x = enemy.getView().getTranslateX(), y = enemy.getView().getTranslateY();
                    root.getChildren().removeAll(enemy.getView());
                    enemy.setRadius(enemy.getRadius() / 2);
                    enemy.radiusUpdate();
                    addGameObject(enemy, x, y);
                    virus.setAlive(false);
                    root.getChildren().removeAll(virus.getView());
                    Virus.virusesCount--;
                    eat.play();
                }
            }
            for (Mutation mutation : mutations) {
                if (enemy.isColliding(mutation)) {
                    if (enemy.getRadius() > mutation.getRadius()) {
                        double x = enemy.getView().getTranslateX(), y = enemy.getView().getTranslateY();
                        root.getChildren().removeAll(enemy.getView());
                        enemy.setRadius(enemy.getRadius() + mutation.getRadius() / 2);
                        enemy.radiusUpdate();
                        addGameObject(enemy, x, y);
                        mutation.setAlive(false);
                        root.getChildren().removeAll(mutation.getView());
                        eat.play();
                    }
                }
            }
        }

        for (Mutation mutation : mutations) {
            mutation.velocityUpdate();
            mutation.passTimeUpdate();
            for (GameObject food : foods) {
                if (mutation.isColliding(food)) {
                    double x = mutation.getView().getTranslateX(), y = mutation.getView().getTranslateY();
                    root.getChildren().removeAll(mutation.getView());
                    mutation.radiusUpdate();
                    addGameObject(mutation, x, y);
                    food.setAlive(false);
                    root.getChildren().removeAll(food.getView());
                    Food.foodsCount--;
                    score.updateScore();
                    eat.play();
                }
            }
            if (mutation.isColliding(player) && mutation.getPassTime() > Mutation.daley) {
                double x = player.getView().getTranslateX(), y = player.getView().getTranslateY();
                root.getChildren().removeAll(player.getView());
                player.setRadius(player.getRadius() + mutation.getRadius());
                player.radiusUpdate();
                addGameObject(player, x, y);
                mutation.setAlive(false);
                root.getChildren().removeAll(mutation.getView());
                eat.play();
            }
            for (GameObject enemy : enemies) {
                if (mutation.isColliding(enemy)) {
                    if (mutation.getRadius() > enemy.getRadius()) {
                        double x = mutation.getView().getTranslateX(), y = mutation.getView().getTranslateY();
                        root.getChildren().removeAll(mutation.getView());
                        mutation.setRadius(mutation.getRadius() + enemy.getRadius() / 2);
                        mutation.radiusUpdate();
                        addGameObject(mutation, x, y);
                        enemy.setAlive(false);
                        root.getChildren().removeAll(enemy.getView());
                        Enemies.enemiesCount--;
                        eat.play();
                    }
                }
            }
            for (GameObject virus : viruses) {
                if (mutation.isColliding(virus)) {
                    double x = mutation.getView().getTranslateX(), y = mutation.getView().getTranslateY();
                    root.getChildren().removeAll(mutation.getView());
                    mutation.setRadius(mutation.getRadius() / 2);
                    mutation.radiusUpdate();
                    addGameObject(mutation, x, y);
                    virus.setAlive(false);
                    root.getChildren().removeAll(virus.getView());
                    Virus.virusesCount--;
                    eat.play();
                }
            }
        }
        
        if(Enemies.enemiesCount == 0){
            player.setAlive(false);
        }

        //เช็คตาย
        if (player.isDead()) {
            timer.stop();
            window.setScene(scenemenu);
            for (GameObject food : foods) {
                food.setAlive(false);
                root.getChildren().removeAll(food.getView());
                Food.foodsCount--;
            }
            for (GameObject enemy : enemies) {
                enemy.setAlive(false);
                root.getChildren().removeAll(enemy.getView());
                Enemies.enemiesCount--;
            }
            for (GameObject virus : viruses) {
                virus.setAlive(false);
                root.getChildren().removeAll(virus.getView());
                Virus.virusesCount--;
            }
            for (GameObject mutation : mutations) {
                mutation.setAlive(false);
                root.getChildren().removeAll(mutation.getView());
            }
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
            score.setZeroScore();
        }

        foods.removeIf(GameObject::isDead);

        for (GameObject enemy : enemies) {
            double c = 2000, x = 1, y = 0;
            if (enemy.radiusCompare(player.getRadius())) {
                c = Math.sqrt(Math.pow((player.getView().getTranslateX() - enemy.getView().getTranslateX()), 2) + Math.pow(player.getView().getTranslateY() - enemy.getView().getTranslateY(), 2));
                x = (player.getView().getTranslateX() - enemy.getView().getTranslateX()) / c;
                y = (player.getView().getTranslateY() - enemy.getView().getTranslateY()) / c;
            } else {
                for (GameObject food : foods) {
                    double temp = Math.sqrt(Math.pow((food.getView().getTranslateX() - enemy.getView().getTranslateX()), 2) + Math.pow(food.getView().getTranslateY() - enemy.getView().getTranslateY(), 2));
                    if (Math.abs(temp) <= Math.abs(c)) {
                        c = temp;
                        x = (food.getView().getTranslateX() - enemy.getView().getTranslateX()) / c;
                        y = (food.getView().getTranslateY() - enemy.getView().getTranslateY()) / c;
                    }
                }
            }
            switch (difficulties.getDifficult()) {
                case 'E':
                    enemy.setVelocity(new Point2D(1.5 * x, 1.5 * y));
                case 'N':
                    enemy.setVelocity(new Point2D(2 * x, 2 * y));
                case 'H':
                    enemy.setVelocity(new Point2D(3 * x, 3 * y));
            }
        }

        foods.removeIf(GameObject::isDead);
        viruses.removeIf(GameObject::isDead);
        enemies.removeIf(GameObject::isDead);
        mutations.removeIf(GameObject::isDead);

        if (player.isAlive()) {
            foods.forEach(GameObject::update);
            viruses.forEach(GameObject::update);
            enemies.forEach(GameObject::update);
            mutations.forEach(GameObject::update);

            player.update();

            Food.timeUpdate();
            if (Food.getTime() >= Food.spawnTime && Food.foodsCount < 40) {
                addFood(new Food(), Math.random() * root.getPrefWidth(), Math.random() * root.getPrefHeight());
                Food.foodsCount++;
                Food.setTime(0);
            }

            Virus.passTimeUpdate();
            if (Virus.getPassTime() >= Virus.spawnTime && Virus.virusesCount < 3) {
                addVirus(new Virus(), Math.random() * root.getPrefWidth(), Math.random() * 855.0);
                Virus.virusesCount++;
                Virus.passTime = 0;
            }
        }

        //คะแนน
        scoreText.setText("Score : " + score.getCurrentScore());
        scoreText.toFront();

    }

    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        scenemenu = new Scene(createContent());
       
        window.setTitle("Asteriods.io");
        window.setScene(scenemenu);
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
