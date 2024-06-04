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
import org.example.myversion.server.model.decks.cards.StarterCard;

import java.io.IOException;
import java.util.Objects;

public class StarterCardSideController extends GUIController {
    @FXML
    private Button button0;
    @FXML
    private Button button1;
    @FXML
    private HBox starterSides;
    private Client client;

    public StarterCardSideController(Client client) {
        this.client = client;
    }

    public void initialize(StarterCard starterCard) throws IOException {
        Platform.runLater(() -> {
            starterSides = new HBox();
            Image imgFront = new Image(getClass().getResource("org/example/myversion/cards_gold_front" + Objects.toString(starterCard.getId()) + ".png").toExternalForm());
            ImageView imgViewFront = new ImageView(imgFront);
            Image imgBack = new Image(getClass().getResource("org/example/myversion/cards_gold_back" + Objects.toString(starterCard.getId()) + ".png").toExternalForm());
            ImageView imgViewBack = new ImageView(imgBack);
            starterSides.getChildren().addAll(imgViewFront, imgViewBack);
            Button button0 = new Button("Front side");
            button0. setOnAction(event -> {
                starterCard.setPlayedBack(false);
                button1.setDisable(true);
            });
            Button button1 = new Button("Back side");
            button1.setOnAction(event -> {
                starterCard.setPlayedBack(true);
                button0.setDisable(true);
            });
            try{
                client.sendMessage(new Message("StarterCard", starterCard));
            }catch(IOException e){
            }
        });
    }
}