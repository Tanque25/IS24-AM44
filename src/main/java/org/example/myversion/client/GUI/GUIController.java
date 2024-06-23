package org.example.myversion.client.GUI;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.example.myversion.client.Client;
import javafx.scene.Scene;

public abstract class GUIController {
    protected GUI gui;


    public GUIController() {
    }

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public void showAlert(String title, String load){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(load);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
            }
        });
    }

}
