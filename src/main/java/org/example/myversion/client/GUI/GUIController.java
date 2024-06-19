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
    protected Parent root;


    public GUIController() {
    }

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public void setClient(Client client){
        this.client = client;
    }

}
