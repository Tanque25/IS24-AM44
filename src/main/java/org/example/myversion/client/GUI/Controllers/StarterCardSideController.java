package org.example.myversion.client.GUI.Controllers;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
    private HBox starterSides;

    public StarterCardSideController(Stage stage, Client client, Scene scene) {
        super(stage, client, scene);
    }

    public void sideChoice(StarterCard starterCard) throws IOException {
        Platform.runLater(() -> {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("org/example/myversion/FXML/StarterCardSide.fxml"));
            try{
                Parent root = fxmlLoader.load();
            }catch(IOException e){
            }

            Image imgFront = new Image(getClass().getResource("org/example/myversion/cards_gold_front" + Objects.toString(starterCard.getId()) + ".png").toExternalForm());
            ImageView imgViewFront = new ImageView(imgFront);
            Image imgBack = new Image(getClass().getResource("org/example/myversion/cards_gold_back" + Objects.toString(starterCard.getId()) + ".png").toExternalForm());
            ImageView imgViewBack = new ImageView(imgBack);
            starterSides.getChildren().addAll(imgViewFront, imgViewBack);
            playedFront.setOnAction(event -> starterCard.setPlayedBack(false));
            playedBack.setOnAction(event -> starterCard.setPlayedBack(true));
            send.setOnAction(event -> {
                try{
                    client.sendMessage(new Message("StarterCard", starterCard));
                }catch(IOException e){
                }
            });

            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        });
    }
}