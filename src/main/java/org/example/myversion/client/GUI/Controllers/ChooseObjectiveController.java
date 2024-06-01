package org.example.myversion.client.GUI.Controllers;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.example.myversion.client.Client;
import org.example.myversion.client.GUI.GUIController;
import org.example.myversion.messages.Message;
import org.example.myversion.server.model.decks.cards.ObjectiveCard;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ChooseObjectiveController implements GUIController {
    @FXML
    private Button button0;
    @FXML
    private Button button1;
    @FXML
    private HBox objectives;

    private Client client;

    public ChooseObjectiveController(Client client) {
        this.client = client;
    }

    public void initialize(List<ObjectiveCard> objectiveCards) throws IOException {
        Platform.runLater(() -> {
            objectives = new HBox();
            for(ObjectiveCard card : objectiveCards){
                Image img = new Image(getClass().getResource("org/example/myversion/cards_gold_front" + card.getId().toString() + ".png").toExternalForm());
                ImageView imgView = new ImageView(img);
                objectives.getChildren().add(imgView);
            }
            Button button0 = new Button("Card 0");
            Button button1 = new Button("Card 1");
            button0.setOnAction(event -> {
                try{
                    client.sendMessage(new Message("ObjectiveCardChoice", objectiveCards.get(1)));
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
        });
    }
}