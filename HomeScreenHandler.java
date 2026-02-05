package andrieste;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.util.ArrayList;

/**
 * this is the HomeScreenHandler class. it handles all the logic for navigating
 * the home screen. This class holds all the image views, and also has
 * arrayList of the images for each different screen. Most of the imageViews have to be
 * configured so a mouse click will call a method.
 */
public class HomeScreenHandler {
    private Game game;
    private Pane gamePane;
    private ImageView titleButtonImageView;
    private ImageView levelButtonImageView;
    private ImageView randomLevelButtonImageView;
    private ImageView tutorialLevelButtonImageView;
    private ImageView easyRandomLevelButtonImageView;
    private ImageView mediumRandomLevelButtonImageView;
    private ImageView hardRandomLevelButtonImageView;
    private ImageView oneLevelButtonImageView;
    private ImageView twoLevelButtonImageView;
    private ImageView threeLevelButtonImageView;
    private ImageView fourLevelButtonImageView;
    private ImageView fiveLevelButtonImageView;
    private ImageView backButtonImageView;
    private ImageView homeButtonImageView;
    private ImageView resumeButtonImageView;
    private ImageView quitButtonImageView;
    private ImageView oneCheckmarkImageView;
    private ImageView twoCheckmarkImageView;
    private ImageView threeCheckmarkImageView;
    private ImageView fourCheckmarkImageView;
    private ImageView fiveCheckmarkImageView;


    private ArrayList<ImageView> homeVisuals;
    private ArrayList<ImageView> choicesVisuals;
    private ArrayList<ImageView> randomLevelVisuals;
    private ArrayList<ImageView> levelChoicesVisuals;
    private ArrayList<ImageView> pauseVisuals;
    private ArrayList<ImageView> tutorialVisuals;

    private ImageView[] checkmarks;
    private Label[] timeLabels;



    /**
     * This is the constructor. It sets up arrays, images, the checkmarks, and then starts the
     * home screen.
     */
    public HomeScreenHandler(Game game, Pane gamePane){
        this.game = game;
        this.gamePane = gamePane;
        this.setupArrays();
        this.setupAllImages();
        this.setupCheckmarks();
        this.startHomeScreen();
    }

    /**
     * This method initializes all the arrayLists.
     */
    private void setupArrays() {
        this.homeVisuals = new ArrayList<>();
        this.choicesVisuals = new ArrayList<>();
        this.randomLevelVisuals = new ArrayList<>();
        this.levelChoicesVisuals = new ArrayList<>();
        this.tutorialVisuals = new ArrayList<>();
        this.pauseVisuals = new ArrayList<>();
        this.timeLabels = new Label[5];
    }

    /**
     * This method sets up the checkmark array
     */
    private void setupCheckmarks() {
        this.checkmarks = new ImageView[5];
        this.checkmarks[0] = this.oneCheckmarkImageView;
        this.checkmarks[1] = this.twoCheckmarkImageView;
        this.checkmarks[2] = this.threeCheckmarkImageView;
        this.checkmarks[3] = this.fourCheckmarkImageView;
        this.checkmarks[4] = this.fiveCheckmarkImageView;
    }

    /**
     * Sets selected level's time label to given string.
     */
    public void setLabelTime(int level, String time) {
        this.timeLabels[level].setText(time);
    }

    /**
     * Important helper method to add all imageViews in an ArrayList to the gamePane.
     */
    private void addAllToGamePane(ArrayList<ImageView> imageViews){
        for (ImageView imageView : imageViews){
            this.gamePane.getChildren().add(imageView);
        }
    }

    /**
     * Important helper method to remove all imageViews in an ArrayList from the gamePane.
     */
    private void removeAllFromGamePane(ArrayList<ImageView> imageViews){
        for (ImageView imageView : imageViews){
            this.gamePane.getChildren().remove(imageView);
        }
    }


    /**
     * Helper method to set all imageViews to null on click.
     */
    private void setAllNoClick(ArrayList<ImageView> imageViews){
        for (ImageView imageView : imageViews){
            imageView.setOnMouseClicked(null);
        }
    }

