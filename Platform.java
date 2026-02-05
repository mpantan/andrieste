package andrieste;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import java.util.HashSet;

/**
 * This is the platform class. It extends collidableRectangle and is a parent class
 * for 4 other platforms. A lot of this class is figuring out collision direction logic,
 * which is a very important aspect of this game.
 */
public class Platform extends CollidableRectangle {
    private boolean isWall;

    /**
     * this is the constructor. it calls the parent's constructor, stores isWall instance variable,
     * and updates its sprite.
     */
    public Platform(Pane gamePane,double x, double y, double width, double height, boolean isWall) {
        super(gamePane,x,y,width,height);
        this.isWall = isWall;
        this.changeSprite();
    }

    /**
     * method to update sprite to the image of its choosing. If it's a wall, use wall image.
     * If not, use regular platform image.
     */
    @Override
    public void changeSprite() {
        Image img;
        if (this.isWall) {
            img = this.getImage("sprites/wall.png");
        } else {
            img = this.getImage("sprites/platform.png");
        }
        this.setSprite(img);
    }


    /**
     * This is a very important method to determine which side of a platform Andrieste is
     * colliding with. The way I came up to do this might not be the best way, but I think
     * it works pretty okay.
     * The idea is like this: calculate the distance between Andrieste and each side
     * of the platform. Then compare those distances to figure out which side is closest at
     * the moment of intersection. The closest side is assumed to be the direction
     * of collision. This can be done by a lot of if statements. Also make sure
     * to figure out if coming from top direction or bottom direction
     * as that helps the calculations.
     */
    public CollidingDirection getCollisionDirection(AndriesteMover andriesteMover) {
        //  Just need a bunch of numbers for calculation. Not sure how to make this cleaner
        double andriesteWidth =andriesteMover.getWidth();
        double andriesteHeight = andriesteMover.getHeight();
        double platformWidth = this.getWidth();
        double platformHeight = this.getHeight();
        double  andriesteLeftX = andriesteMover.getX();
        double  andriesteRightX = andriesteLeftX + andriesteWidth;
        double  andriesteTopY = andriesteMover.getY();
        double  andriesteBottomY = andriesteTopY + andriesteHeight;
        double  platformLeftX = this.getX();
        double  platformRightX = platformLeftX + platformWidth;
        double  platformTopY =  this.getY();
        double  platformBottomY = platformTopY + platformHeight;
        double distanceToTop =  Math.abs(andriesteBottomY -platformTopY);
        double distanceToBottom = Math.abs(andriesteTopY - platformBottomY);
        double distanceToLeft = Math.abs(andriesteRightX - platformLeftX);
        double distanceToRight = Math.abs(andriesteLeftX - platformRightX);

        // Coming from top direction
        if (distanceToTop < distanceToBottom) {
            //Coming from left
            if  (distanceToLeft < distanceToRight) {
                // If distance to top is less than distance to left, it means it's coming from top.
                if (distanceToTop < distanceToLeft) {
                    return CollidingDirection.TOP;
                }
                else {
                    return CollidingDirection.LEFT;
                }
            }
            // Coming from Right
            else {
                if (distanceToTop < distanceToRight) {
                    return CollidingDirection.TOP;
                }
                else{
                    return CollidingDirection.RIGHT;
                }
            }

        }
        //COMING FROM BOTTOM
        else {
            //Coming from Left
            if (distanceToLeft < distanceToRight) {
                if (distanceToBottom < distanceToLeft) {
                    return CollidingDirection.BOTTOM;
                }
                else {
                    return CollidingDirection.LEFT;
                }
            }
            //Coming from Right
            else {
                if (distanceToBottom < distanceToRight) {
                    return CollidingDirection.BOTTOM;
                }
                else {
                    return CollidingDirection.RIGHT;
                }
            }
        }
    }

    /**
     * method that is called when andrieste collides with left side of platform.
     * set y velocity to 0 (gravity will still slowly bring andrieste down), and set X
     * to the side of the platform. Also, set jump to true!
     */
    private void collideLeft(AndriesteMover andriesteMover) {
        andriesteMover.setYVelocity(0);
        andriesteMover.setX(this.getX() - andriesteMover.getWidth());
        andriesteMover.setCanJump(true);
    }

