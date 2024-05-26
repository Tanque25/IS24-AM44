package org.example.myversion.client.GUI;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import org.example.myversion.client.Client;
import org.example.myversion.client.view.GameView;
import org.example.myversion.server.model.Board;
import org.example.myversion.server.model.Player;
import org.example.myversion.server.model.decks.cards.Card;
import org.example.myversion.server.model.decks.cards.ObjectiveCard;
import org.example.myversion.server.model.decks.cards.PlayableCard;
import org.example.myversion.server.model.decks.cards.StarterCard;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class GUI extends GameView {

    //private static final String GOLD_BACK_PATH = "org/example/myversion/cards_gold_back/";
    //private static final String GOLD_FRONT_PATH = "org/example/myversion/cards_gold_front";
    @Override
    public void setClient(Client client) {

    }

    public VBox showBoard(Board board) {
        VBox boardView = new VBox();

        for (Player player : board.getScores().keySet()) {
            HBox hbox = new HBox();
            Label nicknameLabel = new Label(player.getNickname());
            Label scoreLabel = new Label(String.valueOf(board.getScores().get(player)));
            hbox.getChildren().addAll(nicknameLabel, scoreLabel);
            boardView.getChildren().add(hbox);
        }
        return boardView;
    }

    @Override
    public void showMessage(String message) {

    }



    @Override
    public void startView() throws IOException {

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
        for (ObjectiveCard card : objectiveCards) {
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
        VBox vbox = new VBox();
        HBox hbox = new HBox();
        Label label = new Label("Choose the side to play your starter card: ");
        Image imgFront = new Image(getClass().getResource("org/example/myversion/cards_gold_front" + Objects.toString(starterCard.getId()) + ".png").toExternalForm());
        ImageView imgViewFront = new ImageView(imgFront);
        Image imgBack = new Image(getClass().getResource("org/example/myversion/cards_gold_back" + Objects.toString(starterCard.getId()) + ".png").toExternalForm());
        ImageView imgViewBack = new ImageView(imgBack);
        hbox.getChildren().addAll(imgViewFront, imgViewBack);
        vbox.getChildren().addAll(label, hbox);
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
}
