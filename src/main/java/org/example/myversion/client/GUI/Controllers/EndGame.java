package org.example.myversion.client.GUI.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.myversion.client.Client;
import org.example.myversion.client.GUI.GUIController;

import java.io.File;
import java.net.URL;

public class EndGame extends GUIController {
    public EndGame() {
        super();
    }
    
    public void showEndGame(){
        Platform.runLater(() -> {
            try {
                URL fxmlLocation = new File("src/main/resources/org/example/myversion/FXML/EndGame.fxml").toURI().toURL();
                FXMLLoader loader = new FXMLLoader(fxmlLocation);
                loader.setController(this);
                Parent root = loader.load();
                gui.getStage().setTitle("Codex Naturalis");
                gui.getStage().setScene(new Scene(root));
                gui.getStage().show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
