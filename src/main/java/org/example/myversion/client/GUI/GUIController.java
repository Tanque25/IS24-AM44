package org.example.myversion.client.GUI;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import org.example.myversion.client.Client;

public abstract class GUIController {

    protected GUI gui;
    protected Stage stage;
    protected Client client;

    public void setScene(GUI gui, Stage stage){
        this.gui = gui;
        this.stage = stage;
    }

    public void setClient(Client client){
        this.client = client;
    }



    
}
