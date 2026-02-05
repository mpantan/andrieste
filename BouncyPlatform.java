package andrieste;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import java.util.HashSet;

/**
 * This is the BouncyPlatform class. It extends the platform superclass. It's just like a
 * normal platform, but has a unique sprite and unique collision logic.
 */
public class BouncyPlatform extends Platform {

    /**
     * Constructor!!! Just uses calls Platform constructor.
     */
    public BouncyPlatform(Pane gamePane, double x, double y, double width, double height) {
        super(gamePane, x, y, width, height, false);
    }

    /**
     * Overrides changeSprite to set sprite to the BouncyPlatform image.
     */
    @Override
    public void changeSprite() {
        Image img = this.getImage("sprites/bouncyPlatform.png");
        this.setSprite(img);
    }

    /**
     * Collision logic! Calls platform's collision logic, and then add calls
     * bounceLogic method.
     */
    @Override
    public void collide(
            AndriesteMover andriesteMover,
            HashSet<KeyCode> keysPressed,
            double[] respawnCoords,
            double time) {
        super.collide(andriesteMover, keysPressed, respawnCoords, time);
        this.bounceLogic(andriesteMover);
    }

    /**
     * Bounce logic. If andrieste is colliding with top of platform and is not dashing,
     * set Y velocity to specified value.
     */
    private void bounceLogic(AndriesteMover andriesteMover) {
        if (this.getCollisionDirection(andriesteMover) == CollidingDirection.TOP) {
            if(!andriesteMover.getIsDashing()) {
                andriesteMover.setYVelocity(Constants.BOUNCY_Y_VELOCITY);
            }
        }
    }

    /**
     * returns an array of type double of the maxTraversable distance based on
     * the current difficulty.
     */
    @Override
    public double[] getMaxTraversableDistance(Difficulty difficulty) {
        double[] maxDistance = new double[2];
        if (difficulty == Difficulty.EASY) {
            maxDistance[0] = Constants.BOUNCY_PLATFORM_MAX_X_DISTANCE_EASY;
            maxDistance[1] = Constants.BOUNCY_PLATFORM_MAX_Y_DISTANCE_EASY;
            return maxDistance;
        }
        else if (difficulty == Difficulty.MEDIUM) {
            maxDistance[0] = Constants.BOUNCY_PLATFORM_MAX_X_DISTANCE_MEDIUM;
            maxDistance[1] = Constants.BOUNCY_PLATFORM_MAX_Y_DISTANCE_MEDIUM;
            return maxDistance;
        }
        else {
            maxDistance[0] = Constants.BOUNCY_PLATFORM_MAX_X_DISTANCE_HARD;
            maxDistance[1] = Constants.BOUNCY_PLATFORM_MAX_Y_DISTANCE_HARD;
            return maxDistance;
        }
    }

    /**
     * returns an array of type double of the minimum Traversable distance based on
     * the current difficulty.
     */
    @Override
    public double[] getMinTraversableDistance(Difficulty difficulty) {
        double[] minDistance = new double[2];
        if(difficulty == Difficulty.EASY) {
            minDistance[0] = Constants.BOUNCY_PLATFORM_MIN_X_DISTANCE_EASY;
            minDistance[1] = Constants.BOUNCY_PLATFORM_MIN_Y_DISTANCE_EASY;
            return minDistance;
        }
        else if (difficulty == Difficulty.MEDIUM) {
            minDistance[0] = Constants.BOUNCY_PLATFORM_MIN_X_DISTANCE_MEDIUM;
            minDistance[1] = Constants.BOUNCY_PLATFORM_MIN_Y_DISTANCE_MEDIUM;
            return minDistance;
        }
        else {
            minDistance[0] = Constants.BOUNCY_PLATFORM_MIN_X_DISTANCE_HARD;
            minDistance[1] = Constants.BOUNCY_PLATFORM_MIN_Y_DISTANCE_HARD;
            return minDistance;
        }
    }

}
