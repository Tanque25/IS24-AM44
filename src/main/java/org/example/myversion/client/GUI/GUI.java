package org.example.myversion.client.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.myversion.client.Client;
import org.example.myversion.client.GUI.Controllers.ChooseObjectiveController;
import org.example.myversion.client.GUI.Controllers.LoginController;
import org.example.myversion.client.GUI.Controllers.StarterCardSideController;
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
import org.example.myversion.server.serverController.GameController;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static javafx.application.Application.launch;

public class GUI extends GameView{

    private static Client client;
    public static GameController gameController;
    private static LoginController loginController;
    private static StarterCardSideController starterCardSideController;
    private static ChooseObjectiveController chooseObjectiveController;

    public static Stage stage;

    public GUI(){}

    //private static final String GOLD_BACK_PATH = "org/example/myversion/cards_gold_back/";
    //private static final String GOLD_FRONT_PATH = "org/example/myversion/cards_gold_front";
    @Override
    public void setClient(Client client) {
        this.client = client;
    }
    public static Client getClient() {
        return client;
    }
    public static Stage getStage() {
        return stage;
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("org/example/myversion/FXML/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        stage.setScene(scene);
        stage.show();
        clientLogin();
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
        loginController = new LoginController(client);
        loginController.login();
    }

    @Override
    public void showGameAlreadyStartedMessage() {

    }

    @Override
    public void playersNumberChoice() throws IOException {
        // Questo metodo mostrerà la scena con un checkbox da 2 a 4
        // Il controller di questa scena si occuperà di tutto il resto, come inviare
        // il messaggio del client
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
    }

    @Override
    public void showStarterCard(StarterCard starterCard) {
    }

    @Override
    public void starterCardSideChoice(StarterCard starterCard) throws IOException {
        starterCardSideController = new StarterCardSideController(client);
        starterCardSideController.sideChoice(starterCard);
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