    /**
     * Removes all imageviews from a given Arraylist from gamePane and sets them all
     * to null click. Also, make sure to remove labels and checkmarks if on certain
     * level choices screen or random level choices screen.
     */
    private void removeScreen(ArrayList<ImageView> imageViews){
        this.removeAllFromGamePane(imageViews);
        this.setAllNoClick(imageViews);
        if (imageViews == this.levelChoicesVisuals){
            for (int i = 0; i < this.checkmarks.length; i++) {
                this.gamePane.getChildren().removeAll(this.checkmarks[i], this.timeLabels[i]);
            }
        }
        else if (imageViews == this.randomLevelVisuals){
            Difficulty.EASY.removeLabelFromPane(this.gamePane);
            Difficulty.MEDIUM.removeLabelFromPane(this.gamePane);
            Difficulty.HARD.removeLabelFromPane(this.gamePane);
        }
    }


    /**
     * Sets up all the imageViews in the class.
     */
    private void setupAllImages() {
        this.setupTitleImage();
        this.setupTitleButtonImage();
        this.setupLevelButtonImage();
        this.setupRandomLevelButtonImage();
        this.setupTutorialLevelButtonImage();
        this.setupEasyRandomLevelButtonImage();
        this.setupMediumRandomLevelButtonImage();
        this.setupHardRandomLevelButtonImage();
        this.setupOneLevelButtonImage();
        this.setupTwoLevelButtonImage();
        this.setupThreeLevelButtonImage();
        this.setupFourLevelButtonImage();
        this.setupFiveLevelButtonImage();
        this.setupHowToPlayImage();
        this.setupBackButtonImage();
        this.setupPauseAreaImage(); //needs to be b4 home and resume
        this.setupHomeButtonImage();
        this.setupResumeButtonImage();
        this.setupOneCheckmarkImage();
        this.setupTwoCheckmarkImage();
        this.setupThreeCheckmarkImage();
        this.setupFourCheckmarkImage();
        this.setupFiveCheckmarkImage();
        this.setupQuitButtonImage();
        this.setupLabels();
    }

    /**
     * sets up title image. adds to the arraylist of the screens it appears in.
     */
    private void setupTitleImage() {
        Image img = getImage("homeImages/andriesteTitle.png");
         ImageView andriesteTitleImageView = new ImageView(img);
        this.setupImage(andriesteTitleImageView,Constants.TITLE_X, Constants.TITLE_Y,Constants.TITLE_WIDTH);
        this.homeVisuals.add(andriesteTitleImageView);
        this.choicesVisuals.add(andriesteTitleImageView);
        this.randomLevelVisuals.add(andriesteTitleImageView);
        this.levelChoicesVisuals.add(andriesteTitleImageView);
    }

    /**
     * sets up quit button image.
     */
    private void setupQuitButtonImage() {
        Image img = getImage("homeImages/quit.png");
        this.quitButtonImageView = new ImageView(img);
        this.setupImage(this.quitButtonImageView, Constants.QUIT_X, Constants.QUIT_Y,Constants.QUIT_WIDTH);
        this.homeVisuals.add(this.quitButtonImageView);
    }
    /**
     * sets up level button image. adds to the arraylist it appears in.
     */
    private void setupLevelButtonImage() {
        Image img = getImage("homeImages/levels.png");
        this.levelButtonImageView = new ImageView(img);
        this.setupImage(this.levelButtonImageView,
                Constants.LEVEL_BUTTON_X,
                Constants.LEVEL_BUTTON_Y,
                Constants.LEVEL_BUTTON_WIDTH);
        this.choicesVisuals.add(this.levelButtonImageView);
    }

    /**
     * sets up random level button image. adds to the arraylist it appears in.
     */
    private void setupRandomLevelButtonImage() {
        Image img = getImage("homeImages/random.png");
        this.randomLevelButtonImageView = new ImageView(img);
        this.setupImage(this.randomLevelButtonImageView,
                Constants.RANDOM_LEVEL_BUTTON_X,
                Constants.RANDOM_LEVEL_BUTTON_Y,
                Constants.RANDOM_LEVEL_BUTTON_WIDTH);
        this.choicesVisuals.add(this.randomLevelButtonImageView);
    }

