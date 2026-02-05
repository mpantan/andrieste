package andrieste;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * this is the rewind marker class. it's just a little rectangle that marks where andrieste
 * can "rewind" to. All this class does is just store the rectangle that moves around
 * during gameplay.
 */
public class RewindMarker {
    private Rectangle rect;

    /**
     * Constructor. creates new rectangle, and sets color to light green.
     */
    public RewindMarker(double x, double y) {
        this.rect = new Rectangle(x, y, Constants.REWIND_MARKER_WIDTH, Constants.REWIND_MARKER_HEIGHT);
        this.rect.setFill(Color.LIGHTGREEN);
    }

    /**
     * adds rewind marker to given pane
     */
    public void addToPane(Pane gamePane){
        gamePane.getChildren().add(this.rect);
    }

    /**
     * removes rewind marker from given pane
     */
    public void removeFromPane(Pane gamePane){
        gamePane.getChildren().remove(this.rect);
    }

    /**
     * changes location to enter x and y location. add some constants to center
     * it where andrieste was.
     */
    public void changeLocation(double x, double y) {
        this.rect.setX(x + (Constants.ANDRIESTE_WIDTH -Constants.REWIND_MARKER_WIDTH)/2);
        this.rect.setY(y + (Constants.ANDRIESTE_HEIGHT -Constants.REWIND_MARKER_HEIGHT)/2);
    }
}
