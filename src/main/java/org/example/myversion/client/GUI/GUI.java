package org.example.myversion.client.GUI;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import org.example.myversion.client.Client;
import org.example.myversion.client.GUI.Controllers.*;
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
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;

//import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Node;
import static javafx.application.Application.launch;

public class GUI extends GameView{

    private static Client client;
    public static GamePhaseController gameController;
    private static LoginController loginController;
    private static StarterCardSideController starterCardSideController;
    private static ChooseObjectiveController chooseObjectiveController;
    private static ChosePlayerNumberController chosePlayerNumberController;

    private static Stage stage;
    private static Scene scene;
    private static Parent root;

    public GUI(){
    }

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
        //this.stage = stage;
        URL fxmlLocation = (new File("src/main/resources/org/example/myversion/FXML/Login.fxml")).toURI().toURL();

        Parent root = FXMLLoader.load(fxmlLocation);
        stage.setTitle("Codex Naturalis");
        stage.setScene(new Scene(root));
        stage.show();
        //clientLogin();
    }
    public static void main(String[] args) {
        launch(args);
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
        loginController = new LoginController(stage, client, scene);
        loginController.login();
    }

    @Override
    public void showGameAlreadyStartedMessage() {

    }

    @Override
    public void playersNumberChoice() throws IOException {
        chosePlayerNumberController = new ChosePlayerNumberController(stage, client, scene);
        chosePlayerNumberController.choseNumberOfPlayer();
    }

    @Override
    public void invalidPlayersNumberChoice() throws IOException {

    }

    @Override
    public void waitForOtherPlayers() {
        Platform.runLater(() -> {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("org/example/myversion/FXML/WaitForOtherPlayers.fxml"));
            try {
                root = fxmlLoader.load();
                scene.setRoot(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
            }
        });
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
        chooseObjectiveController = new ChooseObjectiveController(stage, client, scene);
        chooseObjectiveController.initialize(objectiveCards);
    }

    @Override
    public void showStarterCard(StarterCard starterCard) {
    }

    @Override
    public void starterCardSideChoice(StarterCard starterCard) throws IOException {
        starterCardSideController = new StarterCardSideController(stage, client, scene);
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

    @Override
    public void showUpdatedHand(String nickname) {

    }

    @Override
    public void showScores(Map<String, Integer> scores) {

    }

    @Override
    public void showEndGame(String winner) {

    }
    /*public void switchToGameScene(ActionEvent event) throws IOException {
        URL fxmlLocation = (new File("src/main/resources/org/example/myversion/FXML/Game.fxml")).toURI().toURL();
        root = FXMLLoader.load(Objects.requireNonNull(fxmlLocation));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }*/

}
