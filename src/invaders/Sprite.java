package invaders;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Rectangle2D;

import java.io.FileInputStream;

public class Sprite
{
    private Image image;
    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;
    public double width;
    public double height;
    public String type;
    public boolean dead;

    public Sprite(String type)
    {
        positionX = 0;
        positionY = 0;
        velocityX = 0;
        velocityY = 0;
        dead = false;
        this.type = type;
    }

    public void setImage(Image i)
    {
        image = i;
        width = i.getWidth();
        height = i.getHeight();
    }

    public void setImage(String filename)
    {
        Image i = new Image(filename);
        setImage(i);
    }

    public void setImage(FileInputStream fileInputStream)
    {
        Image i = new Image(fileInputStream);
        setImage(i);
    }

    public void setPosition(double x, double y)
    {
        positionX = x;
        positionY = y;
    }

    public void setVelocity(double x, double y)
    {
        velocityX = x;
        velocityY = y;
    }

    public void addVelocity(double x, double y)
    {
        velocityX += x;
        velocityY += y;
    }

    public void update(double time)
    {
        positionX += velocityX;
        positionY += velocityY;
    }

    public void render(GraphicsContext gc)
    {
        gc.drawImage( image, positionX, positionY );
    }

    public Rectangle2D getBoundary()
    {
        return new Rectangle2D(positionX,positionY,width,height);
    }

    public boolean intersects(Sprite s)
    {
        return s.getBoundary().intersects( this.getBoundary() );
    }

    public String toString()
    {
        return " Type: " + type + ", Position: [" + positionX + "," + positionY + "]"
                + " Velocity: [" + velocityX + "," + velocityY + "]";
    }

    public double getPositionX(){
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }
}


//package invaders;
//
//import javafx.geometry.Rectangle2D;
//import javafx.scene.Node;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.Pane;
//
//public class Sprite{
//    private Image image;
//    public String type;
//    private double posX;
//    private double posY;
//    private double velX;
//    private double velY;
//    private double width;
//    private double height;
//    public boolean dead = false;
//
//    public Sprite(Image image, double posX, double posY, double velX, double velY, double width, double height, String type) {
//        this.image = image;
//        this.posX = posX;
//        this.posY = posY;
//        this.velX = velX;
//        this.velY = velY;
//        this.width = width;
//        this.height = height;
//        this.type = type;
//
//    }
//
//    public Sprite(double posX, double posY, double velX, double velY, double width, double height, String type) {
//        this.posX = posX;
//        this.posY = posY;
//        this.velX = velX;
//        this.velY = velY;
//        this.width = width;
//        this.height = height;
//        this.type = type;
//        image = null;
//    }
//
//    public Sprite(double posX, double posY, double width, double height, String type) {
//        this.posX = posX;
//        this.posY = posY;
//        this.width = width;
//        this.height = height;
//        this.type = type;
//        velX = 0;
//        velY = 0;
//    }
//
//    public void setImage(Image image){
//        this.image = image;
//    }
//
//    public void update(double time){
//        posX += velX * time;
//        posY += velY * time;
//    }
//
//    public void render(GraphicsContext graphicsContext){
//        if(!dead) graphicsContext.drawImage(image, posX, posY, width, height);
//    }
//
//    public Rectangle2D getBoundary(){
//        return new Rectangle2D(posX, posY, width, height);
//    }
//    public boolean intersects(Sprite sprite){
//        return sprite.getBoundary().intersects(this.getBoundary());
//    }
//
//    public double getPosX() {
//        return posX;
//    }
//
//    public double getPosY() {
//        return posY;
//    }
//
//    void moveLeft(){
//        posX -= 5;
//
//    }
//    void moveRight(){
//        posX += 5;
//
//    }
//    void moveUp(){
//        posY -= 5;
//
//    }
//    void moveDown(){
//        posY += 5;
//    }
//}
//
////public class Sprite extends ImageView {
////    final String type;
////    boolean dead = false;
////    Sprite(Image image, int x, int y, int width, int height, String type){
////        super(image);
////        this.type = type;
////        relocate(x,y);
////
////        setFitHeight(height);
////        setFitWidth(width);
////        System.out.println(type + " instantiated, " + x + " " + y);
////    }
////
////
////
////
////
////}
//
