package org.example.myversion.client.GUI.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import org.example.myversion.client.GUI.GUIController;
import org.example.myversion.messages.Message;

import java.io.IOException;

public class ChosePlayerNumberController extends GUIController {

    @FXML
    ComboBox numberOfPlayers;
    @FXML
    Button confirmChoice;

    public ChosePlayerNumberController() {
        numberOfPlayers.getItems().addAll(2, 3, 4);
    }

    public void choseNumberOfPlayer(){
        int number = (int)numberOfPlayers.getValue();
        confirmChoice.setOnAction(event -> {
            try{
                client.sendMessage(new Message("NumberOfPlayers", number));
            } catch(IOException e){
            }
            confirmChoice.setDisable(true);
        });
    }

}
