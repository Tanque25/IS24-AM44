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
import org.example.myversion.client.GUI.GUIController;
import org.example.myversion.messages.Message;

import java.io.IOException;

public class ChosePlayerNumberController extends GUIController {

    @FXML
    ComboBox numberOfPlayers;
    @FXML
    Button confirmChoice;

    public ChosePlayerNumberController(Stage stage, Client client, Scene scene) {
        super(stage, client, scene);
        numberOfPlayers.getItems().addAll(2, 3, 4);
    }

    public void choseNumberOfPlayer(){
        Platform.runLater(()->{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("org/example/myversion/FXML/ChosePlayerNumber.fxml"));
            try {
                Parent root = fxmlLoader.load();
            } catch (IOException e) {
            }

            int number = (int) numberOfPlayers.getValue();
            confirmChoice.setOnAction(event -> {
                try {
                    client.sendMessage(new Message("NumberOfPlayers", number));
                } catch (IOException e) {
                }
                confirmChoice.setDisable(true);
            });

            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        });
    }

}
