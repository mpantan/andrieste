package andrieste;

import javafx.scene.layout.Pane;
import java.util.ArrayList;

/**
 * This is the level generator class. It's only purpose to hold the algorithm that
 * returns an ArrayList of collidableRectangles. This algorithm generates a random
 * level that is beatable and has different properties based on what difficulty the
 * user selected.
 */
public class LevelGenerator {
    private Pane gamePane;
    private Game game;
    private double[] respawnCoords;

    /**
     * This is the constructor. It stores gamePane and game as instance varaibles
     * and initializes the respawn coordinates array.
     */
    public LevelGenerator(Pane gamePane, Game game) {
        this.gamePane = gamePane;
        this.game = game;
        this.respawnCoords = new double[2];
    }

    /**
     * This is the generate random level algorithm. It is extremely long, so I will break it
     * down throughout the method. Here's the main ideas:
     * 1. Return an ArrayList of CollidableRectangles based on the provided difficulty.
     * There are 3 difficulties: easy, medium, and hard. The main difference is the
     * distance between the platforms. As difficulty increases, the distance increases.
     * 2. Every level must be winnable. That means that this method has to generate platforms
     * so that there is always a way to make it to the winning platform. This is where a
     * lot of the complexity of this algorithm comes from.
     * 3. How does it work?
     * - first randomly pick starting platform and ending platform locations
     * -  While loop: we want to randomize the direction the next collidable rectangle
     *  is generated, but ensure that there is enough distance to be able to
     *  make it to the ending platform.
     */
    public ArrayList<CollidableRectangle> generateRandomLevel(Difficulty difficulty) {
        // Randomly generate height for starting and winning platform, add them to collidable arrayList.
        ArrayList<CollidableRectangle> collidables = new ArrayList<>();
        double startingPlatformX = 0;
        double randDouble1 = Math.random();
        double diffMaxMinHeight = Constants.MAX_PLATFORM_HEIGHT - Constants.MIN_PLATFORM_HEIGHT;
        double startingPlatformY = (diffMaxMinHeight)*randDouble1 + Constants.MIN_PLATFORM_HEIGHT;
        CollidableRectangle startingPlatform = this.createStartingPlatform(startingPlatformX, startingPlatformY);
        collidables.add(startingPlatform);

        double winningPlatformX = Constants.SCENE_WIDTH - Constants.WINNING_PLATFORM_WIDTH;
        double randDouble2 = Math.random();
        double winningPlatformY = (diffMaxMinHeight)*randDouble2 + Constants.MIN_PLATFORM_HEIGHT;
        CollidableRectangle winningPlatform =
                this.createWinningPlatform(winningPlatformX, winningPlatformY, this.game, difficulty);
        collidables.add(winningPlatform);

        boolean canReachEndingPlatform = false;

        // Initialize some important variables for the algorithm.
        double currentX = startingPlatformX +startingPlatform.getWidth();
        double currentY = startingPlatformY;
        this.respawnCoords[0] = startingPlatformX;
        this.respawnCoords[1] = startingPlatformY - Constants.ANDRIESTE_HEIGHT*2;
        CollidableRectangle currentObject = startingPlatform;
        boolean needSpawnBouncy = false;

        // WHILE LOOP! Here is where everything happens. If the user CANNOT possibly reach ending,
        // continue generating platforms.

        while (!canReachEndingPlatform) {
            double radians;

            //first get random direction
            radians = this.getRandomRadian(currentObject, difficulty, currentY);

            //then we need to check if we are able to use that direction with canPickRandomDirection method.
            // if we cannot use that direction because it will spawn a platform too low,
            // get the up direction and make sure to generate bouncy platform so player
            // can get as high as possible
            if (!this.canPickRandomDirection(
                    currentObject, winningPlatformX,winningPlatformY, difficulty, radians, currentX, currentY)){
                radians = this.getUpDirectionRadian();
                needSpawnBouncy = true;
            }

            // calculate the changeX and changeY by passing in angle, difficulty, and currentObject.
            double[] change = this.calculateNewChange(currentObject,difficulty,radians);

            // if we need to spawn a bouncy platform to go up as high as possible, spawn one
            // if not, then randomly select a collidable rectangle.
            if (needSpawnBouncy) {
                currentObject = this.createBouncyPlatform1(change[0],change[1],currentX,currentY, difficulty);
            } else {
                currentObject = this.generateRandomRectangle(change[0], change[1], currentX, currentY, difficulty);
            }

            //add currentObject to ArrayList and update variables.
            collidables.add(currentObject);
            currentX = currentObject.getX() + currentObject.getWidth();
            currentY = currentObject.getY() + currentObject.getHeight();

            // if spawn less than min platform height, set it to min platform height and update sprite
            // this only happens when we need to go up, but want to go past the min height
            if (currentY < Constants.MIN_PLATFORM_HEIGHT) {
                currentObject.setY(Constants.MIN_PLATFORM_HEIGHT);
                currentY = currentObject.getY() + currentObject.getHeight();
                currentObject.setSpriteY(currentObject.getY());

            }

            // makes sure last platform doesn't block winning platform
            if (currentX > winningPlatformX ) {
                currentObject.setX(winningPlatformX - currentObject.getWidth());
                currentObject.setSpriteX(currentObject.getX());
                currentX = currentObject.getX() + currentObject.getWidth();
            }

            // check if user can reach ending platform with method
            canReachEndingPlatform = this.checkCanReachEndingPlatform(currentObject,
                    currentX, currentY, winningPlatformX, winningPlatformY, difficulty);
            needSpawnBouncy = false;
        }

        return collidables;
    }