    /**
     * method that is called when andrieste collides with right side of platform.
     * set y velocity to 0 (gravity will still slowly bring andrieste down), and set X
     * to the side of the platform. Also, set jump to true!
     */
    private void collideRight(AndriesteMover andriesteMover) {
        andriesteMover.setYVelocity(0);
        andriesteMover.setX(this.getX() + this.getWidth());
        andriesteMover.setCanJump(true);
    }


    /**
     * method that is called when andrieste collides with top side of platform.
     * set y velocity to 0, and set Y to the of the platform so andrieste is at top of platform.
     * Also, set jump to true!
     */
    public void collideTop(AndriesteMover andriesteMover) {
        andriesteMover.setYVelocity(0);
        andriesteMover.setY(this.getY() - andriesteMover.getHeight());
        andriesteMover.setCanJump(true);
        andriesteMover.setCanDash(true);
    }

    /**
     * method that is called when andrieste collides with bottom side of platform.
     * set y velocity to 0 (like bonking your head), and set andrieste's Y so he's below platform.
     */
    private void collideBottom(AndriesteMover andriesteMover) {
        andriesteMover.setYVelocity(0);
        andriesteMover.setY(this.getY() + this.getHeight());
    }

    /**
     * This method handles collision logic. First, get colliding direction. Also update
     * direction values in AndriesteMover. Make it so if user is holding down SPACE when on wall,
     * they can't jump --> they have to tap SPACE to wall jump.
     * Finally just handle obvious collision logic. If collidingTop, call collideTop.
     * If collidingRight, call collideRight etc.
     */
    @Override
    public void collide(
            AndriesteMover andriesteMover,
            HashSet<KeyCode> keysPressed,
            double[] respawnCoords,
            double time) {

        CollidingDirection collidingDirection = this.getCollisionDirection(andriesteMover);
        this.handleDirectionUpdates(collidingDirection, andriesteMover);

        //only can wall jump with indiviual clicks
        if (collidingDirection == CollidingDirection.LEFT || collidingDirection == CollidingDirection.RIGHT) {
            if (keysPressed.contains(KeyCode.SPACE)) {
                andriesteMover.setCanWallJump(false);
            }
            else {
                andriesteMover.setCanWallJump(true);
            }
        }
        andriesteMover.setCollidingDirection(collidingDirection);
        if (collidingDirection == CollidingDirection.LEFT) {
            this.collideLeft(andriesteMover);
        }
        else if (collidingDirection == CollidingDirection.RIGHT) {
            this.collideRight(andriesteMover);
        }
        else if (collidingDirection == CollidingDirection.TOP) {
            this.collideTop(andriesteMover);
        }
        else {
            this.collideBottom(andriesteMover);
        }
    }

    /**
     * Returns the max traversable distance depend on if platform is a wall and
     * the difficulty. Walls are easy to wall jump off of, so you can get more horizontal velocity
     * than normal platforms. This is why there's two values depending on if it's a wall or not.
     */
    @Override public double[] getMaxTraversableDistance(Difficulty difficulty) {
        if (this.isWall) {
            return this.getWallMaxTraversableDistance(difficulty);
        } else{
            return this.getNormalMaxTraversableDistance(difficulty);
        }
    }

    /**
     * Returns the min traversable distance depend on if platform is a wall and
     * the difficulty. Walls are easy to wall jump off of, so you can get more horizontal velocity
     * than normal platforms. This is why there's two values depending on if it's a wall or not.
     */
    @Override
    public double[] getMinTraversableDistance(Difficulty difficulty) {
        if (this.isWall) {
            return this.getWallMinTraversableDistance(difficulty);
        } else{
            return this.getNormalMinTraversableDistance(difficulty);
        }
    }

    /**
     * returns max X and Y traversable distance for a platform depending on the difficulty.
     */
    private double[] getNormalMaxTraversableDistance(Difficulty difficulty) {
        double[] maxDistance = new double[2];
        if (difficulty == Difficulty.EASY) {
            maxDistance[0] = Constants.PLATFORM_MAX_X_DISTANCE_EASY;
            maxDistance[1] = Constants.PLATFORM_MAX_Y_DISTANCE_EASY;
            return maxDistance;
        }
        else if (difficulty == Difficulty.MEDIUM) {
            maxDistance[0] = Constants.PLATFORM_MAX_X_DISTANCE_MEDIUM;
            maxDistance[1] = Constants.PLATFORM_MAX_Y_DISTANCE_MEDIUM;
            return maxDistance;
        }
        else {
            maxDistance[0] = Constants.PLATFORM_MAX_X_DISTANCE_HARD;
            maxDistance[1] = Constants.PLATFORM_MAX_Y_DISTANCE_HARD;
            return maxDistance;
        }

    }

