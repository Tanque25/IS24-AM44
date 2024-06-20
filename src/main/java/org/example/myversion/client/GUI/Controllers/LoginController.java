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
import org.example.myversion.client.GUI.GUI;
import org.example.myversion.client.GUI.GUIController;
import org.example.myversion.messages.Message;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class LoginController extends GUIController {
    @FXML
    private TextField username;
    @FXML
    private Button connect;

    public LoginController() {
        super();
    }

    @FXML
    private void initialize() {
        connect.setOnMouseClicked(event -> handleLogin());
    }

    @FXML
    private void handleLogin() {
        String nickname = username.getText();
        try {
            gui.getClient().sendMessage(new Message("Login", nickname));
            connect.setDisable(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void login() {
        Platform.runLater(() -> {
            try {
                URL fxmlLocation = (new File("src/main/resources/org/example/myversion/FXML/Login.fxml")).toURI().toURL();
                FXMLLoader loader = new FXMLLoader(fxmlLocation);
                loader.setController(this);
                Parent root = loader.load();
                gui.getStage().setTitle("Codex Naturalis");
                gui.getStage().setScene(new Scene(root));
                gui.getStage().show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}