    /**
     * returns respawn coordinates
     */
    public double[] getRespawnCoords() {
        return this.respawnCoords;
    }

    /**
     * returns the direction we want to generate platform when we need to go up.
     */
    private double getUpDirectionRadian() {
        return Constants.UP_DIRECTION;
    }

    /**
     * returns the direction we want to generate platform when we need to go down.
     */
    private double getDownDirectionRadian() {
        return -Constants.UP_DIRECTION;
    }

    /**
     * This method first gets a random direction from -90 to 90 degrees.
     * It then passes that radian into a method to check if it will make
     * the platform to spawn offscreen. That method checks the radian and
     * we return whatever that returns.
     */
    private double getRandomRadian(
            CollidableRectangle currentObject,
            Difficulty difficulty,
            double currentY) {
        double radians = Math.toRadians(this.getRandomDirection());
        return this.handlePlatformSpawnOffscreen(currentObject,difficulty,radians,currentY);
    }

    /**
     * method to calculate the change in X and Y from where we want to spawn the
     * next platform. We get a random change from getChange but
     * need to perform some trig so the radians affects the location of the new generated
     * rectangle.  then return newChange
     */
    private double[] calculateNewChange(CollidableRectangle currentObject, Difficulty difficulty, double radians) {
        double[] coordChange = this.getChange(currentObject,difficulty);
        double changeX = coordChange[0];
        double changeY = coordChange[1];
        changeX = changeX*Math.cos(radians);
        changeY = changeY*Math.sin(radians);
        double[] newChange = new double[2];
        newChange[0] = changeX;
        newChange[1] = changeY;
        return newChange;
    }

    /**
     *This method makes sure that a platform does not spawn outside the
     * minimum and maximum platform height coordinate. This method
     * calculates where the platform would be if we do not change the passed in radians.
     * If the platform, WILL spawn offscreen, we need to change the radians to ensure
     * that doesn't happen. I found that the best way to do this is to just go down
     * if we spawn too high and go up if we spawn too low.
     */
    private double handlePlatformSpawnOffscreen(
            CollidableRectangle currentObject, Difficulty difficulty, double radians, double currentY){
        double[] change = this.calculateNewChange(currentObject,difficulty,radians);
        // if up screen, go down,
        //if below screen, go up

        double newY = change[1] + currentY;
        if (newY >Constants.MIN_PLATFORM_HEIGHT && newY < Constants.MAX_PLATFORM_HEIGHT){
            return radians;
        }
        else if (newY > Constants.MAX_PLATFORM_HEIGHT) {
            return this.getUpDirectionRadian();
        } else{
            return this.getDownDirectionRadian();
        }
    }


