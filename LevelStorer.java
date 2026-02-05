package andrieste;

import javafx.scene.layout.Pane;
import java.util.ArrayList;

/**
 * This is the level storer class. It just is where the hardcoded levels are stored.
 * There is no fancy algorithm or anything. I just went and created 5 custom levels
 * and stored them in this class.
 * I do know that there's a bunch of magic numbers in this class, but all of these numbers
 * are just randomly chosen by me with just trial and error. I would need to make
 * a lot of constants, and I was too lazy to do that, so I decided not to. Hopefully that's okay.
 */
public class LevelStorer {
    private double[] respawnCoords;

    /**
     * Constructor. Just initialized respawn coords.
     */
    public LevelStorer() {
        this.respawnCoords = new double[2];
    }

    /**
     * returns the array of respawn coordinates.
     */
    public double[] getRespawnCoords() {
        return respawnCoords;
    }

    /**
     * Creates the arrayList of the collidable rectangles of level one and returns them.
     * Also sets changes respawn coordinates.
     */
    public ArrayList<CollidableRectangle> getLevelOne(Pane gamePane, Game game) {
        ArrayList<CollidableRectangle> collidables = new ArrayList<>();
        collidables.add(new Platform(gamePane,0,Constants.SCENE_HEIGHT/2,100,20,false));
        collidables.add(new ResetDashOrb(gamePane, 275, 360 - 250));
        collidables.add(new SpikedPlatform(gamePane,287.5 -100,160, 200, 74, game, 1));
        collidables.add(new Platform(gamePane,287.5-25,180 +54,50,300-54,true));
        collidables.add(new Platform(gamePane,287.5+100,460,100,20,false));
        collidables.add(new Platform(gamePane,625,20,25,410,true));
        collidables.add(new Platform(gamePane,800,130,25,350,true));
        collidables.add(new SlidingPlatform(gamePane, 875, 500, 100, 20, SlidingDirection.RIGHT));
        collidables.add(new WinningPlatform(
                gamePane, Constants.SCENE_WIDTH - 100, 300,100,20,game, Difficulty.EASY));
        collidables.add(new SpikedPlatform(gamePane,Constants.SCENE_WIDTH - 100,320, 74, 200, game,
                2));

        this.respawnCoords[0] = 0;
        this.respawnCoords[1] = Constants.SCENE_HEIGHT/2 - 100;
        return collidables;
    }

    /**
     * Creates the arrayList of the collidable rectangles of level three and returns them.
     * Also sets changes respawn coordinates.
     */
    public ArrayList<CollidableRectangle> getLevelThree(Pane gamePane, Game game) {
        ArrayList<CollidableRectangle> collidables = new ArrayList<>();
        this.respawnCoords[0] = 0;
        this.respawnCoords[1] = Constants.SCENE_HEIGHT - 200;
        collidables.add(new Platform(gamePane,0,this.respawnCoords[1] +100,100,20,false));
        collidables.add(new Platform(gamePane,250,200,37,400,true));
        collidables.add(new BouncyPlatform(gamePane,0,300,100,20));
        collidables.add(new SpikedPlatform(gamePane,250,0,37, 100, game, 2));

        collidables.add(new SpikedPlatform(gamePane,287,300,200 -37, 60, game, 1));
        collidables.add(new WinningPlatform(
                gamePane, Constants.SCENE_WIDTH - 100, 250,100,20,game, Difficulty.EASY));
        collidables.add(new ResetDashOrb(gamePane, 550,  150));
        collidables.add(new SlidingPlatform(gamePane, 275, 620, 100, 20, SlidingDirection.RIGHT));

        collidables.add(new Platform(gamePane,1000,670,100,20, false));
        //collidables.add(new SpikedPlatform(gamePane,900,450,200, 20, game));
        collidables.add(new ResetDashOrb(gamePane, 900-Constants.DASH_ORB_WIDTH/2,  325));
        collidables.add(new ResetDashOrb(gamePane, 900-Constants.DASH_ORB_WIDTH/2,  100));
        collidables.add(new Platform(gamePane,700,0,20,450, true));

        return collidables;
    }

    /**
     * Creates the arrayList of the collidable rectangles of level five and returns them.
     * Also sets changes respawn coordinates.
     */
    public ArrayList<CollidableRectangle> getLevelFive(Pane gamePane, Game game) {
        ArrayList<CollidableRectangle> collidables = new ArrayList<>();
        this.respawnCoords[0] = 0;
        this.respawnCoords[1] = Constants.SCENE_HEIGHT - 150;
        collidables.add(new Platform(gamePane,0,this.respawnCoords[1] +100,100,20,false));
        collidables.add(new SlidingPlatform(gamePane, 200, 670, 50, 20, SlidingDirection.RIGHT));
        collidables.add(new SlidingPlatform(gamePane, 700, 470, 50, 20, SlidingDirection.LEFT));
        collidables.add(new SpikedPlatform(gamePane,850 -84,200,104, Constants.SCENE_HEIGHT-200, game,
                3));
        collidables.add(new Platform(gamePane,80,250,20,100,true));
        collidables.add(new ResetDashOrb(gamePane, 250,  150));
        collidables.add(new SlidingPlatform(gamePane, 400, 125, 50, 20, SlidingDirection.RIGHT));
        collidables.add(new SpikedPlatform(gamePane,870,200,200, 20, game,4));
        collidables.add(new ResetDashOrb(gamePane, 1150,  125));
        collidables.add(new SpikedPlatform(gamePane,1070,350,Constants.SCENE_WIDTH-1070, 20, game,
                4));
        collidables.add(new ResetDashOrb(gamePane, 930,  400));
        collidables.add(new ResetDashOrb(gamePane, 1150,  550));
        collidables.add(new SpikedPlatform(gamePane,870,500,200, 20, game, 4));
        collidables.add(new WinningPlatform(
                gamePane,  870, Constants.SCENE_HEIGHT-20,100,20,game,
                Difficulty.EASY));

        return collidables;
    }


