package org.example.myversion.client.GUI.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.myversion.client.GUI.GUI;
import org.example.myversion.client.GUI.GUIController;

public class Login implements GUIController {
    @FXML
    private TextField username;

    public void login() {
        Platform.runLater(() -> {
            String nickname = username.getText();
        });
    }
}
