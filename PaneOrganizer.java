package andrieste;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * This is PaneOrganizer class. It organizes all the program's graphical panes, which
 * is just the single game pane in this case.
 */
public class PaneOrganizer {
    private BorderPane root;

    /**
     * Constructor. It initializes the root and creates the game.
     */
    public PaneOrganizer() {
        this.root = new BorderPane();
        this.createGame();
    }


    /**
     * This method creates the game pane, sets the style of it, and also adds the pane
     * to the root's center. It them calls the constructor of the game class.
     */
    private void createGame() {
        Pane gamePane = new Pane();
        gamePane.setStyle("-fx-background-color: linear-gradient(to bottom, #8968DB, #221B77);");

        this.root.setCenter(gamePane);
        new Game(gamePane);
    }

    /**
     * returns the root of this class.
     */
    public BorderPane getRoot() {
        return this.root;
    }

}