    /**
     * this method checks if the andrieste "can" make it to the winning platform.
     * however, it really just checks if andrieste should be capable of making
     * it to the ending platform based on what difficulty the player selected.
     * each collidable rectangle has its own max traversable distance depending on what
     * difficulty the user is playing in.
     * Any ways, this method gets the max traversable X and Y distance, and calculates
     * the distance from the current rectangle to the ending platform.
     * if the traversable X distance is greater than the actually distance, there's a
     * chance andrieste can reach the winning platform. if the winning platform is below
     * the current rectangle, then they can easily make it. However, if winning platform
     * is above, make sure that andrieste can also reach that.
     */
    private boolean checkCanReachEndingPlatform(
            CollidableRectangle currentObject, double currentX, double currentY,
            double winningPlatformX, double winningPlatformY, Difficulty difficulty) {

        double[] traversableDistance = currentObject.getMaxTraversableDistance(difficulty);
        double diffX = winningPlatformX - currentX;
        double diffY = winningPlatformY - currentY; // in negative if higher

        if (traversableDistance[0] > diffX) {
            //check if winningPlatform is ABOVE
            if (winningPlatformY < currentY) {
                //remember its inverse because Y is negative
                return traversableDistance[1] < diffY;
            } return true;
        } return false;
    }

    /**
     * just s switch statement to randomly select which type of collidable
     * rectangle to generate. there's 5 options:
     * platform, wall, reset dash orb, bouncy platform, and sliding platform.
     * easy mode ONLY spawns platform and bouncy platform so use if statements
     * to check which mode we are in.
     */
    private CollidableRectangle generateRandomRectangle(
            double x, double y, double currentX, double currentY, Difficulty difficulty) {
        CollidableRectangle newObject = null;
        int randInt = (int) (Math.random()*5);

        switch (randInt) {
            case 0:
                if(difficulty != Difficulty.EASY) {
                    newObject = this.createWall(x, y, currentX, currentY, difficulty);
                }
                else{
                    newObject = this.createPlatform1(x, y, currentX, currentY, difficulty);;
                }

                break;
            case 1:
                if(difficulty != Difficulty.EASY) {
                    newObject = this.createResetDashOrb1(x, y, currentX, currentY);
                }
                else {
                    newObject = this.createBouncyPlatform1(x, y, currentX, currentY, difficulty);
                }
                break;
            case 2:
                if(difficulty != Difficulty.EASY) {
                    newObject = this.createSlidingPlatform1(x, y, currentX, currentY, difficulty);
                }
                else{
                    newObject = this.createPlatform1(x, y, currentX, currentY, difficulty);;
                }
                break;
            case 3:
                newObject = this.createBouncyPlatform1(x, y, currentX, currentY, difficulty);
                break;
            case 4:
                newObject = this.createPlatform1(x, y, currentX, currentY, difficulty);;
                break;
            default:
                break;
        }
        return newObject;
    }


    /**
     * This is a very important method for the algorithm. It checks if we can use the
     * randomly generated radian. I've experimented with multiple different ways to do this,
     * but I found that the only one that worked was by using a "slope." Essentially, each
     * difficulty has a slope that I picked. This slope basically figures out the average
     * Y decrease (going up) the user should be able to go per each pixel of X distance.
     * So, use the slope to calculate the average Y increase, and if the Y distance between the winning platform
     * and the new object is greater than the average Y increase over the difference in X distance,  then
     * we CANNOT use that angle, and we need to go up. If the average Y increase is greater, then we know
     * that andrieste will eventually be able to make it to the winning platform, so we can use the
     * randomly selected direction.
     */
    private boolean canPickRandomDirection(
            CollidableRectangle currentObject,
            double endingX, double endingY,
            Difficulty difficulty, double radians,
            double currentX, double currentY ) {
        //check if can Pick random direction based on average slope.
        //really only care about checking if we need to go up. we can always go down w/ physics.
        double[] change = this.calculateNewChange(currentObject,difficulty,radians);
        double newX = change[0] + currentX;
        double newY = change[1]+ currentY;

        //only care if ending platform is above
        if (endingY < newY) {
            double diffX = endingX - newX;
            double diffY = newY - endingY;
            double maxAvgYIncrease = difficulty.getMaxRandSlope() * diffX;
            return !(diffY > maxAvgYIncrease);
        }
        else {
            return true;
        }
    }


