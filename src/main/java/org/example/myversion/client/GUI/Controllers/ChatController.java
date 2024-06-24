
package org.example.myversion.client.GUI.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.myversion.client.GUI.GUIController;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ChatController extends GUIController{
    private Stage chatStage;
    @FXML
    VBox chatMessages;
    @FXML
    Button send;
    @FXML
    ComboBox<String> receiver;
    @FXML
    TextField load;

    @FXML
    private void initialize() {
        send.setOnMouseClicked(event -> sendChatMessage());
    }

    public void showChat(){
        chatStage = new Stage();

        chatStage.setOnCloseRequest(event -> {
            chatStage = null;
            gui.activateChatButton();
        });

        chargeScene();
    }

    public void updateChat(){
        Label newLabel = new Label();

        newLabel.setText("New Label");

        newLabel.prefWidthProperty().setValue(300);
        newLabel.prefHeightProperty().setValue(100);

        chatMessages.getChildren().add(newLabel);

        if(chatStage == null){
            showAlert("New message", "You received a new message");
        }else{
            chargeScene();
        }

    }

    private void sendChatMessage(){
        String rec = receiver.getValue();
        String text = load.getText();
    }

    private void chargeScene(){
        Platform.runLater( () -> {
            try {
                URL fxmlLocation = (new File("src/main/resources/org/example/myversion/FXML/Chat.fxml")).toURI().toURL();
                FXMLLoader loader = new FXMLLoader(fxmlLocation);
                loader.setController(this);
                Parent root = loader.load();

                for(String nickname : gui.getPlayAreasMap().keySet()){
                    if(gui.getClient().getNickname() != nickname){
                        receiver.getItems().add(nickname);
                    }
                }

                chatStage.setTitle("Chat");
                chatStage.setScene(new Scene(root));
                chatStage.show();
            } catch (IOException e) {
            }
        });
    }
}
