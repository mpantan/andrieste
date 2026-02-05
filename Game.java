package andrieste;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.HashSet;


/**
 * This is the game class. It is the top level class that runs the game.
 * It has instance variables of many important classes, such as the andriesteMover,
 * LevelGenerator, LevelStorer, and HomeScreenHandler. Together, the game class
 * uses these 4 classes to run the whole game. This is primarily accomplished in handleGameplay
 * method that is called every keyFrame of the timeline.
 */
public class Game {
    private Pane gamePane;
    private AndriesteMover andriesteMover;
    private LevelGenerator levelGenerator;
    private LevelStorer levelStorer;
    private Timeline timeline;
    private HashSet<KeyCode> keysPressed;
    private ArrayList<CollidableRectangle> collidables;
    private double time;
    private double pauseTime;
    private double[] respawnCoords;
    private Difficulty difficulty;
    private boolean isPaused;
    private boolean inGame;
    private HomeScreenHandler homeScreenHandler;
    private double lastPause;
    private boolean[] levelsComplete;
    private double[] levelCompletionTimes;
    private double[] levelStartTimes;
    private int currentLevel;
    private Label durationLabel;
    private Label randomCounterLabel;
    private boolean durationLabelVisable;


    /**
     * This is the constructor. It initializes classes, sets up some labels,
     * sets up keyhandling, initialize instance variable values, and then starts
     * the timeline.
     *
     * @param gamePane
     */
    public Game(Pane gamePane) {
        this.gamePane = gamePane;
        this.gamePane.setFocusTraversable(true);
        this.initializeClasses();
        this.setupDurationLabel();
        this.setupCounterLabel();
        this.setUpKeys();
        this.initializeValues();
        this.gameplayTimeline();
    }

    /**
     * This helper method just initializes classes and stores them as instance variables.
     */
    private void initializeClasses() {
        this.keysPressed = new HashSet<>();
        this.homeScreenHandler = new HomeScreenHandler(this, this.gamePane);
        this.collidables = new ArrayList<>();
        this.levelGenerator = new LevelGenerator(gamePane, this);
        this.levelStorer = new LevelStorer();
        this.andriesteMover = new AndriesteMover(this);
    }

    /**
     * This helper method just initializes instance variable values.
     */
    private void initializeValues() {
        this.respawnCoords = new double[2];
        double[] pastChange = new double[2];
        pastChange[0] = 0;
        pastChange[1] = 0;
        this.difficulty = Difficulty.HARD;
        this.isPaused = true;
        this.inGame = false;
        this.durationLabelVisable = false;
        this.lastPause = -1;
        this.levelsComplete = new boolean[5];
        for (int i = 0; i < 5; i++) {
            this.levelsComplete[i] = false;
        }
        this.levelStartTimes = new double[5];
        for (int i = 0; i < 5; i++) {
            this.levelStartTimes[i] = 0;
        }
        this.levelCompletionTimes = new double[5];
        for (int i = 0; i < 5; i++) {
            this.levelCompletionTimes[i] = 1000;
        }
        this.pauseTime = 0.0;
    }

    /**
     * This method sets up the counter label for random generation levels.
     */
    private void setupCounterLabel() {
        this.randomCounterLabel = new Label("Levels Beaten: 0");
        this.randomCounterLabel.setStyle("-fx-font-size: 25");
        this.randomCounterLabel.setTextFill(Color.WHITE);
    }

    /**
     * This method returns an array of type boolean corresponding to which
     * of the 5 levels have been beaten.
     * 0 = level 1, 1 = level 2, etc.
     */
    public boolean[] getLevelsComplete() {
        return this.levelsComplete;
    }


    /**
     * this method is called when a level is completed or when
     * 10 random levels are beaten.
     * if level = 0, then it's a random level so call
     * handleTenRandomLevelsComplete, if not random,
     * set hardcoded level as complete.
     */
    public void setLevelComplete(int level) {
        if (level == 0) {
            this.handleTenRandomLevelsComplete();
        } else {
            this.handleHardcodedLevelComplete(level);
        }
    }

