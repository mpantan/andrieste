package andrieste;

import javafx.scene.shape.Rectangle;


/**
 * This is the Andrieste Class. It is a wrapper class for the player. It holds the rectangle
 * that is the hitbox of the character. This class just has a bunch of getters
 * and setters which the AndriesteMover class uses.
 */
public class Andrieste {
    private Rectangle rect;
    private double xVelocity;
    private double yVelocity;

    /**
     * This is the constructor. It sets up the graphics.
     */

    public Andrieste() {
        this.setUpGraphics();
    }

    /**
     * Returns an array of andrieste's x and y coordinates
     * @return double[]
     */
    public double[] getState(){
        double[] state = new double[2];
        state[0] = this.getX();
        state[1] = this.getY();
        return state;
    }

    /**
     * Updates andrieste's position by adding the x and y velocity to the coordinates
     */
    public void updatePosition() {
        this.setX(this.getX() + xVelocity);
        this.setY(this.getY() + yVelocity);
    }

    /**
     * Creates the rectangle that represents andrieste
     */
    private void setUpGraphics() {
        this.rect = new Rectangle(0, 0,Constants.ANDRIESTE_WIDTH, Constants.ANDRIESTE_HEIGHT);
    }



    /**
     * Gets andrieste's X location
     * @return double
     */
    public double getX() {
        return this.rect.getX();
    }

    /**
     * Gets andrieste's Y location
     * @return double
     */
    public double getY() {
        return this.rect.getY();
    }

    /**
     * Sets andrieste's x location
     */
    public void setX(double x) {
        this.rect.setX(x);
    }

    /**
     * Sets andrieste's y location
     */
    public void setY(double y) {
        this.rect.setY(y);
    }

    /**
     * returns the width of andrieste's hitbox
     * @return double
     */
    public double getWidth() {
        return this.rect.getWidth();
    }

    /**
     * returns the height of andrieste's hitbox
     * @return double
     */
    public double getHeight() {
        return this.rect.getHeight();
    }

    /**
     * Sets andrieste's x velocity
     */
    public void setXVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
    }

    /**
     * Sets andrieste's y velocity
     */
    public void setYVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }

    /**
     * returns andrieste's x velocity
     * @return double
     */
    public double getXVelocity() {
        return this.xVelocity;
    }

    /**
     * returns andrieste's y velocity
     * @return double
     */
    public double getYVelocity() {
        return this.yVelocity;
    }

}