    /**
     * sets up tutorial level button image. adds to the arraylist it appears in.
     */
    private void setupTutorialLevelButtonImage() {
        Image img = getImage("homeImages/tutorial.png");
        this.tutorialLevelButtonImageView = new ImageView(img);
        this.setupImage(this.tutorialLevelButtonImageView,
                Constants.TUTORIAL_LEVEL_BUTTON_X,
                Constants.TUTORIAL_LEVEL_BUTTON_Y,
                Constants.TUTORIAL_LEVEL_BUTTON_WIDTH);
        this.choicesVisuals.add(this.tutorialLevelButtonImageView);
    }


    /**
     * sets up title button image. adds to the arraylist it appears in.
     */
    private void setupTitleButtonImage() {
        Image img = getImage("homeImages/titleButton.png");
        this.titleButtonImageView = new ImageView(img);
        this.setupImage
                (this.titleButtonImageView,
                Constants.TITLE_BUTTON_X,Constants.TITLE_BUTTON_Y,Constants.TITLE_BUTTON_WIDTH);
        this.homeVisuals.add(this.titleButtonImageView);

    }

    /**
     * sets up easy random level button image. adds to the arraylist it appears in.
     */
    private void setupEasyRandomLevelButtonImage() {
        Image img = getImage("homeImages/easy.png");
        this.easyRandomLevelButtonImageView = new ImageView(img);
        this.setupImage(
                this.easyRandomLevelButtonImageView,
                Constants.EASY_RANDOM_LEVEL_BUTTON_X,
                Constants.EASY_RANDOM_LEVEL_BUTTON_Y,
                Constants.EASY_RANDOM_LEVEL_BUTTON_WIDTH
        );
        this.randomLevelVisuals.add(this.easyRandomLevelButtonImageView);
    }

    /**
     * sets up medium random level button image. adds to the arraylist it appears in.
     */
    private void setupMediumRandomLevelButtonImage() {
        Image img = getImage("homeImages/medium.png");
        this.mediumRandomLevelButtonImageView = new ImageView(img);
        this.setupImage(
                this.mediumRandomLevelButtonImageView,
                Constants.MEDIUM_RANDOM_LEVEL_BUTTON_X,
                Constants.MEDIUM_RANDOM_LEVEL_BUTTON_Y,
                Constants.MEDIUM_RANDOM_LEVEL_BUTTON_WIDTH
        );
        this.randomLevelVisuals.add(this.mediumRandomLevelButtonImageView);
    }

    /**
     * sets up hard random level button image. adds to the arraylist it appears in.
     */
    private void setupHardRandomLevelButtonImage() {
        Image img = getImage("homeImages/hard.png");
        this.hardRandomLevelButtonImageView = new ImageView(img);
        this.setupImage(
                this.hardRandomLevelButtonImageView,
                Constants.HARD_RANDOM_LEVEL_BUTTON_X,
                Constants.HARD_RANDOM_LEVEL_BUTTON_Y,
                Constants.HARD_RANDOM_LEVEL_BUTTON_WIDTH
        );
        this.randomLevelVisuals.add(this.hardRandomLevelButtonImageView);
    }

    /**
     * sets up level one button image. adds to the arraylist it appears in.
     */
    private void setupOneLevelButtonImage() {
        Image img = getImage("homeImages/one.png");
        this.oneLevelButtonImageView = new ImageView(img);
        this.setupImage(
                this.oneLevelButtonImageView,
                Constants.ONE_LEVEL_BUTTON_X,
                Constants.ONE_LEVEL_BUTTON_Y,
                Constants.ONE_LEVEL_BUTTON_WIDTH
        );
        this.levelChoicesVisuals.add(this.oneLevelButtonImageView);
    }

    /**
     * sets up level two button image. adds to the arraylist it appears in.
     */
    private void setupTwoLevelButtonImage() {
        Image img = getImage("homeImages/two.png");
        this.twoLevelButtonImageView = new ImageView(img);
        this.setupImage(
                this.twoLevelButtonImageView,
                Constants.TWO_LEVEL_BUTTON_X,
                Constants.TWO_LEVEL_BUTTON_Y,
                Constants.TWO_LEVEL_BUTTON_WIDTH
        );
        this.levelChoicesVisuals.add(this.twoLevelButtonImageView);
    }

