package org.example.myversion.client.GUI.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.myversion.client.Client;
import org.example.myversion.client.GUI.GUIController;

import java.io.File;
import java.net.URL;
import java.util.Map;

public class EndGame extends GUIController {
    public EndGame() {
        super();
    }
    
    public void showEndGame(Map <String, Integer> results){
        Platform.runLater(() -> {
            try {
                URL fxmlLocation = new File("src/main/resources/org/example/myversion/FXML/EndGame.fxml").toURI().toURL();
                FXMLLoader loader = new FXMLLoader(fxmlLocation);
                loader.setController(this);
                Parent root = loader.load();

                int k=0;
                for(String nickname: results.keySet()){
                    switch(k){
                        case 0:
                            ((javafx.scene.control.Label) root.lookup("#player1Label")).setText(nickname);
                            ((javafx.scene.control.Label) root.lookup("#score1Label")).setText(Integer.toString(results.get(nickname)));
                            break;
                        case 1:
                            ((javafx.scene.control.Label) root.lookup("#player2Label")).setText(nickname);
                            ((javafx.scene.control.Label) root.lookup("#score2Label")).setText(Integer.toString(results.get(nickname)));
                            break;
                        case 2:
                            ((javafx.scene.control.Label) root.lookup("#player3Label")).setText(nickname);
                            ((javafx.scene.control.Label) root.lookup("#score3Label")).setText(Integer.toString(results.get(nickname)));
                            break;
                        case 3:
                            ((javafx.scene.control.Label) root.lookup("#player4Label")).setText(nickname);
                            ((javafx.scene.control.Label) root.lookup("#score4Label")).setText(Integer.toString(results.get(nickname)));
                            break;
                    }
                    k++;
                }



                gui.getStage().setTitle("Codex Naturalis");
                gui.getStage().setScene(new Scene(root));
                gui.getStage().show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void askExit(javafx.event.ActionEvent actionEvent) {
        System.exit(0);
    }
}
