package andrieste;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * enum class that handles a lot of logic based on which difficulty is selected
 * for the random level generation. This class helps randomize width of platforms,
 * heights of walls, and also stores logic to handle the time it takes to complete 10 random levels
 * of the given difficulty. Most of this class is just to store important values that
 * other classes then use.
 */
public enum Difficulty {
    EASY(
            Constants.EASY_PLATFORM_MAX_WIDTH,
            Constants.EASY_PLATFORM_MIN_WIDTH,
            Constants.EASY_RAND_SLOPE,
            Constants.EASY_WALL_MAX_HEIGHT,
            Constants.EASY_WALL_MIN_HEIGHT,
            Constants.EASY_LABEL_X,
            Constants.EASY_LABEL_Y
    ),
    MEDIUM(
            Constants.MEDIUM_PLATFORM_MAX_WIDTH,
            Constants.MEDIUM_PLATFORM_MIN_WIDTH,
            Constants.MEDIUM_RAND_SLOPE,
            Constants.MEDIUM_WALL_MAX_HEIGHT,
            Constants.MEDIUM_WALL_MIN_HEIGHT,
            Constants.MEDIUM_LABEL_X,
            Constants.MEDIUM_LABEL_Y
    ),
    HARD(
            Constants.HARD_PLATFORM_MAX_WIDTH,
            Constants.HARD_PLATFORM_MIN_WIDTH,
            Constants.HARD_RAND_SLOPE,
            Constants.HARD_WALL_MAX_HEIGHT,
            Constants.HARD_WALL_MIN_HEIGHT,
            Constants.HARD_LABEL_X,
            Constants.HARD_LABEL_Y
    );

    private double platformMaxWidth;
    private double platformMinWidth;
    private double maxRandSlope;
    private double wallMaxHeight;
    private double wallMinHeight;
    private int levelsCompletedCounter;
    private double tenLevelDurationStartTime;
    private double tenLevelsCompleteTime;
    private boolean tenLevelsComplete;
    private Label tenLevelsDurationLabel;

    /**
     * constructor for enum. each difficulty stores the values into instance
     * varaibles. creates tenLevels duration label. initializes values for tenLevels duration
     * logic.

     */
    Difficulty(
        double platformMaxWidth, double platformMinWidth,
        double pickRandDirectionSlope, double wallMaxHeight, double wallMinHeight,
        double labelX, double labelY) {
        this.setupTenLevelsDurationLabel(labelX, labelY);
        this.platformMaxWidth = platformMaxWidth;
        this.platformMinWidth = platformMinWidth;
        this.maxRandSlope = pickRandDirectionSlope;
        this.wallMaxHeight = wallMaxHeight;
        this.wallMinHeight = wallMinHeight;

        this.levelsCompletedCounter = 0;
        this.tenLevelsCompleteTime = 100000;
        this.tenLevelsComplete = false;
        this.tenLevelDurationStartTime = 0;
    }

    /**
     * helper method to create the ten levels duration label.
     */
    private void setupTenLevelsDurationLabel(double labelX, double labelY) {
        this.tenLevelsDurationLabel = new Label();
        this.tenLevelsDurationLabel.setLayoutX(labelX);
        this.tenLevelsDurationLabel.setLayoutY(labelY);
        this.tenLevelsDurationLabel.setStyle("-fx-font-size: 20");
        this.tenLevelsDurationLabel.setTextFill(Color.WHITE);
}

    /**
     * adds tenLevels duration label to given pane.
     */
    public void addLabelToPane(Pane pane) {
        pane.getChildren().add(this.tenLevelsDurationLabel);
    }

    /**
     * removes tenLevels duration label from the given pane.
     */
    public void removeLabelFromPane(Pane pane) {
        pane.getChildren().remove(this.tenLevelsDurationLabel);
    }

    /**
     * returns tenLevel Duration starttime
     */
    public double getTenLevelDurationStartTime() {
        return this.tenLevelDurationStartTime;
    }

    /**
     * sets tenLevel duration start time
     */
    public void setTenLevelDurationStartTime(double time) {
        this.tenLevelDurationStartTime = time;
    }

    /**
     * set ten levels complete boolean to provide boolean
     */
    public void setTenLevelsComplete(boolean bool) {
        this.tenLevelsComplete = bool;
    }

    /**
     * returns tenLevelsComplete boolean
     */
    public boolean getTenLevelsComplete(){
        return this.tenLevelsComplete;
    }

    /**
     * increases levelsCompleted Counter. If counter reaches 10, reset counter to 0.
     */
    public void increaseCounter() {
        this.levelsCompletedCounter++;
        if (this.levelsCompletedCounter ==10) {
            this.levelsCompletedCounter = 0;
        }
    }

    /**
     * resets levelsCompleted counter to 0
     */
    public void resetCounter() {
        this.levelsCompletedCounter = 0;
    }

    /**
     * set label to given string.
     */
    public void setLabelTime(String formatTime) {
        this.tenLevelsDurationLabel.setText(formatTime);
    }

    /**
     * returns the time it took to complete ten levels.
     * returns a double
     */
    public double getTenLevelsCompleteTime() {
        return this.tenLevelsCompleteTime;
    }

    /**
     * set amount of time it took to complete ten levels
     */
    public void setTenLevelsCompleteTime(double time) {
        this.tenLevelsCompleteTime = time;
    }

    /**
     * returns counter of how many levels have been completed
     */
    public int getCounter() {
        return this.levelsCompletedCounter;
    }

    /**
     * returns random width based on the max and min width of this difficulty
     */
    public double getRandomWidth() {
        double rand = Math.random();
        double max = this.platformMaxWidth;
        double min = this.platformMinWidth;
        double diff = max-min;
        return rand*diff + min;
    }

    /**
     * returns max random slope
     */
    public double getMaxRandSlope() {
        return this.maxRandSlope;
    }

    /**
     * returns random wall height in between the max and min wall height of the current
     * difficulty
     */
    public double getRandomWallHeight() {
        double rand = Math.random();
        double max = this.wallMaxHeight;
        double min = this.wallMinHeight;
        double diff = max-min;
        return rand*diff + min;
    }

}

