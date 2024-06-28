package it.polimi.ingsw.client.GUI.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import it.polimi.ingsw.client.GUI.GUIController;
import javafx.scene.control.Label;

import java.io.File;
import java.net.URL;
import java.util.Map;

public class EndGameController extends GUIController {
    @FXML
    private Label winner;
    public EndGameController() {
        super();
    }

    public void showEndGame(String nickname){
        try {
            URL fxmlLocation = new File("src/main/resources/it/polimi/ingsw/FXML/EndGame.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            loader.setController(this);
            Parent root = loader.load();
            winner.setText(nickname);


            Platform.runLater(()->{
                gui.getStage().setTitle("Codex Naturalis");
                gui.getStage().setScene(new Scene(root));
                gui.getStage().show();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