    /**
     * sets up level three button image. adds to the arraylist it appears in.
     */
    private void setupThreeLevelButtonImage() {
        Image img = getImage("homeImages/three.png");
        this.threeLevelButtonImageView = new ImageView(img);
        this.setupImage(
                this.threeLevelButtonImageView,
                Constants.THREE_LEVEL_BUTTON_X,
                Constants.THREE_LEVEL_BUTTON_Y,
                Constants.THREE_LEVEL_BUTTON_WIDTH
        );
        this.levelChoicesVisuals.add(this.threeLevelButtonImageView);
    }

    /**
     * sets up level four button image. adds to the arraylist it appears in.
     */
    private void setupFourLevelButtonImage() {
        Image img = getImage("homeImages/four.png");
        this.fourLevelButtonImageView = new ImageView(img);
        this.setupImage(
                this.fourLevelButtonImageView,
                Constants.FOUR_LEVEL_BUTTON_X,
                Constants.FOUR_LEVEL_BUTTON_Y,
                Constants.FOUR_LEVEL_BUTTON_WIDTH
        );
        this.levelChoicesVisuals.add(this.fourLevelButtonImageView);
    }

    /**
     * sets up level five button image. adds to the arraylist it appears in.
     */
    private void setupFiveLevelButtonImage() {
        Image img = getImage("homeImages/five.png");
        this.fiveLevelButtonImageView = new ImageView(img);
        this.setupImage(
                this.fiveLevelButtonImageView,
                Constants.FIVE_LEVEL_BUTTON_X,
                Constants.FIVE_LEVEL_BUTTON_Y,
                Constants.FIVE_LEVEL_BUTTON_WIDTH
        );
        this.levelChoicesVisuals.add(this.fiveLevelButtonImageView);
    }

    /**
     * sets up how to play image. adds to the arraylist it appears in.
     */
    private void setupHowToPlayImage() {
        Image img = getImage("homeImages/howToPlay.png");
        ImageView howToPlayImageView = new ImageView(img);
        this.setupImage(
                howToPlayImageView,
                Constants.HOW_TO_PLAY_X,
                Constants.HOW_TO_PLAY_Y,
                Constants.HOW_TO_PLAY_WIDTH
        );
        this.tutorialVisuals.add(howToPlayImageView);
    }

    /**
     * sets up back button image. adds to the arraylists it appears in.
     */
    private void setupBackButtonImage() {
        Image img = getImage("homeImages/backArrow.png");
        this.backButtonImageView = new ImageView(img);
        this.setupImage(
                this.backButtonImageView,
                Constants.BACK_BUTTON_X,
                Constants.BACK_BUTTON_Y,
                Constants.BACK_BUTTON_WIDTH
                );
        this.choicesVisuals.add(this.backButtonImageView);
        this.randomLevelVisuals.add(this.backButtonImageView);
        this.levelChoicesVisuals.add(this.backButtonImageView);
        this.tutorialVisuals.add(this.backButtonImageView);
    }

    /**
     * sets up home button image. adds to the arraylist it appears in.
     */
    private void setupHomeButtonImage() {
        Image img = getImage("homeImages/homeButton.png");
        this.homeButtonImageView = new ImageView(img);
        this.setupImage(this.homeButtonImageView,
                Constants.HOME_BUTTON_X,
                Constants.HOME_BUTTON_Y,
                Constants.HOME_BUTTON_WIDTH);
        this.pauseVisuals.add(this.homeButtonImageView);
    }

    /**
     * sets up resume button image. adds to the arraylist it appears in.
     */
    private void setupResumeButtonImage() {
        Image img = getImage("homeImages/playArrow.png");
        this.resumeButtonImageView = new ImageView(img);
        this.setupImage(this.resumeButtonImageView,
                Constants.RESUME_BUTTON_X,
                Constants.RESUME_BUTTON_Y,
                Constants.RESUME_BUTTON_WIDTH);
        this.pauseVisuals.add(this.resumeButtonImageView);
    }

