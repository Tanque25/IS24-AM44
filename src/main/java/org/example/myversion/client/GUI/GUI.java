package org.example.myversion.client.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.myversion.client.Client;
import org.example.myversion.client.view.GameView;
import org.example.myversion.server.model.Board;
import org.example.myversion.server.model.Player;
import org.example.myversion.server.model.decks.cards.Card;
import org.example.myversion.server.model.decks.cards.ObjectiveCard;
import org.example.myversion.server.model.decks.cards.PlayableCard;
import org.example.myversion.server.model.decks.cards.StarterCard;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static javafx.application.Application.launch;

public class GUI extends GameView{

    //private static final String GOLD_BACK_PATH = "org/example/myversion/cards_gold_back/";
    //private static final String GOLD_FRONT_PATH = "org/example/myversion/cards_gold_front";
    @Override
    public void setClient(Client client) {

    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("org/example/myversion/FXML/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        stage.setTitle("LoginPage");
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void showMessage(String message) {

    }



    @Override
    public void startView() throws IOException {
        launch();
    }

    @Override
    public void clientLogin() throws IOException {

    }

    @Override
    public void showGameAlreadyStartedMessage() {

    }

    @Override
    public void playersNumberChoice() throws IOException {

    }

    @Override
    public void invalidPlayersNumberChoice() throws IOException {

    }

    @Override
    public void waitForOtherPlayers() {

    }

    @Override
    public void showVisibleCards() {

    }

    @Override
    public void showCommonObjectives(List<ObjectiveCard> objectiveCards) {

    }

    @Override
    public void showSecretObjectives(List<ObjectiveCard> objectiveCards) {

    }

    @Override
    public void secretObjectiveCardChoice(List<ObjectiveCard> objectiveCards) throws IOException {
        VBox vbox = new VBox();
        HBox hbox = new HBox();
        Label label = new Label("Choose your secret objective card: ");
        for(ObjectiveCard card : objectiveCards){
            Image img = new Image(getClass().getResource("org/example/myversion/cards_gold_front" + card.getId().toString() + ".png").toExternalForm());
            ImageView imgView = new ImageView(img);
            hbox.getChildren().add(imgView);
        }
        vbox.getChildren().addAll(label, hbox);
    }

    @Override
    public void showStarterCard(StarterCard starterCard) {
    }

    @Override
    public void starterCardSideChoice(StarterCard starterCard) throws IOException {
    }

    @Override
    public void showMyHand() {

    }

    public HBox showMyHand(List<PlayableCard> hand) {
        HBox hbox = new HBox();
        for (PlayableCard card : hand) {
            Image img = new Image(getClass().getResource("org/example/myversion/cards_gold_front" + Objects.toString(card.getId()) + ".png").toExternalForm());
            ImageView imgView = new ImageView(img);
            hbox.getChildren().add(imgView);
        }
        return hbox;
    }

    public HBox showMyHandBack(List<PlayableCard> hand) {
        HBox hbox = new HBox();
        for (PlayableCard card : hand) {
            Image img = new Image(getClass().getResource("org/example/myversion/cards_gold_back" + Objects.toString(card.getId()) + ".png").toExternalForm());
            ImageView imgView = new ImageView(img);
            hbox.getChildren().add(imgView);
        }
        return hbox;
    }

    @Override
    public void showMyPlayArea() {

    }

    @Override
    public void showOthersHandsAndPlayAreas() {

    }

    @Override
    public void chooseCardToPlay() throws IOException {

    }

    @Override
    public void invalidMove() throws IOException {

    }

    @Override
    public void showUpdatedPlayArea(String nickname, Card[][] playArea) {

    }

    @Override
    public void chooseCardToDraw() {

    }
}
