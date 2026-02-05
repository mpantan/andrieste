package andrieste;

import javafx.scene.layout.Pane;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This is the AndriesteMover class. It handles all the logic of how Andrieste moves
 * and interacts with the game. It keeps track of a bunch of booleans, about the player,
 * like if Andrieste is able to dash, rewind or like if Andrieste is grounded or currently running.
 * It contains Andrieste, RewindMarker, and the animator that animates Andrieste. This
 * is an extremely fundamental class of this program.
 */
public class AndriesteMover {
    private Andrieste andrieste;
    private boolean canJump;
    private CollidingDirection collidingDirection;
    private boolean canWallJump;
    private boolean isDashing;
    private double dashStartTime;
    private double dashStopTime;
    private double jumpStartTime;
    private boolean canDash;
    private boolean canRewind;
    private boolean jumpStoppedRewind;
    private Queue<double[]> andriesteStates;
    private RewindMarker rewindMarker;
    private Game game;
    private Animator animator;
    private WallClimbDirection wallClimbDirection;
    private boolean isGrounded;
    private boolean isRunning;


    /**
     * This is the constructor. It initializes many values and booleans, and creates
     * instances of an animator, rewindMarker, and Andrieste.
     * @param game
     */
    public AndriesteMover(Game game) {
        this.setupValues();
        this.andriesteStates = new LinkedList<>();
        this.andrieste = new Andrieste();
        this.rewindMarker = new RewindMarker(this.andrieste.getX(), this.andrieste.getY());
        this.game = game;
        this.animator = new Animator(this);

    }

    /**
     * Helper method to intitailize values
     */
    private void setupValues() {
        this.canJump = true;
        this.canDash = true;
        this.isDashing = false;
        this.canWallJump = true;
        this.wallClimbDirection = WallClimbDirection.NONE;
        this.isGrounded = true;
        this.isRunning = false;
        this.jumpStoppedRewind = false;
        this.canRewind = true;
        this.dashStartTime = 0;
        this.dashStopTime = 0;
        this.jumpStartTime = 0;
    }

    /**
     * This is a very important method. It handles update Andriestes location every frame.
     * It is fairly simple. First, it checks if Andrieste is dashing. If not, andrieste's Y position
     * is updated due to gravity. Then, andrieste's velocities are accounted for and update
     * the position. Finally, this loop handles the rewindMarker queue, cooldowns, and being offscreen.
     */
    public void updatePosition(double time) {
        if (isDashing) {
            if (time - this.dashStartTime > Constants.DASH_TIME) {
                this.isDashing = false;
                this.dashStopTime = time;
            }
        }
        else {
            this.andrieste.setY(this.getUpdatedYPosition());
            this.slowDownHorizontally();
        }
        this.andrieste.updatePosition();
        this.handleStateQueue();
        this.handleCooldown(time);
        this.handleOffScreen();
        this.handleFallingOffScreen();

    }

    /**
     * this method is used to handle the rewind marker logic. It uses a while
     * loop to fill the queue with the coordinates of andrieste, and polls the head
     * state and then sets the rewind marker to that location. It essentially makes
     * the marker be a delayed version of Andrieste by the size of the queue.
     */
    private void handleStateQueue(){
        while (this.andriesteStates.size() < Constants.STATE_QUEUE_SIZE) {
            this.andriesteStates.add(this.andrieste.getState());
        }
        double[] headState = this.andriesteStates.poll();
        this.rewindMarker.changeLocation(headState[0], headState[1]);
    }

    /**
     * returns the wall climbing direction of Andrieste.
     * @return WallClimbDirection
     */
    public WallClimbDirection getWallClimbDirection() {
        return this.wallClimbDirection;
    }

    /**
     * Returns isGrounded boolean
     */
    public boolean getIsGrounded(){
        return this.isGrounded;
    }

    /**
     * Returns isRunning boolean
     */
    public boolean getIsRunning(){
        return this.isRunning;
    }

    /**
     * Sets wall climbing direction
     */
    public void setWallClimbDirection(WallClimbDirection direction){
        this.wallClimbDirection = direction;
    }

    /**
     * sets isGrounded boolean
     */
    public void setIsGrounded(boolean isGrounded){
        this.isGrounded = isGrounded;
    }

