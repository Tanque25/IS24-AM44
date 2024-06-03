package org.example.myversion.client.GUI.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.myversion.client.Client;
import org.example.myversion.client.GUI.GUI;
import org.example.myversion.client.GUI.GUIController;
import org.example.myversion.messages.Message;

import java.io.IOException;

public class LoginController implements GUIController {
    @FXML
    private TextField username;

    private Client client;
    @FXML
    private Button connect;

    public LoginController(Client client) {
        this.client = client;
    }

    public void login() throws IOException {
        Platform.runLater(() -> {
            String nickname = username.getText();
            Button connect = new Button("Connect");
                connect.setOnAction(event -> {
                    try {
                        client.sendMessage(new Message("Login", nickname));
                    } catch (IOException e) {
                    }
                    connect.setDisable(true);
                });
        });
    }
}
