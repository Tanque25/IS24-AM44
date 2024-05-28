package org.example.myversion.client.GUI.Controllers;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.example.myversion.client.GUI.GUIController;
import org.example.myversion.server.model.decks.cards.StarterCard;

import java.util.Objects;

public class StarterCardSide implements GUIController {
    @FXML
    private Button button0;
    @FXML
    private Button button1;
    @FXML
    private HBox starterSides;

    public void initialize(StarterCard starterCard) {
        Platform.runLater(() -> {
            starterSides = new HBox();
            Image imgFront = new Image(getClass().getResource("org/example/myversion/cards_gold_front" + Objects.toString(starterCard.getId()) + ".png").toExternalForm());
            ImageView imgViewFront = new ImageView(imgFront);
            Image imgBack = new Image(getClass().getResource("org/example/myversion/cards_gold_back" + Objects.toString(starterCard.getId()) + ".png").toExternalForm());
            ImageView imgViewBack = new ImageView(imgBack);
            starterSides.getChildren().addAll(imgViewFront, imgViewBack);
            Button button0 = new Button("Front side");
            button0.setOnAction(event -> play(0));
            Button button1 = new Button("Front side");
            button0.setOnAction(event -> play(1));
        });
    }

    public void play(int number){
    }
}