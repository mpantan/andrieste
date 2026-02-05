package andrieste;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import java.util.HashSet;

/**
 * this is the spiked platform class. in retrospect, it doesn't really act as a platform
 * at all because you can't touch it at all, but it's all good.
 * Any ways, this class is really simple.
 */
public class SpikedPlatform extends Platform {
    private Game game;
    private int spikeNumber;

    /**
     * constructor. calls parents constructor and stores game and spike number and changes sprite.
     */
    public SpikedPlatform(Pane gamePane, double x, double y, double width, double height, Game game, int spikeNumber) {
        super(gamePane, x, y, width, height, false);
        this.game = game;
        this.spikeNumber = spikeNumber;
        this.changeSprite();
    }

    /**
     * This method is a bit more complicated than other changeSprites. This is because I wanted the
     * spikedPlatforms to actually look decent, so I made a few different sprites and handpicked which
     * sprite by putting a spike number as an argument.Different spike numbers set different sprites.
     */

    @Override
    public void changeSprite() {
        Image img = null;
        switch (this.spikeNumber) {
            //normal spike ratio: w = 2.71 * h
            case 1:
                img = this.getImage("sprites/spikedPlatform.png");
                break;
            //normal spiked wall ratio h = 2.71 * h
            case 2:
                img = this.getImage("sprites/spikedWall.png");
                break;

            case 3:
                img = this.getImage("sprites/spikedWall2.png");
                break;
            case 4:
                img = this.getImage("sprites/spikedPlatform2.png");
                break;
            default:
                break;
        }

        this.setSprite(img);
    }


    /**
     * Collision logic. When collides, restart level.
     */
    @Override
    public void collide(
            AndriesteMover andriesteMover,
            HashSet<KeyCode> keysPressed,
            double[] respawnCoords,
            double time) {
        this.game.restartLevel();
    }
}
