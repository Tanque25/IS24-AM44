package org.example.myversion.client.GUI.Controllers;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
    private AnchorPane objectives; // Utilizziamo AnchorPane al posto di HBox

    private Client client;

    public ChooseObjectiveController() {
        super();
    }

    public void initialize(List<ObjectiveCard> objectiveCards) {
        Platform.runLater(() -> {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/myversion/FXML/ChooseObjective.fxml"));
            try {
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (ObjectiveCard card : objectiveCards) {
                Image img = new Image(getClass().getResource("org/example/myversion/cards_gold_front/front" + card.getId().toString() + ".png").toExternalForm());
                ImageView imgView = new ImageView(img);
                objectives.getChildren().add(imgView);
            }

            // Settiamo l'azione per i pulsanti
            button0.setOnAction(event -> {
                try {
                    client.sendMessage(new Message("ObjectiveCardChoice", objectiveCards.get(0)));
                    button0.setDisable(true);
                    button1.setDisable(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            button1.setOnAction(event -> {
                try {
                    client.sendMessage(new Message("ObjectiveCardChoice", objectiveCards.get(1)));
                    button0.setDisable(true);
                    button1.setDisable(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
    }
}
