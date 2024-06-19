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
import javafx.stage.Stage;
import org.example.myversion.client.Client;
import org.example.myversion.client.GUI.GUIController;
import org.example.myversion.messages.Message;
import org.example.myversion.server.model.decks.cards.StarterCard;

import java.io.IOException;
import java.util.Objects;

public class StarterCardSideController extends GUIController {

    @FXML
    private Button playedFront;
    @FXML
    private Button playedBack;
    @FXML
    private Button send;
    @FXML
    private AnchorPane starterSides;
    @FXML
    private ImageView imgViewFront;
    @FXML
    private ImageView imgViewBack;

    public StarterCardSideController() {
        super();
    }

    public void sideChoice(StarterCard starterCard) {
        Platform.runLater(() -> {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/myversion/FXML/StarterCardSide.fxml"));
            try {
                root = fxmlLoader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                return; // Exit method if FXML loading fails
            }

            String imgFrontPath = String.format("/org/example/myversion/cards_gold_front/front%d.png", starterCard.getId());
            String imgBackPath = String.format("/org/example/myversion/cards_gold_back/back%d.png", starterCard.getId());
            Image imgFront = new Image(Objects.requireNonNull(getClass().getResource(imgFrontPath)).toExternalForm());
            imgViewFront.setImage(imgFront);
            Image imgBack = new Image(Objects.requireNonNull(getClass().getResource(imgBackPath)).toExternalForm());
            imgViewBack.setImage(imgBack);

            playedFront.setOnAction(event -> starterCard.setPlayedBack(false));
            playedBack.setOnAction(event -> starterCard.setPlayedBack(true));
            send.setOnAction(event -> {
                try {
                    client.sendMessage(new Message("StarterCard", starterCard));
                    playedBack.setDisable(true);
                    playedFront.setDisable(true);
                    send.setDisable(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
    }
}