    /**
     * Creates the arrayList of the collidable rectangles of level four and returns them.
     * Also sets changes respawn coordinates.
     */
    public ArrayList<CollidableRectangle> getLevelFour(Pane gamePane, Game game) {
        ArrayList<CollidableRectangle> collidables = new ArrayList<>();
        this.respawnCoords[0] = 0;
        this.respawnCoords[1] = Constants.SCENE_HEIGHT - 200;
        collidables.add(new Platform(gamePane,0,this.respawnCoords[1] +100,100,20,false));
        collidables.add(new SlidingPlatform(gamePane, 200, 620, 50, 20, SlidingDirection.RIGHT));
        collidables.add(new ResetDashOrb(gamePane, 930,  620));
        collidables.add(new Platform(gamePane, Constants.SCENE_WIDTH-150, 620, 50, 20, false));
        collidables.add(new Platform(gamePane, Constants.SCENE_WIDTH-20, 200, 20, 200, true));
        collidables.add(new SlidingPlatform(gamePane, 950, 350, 50, 20, SlidingDirection.LEFT));
        collidables.add(new ResetDashOrb(gamePane, 650,  400));
        collidables.add(new SlidingPlatform(gamePane, 1050, 150, 50, 20, SlidingDirection.LEFT));
        collidables.add(new ResetDashOrb(gamePane, 300,  150));
        collidables.add(new SpikedPlatform(gamePane,300,200,100, 37, game, 1));
        collidables.add(new SpikedPlatform(gamePane,600,200,100, 37, game, 1));
        collidables.add(new WinningPlatform(
                gamePane,  0, 200,100,20,game,
                Difficulty.EASY));



        return collidables;
    }


    /**
     * Creates the arrayList of the collidable rectangles of level two and returns them.
     * Also sets changes respawn coordinates.
     */
    public ArrayList<CollidableRectangle> getLevelTwo(Pane gamePane, Game game) {
        ArrayList<CollidableRectangle> collidables = new ArrayList<>();
        this.respawnCoords[0] = 0;
        this.respawnCoords[1] = Constants.SCENE_HEIGHT - 200;
        collidables.add(new Platform(gamePane,0,this.respawnCoords[1] +100,100,20,false));
        collidables.add(new ResetDashOrb(gamePane, 50,  275));
        collidables.add(new Platform(gamePane,200,150,100,20,false));
        collidables.add(new Platform(gamePane,0,150,20,100,true));
        collidables.add(new Platform(gamePane,550,-100,20,100,true));
        collidables.add(new SpikedPlatform(gamePane,510,0,60, 300, game, 3));

        collidables.add(new SpikedPlatform(gamePane,360,200,60, 300, game, 3));
        collidables.add(new Platform(gamePane,675,500,75,150, true));
        collidables.add(new BouncyPlatform(gamePane,Constants.SCENE_WIDTH-100,500,100,20));
        collidables.add(new Platform(gamePane,Constants.SCENE_WIDTH-20,0,20,300,true));
        collidables.add(new ResetDashOrb(gamePane, 800,  75));
        collidables.add(new SpikedPlatform(gamePane,950 +46,250,54, 20, game, 1));
        collidables.add(new WinningPlatform(
                gamePane,  570, 100,100,20,game,
                Difficulty.EASY));
        return collidables;
    }

    /**
     * Creates the arrayList of the collidable rectangles of level six and returns them.
     * Also sets changes respawn coordinates.
     */
    public ArrayList<CollidableRectangle> getLevelSix(Pane gamePane, Game game) {
        ArrayList<CollidableRectangle> collidables = new ArrayList<>();
        this.respawnCoords[0] = 0;
        this.respawnCoords[1] = Constants.SCENE_HEIGHT - 200;
        collidables.add(new Platform(gamePane,0,this.respawnCoords[1] +100,200,20,false));
        collidables.add(new Platform(gamePane,325,550,175,20,false));
        collidables.add(new Platform(gamePane,480,200,20,350,true));
        collidables.add(new Platform(gamePane,325,0,20,400,true));
        collidables.add(new Platform(gamePane,525,550,100,20,false));
        collidables.add(new Platform(gamePane,900,550,100,20,false));
        collidables.add(new ResetDashOrb(gamePane,1000, 250));


        collidables.add(new WinningPlatform(
                gamePane,  Constants.SCENE_WIDTH - 100, 200,100,20,game,
                Difficulty.EASY));
        return collidables;
    }
}
