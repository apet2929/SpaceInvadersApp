package invaders;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Sprite extends ImageView {
    final String type;
    boolean dead = false;
    Sprite(Image image, int x, int y, int width, int height, String type){
        super(image);
        this.type = type;
        relocate(x,y);

        setFitHeight(height);
        setFitWidth(width);
        System.out.println(type + " instantiated, " + x + " " + y);
    }

    void moveLeft(){
        relocate(getLayoutX()-5, getLayoutY());

    }
    void moveRight(){
        relocate(getLayoutX()+5, getLayoutY());

    }
    void moveUp(){
        relocate(getLayoutX(),getLayoutY()-5);

    }
    void moveDown(){
        relocate(getLayoutX(),getLayoutY()+5);
    }


}
//public class Sprite extends Image {
//
//    Image image;
//    private ImageView imageView;
//
//    private Pane root;
//
//    private double x;
//    private double y;
//
//
//    public boolean dead = false;
//
//    private double width;
//    private double height;
//
//    private boolean canMove = true;
//    public String type;
//    public Sprite(Pane root, Image image, double x, double y, double width, double height, String type) {
//
//        this.root = root;
//        this.image = image;
//        this.x = x;
//        this.y = y;
//
//        this.imageView = new ImageView(image);
//        this.imageView.relocate(x, y);
//
//        this.width = width;
//        this.height = height;
//        this.type = type;
//        addToLayer();
//
//    }
//
//    public void addToLayer() {
//        this.root.getChildren().add(this.imageView);
//    }
//
//    public void removeFromLayer() {
//        this.root.getChildren().remove(this.imageView);
//    }
//
//    public Pane getLayer() {
//        return root;
//    }
//
//    public void setLayer(Pane root) {
//        this.root = root;
//    }
//
//    public double getX() {
//        return x;
//    }
//
//    public void setX(double x) {
//        this.x = x;
//    }
//
//    public double getY() {
//        return y;
//    }
//
//    public void setY(double y) {
//        this.y = y;
//    }
//
//    void moveLeft(){
//        setX(getX()-5);
//
//    }
//    void moveRight(){
//
//        setX(getX()+5);
//    }
//    void moveUp(){
//
//        setY(getY()-5);
//    }
//    void moveDown(){
//        setY(getY()+5);
//    }
//
//
//    public ImageView getView() {
//        return imageView;
//    }
//
//    public void updateUI() {
//        imageView.relocate(x, y);
//    }
//
//    public double getWidth() {
//        return width;
//    }
//
//    public double getHeight() {
//        return height;
//    }
//
//    public double getCenterX() {
//        return x + width * 0.5;
//    }
//
//    public double getCenterY() {
//        return y + height * 0.5;
//    }
//
//
//    public boolean collidesWith( Sprite otherSprite) {
//        return ( otherSprite.x + otherSprite.width >= x && otherSprite.y + otherSprite.height >= y && otherSprite.x <= x + width && otherSprite.y <= y + height);
//    }
//
//    /**
//     * Set flag that the sprite can't move anymore.
//     */
//    public void stopMovement() {
//        this.canMove = false;
//    }
//
//    @Override
//    public Node getStyleableNode() {
//        return this;
//    }
//}
