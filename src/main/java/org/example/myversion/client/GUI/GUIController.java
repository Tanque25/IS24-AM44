package org.example.myversion.client.GUI;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.example.myversion.client.Client;
import javafx.scene.Scene;

public abstract class GUIController {

    protected GUI gui;
    protected Stage stage;
    protected Client client;
    protected Scene scene;
    protected Parent root;


    public GUIController(Stage stage, Client client, Scene scene) {
        this.stage = stage;
        this.client = client;
        this.scene = scene;
    }

    public void setScene(GUI gui, Stage stage){
        this.gui = gui;
        this.stage = stage;
    }

    public void setClient(Client client){
        this.client = client;
    }



    
}