    /**
     * This is called when 10 levels are beaten of random levels.
     * It sets the difficulty's boolean of if ten levels have been completed to true.
     * then it gets the time it took to beat the 10 levels, and if that time is less
     * than the past completion time, it sets the completion time to that new time.
     */
    private void handleTenRandomLevelsComplete() {
        this.difficulty.setTenLevelsComplete(true);
        double newTime = this.time - this.difficulty.getTenLevelDurationStartTime();
        if (this.difficulty.getTenLevelsCompleteTime() > newTime) {
            this.difficulty.setTenLevelsCompleteTime(newTime);
            String formatTime = String.format("%.2f", newTime);
            this.difficulty.setLabelTime(formatTime);
        }
    }

    /**
     * This is called when one of the hardcoded levels are beaten.
     * It sets the current level as completed.
     * then it gets the time it took to beat the level, and if that time is less
     * than the past completion time, it sets the completion time to that new time.
     */
    private void handleHardcodedLevelComplete(int level) {
        this.levelsComplete[level - 1] = true;
        double newTime = this.time - this.levelStartTimes[level - 1];
        if (this.levelCompletionTimes[level - 1] > newTime) {
            this.levelCompletionTimes[level - 1] = newTime;
            String formatTime = String.format("%.2f", newTime);
            this.homeScreenHandler.setLabelTime(level - 1, formatTime);
        }
    }

    /**
     * helper method to set up duration level
     */
    private void setupDurationLabel() {
        this.durationLabel = new Label();
        this.durationLabel.setLayoutX(Constants.DURATION_LABEL_X);
        this.durationLabel.setTextFill(Color.WHITE);
        this.durationLabel.setStyle("-fx-font-size: 25;");
    }

    /**
     * sets the current difficulty's start time to the current time
     */
    public void setDifficultyStartTime() {
        this.difficulty.setTenLevelDurationStartTime(this.time);
    }

    /**
     * method that calls homeScreenHandler's return levels method.
     */
    public void returnLevels() {
        this.homeScreenHandler.returnLevels();
    }

    /**
     * method that calls homeScreenHandler's return choices method.
     */
    public void returnChoices() {
        this.homeScreenHandler.returnChoices();
    }

    /**
     * method that calls homeScreenHandler's return random method.
     */
    public void returnRandom() {
        this.homeScreenHandler.returnRandom();
    }

    /**
     * method that calls andriesteMovers's show Andrieste method.
     */
    public void showAndrieste() {
        this.andriesteMover.showAndrieste(this.gamePane);
    }

    /**
     * method that calls andriesteMovers's hide Andrieste method.
     */
    public void hideAndrieste() {
        this.andriesteMover.hideAndrieste(this.gamePane);
    }

    /**
     * setter method for isPaused boolean
     */
    public void setPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    /**
     * setter method for inGame boolean
     */
    public void setInGame(boolean isInGame) {
        this.inGame = isInGame;
    }

    /**
     * returns current level number
     */
    public int getCurrentLevel() {
        return this.currentLevel;
    }

    /**
     * adds the counter label to the gamePane
     */
    public void addCounterLabelToPane() {
        this.gamePane.getChildren().add(this.randomCounterLabel);
    }

    /**
     * removes the counter label from gamePane
     */
    public void removeCounterLabelFromPane() {
        this.gamePane.getChildren().remove(this.randomCounterLabel);
    }

    /**
     * This method is called whenever the game wants to play the next level.
     * it sets the difficulty to the provided difficulty. it removes every collidable
     * rectangle from the gamePane and clears the collidable arrayList ( graphically + logically!)
     * if level = 0, it's a random level, so create new random level.
     * if not random level, create new hardcoded level.
     * Finally, reset andrieste's states and reset all the times.
     */
    public void newLevel(Difficulty difficulty, int level) {
        this.difficulty = difficulty;
        for (CollidableRectangle collidable : this.collidables) {
            collidable.removeFromGamePane(this.gamePane);
        }
        this.collidables.clear();
        if (level == 0) {
            this.newRandomLevel(difficulty);
            this.randomCounterLabel.setText("Levels Beaten:  " + difficulty.getCounter());
        } else {
            this.newHardcodedLevel(level);
        }
        this.andriesteMover.resetAndrieste(this.respawnCoords);
        this.andriesteMover.clearAndriesteStates();
        this.currentLevel = level;
        this.resetTimes();
    }

