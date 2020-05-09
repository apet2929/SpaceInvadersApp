package invaders;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SpaceInvadersApp extends Application{
    private final double WIDTH = 600;
    private final double HEIGHT = 800;
    private List<Sprite> spriteList;
    private List<Sprite> toAdd;
    private int enemyCount;
    public static void main(String[] args)
    {
        launch(args);
    }
    @Override
    public void start(Stage theStage) throws FileNotFoundException {
        theStage.setTitle( "Collect the Money Bags!" );
        spriteList = new ArrayList<Sprite>();
        toAdd = new ArrayList<>();
        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );

        Sprite player = new Sprite("player");
        Canvas canvas = new Canvas( WIDTH, HEIGHT );
        root.getChildren().add( canvas );

        ArrayList<String> input = new ArrayList<String>();

        theScene.setOnKeyPressed(
                new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        String code = e.getCode().toString();
                        if(code.equals("SPACE")) shoot(player);
                        if ( !input.contains(code) )
                            input.add( code );
                    }
                });

        theScene.setOnKeyReleased(
                new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        String code = e.getCode().toString();
                        input.remove( code );
                    }
                });

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Font theFont = Font.font( "Helvetica", FontWeight.BOLD, 24 );
        gc.setFont( theFont );
        gc.setFill( Color.GREEN );
        gc.setStroke( Color.BLACK );
        gc.setLineWidth(1);


        player.setImage(genImages("player"));
        player.setPosition(300, 750);

        spriteList.add(player);


        nextLevel();
        LongValue lastNanoTime = new LongValue( System.nanoTime() );

        IntValue score = new IntValue(0);

        new AnimationTimer()
        {
            double counter2s = 0;
            public void handle(long currentNanoTime)
            {
                // calculate time since last update.
                double elapsedTime = (currentNanoTime - lastNanoTime.value) / 1000000000.0;
                lastNanoTime.value = currentNanoTime;
                counter2s += elapsedTime;

                        // game logic

                player.setVelocity(0,0);
                if (input.contains("LEFT"))
                    player.addVelocity(-4,0);
                if (input.contains("RIGHT"))
                    player.addVelocity(4,0);
                if (input.contains("UP"))
                    player.addVelocity(0,-4);
                if (input.contains("DOWN"))
                    player.addVelocity(0,4);

                // collision detection

                for (Sprite s : spriteList) {
                    switch (s.type) {
                        case "enemybullet":
                            s.setVelocity(0, 10);
                            if (s.intersects(player)) {
                                player.dead = true;
                                s.dead = true;
                            }
                            break;
                        case "playerbullet":
                            s.setVelocity(0, -10);
                            spriteList.stream().filter(e -> e.type.equals("enemy")).forEach(enemy -> {
                                if (s.intersects(enemy)) {
                                    enemy.dead = true;
                                    s.dead = true;

                                }
                            });
                            break;
                        case "enemy":
                            if (counter2s > 2) {
                                if (Math.random() < 0.3) {
                                    shoot(s);
                                    System.out.println("Shooting");
                                }
                                counter2s = 0;
                                break;
                            }
                        default: break;
                    }

                }
                for(int i = 0; i < 1 && toAdd.size() != 0; i++){
                    spriteList.add(toAdd.get(i));
                    toAdd.remove(i);
                    i--;
                }
                spriteList.removeIf(s -> s.dead);
                gc.clearRect(0, 0, WIDTH,HEIGHT);


                for (Sprite sprite : spriteList){
                    sprite.update(elapsedTime);
                    sprite.render( gc );

                }

                String controls = "CONTROLS: arrow keys";
                gc.fillText( controls, 300, 36 );
                gc.strokeText( controls, 300, 36 );

            }
        }.start();

        theStage.show();
    }
    private FileInputStream genImages(String image){
        {
            try {
                switch(image){
                    case "player" : return new FileInputStream("F:\\Documents\\My Games\\SpaceInvadersApp\\src\\invaders\\player.png");
                    case "enemy" : return new FileInputStream("F:\\Documents\\My Games\\SpaceInvadersApp\\src\\invaders\\enemy.png");
                    case "bullet" : return new FileInputStream("F:\\Documents\\My Games\\SpaceInvadersApp\\src\\invaders\\bullet.png");
                    default: System.out.println("Asset does not exist"); break;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
    private void nextLevel() {
        for (int i = 0; i < 5; i++)
        {
            Sprite enemy = new Sprite("enemy");
            enemy.setImage(genImages("enemy"));
            enemy.setPosition(90 + i*100, 150);
            toAdd.add(enemy);
        }
    }

    private void shoot(Sprite who){
        Sprite s = new Sprite(who.type + "bullet");
        s.setPosition(who.getPositionX() + who.width / 2, who.getPositionY());
        s.setImage(genImages("bullet"));
        toAdd.add(s);

    }
}






//package invaders;
//
//import javafx.animation.AnimationTimer;
//import javafx.application.Application;
//import javafx.scene.Group;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.image.Image;
//import javafx.scene.layout.Pane;
//import javafx.stage.Stage;
//
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class SpaceInvadersApp extends Application {
//    private GraphicsContext graphicsContext;
//    private Pane root = new Pane();
//    private Sprite player;
//    private List<Sprite> sprites;
//    private double time;
//    private FileInputStream playerImage;
//    private FileInputStream enemyImage;
//    private FileInputStream bulletImage;
//
//
//    private Parent createContent(){
//        sprites = new ArrayList<>();
//        loadImages();
//        Canvas canvas = new Canvas(600,800);
//        root.getChildren().add(canvas);
//        graphicsContext = canvas.getGraphicsContext2D();
//
//        player = new Sprite(new Image(playerImage),300, 750, 0, 0, 40, 40, "player");
//        nextLevel();
//        AnimationTimer timer = new AnimationTimer() {
//            @Override
//            public void handle(long l) {
//                update();
//            }
//        };
//        timer.start();
//
//        return root;
//    }
//
//    private void nextLevel(){
//        for(int i = 0; i < 5; i++){
//            Sprite enemy = new Sprite(90 + i*100, 150, 30, 30, "enemy");
//            enemy.setImage(new Image(enemyImage));
//            addSprite(enemy);
//            System.out.println("nextLevel(), i:" + i + ", " + enemy.getPosX() + ", " + enemy.getPosY());
//
//        }
//    }
//
//    private void update(){
//        time += 0.016;
//        graphicsContext.clearRect(0,0,600,800);
//        Iterator<Sprite> iterator = sprites.iterator();
//        while(iterator.hasNext()) {
//            Sprite s = iterator.next();
//            s.update(time);
//            switch (s.type) {
//                case "enemybullet":
//                    s.moveDown();
//                    if (s.intersects(player)) {
//                        player.dead = true;
//                        s.dead = true;
//                    }
//                    break;
//                case "playerbullet":
//                    s.moveUp();
//                    sprites.stream().filter(e -> e.type.equals("enemy")).forEach(enemy -> {
//                        if (s.intersects(enemy)) {
//                            enemy.dead = true;
//                            s.dead = true;
//                        }
//                    });
//
//                    break;
//                case "enemy":
//                    if (time > 2) {
//                        if (Math.random() < 0.3) {
//                            shoot(s);
//                        }
//                        break;
//                    }
//            }
//            player.render(graphicsContext);
//            for(Sprite a : sprites){
//                a.render(graphicsContext);
//            }
//
//            if (time > 2) time = 0;
//        }
//    }
//
//    private void shoot(Sprite who){
////        System.out.println(who.getPosX());
////        System.out.println(who.getLayoutY() + " " + who.getY());
//        Sprite s = new Sprite((int)who.getPosX() + 20, (int)who.getPosY(), 5, 20, who.type + "bullet");
//        s.setImage(new Image(bulletImage));
//        addSprite(s);
////        root.getChildren().add(s);
//    }
//
//    @Override
//    public void start(Stage stage) {
//        Scene scene = new Scene(createContent());
//        scene.setOnKeyPressed(e -> {
//            switch (e.getCode()) {
//                case A -> player.moveLeft();
//                case D -> player.moveRight();
//                case SPACE -> shoot(player);
//            }
//        });
//        stage.setScene(scene);
//
//        stage.show();
//    }
//    private void loadImages(){
//        {
//            try {
//                playerImage = new FileInputStream("F:\\Documents\\My Games\\SpaceInvadersApp\\src\\invaders\\player.png");
//                enemyImage = new FileInputStream("F:\\Documents\\My Games\\SpaceInvadersApp\\src\\invaders\\enemy.png");
//                bulletImage = new FileInputStream("F:\\Documents\\My Games\\SpaceInvadersApp\\src\\invaders\\bullet.png");
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public void addSprite(Sprite s){
//        sprites.add(s);
//        s.render(graphicsContext);
//    }
//    public static void main(String[] args){
//        launch(args);
//    }
//
//}
