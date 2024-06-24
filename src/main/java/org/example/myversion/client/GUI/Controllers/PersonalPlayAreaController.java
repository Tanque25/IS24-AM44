package org.example.myversion.client.GUI.Controllers;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.myversion.client.Client;
import org.example.myversion.client.GUI.GUI;
import org.example.myversion.client.GUI.GUIController;
import org.example.myversion.messages.Message;

import java.io.File;
import java.io.IOException;
import java.net.URL;


public class PersonalPlayAreaController extends GUIController{

    @FXML
    private Button goBack;

    public PersonalPlayAreaController(){
        super();
    }

    public void initialize(){
        Platform.runLater(() -> {
            try {
                URL fxmlLocation = (new File("src/main/resources/org/example/myversion/FXML/OtherPlayArea.fxml")).toURI().toURL();
                FXMLLoader loader = new FXMLLoader(fxmlLocation);
                loader.setController(this);
                Parent root = loader.load();

                gui.getStage().setTitle("Codex Naturalis");
                gui.getStage().setScene(new Scene(root));
                gui.getStage().show();
            } catch (IOException e) {
                e.printStackTrace();
            }

            goBack.setOnMouseClicked(event -> {
                //devo tornare alla schermata precedente
                try {
                    gui.getClient().sendMessage(new Message("GoBack"));
                    goBack.setDisable(true);
                    //gui.restorePreviousScene();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        });
    });

    }


}
