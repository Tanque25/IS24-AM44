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

import java.io.File;
import java.io.IOException;
import java.net.URL;
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

    public void initialize(){

    }

    public void sideChoice(StarterCard starterCard) {
        Platform.runLater(() -> {
            try {
                URL fxmlLocation = (new File("src/main/resources/org/example/myversion/FXML/StarterCardSide.fxml")).toURI().toURL();
                FXMLLoader loader = new FXMLLoader(fxmlLocation);
                loader.setController(this);
                Parent root = loader.load();

                //String imgFrontPath = String.format("/org/example/myversion/cards_gold_front/front" + starterCard.getId() +".png");
                //String imgBackPath = String.format("resources/org/example/myversion/cards_gold_back/back" + starterCard.getId() + ".png");
                Image imgFront = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + starterCard.getId() +".png"));
                imgViewFront.setImage(imgFront);
                Image imgBack = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_back/back" + starterCard.getId() +".png"));
                imgViewBack.setImage(imgBack);

                gui.getStage().setTitle("Codex Naturalis");
                gui.getStage().setScene(new Scene(root));
                gui.getStage().show();
            } catch (IOException e) {
                e.printStackTrace();
                return; // Exit method if FXML loading fails
            }

            playedFront.setOnMouseClicked(event -> starterCard.setPlayedBack(false));
            playedBack.setOnMouseClicked(event -> starterCard.setPlayedBack(true));

            send.setOnMouseClicked(event -> {
                try {
                    gui.getClient().sendMessage(new Message("StarterCard", starterCard));
                    playedBack.setDisable(true);
                    playedFront.setDisable(true);
                    send.setDisable(true);
                    gui.setPlayerStarterChosen(true);
                    gui.secretObjectiveCardChoice(gui.getObjectiveCards());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
    }
}