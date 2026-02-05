package andrieste;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import java.util.HashSet;

/**
 * this is the sliding platform class. It's a platform that when colliding on the top slides
 * either left or right. it's a bit more complicated than the other platforms because it moves,
 * but only when activated.
 */
public class SlidingPlatform extends Platform {
    private boolean isDone;
    private double startX;
    private SlidingDirection direction;
    private double x;
    private double y;
    private double width;
    private double height;


    /**
     * constructor. call parent constructor to create rect and sprite. initialize instance variables
     * and change sprite.
     */
    public SlidingPlatform(Pane gamePane, double x, double y, double width, double height, SlidingDirection direction) {
        super(gamePane, x, y, width, height, false);
        this.isDone = false;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.startX = x;
        this.direction = direction;
        this.changeSprite();
    }

    /**
     * method called to update sprite. sprite is different depending on the width of the platform.
     * also, if platform slides left, flip direction of sprite, so arrow points left.
     */
    @Override
    public void changeSprite() {
        Image img;
        if (this.width >= Constants.SMALL_SLIDING_PLATFORM_WIDTH &&
                this.width <= Constants.MEDIUM_SLIDING_PLATFORM_WIDTH) {
            img = this.getImage("sprites/slidingPlatform2.png");
        } else if(this.width < Constants.SMALL_SLIDING_PLATFORM_WIDTH) {
            img = this.getImage("sprites/slidingPlatform3.png");
        }
        else {
            img = this.getImage("sprites/slidingPlatform.png");
        }
        this.setSprite(img);
        if (this.direction == SlidingDirection.LEFT){
            this.flipSprite();
        }
    }


    /**
     * Collision logic. Calls same exact logic as parent but also adds its own slide logic.
     */
    @Override
    public void collide(
            AndriesteMover andriesteMover,
            HashSet<KeyCode> keysPressed,
            double[] respawnCoords,
            double time) {
        super.collide(andriesteMover, keysPressed, respawnCoords, time);
        this.slideLogic(andriesteMover);
    }

    /**
     * resets platform. it does this by removing it graphically and then return a new fresh platform.
     * just returns itself at the state it was before it activated
     */
    @Override
    public CollidableRectangle reset(Pane gamePane){
        this.removeFromGamePane(gamePane);
        return new SlidingPlatform(gamePane, this.x, this.y, this.width, this.height, this.direction);
    }


    /**
     * slide logic. only slides when colliding with top. if sliding direction is right,
     * slide right. if left, slide left.
     */
    private void slideLogic(AndriesteMover andriesteMover) {
        if (this.getCollisionDirection(andriesteMover) == CollidingDirection.TOP) {
            if (this.direction == SlidingDirection.RIGHT) {
                this.slideRight(andriesteMover);
            }
            else if (this.direction == SlidingDirection.LEFT) {
                this.slideLeft(andriesteMover);
            }
        }
        }

    /**
     * slide right logic. first checks if platform is done moving already. if not,
     * move platform over by delta, and update sprite. also, set andrieste's velocity
     * to constant velocity. finally, check if sliding platform went far enough to be done sliding.
     */
    private void slideRight(AndriesteMover andriesteMover) {
            if (!isDone) {
                double newX = this.getX() + Constants.SLIDING_PLATFORM_X_DELTA;
                this.setX(newX);
                this.setSpriteX(newX);
                andriesteMover.setXVelocity(Constants.SLIDING_PLATFORM_VELOCITY);
                if (newX - startX > Constants.SLIDING_PLATFORM_X_CHANGE) {
                    this.isDone = true;
                }
            }
        }

    /**
     * slide left logic. first checks if platform is done moving already. if not,
     * move platform over by delta, and update sprite. also, set andrieste's velocity
     * to constant velocity. finally, check if sliding platform went far enough to be done sliding.
     */
    private void slideLeft(AndriesteMover andriesteMover) {
        if (!isDone) {
            double newX = this.getX() - Constants.SLIDING_PLATFORM_X_DELTA;
            this.setX(newX);
            this.setSpriteX(newX);
            andriesteMover.setXVelocity(-Constants.SLIDING_PLATFORM_VELOCITY);
            if (newX - startX < -Constants.SLIDING_PLATFORM_X_CHANGE) {
                this.isDone = true;
            }
        }
    }


    /**
     * returns max traversable X and Y based on difficulty.
     */
    @Override
    public double[] getMaxTraversableDistance(Difficulty difficulty) {
        double[] maxDistance = new double[2];
        if (difficulty == Difficulty.EASY) {
            maxDistance[0] = Constants.SLIDING_PLATFORM_MAX_X_DISTANCE_EASY;
            maxDistance[1] = Constants.SLIDING_PLATFORM_MAX_Y_DISTANCE_EASY;
            return maxDistance;
        }
        else if (difficulty == Difficulty.MEDIUM) {
            maxDistance[0] = Constants.SLIDING_PLATFORM_MAX_X_DISTANCE_MEDIUM;
            maxDistance[1] = Constants.SLIDING_PLATFORM_MAX_Y_DISTANCE_MEDIUM;
            return maxDistance;
        }
        else {
            maxDistance[0] = Constants.SLIDING_PLATFORM_MAX_X_DISTANCE_HARD;
            maxDistance[1] = Constants.SLIDING_PLATFORM_MAX_Y_DISTANCE_HARD;
            return maxDistance;
        }
    }

    /**
     * returns minimum traversable X and Y based on difficulty.
     */
    @Override
    public double[] getMinTraversableDistance(Difficulty difficulty) {
        double[] minDistance = new double[2];
        if (difficulty == Difficulty.EASY) {
            minDistance[0] = Constants.SLIDING_PLATFORM_MIN_X_DISTANCE_EASY;
            minDistance[1] = Constants.SLIDING_PLATFORM_MIN_Y_DISTANCE_EASY;
            return minDistance;
        }
        else if (difficulty == Difficulty.MEDIUM) {
            minDistance[0] = Constants.SLIDING_PLATFORM_MIN_X_DISTANCE_MEDIUM;
            minDistance[1] = Constants.SLIDING_PLATFORM_MIN_Y_DISTANCE_MEDIUM;
            return minDistance;
        }
        else {
            minDistance[0] = Constants.SLIDING_PLATFORM_MIN_X_DISTANCE_HARD;
            minDistance[1] = Constants.SLIDING_PLATFORM_MIN_Y_DISTANCE_HARD;
            return minDistance;
        }
    }

}



