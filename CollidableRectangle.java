package andrieste;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.HashSet;

/**
 * This is the abstract class that is the super class for all the objects
 * that Andrieste can interact with. This class holds the rectangle
 * and the sprite that is overlaid over the rectangle.
 * It keeps some methods abstract so it's children can figure out how to implement
 * the methods.
 */
public abstract class CollidableRectangle {
    private Rectangle rect;
    private ImageView sprite;

    /**
     * This is the constructor. It sets up the rectangle and the sprite.
     */
    public CollidableRectangle(Pane gamePane, double x, double y, double width, double height) {
        this.setUpRect(x, y, width, height);
        this.setUpSprite(gamePane);
        this.changeSprite();
    }

    /**
     * Abstract method to change the sprite of platform to each child's own image.
     */
    public abstract void changeSprite();

    /**
     * method to flip the sprite
     */
    public void flipSprite() {
        this.sprite.setScaleX(-1);
    }

    /**
     * Method to set sprites' x location
     */
    public void setSpriteX(double x) {
        this.sprite.setX(x);
    }

    /**
     * method to set sprites' y location
     */
    public void setSpriteY(double y) {
        this.sprite.setY(y);
    }

    /**
     * sets up the sprite by matching the width, height, x, and y to that
     * of the rectangle. adds sprite ImageView to gamePane
     */
    private void setUpSprite(Pane gamePane) {
        this.sprite = new ImageView();
        this.sprite.setX(this.getX());
        this.sprite.setY(this.getY());
        this.sprite.setFitWidth(this.getWidth());
        this.sprite.setFitHeight(this.getHeight());
        gamePane.getChildren().add(this.sprite);
    }

    /**
     * sets imageView sprite to the given image.
     */
    public void setSprite(Image image) {
        this.sprite.setImage(image);
    }

    /**
     * creates rectangle
     */
    private void setUpRect(double x, double y, double width, double height) {
        this.rect = new Rectangle(x, y, width, height);
    }

    /**
     * returns X location of rectangle
     */
    public double getX() {
        return this.rect.getX();
    }

    /**
     * returns Y location of rectangle
     */
    public double getY() {
        return this.rect.getY();
    }

    /**
     * sets X location of rectangle to provided double
     */
    public void setX(double x) {
        this.rect.setX(x);
    }

    /**
     * sets Y location of rectangle to provided double
     */
    public void setY(double y) {
        this.rect.setY(y);
    }

    /**
     * returns width of rectangle
     */
    public double getWidth() {
        return this.rect.getWidth();
    }

    /**
     * returns height of rectangle
     */
    public double getHeight() {
        return this.rect.getHeight();
    }

    /**
     * checks collision of itself and Andrieste. simply returns boolean
     * based on if they are intersecting.
     */
    public boolean checkCollision(AndriesteMover andriesteMover) {
        return this.rect.intersects(andriesteMover.getX(), andriesteMover.getY(),
                andriesteMover.getWidth(), andriesteMover.getHeight());
    }

    /**
     * helper method to return Image based on string
     */
    public Image getImage(String imageName) {
        return new Image(this.getClass().getResourceAsStream(imageName));
    }


    /**
     * removes sprite from given pane.
     */
    public void removeFromGamePane(Pane gamePane){
        gamePane.getChildren().remove(this.sprite);
    }


    /**
     * Abstract method so children classes can decide how collision logic works.
     */
    public abstract void collide(
            AndriesteMover andriesteMover,
            HashSet<KeyCode> keysPressed,
            double[] respawnCoords,
            double time);

    /**
     * placeholder method kind of. when level restarts, all platforms need to "reset".
     * only class that needs to actually reset is slidingPlatform, so this method returns
     * null for all classes except the slidingPlatform (it overrides this method)
     */
    public CollidableRectangle reset(Pane gamePane) {
        return null;
    }

    /**
     * abstract method to get the max traversable distance based on the current
     * difficulty. this is used for the random level generation algorithm. each
     * collidable rectangle has a different max distance.
     * returns double array of max X and max Y
     */
    public abstract double[] getMaxTraversableDistance(Difficulty difficulty);

    /**
     * abstract method to get the min traversable distance based on the current
     * difficulty. this is used for the random level generation algorithm. each
     * collidable rectangle has a different min distance.
     * returns double array of min X and min Y
     */
    public abstract double[] getMinTraversableDistance(Difficulty difficulty);
}