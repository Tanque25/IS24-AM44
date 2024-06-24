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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class ChooseObjectiveController extends GUIController {
    @FXML
    private Button button0;
    @FXML
    private Button button1;
    @FXML
    private AnchorPane objectives; // Utilizziamo AnchorPane al posto di HBox

    @FXML
    private ImageView objective1;
    @FXML
    private ImageView objective2;

    public ChooseObjectiveController() {
        super();
    }

    public void initialize(List<ObjectiveCard> objectiveCards) {
        Platform.runLater(() -> {
            try {
                URL fxmlLocation = (new File("src/main/resources/org/example/myversion/FXML/ChooseObjectiveCard.fxml")).toURI().toURL();
                FXMLLoader loader = new FXMLLoader(fxmlLocation);
                loader.setController(this);
                Parent root = loader.load();

                Image img1 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + objectiveCards.getFirst().getId() +".png"));
                objective1.setImage(img1);

                Image img2 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + objectiveCards.get(1).getId() +".png"));
                objective2.setImage(img2);


                gui.getStage().setTitle("Codex Naturalis");
                gui.getStage().setScene(new Scene(root));
                gui.getStage().show();
            } catch (IOException e) {
                e.printStackTrace();
            }

            button0.setOnMouseClicked(event -> {
                try {
                    gui.getClient().sendMessage(new Message("ObjectiveCardChoice", objectiveCards.get(0)));
                    button0.setDisable(true);
                    button1.setDisable(true);
                    gui.setSecretObjectiveCard(objectiveCards.get(0));
                    //gui.showMyPlayArea();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            button1.setOnAction(event -> {
                try {
                    gui.getClient().sendMessage(new Message("ObjectiveCardChoice", objectiveCards.get(1)));
                    button0.setDisable(true);
                    button1.setDisable(true);
                    gui.setSecretObjectiveCard(objectiveCards.get(1));
                    //gui.showMyPlayArea();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
    }
}