    /**
     * Makes the sprites of Andrieste and the rewind marker appear (add to gamePane)
     */
    public void showAndrieste(Pane gamePane){
        this.rewindMarker.addToPane(gamePane);
        this.animator.showSprite(gamePane);
    }
    /**
     * Makes the sprites of Andrieste and the rewind marker disappear (remove from gamePane)
     */
    public void hideAndrieste(Pane gamePane){
        this.animator.hideSprite(gamePane);
        this.rewindMarker.removeFromPane(gamePane);
    }

    /**
     * Helpful method used when Andrieste needs to respawn. Sets location to respawn coordinates
     * and sets velocities to 0.
     */
    public void resetAndrieste(double[] respawnCoords) {
        this.setLocation(respawnCoords[0],respawnCoords[1]);
        this.andrieste.setXVelocity(0);
        this.andrieste.setYVelocity(0);
    }

    /**
     * This method handles rewind logic. When the user presses TAB, andrieste's location is
     * set to the green square that is following him.
     * Checks if Andrieste can rewind and then if that is true, sets location to location
     * of rewind marker.
     */
    public void rewindAndrieste() {
        if (this.canRewind) {
            double[] state = this.andriesteStates.poll();
            if (state != null) {
                this.andrieste.setX(state[0]);
                this.andrieste.setY(state[1]);
                this.canJump = false;
                this.andriesteStates.clear();
            }
        }
    }

    /**
     * Clears andriesteStates queue.
     */
    public void clearAndriesteStates() {
        this.andriesteStates.clear();
    }

    /**
     * calls animator.updateSprite() so game can access animator method.
     */
    public void updateSprite() {
        this.animator.updateSprite(this);
    }

    /**
     * If andrieste falls of screen, restart level.
     */
    private void handleFallingOffScreen() {
        if (this.andrieste.getY() > Constants.SCENE_HEIGHT) {
            this.game.restartLevel();
        }
    }

    /**
     * If andrieste tries to move off the screen (left and right), set location to
     * correct side of screen
     */
    private void handleOffScreen() {
        if (this.andrieste.getX() < 0) {
            this.andrieste.setX(0);
        }
        else if (this.andrieste.getX() > Constants.SCENE_WIDTH -  this.andrieste.getWidth()) {
            this.andrieste.setX(Constants.SCENE_WIDTH -  this.andrieste.getWidth());
        }
    }

    /**
     * Handles rewind cooldown. This method helps patch a glitch where you can endlessly
     * jump and rewind. Basically jump disables rewind for a bit of time.
     */
    private void handleCooldown(double time) {
        if (this.jumpStoppedRewind) {
            if (time - this.jumpStartTime > Constants.JUMP_REWIND_COOLDOWN) {
                this.canRewind = true;
                this.jumpStoppedRewind = false;
            }
        }

    }

    /**
     * Returns isDashing boolean
     */
    public boolean getIsDashing() {
        return this.isDashing;
    }

    /**
     * Set can wall jump boolean
     */
    public void setCanWallJump(boolean canWallJump) {
        this.canWallJump = canWallJump;
    }

    /**
     * returns canDash boolean
     */
    public boolean getCanDash() {
        return this.canDash;
    }

    /**
     * set canDash boolean
     */
    public void setCanDash(boolean canDash) {
        this.canDash = canDash;
    }

    /**
     * This method makes Andrieste naturally slow down Horizontally.
     * Just makes xVelocity get closer to 0 every frame.
     */
    public void slowDownHorizontally() {
        if(!this.isDashing) {
            double xVelocity = this.andrieste.getXVelocity();
            if (xVelocity > Constants.SLOW_DOWN_VELOCITY) {
                this.andrieste.setXVelocity(xVelocity - Constants.SLOW_DOWN_VELOCITY);
            } else if (xVelocity < -Constants.SLOW_DOWN_VELOCITY) {
                this.andrieste.setXVelocity(xVelocity + Constants.SLOW_DOWN_VELOCITY);
            } else {
                this.andrieste.setXVelocity(0);
            }
        }
    }

    /**
     *Handles jump logic. If can jump and not wallclimbing, jump normally.
     * if wallClimbing and canWallJump, wall jump.
     */
    public void jumpAndrieste(double time) {
        if (!this.isDashing) {
            if (this.canJump && this.wallClimbDirection == WallClimbDirection.NONE) {
                this.normalJump(time);
            } else if (this.wallClimbDirection != WallClimbDirection.NONE) {
                if (this.canWallJump) {
                    this.wallJumpAndrieste();
                }
            }
        }
    }

