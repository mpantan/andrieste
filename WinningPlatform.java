package andrieste;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.util.HashSet;

/**
 * this is the winning platform class. it is a special platform that triggers either a new level, or going
 * back to the home screen. this happens depending on many factors that will be seen in the code below.
 */
public class WinningPlatform extends Platform{
    private Game game;
    private Difficulty difficulty;

    /**
     * constructor. call parent's constructor and store game and difficulty as instance variables.
     */
    public WinningPlatform(
            Pane gamePane, double x, double y, double width, double height, Game game, Difficulty difficulty) {
        super(gamePane, x, y, width, height, false);
        this.game = game;
        this.difficulty = difficulty;
    }

    /**
     * changes sprite to winning platform image.
     */
    @Override
    public void changeSprite() {
        Image img = this.getImage("sprites/winningPlatform.png");
        this.setSprite(img);
    }

    /**
     * collision logic.
     * uses parent's collision logic and then also adds win logic.
     */
    @Override
    public void collide(
            AndriesteMover andriesteMover,
            HashSet<KeyCode> keysPressed,
            double[] respawnCoords,
            double time) {
        super.collide(andriesteMover, keysPressed, respawnCoords, time);
        this.winLogic(andriesteMover);
    }

    /**
     * win logic. first figure out what the current level is. then, only proceed if colliding
     * with top of platform.
     * if level 0, that means it's a random level, so if increase levels beaten counter by one. if
     * counter is 0 (means they completed 10 levels), then set random level as complete, return back to
     * random level choices and set inGame boolean to false. If they have not beaten 10 levels,
     * just make a new level.
     * If they beat a hardcoded level (else), set current level as complete, return to level home screen,
     * and set in game boolean to fase.
     */
    private void winLogic(AndriesteMover andriesteMover) {
            int currentLevel = this.game.getCurrentLevel();
            if (this.getCollisionDirection(andriesteMover) == CollidingDirection.TOP) {
                if (currentLevel == 0) {
                    this.difficulty.increaseCounter();
                    if(this.difficulty.getCounter() == 0) {
                        this.game.setLevelComplete(currentLevel);
                        this.game.returnRandom();
                        this.game.setInGame(false);
                    } else{
                        this.game.newLevel(this.difficulty, currentLevel);
                    }
                } else {
                    this.game.setLevelComplete(currentLevel);
                    this.game.returnLevels();
                    this.game.setInGame(false);
                }


            }
        }
    }

