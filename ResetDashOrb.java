package andrieste;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import java.util.HashSet;

/**
 * This is the reset dash orb class. When andrieste collides with this rectangle,
 * he gets his dash back. It's pretty easy.
 */
public class ResetDashOrb extends CollidableRectangle {
    private double lastCollideTime;
    private boolean canCollide;

    /**
     * constructor. call parent class to set up rect and sprite. initialize instance varaibles.
     */
    public ResetDashOrb(Pane gamePane, double x, double y) {
        super(gamePane, x, y, Constants.DASH_ORB_WIDTH, Constants.DASH_ORB_WIDTH);
        this.lastCollideTime = 0;
        this.canCollide = false;
    }

    /**
     * changes sprite to waterBottle image.
     */
    @Override
    public void changeSprite() {
        Image img = this.getImage("sprites/waterBottle.png");
        this.setSprite(img);
    }

    /**
     * collision logic. we want a small cooldown on the orb, as that works best in practice.
     * have a check to see if cooldown is over. if can collide, set canDash to true and store
     * last collide time.
     */
    @Override
    public void collide(
            AndriesteMover andriesteMover,
            HashSet<KeyCode> keysPressed,
            double[] respawnCoords,
            double time) {
        if (time - this.lastCollideTime > Constants.DASH_ORB_COOLDOWN){
            this.canCollide = true;
        }
        if (this.canCollide) {
            andriesteMover.setCanDash(true);
        }
        this.canCollide = false;
        this.lastCollideTime = time;
    }


    /**
     * returns max traversable distance based on difficulty.
     */
    @Override
    public double[] getMaxTraversableDistance(Difficulty difficulty) {
        double[] maxDistance = new double[2];
        if (difficulty == Difficulty.EASY) {
            maxDistance[0] = Constants.DASH_ORB_MAX_X_DISTANCE_EASY;
            maxDistance[1] = Constants.DASH_ORB_MAX_Y_DISTANCE_EASY;
            return maxDistance;
        }
        else if (difficulty == Difficulty.MEDIUM) {
            maxDistance[0] = Constants.DASH_ORB_MAX_X_DISTANCE_MEDIUM;
            maxDistance[1] = Constants.DASH_ORB_MAX_Y_DISTANCE_MEDIUM;
            return maxDistance;
        }
        else {
            maxDistance[0] = Constants.DASH_ORB_MAX_X_DISTANCE_HARD;
            maxDistance[1] = Constants.DASH_ORB_MAX_Y_DISTANCE_HARD;
            return maxDistance;
        }
    }

    /**
     * returns min traversable distance based on difficulty.
     */
    @Override
    public double[] getMinTraversableDistance(Difficulty difficulty) {
        double[] minDistance = new double[2];
        if (difficulty == Difficulty.EASY) {
            minDistance[0] = Constants.DASH_ORB_MIN_X_DISTANCE_EASY;
            minDistance[1] = Constants.DASH_ORB_MIN_Y_DISTANCE_EASY;
            return minDistance;
        }
        else if (difficulty == Difficulty.MEDIUM) {
            minDistance[0] = Constants.DASH_ORB_MIN_X_DISTANCE_MEDIUM;
            minDistance[1] = Constants.DASH_ORB_MIN_Y_DISTANCE_MEDIUM;
            return minDistance;
        }
        else {
            minDistance[0] = Constants.DASH_ORB_MIN_X_DISTANCE_HARD;
            minDistance[1] = Constants.DASH_ORB_MIN_Y_DISTANCE_HARD;
            return minDistance;
        }
    }


    }
