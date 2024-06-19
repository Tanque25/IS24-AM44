package org.example.myversion.client.GUI.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.example.myversion.client.Client;
import org.example.myversion.client.GUI.GUI;
import org.example.myversion.client.GUI.GUIController;
import org.example.myversion.messages.Message;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ChosePlayerNumberController extends GUIController {

    @FXML
    private ComboBox<Integer> numberOfPlayers;
    @FXML
    private Button confirmChoice;

    public ChosePlayerNumberController() {
        super();
    }

    @FXML
    private void initialize() {
        confirmChoice.setOnAction(event -> handleChoice());
    }

    public void choseNumberOfPlayer(){
        Platform.runLater(() -> {
            try {
                URL fxmlLocation = (new File("src/main/resources/org/example/myversion/FXML/ChoosePlayerNumber.fxml")).toURI().toURL();
                FXMLLoader loader = new FXMLLoader(fxmlLocation);
                loader.setController(this);
                Parent root = loader.load();
                numberOfPlayers.getItems().addAll(2, 3, 4);
                gui.getStage().setTitle("Codex Naturalis");
                gui.getStage().setScene(new Scene(root));
                gui.getStage().show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void handleChoice() {
        Integer number = numberOfPlayers.getValue();
        if (number != null) {
            try {
                gui.getClient().sendMessage(new Message("NumberOfPlayers", number));
                confirmChoice.setDisable(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