    /**
     * Normally jumps Andrieste.Sets YVelocity to a value, and then sets booleans and variables
     * to correct values.
     */
    private void normalJump(double time) {
        this.andrieste.setYVelocity(Constants.JUMP_Y_VELOCITY);
        this.canJump = false;
        this.jumpStartTime = time;
        this.canRewind = false;
        this.jumpStoppedRewind = true;
    }

    /**
     * Handles wall jump logic. If colliding on right side of a platform, wall jump right.
     * If on left side, wall jump left.
     */
    private void wallJumpAndrieste() {
        if (this.canJump && this.collidingDirection == CollidingDirection.RIGHT){
            this.andrieste.setYVelocity(Constants.WALL_JUMP_Y_VELOCITY);
            this.andrieste.setXVelocity(Constants.WALL_JUMP_X_VELOCITY);
        }
        else if (this.canJump && this.collidingDirection == CollidingDirection.LEFT) {
            this.andrieste.setYVelocity(Constants.WALL_JUMP_Y_VELOCITY);
            this.andrieste.setXVelocity(-Constants.WALL_JUMP_X_VELOCITY);
        }
        this.canJump = false;
    }

    /**
     * sets canJump boolean.
     */
    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

    /**
     * sets Colliding Direction
     */
    public void setCollidingDirection(CollidingDirection collidingDirection) {
        this.collidingDirection = collidingDirection;
    }

    // don't want to move if velocity is higher than running velocity
    // also don't want to dramatically switch directions

    /**
     * Moves andrieste to the left if running velocity is greater than
     * the current velocity AND if xVelocity is less than minimum change direction
     */
    public void moveAndriesteLeft() {
        if (this.andrieste.getXVelocity() > -Constants.RUNNING_VELOCITY &&
                this.andrieste.getXVelocity() < Constants.MIN_CHANGE_DIRECTION_VELOCITY) {
            this.andrieste.setXVelocity(-Constants.RUNNING_VELOCITY);
        }
    }

    /**
     * Moves andrieste to the left if running velocity is less than
     * the current velocity AND if xVelocity is greater than negative of minimum change direction
     */
    public void moveAndriesteRight() {
        if (this.andrieste.getXVelocity() > -Constants.MIN_CHANGE_DIRECTION_VELOCITY &&
                this.andrieste.getXVelocity() < Constants.RUNNING_VELOCITY) {
            this.andrieste.setXVelocity(Constants.RUNNING_VELOCITY);
        }
    }

    /**
     * Helper to set location of andrieste taking in x and y arguments.
     */
    private void setLocation(double x, double y) {
        this.andrieste.setX(x);
        this.andrieste.setY(y);
    }

    /**
     * Calculates updated Y position based on gravity. ( I honestly took this from doodleJump).
     * Returns the updated y position.
     */
    public double getUpdatedYPosition() {
        double duration = Constants.DURATION;
        double currentPosition = this.andrieste.getY();
        double updatedVelocity = this.andrieste.getYVelocity() + Constants.GRAVITY*duration;
        double updatedPosition = currentPosition + updatedVelocity*duration;
        this.andrieste.setYVelocity(updatedVelocity);
        return updatedPosition;
    }
    /**
     *Handles dash logic. Takes it time and the dashingDirection.
     * First, make sure able to dash. If andrieste can dash, set booleans and start dash time,
     * and then dash based on dashingDirection.
     */
    public void dash(double time, DashingDirection dashingDirection) {
        //Make sure dash cooldown is done
        if (time - this.dashStopTime < Constants.DASH_COOLDOWN) {
            this.canDash = false;
        }
        if (this.canDash) {
            this.canDash = false;
            this.isDashing = true;
            this.dashStartTime = time;
            this.dashBasedOnDirection(dashingDirection);
        }
    }

    /**
     * Long if statement that makes Andrieste dash based on the dashing Direction.
     */
    private void dashBasedOnDirection(DashingDirection dashingDirection) {
        if (dashingDirection == DashingDirection.EAST) {
            this.dashEast();
        } else if (dashingDirection == DashingDirection.WEST) {
            this.dashWest();
        } else if (dashingDirection == DashingDirection.NORTH) {
            this.dashNorth();
        } else if (dashingDirection == DashingDirection.SOUTH) {
            this.dashSouth();
        } else if (dashingDirection == DashingDirection.SOUTH_WEST) {
            this.dashSouthWest();
        } else if (dashingDirection == DashingDirection.SOUTH_EAST) {
            this.dashSouthEast();
        } else if (dashingDirection == DashingDirection.NORTH_WEST) {
            this.dashNorthWest();
        } else {
            this.dashNorthEast();
        }
    }

