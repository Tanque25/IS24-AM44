package it.polimi.ingsw.client.GUI.Controllers;

import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.util.Duration;
import it.polimi.ingsw.client.GUI.GUIController;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Controller class for displaying a waiting screen while waiting for other players.
 */
public class WaitForOtherPlayersController extends GUIController {

    @FXML
    private Label waitingForPlayers;
    @FXML
    private Arc waitingAnimation;

    /**
     * Initializes the controller class. This method is automatically called after the FXML file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Displays the waiting screen with an animation.
     */
    public void waitScreen() {
        Platform.runLater(() -> {
            try {
                URL fxmlLocation = (new File("src/main/resources/it/polimi/ingsw/FXML/WaitForOtherPlayers.fxml")).toURI().toURL();
                FXMLLoader loader = new FXMLLoader(fxmlLocation);
                loader.setController(this);
                Parent root = loader.load();

                waitingAnimation.setFill(Color.TRANSPARENT);
                waitingAnimation.setStroke(Color.BLUE);
                waitingAnimation.setStrokeWidth(5);
                RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), waitingAnimation);
                rotateTransition.setByAngle(360);
                rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
                rotateTransition.play();

                waitingForPlayers.setAccessibleText("Waiting for other players to join the game");

                gui.getStage().setTitle("Codex Naturalis");
                gui.getStage().setScene(new Scene(root));
                gui.getStage().show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
