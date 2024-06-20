package org.example.myversion.client.GUI.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.example.myversion.client.GUI.GUIController;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class WaitForOtherPlayersController extends GUIController {

    @FXML
    private Label waitingForPlayers;
    @FXML
    private void initialize() {
    }
    public void waitScreen(){
        Platform.runLater(() -> {
            try {
                URL fxmlLocation = (new File("src/main/resources/org/example/myversion/FXML/WaitForOtherPlayers.fxml")).toURI().toURL();
                FXMLLoader loader = new FXMLLoader(fxmlLocation);
                loader.setController(this);
                Parent root = loader.load();
                waitingForPlayers.setAccessibleText("Waiting for other players to join the game");
                gui.getStage().setTitle("Codex Naturalis");
                gui.getStage().setScene(new Scene(root));
                gui.getStage().show();
            } catch (IOException e) {
            }
        });
    }
}
