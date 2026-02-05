package andrieste;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;


/**
 * This is the animator class. It handles the sprite that is overlayed on top
 * of Andrieste's hitbox. It figures out which image to put on the hitbox
 * and does so.
 */
public class Animator {
    private ImageView sprite;
    private Image[] wallClimbImages;
    private Image[] idleImages;
    private Image[] jumpImages;
    private Image[][] runImages;
    private Image dashImage;
    private int runCounter;
    private int nextFrameCounter;

    /**
     * This is the constructor. It creates and setups the sprite, stores all the different sprites
     * in Image arrays, and initializes some variables.
     */
    public Animator (AndriesteMover andriesteMover) {
        this.sprite = new ImageView();
        this.setupImages();
        this.setupSprite(andriesteMover);
        this.runCounter = 0;
        this.nextFrameCounter = 0;
    }

    /**
     * this method hides the sprite by removing it from the gamePane.
     */
    public void hideSprite(Pane gamePane) {
        gamePane.getChildren().remove(this.sprite);
    }
    /**
     * this method shows the sprite by adding it to the gamePane.
     */
    public void showSprite(Pane gamePane) {
        gamePane.getChildren().add(this.sprite);
    }

    /**
     * this method sets up the sprite by setting the height and width to andrieste's
     * height and width, and setting its coordinates.
     */
    private void setupSprite(AndriesteMover andriesteMover) {
        this.sprite = new ImageView(this.idleImages[0]);
        this.sprite.setX(andriesteMover.getX());
        this.sprite.setY(andriesteMover.getY());
        this.sprite.setFitWidth(andriesteMover.getWidth());
        this.sprite.setFitHeight(andriesteMover.getHeight());
    }

    /**
     * This method stores the images of each different sprite into arrays so they are easily
     * accessible for animation.
     */
    private void setupImages() {
        this.wallClimbImages = new Image[2];
        this.wallClimbImages[0] = getImage("sprites/wallClimb.png");
        this.wallClimbImages[1] = getImage("sprites/noDashWallClimb.png");

        this.idleImages = new Image[2];
        this.idleImages[0] = getImage("sprites/idle.png");
        this.idleImages[1] = getImage("sprites/noDashIdle.png");

        this.jumpImages = new Image[2];
        this.jumpImages[0] = getImage("sprites/jump.png");
        this.jumpImages[1] = getImage("sprites/noDashJump.png");

        this.runImages = new Image[2][4];
        this.runImages[0][0] = getImage("sprites/run1.png");
        this.runImages[0][1] = getImage("sprites/run2.png");
        this.runImages[0][2] = getImage("sprites/run3.png");
        this.runImages[0][3] = getImage("sprites/run4.png");
        this.runImages[1][0] = getImage("sprites/noDashRun1.png");
        this.runImages[1][1] = getImage("sprites/noDashRun2.png");
        this.runImages[1][2] = getImage("sprites/noDashRun3.png");
        this.runImages[1][3] = getImage("sprites/noDashRun4.png");

        this.dashImage = getImage("sprites/dash.png");
    }

    /**
     * this method handles the animation of andrieste. It updates the sprite's
     * location to the location of andrieste. And then it basically checks all the
     * states that Andrieste could be in. With this information, a few if statements
     * are ran to see which image should be overlaid over the hitbox of andrieste.
     * Also, the direction the sprite is facing is also calculated.
     */
    public void updateSprite(AndriesteMover andriesteMover) {
        WallClimbDirection wallClimbDirection = andriesteMover.getWallClimbDirection();
        if (wallClimbDirection == WallClimbDirection.NONE) {
            this.handleFacingDirection(andriesteMover);
        }
        this.sprite.setX(andriesteMover.getX());
        this.sprite.setY(andriesteMover.getY());

        boolean canDash = andriesteMover.getCanDash();
        boolean isDashing = andriesteMover.getIsDashing();
        boolean isGrounded = andriesteMover.getIsGrounded();
        boolean isRunning = andriesteMover.getIsRunning();


        //  This is where the if statements are ran to see which image should be overlaid.
        if (isDashing) {
            this.sprite.setImage(this.dashImage);
        } else if (wallClimbDirection != WallClimbDirection.NONE) {
            this.handleClimbing(canDash, wallClimbDirection);
        }else if (!isGrounded) {
            this.handleJumping(canDash);
        }  else if(isRunning) {
            this.handleRunning(canDash);
        } else{
            this.handleIdling(canDash);
        }
    }

    /**
     * if Andrieste is idling, set sprite to Idle image based on if
     * dash is available or not.
     */
    private void handleIdling(boolean canDash) {
        if (canDash) {
            this.sprite.setImage(idleImages[0]);
        } else {
            this.sprite.setImage(idleImages[1]);
        }
    }

    /**
     * Here is the method to handle the running animation.
     * I designed 4 different frames for the running animation.
     * We don't want the sprite to change every frame because that doesn't look good,
     * so we only update the runCounter when the nextFrameCounter is divisible by
     * a constant I picked. Next, calculate the remainder of runCounter/4 (gives 4 different
     * options: 0,1,2,3). Pass the remainder into the runningHelper method to get
     * the image to overlay on the hitbox.
     */
    private void handleRunning(boolean canDash) {
        //we are grounded and not on wall and RUNNING
        this.nextFrameCounter++;
        if(this.nextFrameCounter % Constants.RUNNING_ANIMATION_FRAME_UPDATE == 0) {
            this.runCounter++;
        }
        int remainder = this.runCounter % 4;
        this.runningHelper(canDash, remainder);
        }

    /**
     *Picks the running image frame to overlay over the hitbox based on the remainder
     * of the runCounter and if andrieste can dash.
     */
    private void runningHelper(boolean canDash, int remainder) {
        if (canDash) {
            this.sprite.setImage(runImages[0][remainder]);
        } else {
            this.sprite.setImage(runImages[1][remainder]);
        }
    }

    /**
     * if Andrieste is jumping, set sprite to jumping image based on if
     * dash is available or not.
     */
    private void handleJumping(boolean canDash) {
        if(canDash) {
            this.sprite.setImage(this.jumpImages[0]);
        } else {
            this.sprite.setImage(this.jumpImages[1]);
        }
    }

    /**
     * if Andrieste is climbing, set sprite to Idle image based on if
     * dash is available or not.Also, climbing is special because we need to check
     * which way to face the image in this method. If climbing left, flip image.
     * if climbing right, keep it normal.
     */
    private void handleClimbing(boolean canDash, WallClimbDirection wallClimbDirection) {
        if(canDash) {
            this.sprite.setImage(wallClimbImages[0]);
        } else {
            this.sprite.setImage(wallClimbImages[1]);
        }
        //flip image if climbing on left
        if (wallClimbDirection == WallClimbDirection.LEFT) {
            this.sprite.setScaleX(-1);
        } else {
            this.sprite.setScaleX(1);
        }
    }

    /**
     * Figures out which direction to face the image. If andrieste is moving right,
     * face right (keep image normal orientation)
     * if moving left, flip image to make it face left.
     */
    private void handleFacingDirection(AndriesteMover andriesteMover) {
        AndriesteDirection direction = andriesteMover.getDirection();
         if (direction == AndriesteDirection.RIGHT) {
            this.sprite.setScaleX(1);
        } else if  (direction == AndriesteDirection.LEFT) {
            this.sprite.setScaleX(-1);
        }
    }

    /**
     * helper method to get the image by passing in a string. helps speed up process
     */
    private Image getImage(String imageName) {
        return new Image(this.getClass().getResourceAsStream(imageName));
    }
}
