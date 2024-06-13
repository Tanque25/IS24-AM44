package org.example.myversion.client.GUI.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.myversion.client.Client;
import org.example.myversion.client.GUI.GUIController;
import org.example.myversion.messages.Message;

import java.io.IOException;

public class LoginController extends GUIController {
    @FXML
    private TextField username;
    @FXML
    private Button connect;

    public LoginController(Stage stage, Client client, Scene scene) {
        super(stage, client, scene);
    }

    @FXML
    private void handleLogin() {
        String nickname = username.getText();
        try {
            client.sendMessage(new Message("Login", nickname));
            connect.setDisable(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void login() {
        Platform.runLater(() -> {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/myversion/FXML/Login.fxml"));
            try {
                Parent root = fxmlLoader.load();
                scene.setRoot(root);
                connect.setOnAction(event -> handleLogin());
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
