package org.example.myversion.client.GUI;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import org.example.myversion.client.Client;
import org.example.myversion.client.CodexNaturalis;
import org.example.myversion.client.GUI.Controllers.*;
import org.example.myversion.client.view.GameView;
import org.example.myversion.server.model.Board;
import org.example.myversion.server.model.Player;
import org.example.myversion.server.model.decks.cards.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.example.myversion.server.serverController.GameController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

//import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Node;
import static javafx.application.Application.launch;

public class GUI extends GameView{

    private Client client;
    private WaitForOtherPlayersController waitForOtherPlayersController;
    private ShowCommonObjectivesController showCommonObjectivesController;
    //public GamePhaseController gameController;
    private LoginController loginController;
    private StarterCardSideController starterCardSideController;
    private ChooseObjectiveController chooseObjectiveController;
    private ChosePlayerNumberController chosePlayerNumberController;
    private GamePhaseController gamePhaseController;

    private boolean playerNumberChoosen = true;
    private boolean playerStarterChosen = false;
    private boolean playerObjectiveSeen = false;
    List<ObjectiveCard> objectiveCards; //secretObjectiveCards
    StarterCard starterCard;
    List<PlayableCard> visiblePlayableCards;
    List<GoldCard> visibleGoldCards;

    private Stage stage;
    private Parent root;
    Map<String, List<PlayableCard>> playerHand;
    List<ObjectiveCard> commonObjectiveCards;



    public GUI(){
        CodexNaturalis.setParameters("localhost", "tcp", this);
    }

    //private static final String GOLD_BACK_PATH = "org/example/myversion/cards_gold_back/";
    //private static final String GOLD_FRONT_PATH = "org/example/myversion/cards_gold_front";
    @Override
    public void setClient(Client client) {
        this.client = client;
    }

    public void setPlayerStarterChosen(boolean playerStarterChosen) {
        this.playerStarterChosen = playerStarterChosen;
    }

    public void setPlayerObjectiveSeen(boolean playerObjectiveSeen) {
        this.playerObjectiveSeen = playerObjectiveSeen;
    }

    public void setPlayerNumberChoosen(boolean playerNumberChoosen) {
        this.playerNumberChoosen = playerNumberChoosen;
    }

    public Client getClient() {
        return client;
    }

    public Stage getStage() {
        return stage;
    }

    public StarterCard getStarterCard() {
        return starterCard;
    }

    public List<ObjectiveCard> getObjectiveCards() {
        return objectiveCards;
    }
    public List<ObjectiveCard> getCommonObjectiveCards() {
        return commonObjectiveCards;
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        clientLogin();
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
        loginController = new LoginController();
        loginController.setGui(this);
        loginController.login();
    }

    @Override
    public void showGameAlreadyStartedMessage() {

    }

    @Override
    public void playersNumberChoice() throws IOException {
        playerNumberChoosen = false;
        chosePlayerNumberController = new ChosePlayerNumberController();
        chosePlayerNumberController.setGui(this);
        chosePlayerNumberController.choseNumberOfPlayer();
    }

    @Override
    public void invalidPlayersNumberChoice() throws IOException {

    }

    @Override
    public void waitForOtherPlayers() {
        if (playerNumberChoosen){
            waitForOtherPlayersController = new WaitForOtherPlayersController();
            waitForOtherPlayersController.setGui(this);
            waitForOtherPlayersController.waitScreen();
        }
    }

    @Override
    public void showVisibleCards(){
        //List<PlayableCard> visiblePlayableCards = getVisibleResourceCards();
        //List<GoldCard> visibleGoldCards = getVisibleGoldCards();
        visibleGoldCards = getVisibleGoldCards();
        visiblePlayableCards = getVisibleResourceCards();
        showCommonObjectivesController = new ShowCommonObjectivesController();
        showCommonObjectivesController.setGui(this);
        showCommonObjectivesController.displayCards(visiblePlayableCards, visibleGoldCards);

    }

    @Override
    public void showCommonObjectives(List<ObjectiveCard> objectiveCards) {
        List<ObjectiveCard> commonObjectiveCards = this.objectiveCards;
    }

    @Override
    public void showSecretObjectives(List<ObjectiveCard> objectiveCards) {
        //List<ObjectiveCard> secretObjectiveCards = this.objectiveCards;
    }


    @Override
    public void secretObjectiveCardChoice(List<ObjectiveCard> objectiveCards) throws IOException {
        this.objectiveCards = objectiveCards;
        if(playerStarterChosen){
            chooseObjectiveController = new ChooseObjectiveController();
            chooseObjectiveController.setGui(this);
            chooseObjectiveController.initialize(objectiveCards);
        }
    }

    @Override
    public void showStarterCard(StarterCard starterCard) {
    }

    @Override
    public void starterCardSideChoice(StarterCard starterCard) throws IOException {
        this.starterCard = starterCard;
        if(playerObjectiveSeen){
            starterCardSideController = new StarterCardSideController();
            starterCardSideController.setGui(this);
            starterCardSideController.sideChoice(starterCard);
        }
    }

    @Override
    public void showMyHand() {

    }

    @Override
    public void showMyPlayArea() {
        playerHand = getHandsMap();

        gamePhaseController =  new GamePhaseController();
        gamePhaseController.setGui(this);
        //playerHand, commonObjectiveCards, (secret)objectiveCards, deckG, deckRes,
        gamePhaseController.showGame(playerHand, objectiveCards, commonObjectiveCards, visibleGoldCards, visiblePlayableCards);
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

    /*public HBox showMyHand(List<PlayableCard> hand) {
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
    }*/

    /*public void loadScene(GameScene sceneType) throws IOException {
        Platform.runLater(() -> {
            String path;

            switch (sceneType) {
                case LOGIN:
                    path = "/Login.fxml";
                    break;
                case WAIT_FOR_OTHER_PLAYERS:
                    path = "/WaitForOtherPlayers.fxml";
                    break;
                case SHOW_COMMON_OBJECTIVES:
                    path = "/ShowCommonObjective.fxml";
                    break;
                case GAME_PHASE:
                    path = "/GamePhase.fxml";
                    break;
                case CHOOSE_OBJECTIVE:
                    path = "/ChooseObjectiveCard.fxml";
                    break;
                case CHOOSE_PLAYER_NUMBER:
                    path = "/ChoosePlayerNumber.fxml";
                    break;
                case CHOOSE_STARTER:
                    path = "/StarterCardSide.fxml";
                    break;
                case END_GAME:
                    path = "/EndGame.fxml";
                    break;
                default:
                    path = "/Login.fxml";
            }

            try {
               // URL fxmlLocation = new File("src/main/resources/org/example/myversion/FXML" + path).toURI().toURL();
                FXMLLoader loader = new FXMLLoader(GUI.class.getResource("/FXML/"+path));
                loader.setController(this);
                Parent root = loader.load();
                scene.setRoot(root);

                /*this.getStage().setTitle("Codex Naturalis");
                this.getStage().setScene(new Scene(root));
                this.getStage().show();

                genericController = loader.getController();
                genericController.setStage(stage);
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }*/
}