    /**
     * this method resets the start times of a level.
     * if it's a random level, only reset the times if they haven't beaten a level yet.
     * if it's not a random level, just reset the time.
     */
    private void resetTimes() {
        if (this.currentLevel == 0) {
            if (this.difficulty.getCounter() == 0) {
                this.difficulty.setTenLevelDurationStartTime(this.time);
            }
        } else {
            this.levelStartTimes[this.currentLevel - 1] = this.time;
        }
    }

    /**
     * This method creates a new Random level.
     * It makes the level generator generate an arraylist of collidables based on a requested
     * difficulty. Then it gets the respawn coordinates for Andrieste based on that level.
     */
    private void newRandomLevel(Difficulty difficulty) {
        this.collidables = this.levelGenerator.generateRandomLevel(difficulty);
        this.respawnCoords = this.levelGenerator.getRespawnCoords();
    }

    /**
     * This method creates a new hardcoded level based on the requested level.
     * It's just a switch case that gets the arraylist of collidables for the requested level.
     * finally, gets respawn coordinates for given level.
     */
    private void newHardcodedLevel(int level) {
        switch (level) {
            case 1:
                this.collidables = this.levelStorer.getLevelOne(this.gamePane, this);
                break;
            case 2:
                this.collidables = this.levelStorer.getLevelTwo(this.gamePane, this);
                break;
            case 3:
                this.collidables = this.levelStorer.getLevelThree(this.gamePane, this);
                break;
            case 4:
                this.collidables = this.levelStorer.getLevelFour(this.gamePane, this);
                break;
            case 5:
                this.collidables = this.levelStorer.getLevelFive(this.gamePane, this);
                break;
            case 6:
                this.collidables = this.levelStorer.getLevelSix(this.gamePane, this);
            default:
                break;

        }
        this.respawnCoords = this.levelStorer.getRespawnCoords();
    }


    /**
     * method to restart level. This is called when Andrieste falls of the screen, when
     * he hits a spikedPlatform, or when "R" is pressed.
     * Just reset all platforms and respawn andrieste
     */
    //reset all platforms and reset andrieste
    public void restartLevel() {
        this.resetCollidables();
        this.andriesteMover.resetAndrieste(this.respawnCoords);
    }

    /**
     * method to reset all collidables. only need to reset some platforms.
     * how the resetting works is we just remove the platform and then add it back
     * in its original state. so , see which collidables to remove and store the old state.
     * remove the collidables and then add the collidables back to our collidables ArrayList.
     */
    private void resetCollidables() {
        ArrayList<CollidableRectangle> toRemove = new ArrayList<>();
        ArrayList<CollidableRectangle> toAdd = new ArrayList<>();
        for (CollidableRectangle collidable : this.collidables) {
            CollidableRectangle resetPlatform = collidable.reset(this.gamePane);
            if (resetPlatform != null) {
                toRemove.add(collidable);
                toAdd.add(resetPlatform);
            }
        }

        for (CollidableRectangle collidable : toRemove) {
            this.collidables.remove(collidable);
        }
        this.collidables.addAll(toAdd);
    }

    /**
     * helper method to clear a level from the game. Removes them graphically from gamePane
     * and then logically from ArrayList.
     */
    private void clearLevel() {
        for (CollidableRectangle collidable : this.collidables) {
            collidable.removeFromGamePane(this.gamePane);
        }
        this.collidables.clear();
    }


    /**
     * this method completely removes a level. It clears the level and then also
     * makes sure andrieste is hidden, and sets inGame to false.
     */
    public void removeLevel() {
        this.clearLevel();
        this.hideAndrieste();
        this.inGame = false;
    }


    /**
     * This method sets up the timeline.
     * Each keyframe, it handles the gameplay and handles the time.
     */
    private void gameplayTimeline() {
        this.timeline = new Timeline();
        KeyFrame kf = new KeyFrame(Duration.seconds(Constants.DURATION), ActionEvent -> {
            this.handleGameplay();
            this.handleTime();
        });
        this.timeline.getKeyFrames().add(kf);
        this.timeline.setCycleCount(Timeline.INDEFINITE);
        this.timeline.play();
    }