    /**
     * returns min X and Y traversable distance for a platform depending on the difficulty.
     */
    private double[] getNormalMinTraversableDistance(Difficulty difficulty) {
        double[] minDistance = new double[2];
        if(difficulty == Difficulty.EASY) {
            minDistance[0] = Constants.PLATFORM_MIN_X_DISTANCE_EASY;
            minDistance[1] = Constants.PLATFORM_MIN_Y_DISTANCE_EASY;
            return minDistance;
        }
        else if (difficulty == Difficulty.MEDIUM) {
            minDistance[0] = Constants.PLATFORM_MIN_X_DISTANCE_MEDIUM;
            minDistance[1] = Constants.PLATFORM_MIN_Y_DISTANCE_MEDIUM;
            return minDistance;
        }
        else {
            minDistance[0] = Constants.PLATFORM_MIN_X_DISTANCE_HARD;
            minDistance[1] = Constants.PLATFORM_MIN_Y_DISTANCE_HARD;
            return minDistance;
        }

    }

    /**
     * returns max X and Y traversable distance for a wall depending on the difficulty.
     */
    private double[] getWallMaxTraversableDistance(Difficulty difficulty) {
        double[] maxDistance = new double[2];
        if (difficulty == Difficulty.EASY) {
            maxDistance[0] = Constants.WALL_PLATFORM_MAX_X_DISTANCE_EASY;
            maxDistance[1] = Constants.WALL_PLATFORM_MAX_Y_DISTANCE_EASY;
            return maxDistance;
        }
        else if (difficulty == Difficulty.MEDIUM) {
            maxDistance[0] = Constants.WALL_PLATFORM_MAX_X_DISTANCE_MEDIUM;
            maxDistance[1] = Constants.WALL_PLATFORM_MAX_Y_DISTANCE_MEDIUM;
            return maxDistance;
        }
        else {
            maxDistance[0] = Constants.WALL_PLATFORM_MAX_X_DISTANCE_HARD;
            maxDistance[1] = Constants.WALL_PLATFORM_MAX_Y_DISTANCE_HARD;
            return maxDistance;
        }
    }

    /**
     * returns min X and Y traversable distance for a wall depending on the difficulty.
     */
    private double[] getWallMinTraversableDistance(Difficulty difficulty) {
        double[] minDistance = new double[2];
        if(difficulty == Difficulty.EASY) {
            minDistance[0] = Constants.WALL_PLATFORM_MIN_X_DISTANCE_EASY;
            minDistance[1] = Constants.WALL_PLATFORM_MIN_Y_DISTANCE_EASY;
            return minDistance;
        }
        else if (difficulty == Difficulty.MEDIUM) {
            minDistance[0] = Constants.WALL_PLATFORM_MIN_X_DISTANCE_MEDIUM;
            minDistance[1] = Constants.WALL_PLATFORM_MIN_Y_DISTANCE_MEDIUM;
            return minDistance;
        }
        else {
            minDistance[0] = Constants.WALL_PLATFORM_MIN_X_DISTANCE_HARD;
            minDistance[1] = Constants.WALL_PLATFORM_MIN_Y_DISTANCE_HARD;
            return minDistance;
        }
    }

    /**
     * method to update direction values stored in andriesteMover. These values are then accessed
     * by animator to handle animation.
     * Pretty simple logic.
     */
    private void handleDirectionUpdates(CollidingDirection direction, AndriesteMover andriesteMover) {
        if (direction == CollidingDirection.TOP) {
            andriesteMover.setIsGrounded(true);
            andriesteMover.setWallClimbDirection(WallClimbDirection.NONE);
        } else if (direction == CollidingDirection.LEFT) {
            andriesteMover.setWallClimbDirection(WallClimbDirection.LEFT);
        } else if (direction == CollidingDirection.RIGHT) {
            andriesteMover.setWallClimbDirection(WallClimbDirection.RIGHT);
        }
        }
    }