    /**
     * sets up pause area image. adds to the arraylist it appears in.
     */
    private void setupPauseAreaImage() {
        Image img = getImage("homeImages/pauseArea.png");
        ImageView pauseAreaImageView = new ImageView(img);
        this.setupImage(pauseAreaImageView,
                Constants.PAUSE_AREA_X,
                Constants.PAUSE_AREA_Y,
                Constants.PAUSE_AREA_WIDTH);
        this.pauseVisuals.add(pauseAreaImageView);
    }


    /**
     * sets up checkmark one image.
     */
    private void setupOneCheckmarkImage() {
        Image img = getImage("homeImages/checkmark.png");
        this.oneCheckmarkImageView = new ImageView(img);
        this.setupImage(
                this.oneCheckmarkImageView,
                Constants.ONE_CHECKMARK_X,
                Constants.ONE_CHECKMARK_Y,
                Constants.ONE_CHECKMARK_WIDTH);
    }

    /**
     * sets up checkmark two image.
     */
    private void setupTwoCheckmarkImage() {
        Image img = getImage("homeImages/checkmark.png");
        this.twoCheckmarkImageView = new ImageView(img);
        this.setupImage(
                this.twoCheckmarkImageView,
                Constants.TWO_CHECKMARK_X,
                Constants.TWO_CHECKMARK_Y,
                Constants.TWO_CHECKMARK_WIDTH
        );
    }

    /**
     * sets up checkmark three image.
     */
    private void setupThreeCheckmarkImage() {
        Image img = getImage("homeImages/checkmark.png");
        this.threeCheckmarkImageView = new ImageView(img);
        this.setupImage(
                this.threeCheckmarkImageView,
                Constants.THREE_CHECKMARK_X,
                Constants.THREE_CHECKMARK_Y,
                Constants.THREE_CHECKMARK_WIDTH
        );
    }

    /**
     * sets up checkmark four image.
     */
    private void setupFourCheckmarkImage() {
        Image img = getImage("homeImages/checkmark.png");
        this.fourCheckmarkImageView = new ImageView(img);
        this.setupImage(
                this.fourCheckmarkImageView,
                Constants.FOUR_CHECKMARK_X,
                Constants.FOUR_CHECKMARK_Y,
                Constants.FOUR_CHECKMARK_WIDTH
        );
    }

    /**
     * sets up checkmark five image.
     */
    private void setupFiveCheckmarkImage() {
        Image img = getImage("homeImages/checkmark.png");
        this.fiveCheckmarkImageView = new ImageView(img);
        this.setupImage(
                this.fiveCheckmarkImageView,
                Constants.FIVE_CHECKMARK_X,
                Constants.FIVE_CHECKMARK_Y,
                Constants.FIVE_CHECKMARK_WIDTH
        );
    }


    /**
     * helper method to return image based on string input.
     */
    private Image getImage(String imageName) {
        return new Image(this.getClass().getResourceAsStream(imageName));
    }

    /**
     * sets up the time labels.
     */
    private void setupLabels() {
        for (int i = 0; i < 5; i++) {
            this.timeLabels[i] = new Label("0.0");
            this.timeLabels[i].setStyle("-fx-font-size: 20");
            this.timeLabels[i].setTextFill(Color.WHITE);
            this.timeLabels[i].setLayoutX(Constants.TIME_LABEL_X +i*Constants.TIME_LABEL_X_OFFSET);
            this.timeLabels[i].setLayoutY(Constants.TIME_LABEL_Y);
        }

    }

    /**
     * helper method to set up the images.
     */
    private void setupImage(ImageView imageView, double x, double y, double width) {
        imageView.setFitWidth(width);
        imageView.setPreserveRatio(true);
        imageView.setX(x);
        imageView.setY(y);
    }

    /**
     * This method starts the home screen. It adds the home visuals and
     * sets the  title button image view to handle play when clicked. ALso
     * set in game to false.
     */
    private void startHomeScreen() {
        this.addAllToGamePane(this.homeVisuals);
        this.titleButtonImageView.setOnMouseClicked(e -> {
            this.handlePlay();
        });
        this.quitButtonImageView.setOnMouseClicked(e -> {
            System.exit(0);
        });
        this.game.setInGame(false);
    }