    /**
     * returns a random double in between 90 and -90.
     */
    private double getRandomDirection() {
        double randDouble = Math.random();
        double degrees = Constants.RANDOM_DIRECTION_DEGREES*randDouble;
        return Constants.RANDOM_DIRECTION_DEGREES/2 - degrees;
    }

    /**
     * As mentioned earlier, each different collidable rectangle has a different distance that you can cover.
     * For example, a sliding platform can travel way more horizontal distance than a normal platform.
     * Also, the game expects the user to be able to travel different amounts depending on the difficulty.
     * IF hard mode, there will be larger distances than easy mode. ALl this method does
     * is it gets the max and min traversable distance of the current object and then randomizes
     * it to be somewhere in between that range. Then return changeX and change Y.
     */
    private double[] getChange(CollidableRectangle collidableRectangle, Difficulty difficulty) {
        double[] maxDistance = collidableRectangle.getMaxTraversableDistance(difficulty);
        double[] minDistance = collidableRectangle.getMinTraversableDistance(difficulty);
        double randDoubleX =  Math.random();
        double randDoubleY =  Math.random();
        double changeX = (maxDistance[0] - minDistance[0]) * randDoubleX + minDistance[0];
        double changeY = (maxDistance[1] - minDistance[1]) * randDoubleY + minDistance[1];
        double[] change = new double[2];
        change[0] = changeX;
        change[1] = changeY;
        return change;
    }

    /**
     * Just returns a standard platform. Width is randomized by difficulty method (getRandomWidth).
     */
    private Platform createPlatform1(double x, double y, double offsetX, double offsetY,Difficulty difficulty) {
      return new Platform(
              this.gamePane,
              x + offsetX,y + offsetY,
              difficulty.getRandomWidth(),
              Constants.NORMAL_PLATFORM_HEIGHT,
              false);
    }

    /**
     * Just returns a wall platform. Height is randomized by difficulty method (getRandomWallHeight)
     */
    private Platform createWall(double x, double y, double offsetX, double offsetY,Difficulty difficulty) {
         return new Platform(
                this.gamePane, x + offsetX,y + offsetY,
                 Constants.WALL_WIDTH,difficulty.getRandomWallHeight(), true);
    }

    /**
     * Returns standard reset dash orb.
     */
    private ResetDashOrb createResetDashOrb1(double x, double y, double offsetX, double offsetY) {
        return new ResetDashOrb(this.gamePane, x + offsetX,y + offsetY);
    }

    /**
     * returns sliding platform that slides to the right. Width is randomized based on difficulty.
     */
    private SlidingPlatform createSlidingPlatform1(
            double x, double y, double offsetX, double offsetY, Difficulty difficulty) {
        return new SlidingPlatform(
                this.gamePane,
                x + offsetX,
                y + offsetY,difficulty.getRandomWidth(),Constants.NORMAL_PLATFORM_HEIGHT, SlidingDirection.RIGHT);
    }

    /**
     * returns bouncy platform. width is randomized based on difficulty.
     */
    private BouncyPlatform createBouncyPlatform1(
            double x, double y, double offsetX, double offsetY, Difficulty difficulty) {
        return new BouncyPlatform(
                this.gamePane,
                x + offsetX,
                y + offsetY,difficulty.getRandomWidth(),Constants.NORMAL_PLATFORM_HEIGHT);
    }


    /**
     * returns winning platform.
     */
    private WinningPlatform createWinningPlatform(double x, double y, Game game, Difficulty difficulty) {
        return new WinningPlatform(
                this.gamePane, x, y,
                Constants.WINNING_PLATFORM_WIDTH, Constants.WINNING_PLATFORM_HEIGHT, game, difficulty);
    }

    /**
     * returns starting platform (just a standardized normal platform)
     */
    private Platform createStartingPlatform(double x, double y) {
        return new Platform(
                this.gamePane, x, y, Constants.STARTING_PLATFORM_WIDTH, Constants.STARTING_PLATFORM_HEIGHT, false);
    }

}
