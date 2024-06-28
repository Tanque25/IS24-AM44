package it.polimi.ingsw.client.GUI.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.client.GUI.GUIController;
import it.polimi.ingsw.messages.Message;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Controller class for handling the login process in the GUI.
 */
public class LoginController extends GUIController {

    @FXML
    private TextField username;
    @FXML
    private Button connect;

    /**
     * Default constructor for the LoginController class.
     */
    public LoginController() {
        super();
    }

    /**
     * Handles the login process by sending the username to the server.
     */
    @FXML
    private void handleLogin() {
        String nickname = username.getText();
        try {
            gui.getClient().sendMessage(new Message("Login", nickname));
            gui.getClient().setNickname(nickname);
            connect.setDisable(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets up the login GUI and displays the corresponding scene.
     */
    public void login() {
        Platform.runLater(() -> {
            try {
                URL fxmlLocation = (new File("src/main/resources/it/polimi/ingsw/FXML/Login.fxml")).toURI().toURL();
                FXMLLoader loader = new FXMLLoader(fxmlLocation);
                loader.setController(this);
                Parent root = loader.load();
                gui.getStage().setTitle("Codex Naturalis");
                gui.getStage().setScene(new Scene(root));
                gui.getStage().show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            connect.setOnMouseClicked(event -> handleLogin());
        });
    }

    /**
     * Handles the scenario when the login fails, prompting the user to choose another username
     * and allowing the client to send another one to the server
     */
    public void loginFailed() {
        Platform.runLater(() -> {
            try {
                URL fxmlLocation = (new File("src/main/resources/it/polimi/ingsw/FXML/Login.fxml")).toURI().toURL();
                FXMLLoader loader = new FXMLLoader(fxmlLocation);
                loader.setController(this);
                Parent root = loader.load();
                gui.getStage().setTitle("Codex Naturalis");
                gui.getStage().setScene(new Scene(root));
                gui.getStage().show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            showAlert("Invalid username", "Please chose another nickname");
            connect.setOnMouseClicked(event -> {
                handleLogin();
                gui.waitForOtherPlayers();
            });
        });
    }

}