    /**
     * method to handle pause screen. when the game wants to pause,
     * set game to pause, add pause visuals, and make resume button and
     * home button call methods when clicked.
     */
    public void handlePauseScreen(){
        this.game.setPaused(true);
        this.addAllToGamePane(this.pauseVisuals);
        this.resumeButtonImageView.setOnMouseClicked(e -> {
            this.handleUnpauseScreen();
        });
        this.homeButtonImageView.setOnMouseClicked(e -> {
            this.returnHomeClick();
        });
    }

    /**
     * handles unpause screen. remove pause visuals and set game to unpaused.
     */
    public void handleUnpauseScreen(){
        this.removeScreen(this.pauseVisuals);
        this.game.setPaused(false);
    }

    /**
     * method to return home. remove level, remove pause visuals,
     * start home screen, and then remove random level counter
     * if was playing a random level.
     */
    private void returnHomeClick()  {
        this.game.removeLevel();
        this.removeScreen(this.pauseVisuals);
        this.startHomeScreen();
        if (this.game.getCurrentLevel() == 0) {
            this.game.removeCounterLabelFromPane();
        }
    }

    /**
     * Return to levels screen. Remove level and level choices visuals.
     */
    public void returnLevels() {
        this.game.removeLevel();
        this.addLevelChoices();
    }

    /**
     * return to choices screen. remove level and set up choices screen.
     */
    public void returnChoices(){
        this.game.removeLevel();
        this.setupChoicesScreen();
    }

    /**
     * return to random levels choices screen. removes level, remove counter label,
     *
     */
    public void returnRandom(){
        this.game.removeLevel();
        this.game.removeCounterLabelFromPane();
        this.setupRandomLevelsChoicesScreen();
    }


    /**
     * handle play button. remove home visuals screen and set up choices screen.
     */
    private void handlePlay() {
        this.removeScreen(this.homeVisuals);
        this.setupChoicesScreen();
    }

    /**
     * handle random levels click. remove choice visuals screen and set
     * up random level choices screen.
     */
    private void handleRandomLevelsClick() {
        this.removeScreen(this.choicesVisuals);
        this.setupRandomLevelsChoicesScreen();
    }

    /**
     * sets up choices screen. adds all choice visuals to game pane.
     * sets image views to call method when clicked.
     */
    private void setupChoicesScreen() {
        this.addAllToGamePane(this.choicesVisuals);
        this.levelButtonImageView.setOnMouseClicked(e -> {
            this.handleLevelButtonClick();
        });
        this.randomLevelButtonImageView.setOnMouseClicked(e -> {
            this.handleRandomLevelsClick();
        });
        this.tutorialLevelButtonImageView.setOnMouseClicked(e -> {
            this.handleTutorialLevelClick();
        });
        this.backButtonImageView.setOnMouseClicked(e -> {this.handleBackChoicesClick();});

    }


    /**
     * sets up random levels choices screen. adds random level visuals
     * and sets image views to call method when clicked.
     * also if ten levels have been completed, add the time it took to beat
     * 10 levels.
     */
    private void setupRandomLevelsChoicesScreen() {
        this.addAllToGamePane(this.randomLevelVisuals);
        this.easyRandomLevelButtonImageView.setOnMouseClicked(e -> {
            this.handleRandomLevelSelectClick(Difficulty.EASY);
        });
        this.mediumRandomLevelButtonImageView.setOnMouseClicked(e -> {
            this.handleRandomLevelSelectClick(Difficulty.MEDIUM);
        });
        this.hardRandomLevelButtonImageView.setOnMouseClicked(e -> {
            this.handleRandomLevelSelectClick(Difficulty.HARD);
        });
        this.backButtonImageView.setOnMouseClicked(e -> {this.handleBackRandomLevelsClick();});

        for (Difficulty difficulty : Difficulty.values()) {
            if(difficulty.getTenLevelsComplete()) {
                difficulty.addLabelToPane(this.gamePane);
            }
        }

    }

