
package it.polimi.ingsw.client.GUI.Controllers;

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
import it.polimi.ingsw.client.GUI.GUIController;
import it.polimi.ingsw.messages.ChatMessage;

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

    public void updateChatReceive(ChatMessage message){
        Label newLabel = new Label();

        newLabel.setText(message.getSender().getNickname() + " sent you this message at " + message.getTime() + ": " + message.getText());
        newLabel.prefWidthProperty().setValue(286);
        newLabel.prefHeightProperty().setValue(100);
        newLabel.setStyle("-fx-background-color: grey; -fx-text-fill: black;");
        newLabel.setOpacity(0.8);

        chatMessages.getChildren().add(newLabel);

        if(chatStage == null){
            Platform.runLater(() -> {
                showAlert("New message", "You received a new message");
            });
        }else{
            chargeScene();
        }
    }

    public void updateChatSend(ChatMessage message){
        Label newLabel = new Label();

        newLabel.setText("You sent this message at " + message.getTime() + ": " + message.getText());
        newLabel.prefWidthProperty().setValue(286);
        newLabel.prefHeightProperty().setValue(100);
        newLabel.setStyle("-fx-background-color: orange; -fx-text-fill: black;");
        newLabel.setOpacity(0.8);

        chatMessages.getChildren().add(newLabel);

        chargeScene();
    }

    private void sendChatMessage(){
        String rec = receiver.getValue();
        String text = load.getText();
    }

    private void chargeScene(){
        Platform.runLater( () -> {
            try {
                URL fxmlLocation = (new File("src/main/resources/it/polimi/ingsw/FXML/Chat.fxml")).toURI().toURL();
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
