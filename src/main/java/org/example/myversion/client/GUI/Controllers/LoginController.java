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

import java.io.IOException;

public class LoginController extends GUIController {
    @FXML
    private TextField username;

    private Client client;
    @FXML
    private Button connect;

    public LoginController(Stage stage, Client client, Scene scene) {
        super(stage, client, scene);
    }

    public void login() throws IOException {
        Platform.runLater(() -> {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("org/example/myversion/FXML/Login.fxml"));
            try{
                Parent root = fxmlLoader.load();
            }catch(IOException e){
            }

            String nickname = username.getText();
            connect.setOnAction(event -> {
                try {
                    client.sendMessage(new Message("Login", nickname));
                } catch (IOException e) {
                }
                connect.setDisable(true);
            });

            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        });
    }
}