    /**
     * this method handles the time during the game. The pause time always runs,
     * as that is only to used as a delay for hitting "P" to pause the game.
     * The "time" variable keeps track of time playing in the game.
     * If in game and UNPAUSED, increase the time.  Also make sure duration label
     * is visible if in game. Calculate duration time for the given level.
     * If NOT in game, and duration label is still visible,remove duration label.
     */
    private void handleTime() {
        this.pauseTime += Constants.DURATION;
        if (inGame && !isPaused) {
            if (!this.durationLabelVisable) {
                this.gamePane.getChildren().add(this.durationLabel);
                this.durationLabelVisable = true;
            }
            this.time += Constants.DURATION;
            double levelDuration;
            if (this.currentLevel == 0) {
                //TEN LEVEL DURATION
                levelDuration = this.time - this.difficulty.getTenLevelDurationStartTime();
            } else {
                levelDuration = this.time - this.levelStartTimes[this.currentLevel - 1];
            }
            String formattedDuration = String.format("%.2f", levelDuration);
            this.durationLabel.setText(formattedDuration);
        } else if (!this.inGame && this.durationLabelVisable) {
            this.gamePane.getChildren().remove(this.durationLabel);
            this.durationLabelVisable = false;
        }
    }

    /**
     * This method handles the main gameplay look.
     * If not paused and in game, handle hash set logic to move andrieste,
     * update andrieste's position, handle all collisions, and update andrieste's sprite.
     * If JUST in game, handle Pause hash set logic (able to unpause by clicking ESC/P)
     */
    private void handleGameplay() {
        if (!this.isPaused && inGame) {
            this.handleHashSetLogic();
            this.andriesteMover.updatePosition(this.time);
            this.handleCollision();
            this.andriesteMover.updateSprite();
        }
        if (inGame) {
            this.handlePauseHashSetLogic();
        }
    }


    /**
     * Method to handle collisions with Andrieste and Collidable rectangles.
     * Loop through each collidable in the collidable arrayList. Check if colliding with andrieste,
     * if colliding, then collide. Also keep track of how many collisons there are. If NO collisions,
     * set andrieste to NOT grounded (airborne) and set wall climb direction to none.
     */
    private void handleCollision() {
        //need to create a copy to loop over incase new level is being created
        ArrayList<CollidableRectangle> collidablesCopy = new ArrayList<>(this.collidables);
        int collisions = 0;
        for (CollidableRectangle collidable : collidablesCopy) {
            if (collidable.checkCollision(this.andriesteMover)) {
                collidable.collide(this.andriesteMover, this.keysPressed, this.respawnCoords, this.time);
                collisions++;
            }
        }
        if (collisions == 0) {
            this.andriesteMover.setIsGrounded(false);
            this.andriesteMover.setWallClimbDirection(WallClimbDirection.NONE);
        }
    }


    /**
     * Helper method to get the dashing direction. Is called when user wants
     * to dash. If user is using WASD keys, then find dash direction with WASD keys.
     * If not using WASD keys, get dash direction with arrow keys.
     */
    private DashingDirection getDashDirection() {
        if (this.keysPressed.contains(KeyCode.A)
                || this.keysPressed.contains(KeyCode.D)
                || this.keysPressed.contains(KeyCode.W)
                || this.keysPressed.contains(KeyCode.S)) {
            return helperDashDirection(KeyCode.A, KeyCode.D, KeyCode.W, KeyCode.S);
        } else {
            return helperDashDirection(KeyCode.LEFT, KeyCode.RIGHT, KeyCode.UP, KeyCode.DOWN);
        }

    }

    /**
     * Huge if statement list to figure out which direction user wants to dash in.
     * Not very complicated, just have to figure out all 8 directions based on
     * key inputs.
     */
    private DashingDirection helperDashDirection(KeyCode left, KeyCode right,
                                                 KeyCode up, KeyCode down) {
        if (this.keysPressed.contains(up)) {
            if (this.keysPressed.contains(left)) {
                return DashingDirection.NORTH_WEST;
            } else if (this.keysPressed.contains(right)) {
                return DashingDirection.NORTH_EAST;
            } else if (this.keysPressed.contains(down)) {
                return DashingDirection.EAST;
            } else {
                return DashingDirection.NORTH;
            }

        } else if (this.keysPressed.contains(down)) {
            if (this.keysPressed.contains(left)) {
                return DashingDirection.SOUTH_WEST;
            } else if (this.keysPressed.contains(right)) {
                return DashingDirection.SOUTH_EAST;
            } else {
                return DashingDirection.SOUTH;
            }

        } else if (this.keysPressed.contains(left)) {
            if (this.keysPressed.contains(right)) {
                return DashingDirection.EAST;
            } else {
                return DashingDirection.WEST;
            }

        } else if (this.keysPressed.contains(right)) {
            return DashingDirection.EAST;

        } else {
            return DashingDirection.EAST;
        }
    }