    /**
     * Handles dashEast logic. Sets x and y velocity to specified velocity
     */
    private void dashEast() {
        this.andrieste.setYVelocity(Constants.DASH_SIDE_Y_VELOCITY);
        this.andrieste.setXVelocity(Constants.DASH_SIDE_X_VELOCITY);
    }

    /**
     * Handles dashWest logic. Sets x and y velocity to specified velocity
     */
    private void dashWest() {
        this.andrieste.setYVelocity(Constants.DASH_SIDE_Y_VELOCITY);
        this.andrieste.setXVelocity(-Constants.DASH_SIDE_X_VELOCITY);
    }

    /**
     * Handles dashNorth logic. Sets x and y velocity to specified velocity
     */
    private void dashNorth()  {
        this.andrieste.setYVelocity(Constants.DASH_UP_Y_VELOCITY);
        this.andrieste.setXVelocity(Constants.DASH_UP_X_VELOCITY);
    }

    /**
     * Handles dashSouth logic. Sets x and y velocity to specified velocity
     */
    private void dashSouth() {
        this.andrieste.setYVelocity(-Constants.DASH_UP_Y_VELOCITY);
        this.andrieste.setXVelocity(Constants.DASH_UP_X_VELOCITY);
    }

    /**
     * Handles dashSouthEast logic. Sets x and y velocity to specified velocity
     */
    private void dashSouthEast(){
        this.andrieste.setYVelocity(-Constants.DASH_DIAGONAL_Y_VELOCITY);
        this.andrieste.setXVelocity(Constants.DASH_DIAGONAL_X_VELOCITY);
    }

    /**
     * Handles dashSouthWest logic. Sets x and y velocity to specified velocity
     */
    private void dashSouthWest() {
        this.andrieste.setYVelocity(-Constants.DASH_DIAGONAL_Y_VELOCITY);
        this.andrieste.setXVelocity(-Constants.DASH_DIAGONAL_X_VELOCITY);
    }

    /**
     * Handles dashNorthEast logic. Sets x and y velocity to specified velocity
     */
    private void dashNorthEast() {
        this.andrieste.setYVelocity(Constants.DASH_DIAGONAL_Y_VELOCITY);
        this.andrieste.setXVelocity(Constants.DASH_DIAGONAL_X_VELOCITY);
    }

    /**
     * Handles dashNorthWest logic. Sets x and y velocity to specified velocity
     */
    private void dashNorthWest() {
        this.andrieste.setYVelocity(Constants.DASH_DIAGONAL_Y_VELOCITY);
        this.andrieste.setXVelocity(-Constants.DASH_DIAGONAL_X_VELOCITY);
    }

    /**
     * returns andrieste's X position
     */
    public double getX() {
        return this.andrieste.getX();
    }

    /**
     * returns andrieste's Y position
     */
    public double getY() {
        return this.andrieste.getY();
    }
    /**
     * returns andrieste's width
     */
    public double getWidth() {
        return this.andrieste.getWidth();
    }

    /**
     * returns andrieste's height
     */
    public double getHeight() {
        return this.andrieste.getHeight();
    }

    /**
     * sets andrieste's Y velocity to entered velocity
     */
    public void setYVelocity(double yVelocity) {
        this.andrieste.setYVelocity(yVelocity);
    }

    /**
     * sets andrieste's X velocity to entered velocity
     */
    public void setXVelocity(double xVelocity) {
        this.andrieste.setXVelocity(xVelocity);
    }

    /**
     * sets andrieste's X position to entered position
     */
    public void setX(double x) {
        this.andrieste.setX(x);
    }

    /**
     * sets andrieste's X position to entered position
     */
    public void setY(double y) {
        this.andrieste.setY(y);
    }

    /**
     * calculates Andrieste's  direction that he is moving in and puts it into
     * terms of the enum AndriesteDirection. Also sets isRunning boolean
     */
    public AndriesteDirection getDirection() {
        double xVelocity = this.andrieste.getXVelocity();
        if (xVelocity == 0) {
            this.isRunning = false;
            return AndriesteDirection.NONE;
        }
        else if (xVelocity > 0) {
            this.isRunning = true;
            return AndriesteDirection.RIGHT;
        }
        else {
            this.isRunning = true;
            return AndriesteDirection.LEFT;
        }
    }
}
