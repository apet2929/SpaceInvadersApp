package invaders;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class SpaceInvadersApp extends Application {

    private Pane root = new Pane();
    private Sprite player;
    private double time;
    private FileInputStream playerImage;
    private FileInputStream enemyImage;
    private FileInputStream bulletImage;


    private Parent createContent(){
        loadSprites();
        root.setPrefSize(600,800);
        player = new Sprite(new Image(playerImage),300,500,40,40,"player");
        root.getChildren().add(player);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };
        timer.start();
        nextLevel();
        return root;
    }

    private void nextLevel(){
        for(int i = 0; i < 5; i++){
            Sprite s = new Sprite(new Image(enemyImage),90 + i*100, 150, 30, 30, "enemy");
            System.out.println("nextLevel(), i:" + i + ", " + s.getX() + ", " + s.getY());
            root.getChildren().add(s);
        }
    }
    private List<Sprite> sprites(){
        return root.getChildren().stream().map(n -> (Sprite)n).collect(Collectors.toList());
    }
    private void update(){
        time += 0.016;

        sprites().forEach(s ->{
            switch (s.type){
                case "enemybullet":
                    s.moveDown();
                    if(s.getBoundsInParent().intersects(player.getBoundsInParent())){
                        player.dead = true;
                        s.dead = true;
                    }
                    break;
                case "playerbullet":
                    s.moveUp();
                    sprites().stream().filter(e -> e.type.equals("enemy")).forEach(enemy -> {
                        if(s.getBoundsInParent().intersects(enemy.getBoundsInParent())){
                            enemy.dead = true;
                            s.dead = true;
                        }
                    });

                    break;
                case "enemy": if(time > 2){
                    if(Math.random() < 0.3){
                        shoot(s);
                    }
                    break;
                }
            }
        });

        root.getChildren().removeIf(n -> {
            Sprite s = (Sprite)n;
            return s.dead;
        });
        if(time > 2) time = 0;
    }

    private void shoot(Sprite who){
        System.out.println(who.getLayoutX() + " " + who.getX());
        System.out.println(who.getLayoutY() + " " + who.getY());
        Sprite s = new Sprite(new Image(bulletImage), (int)who.getLayoutX() + 20, (int)who.getLayoutY(), 5, 20, who.type + "bullet");
        root.getChildren().add(s);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(createContent());
        scene.setOnKeyPressed(e -> {
            switch(e.getCode()){
                case A :
                    player.moveLeft();
                    break;
                case D:
                    player.moveRight();
                    break;
                case SPACE:
                    shoot(player);
                    break;
            }
        });
        stage.setScene(scene);

        stage.show();
    }
    private void loadSprites(){
        {
            try {
                playerImage = new FileInputStream("F:\\Documents\\My Games\\SpaceInvadersApp\\src\\invaders\\player.png");
                enemyImage = new FileInputStream("F:\\Documents\\My Games\\SpaceInvadersApp\\src\\invaders\\enemy.png");
                bulletImage = new FileInputStream("F:\\Documents\\My Games\\SpaceInvadersApp\\src\\invaders\\bullet.png");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args){
        launch(args);
    }
}