    /**
     * Handles pausing and unpausing the game with escape/P.
     * This needs a cooldown so pauseTIme and lastPause are factored in
     * to ensure cooldown.
     */
    private void handlePauseHashSetLogic() {
        //add COOLDOWN
        if (this.keysPressed.contains(KeyCode.ESCAPE) || this.keysPressed.contains(KeyCode.P)) {
            if (this.pauseTime - this.lastPause > Constants.PAUSE_COOLDOWN) {
                if (!this.isPaused) {
                    this.homeScreenHandler.handlePauseScreen();
                } else {
                    this.homeScreenHandler.handleUnpauseScreen();
                }
                this.lastPause = this.pauseTime;
            }
        }
    }

    /**
     * Long list of if statements to handle logic. Sees which keys are being presses
     * by looking at the HashSet. Besides that, it's pretty simple.
     */
    private void handleHashSetLogic() {
        //Handles game restarting with R. IF R + CONTROL --> generate new random level
        if (this.keysPressed.contains(KeyCode.R)) {
            if (this.inGame) {
                if (this.keysPressed.contains(KeyCode.CONTROL)) {
                    this.difficulty.resetCounter();
                    this.newLevel(this.difficulty, this.currentLevel);
                } else {
                    this.restartLevel();
                    this.resetTimes();
                    this.andriesteMover.clearAndriesteStates();
                }
            }
        }

        // handles jumping : space = jump
        if (this.keysPressed.contains(KeyCode.SPACE)) {
            this.andriesteMover.jumpAndrieste(this.time);
        }

        // handles rewinding. make sure to check Collision: tab = rewind
        if (this.keysPressed.contains(KeyCode.TAB)) {
            this.andriesteMover.rewindAndrieste();
            this.handleCollision();
        }

        // dashing logic
        if (this.keysPressed.contains(KeyCode.SHIFT)) {
            this.andriesteMover.dash(this.time, getDashDirection());
        }

        // handles moving left and right. If using WASD keys, move with WASD, if not use arrows
        // also makes andrieste slow down if not pressing any keys
        if (this.keysPressed.contains(KeyCode.A) || this.keysPressed.contains(KeyCode.D)) {
            this.movingHelper(KeyCode.A, KeyCode.D);
        } else {
            this.movingHelper(KeyCode.LEFT, KeyCode.RIGHT);
        }
    }


    /**
     * Handles moving andreiste with the given left and right key codes.
     * If both left and right are pressed at same time, slow down.
     * If only pressing left, move left.
     * If only pressing right, move right.
     * if user is not pressing any keys, slow down
     */
    private void movingHelper(KeyCode left, KeyCode right) {
        if (this.keysPressed.contains(left) && this.keysPressed.contains(right)) {
            this.andriesteMover.slowDownHorizontally();
        } else if (this.keysPressed.contains(left)) {
            this.andriesteMover.moveAndriesteLeft();
        } else if (this.keysPressed.contains(right)) {
            this.andriesteMover.moveAndriesteRight();
        } else {
            this.andriesteMover.slowDownHorizontally();

        }
    }

    /**
     * method that sets up key press logic for the game. Basically, if key is pressed,
     * add the key to the hashset containing all the current keys that are pressed.
     * When key is released, remove key from hashset.
     */
    private void setUpKeys() {
        this.gamePane.setOnKeyPressed((KeyEvent e) -> {
            this.keysPressed.add(e.getCode());
            e.consume();
        });
        this.gamePane.setOnKeyReleased((KeyEvent e) -> {
            this.keysPressed.remove(e.getCode());
            e.consume();
        });
    }
}