    /**
     * handles random level selection click. passes in difficulty.
     * removes random level visuals screen, resets difficulty level counter,
     * creates new level, makes andrieste visiable, sets pause boolean to false,
     * and sets in game boolean to true. finally start timer for the difficulty
     * and add level counter to pane.
     */
    private void handleRandomLevelSelectClick(Difficulty difficulty) {
        this.removeScreen(this.randomLevelVisuals);
        difficulty.resetCounter();
        this.game.newLevel(difficulty, 0);
        this.game.showAndrieste();
        this.game.setPaused(false);
        this.game.setInGame(true);
        this.game.setDifficultyStartTime();
        this.game.addCounterLabelToPane();
    }

    /**
     * handles pressing level choices button.
     * removes choices visuals and add level choices.
     */
    private void handleLevelButtonClick() {
        this.removeScreen(this.choicesVisuals);
        this.addLevelChoices();
    }

    /**
     * adds level choices. adds level choices to game Pane.
     * sets image views to call methods when clicked.
     * also make sure to add checkmarks and time levels if
     * user has comepleted the level.
     */
    private void addLevelChoices() {
        this.addAllToGamePane(this.levelChoicesVisuals);
        this.oneLevelButtonImageView.setOnMouseClicked(e -> {
            this.handleLevelButtonClick(1);
        });
        this.twoLevelButtonImageView.setOnMouseClicked(e -> {
            this.handleLevelButtonClick(2);
        });
        this.threeLevelButtonImageView.setOnMouseClicked(e -> {
            this.handleLevelButtonClick(3);
        });
        this.fourLevelButtonImageView.setOnMouseClicked(e -> {
            this.handleLevelButtonClick(4);
        });
        this.fiveLevelButtonImageView.setOnMouseClicked(e -> {
            this.handleLevelButtonClick(5);
        });
        this.backButtonImageView.setOnMouseClicked(e -> {this.handleBackLevelsClick();});

        boolean[] levelsComplete = this.game.getLevelsComplete();
        for  (int i = 0; i < 5; i++) {
            if (levelsComplete[i]) {
                this.gamePane.getChildren().add(this.checkmarks[i]);
                this.gamePane.getChildren().add(this.timeLabels[i]);
            }
        }
    }

    /**
     * handles clicking back button on choices screen.
     * remove choices visuals and starts home screen.
     */
    private void handleBackChoicesClick() {
        this.removeScreen(this.choicesVisuals);
        this.startHomeScreen();
    }

    /**
     * handles clicking back button on random levels choices screen.
     * remove random level visuals and sets up choices screen.
     */
    private void handleBackRandomLevelsClick() {
        this.removeScreen(this.randomLevelVisuals);
        this.setupChoicesScreen();
    }

    /**
     * handles clicking back button on levels screen.
     * remove level choices visuals and starts choices screen.
     */
    private void handleBackLevelsClick() {
        this.removeScreen(this.levelChoicesVisuals);
        this.setupChoicesScreen();
    }

    /**
     * this handles clicking a given level. it removes
     * level choices visuals, starts a new level, makes andrieste viewable,
     * sets pause to false and sets in game to true.
     */
    private void handleLevelButtonClick(int level) {
        this.removeScreen(this.levelChoicesVisuals);
        this.game.newLevel(Difficulty.EASY, level);
        this.game.showAndrieste();
        this.game.setPaused(false);
        this.game.setInGame(true);
    }

    /**
     * handles tutorial click. removes choices visuals screen and sets up tutorial
     */
    private void handleTutorialLevelClick() {
        this.removeScreen(this.choicesVisuals);
        this.setupTutorial();
    }

    /**
     * adds all tutorial level visuals to the gamePane and makes
     * back button call specific method when clicked.
     */
    private void setupTutorial() {
        this.addAllToGamePane(this.tutorialVisuals);
        this.backButtonImageView.setOnMouseClicked(e -> {
            this.handleBackTutorialLevelClick();
        });

    }

    /**
     * method called when wanted to quit tutorial. remove tutorial screen and set up choices.
     */
    private void handleBackTutorialLevelClick() {
        this.removeScreen(this.tutorialVisuals);
        this.setupChoicesScreen();
    }

}
