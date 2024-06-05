package org.example.myversion.client.GUI.Controllers;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.myversion.client.Client;
import org.example.myversion.client.GUI.GUIController;
import org.example.myversion.messages.Message;
import org.example.myversion.server.model.decks.cards.ObjectiveCard;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ChooseObjectiveController extends GUIController {
    @FXML
    private Button button0;
    @FXML
    private Button button1;
    @FXML
    private HBox objectives;

    private Client client;

    public ChooseObjectiveController(Stage stage, Client client, Scene scene) {
        super(stage, client, scene);
    }

    public void initialize(List<ObjectiveCard> objectiveCards) throws IOException {
        Platform.runLater(() -> {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("org/example/myversion/FXML/Login.fxml"));
            try{
                Parent root = fxmlLoader.load();
            }catch(IOException e){
            }

            for(ObjectiveCard card : objectiveCards){
                Image img = new Image(getClass().getResource("org/example/myversion/cards_gold_front" + card.getId().toString() + ".png").toExternalForm());
                ImageView imgView = new ImageView(img);
                objectives.getChildren().add(imgView);
            }
            button0.setOnAction(event -> {
                try{
                    client.sendMessage(new Message("ObjectiveCardChoice", objectiveCards.get(0)));
                }catch(IOException e){
                }
                button0.setDisable(true);
                button1.setDisable(true);
            });
            button1.setOnAction(event -> {
                try{
                    client.sendMessage(new Message("ObjectiveCardChoice", objectiveCards.get(1)));
                }catch (IOException e){
                }
                button0.setDisable(true);
                button1.setDisable(true);
            });

            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        });
    }
}