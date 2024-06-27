package org.example.myversion.client.GUI;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.Node;
import org.example.myversion.client.Client;
import org.example.myversion.client.CodexNaturalis;
import org.example.myversion.client.GUI.Controllers.*;
import org.example.myversion.client.view.GameView;
import org.example.myversion.messages.ChatMessage;
import org.example.myversion.server.model.Board;
import org.example.myversion.server.model.Player;
import org.example.myversion.server.model.decks.cards.*;
import javafx.collections.ObservableList;

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
    private ShowVisibleCardsController showVisibleCardsController;
    private StarterCardSideController starterCardSideController;
    private ChooseObjectiveController chooseObjectiveController;
    private ChosePlayerNumberController chosePlayerNumberController;
    private GamePhaseController gamePhaseController;
    private EndGame endGameController;
    private PersonalPlayAreaController personalPlayAreaController;
    private ChatController chatController;

    private boolean playerNumberChoosen = true;
    private boolean playerStarterChosen = false;
    private boolean playerObjectiveSeen = false;
    private boolean playerVisibleseen = false;
    List<ObjectiveCard> objectiveCards; //secretObjectiveCards
    StarterCard starterCard;
    List<PlayableCard> visiblePlayableCards;
    List<GoldCard> visibleGoldCards;
    ObjectiveCard secretObjectiveCard;

    private Stage stage;
    private Parent root;
    Map<String, List<PlayableCard>> playerHand;
    List<ObjectiveCard> commonObjectiveCards;

    public PlayableCard getSelectedCard() {
        return selectedCard;
    }

    public void setSelectedCard(PlayableCard selectedCard) {
        this.selectedCard = selectedCard;
    }

    private PlayableCard selectedCard;
    //private Scene previousScene;



    public GUI(){
        CodexNaturalis.setParameters(this);
    }

    //private static final String GOLD_BACK_PATH = "org/example/myversion/cards_gold_back/";
    //private static final String GOLD_FRONT_PATH = "org/example/myversion/cards_gold_front";
    @Override
    public void setClient(Client client) {
        this.client = client;
    }

    public void setPlayerVisibleseen(boolean playerVisibleseen) {
        this.playerVisibleseen = playerVisibleseen;
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

    public boolean isPlayerStarterChosen() {
        return playerStarterChosen;
    }

    public List<ObjectiveCard> getObjectiveCards() {
        return objectiveCards;
    }
    public List<ObjectiveCard> getCommonObjectiveCards() {
        return commonObjectiveCards;
    }
    public ObjectiveCard getSecretObjectiveCard() {
        return secretObjectiveCard;
    }
    public void setSecretObjectiveCard(ObjectiveCard secretObjectiveCard) {
        this.secretObjectiveCard = secretObjectiveCard;
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

    public void showAlert(String title, String content){}

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
    public void showTakenUsername() {
        loginController.loginFailed();
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
        if(playerStarterChosen == false){
            if (showVisibleCardsController == null) {
                showVisibleCardsController = new ShowVisibleCardsController();
                showVisibleCardsController.setGui(this);
            }
            showVisibleCardsController.displayCards();
        }

    }

    @Override
    public void showCommonObjectives(List<ObjectiveCard> objectiveCards) {
        if(playerStarterChosen){
            showCommonObjectivesController = new ShowCommonObjectivesController();
            showCommonObjectivesController.setGui(this);
            showCommonObjectivesController.showObjectives();
        }else{
            this.commonObjectiveCards = objectiveCards;
        }
    }



    @Override
    public void showSecretObjectives(List<ObjectiveCard> objectiveCards) {
        //List<ObjectiveCard> secretObjectiveCards = this.objectiveCards;
    }

    // metodo che viene chiamato quando si clicca sul bottone chat
    public void openChat(){
        if(chatController == null){
            chatController = new ChatController();
            chatController.setGui(this);
        }
        chatController.showChat();
    }
    // metodo che viene chiamato quando si riceve un nuovo messaggio
    public void updateChatReceive(ChatMessage message){
        if(chatController == null){
            chatController = new ChatController();
            chatController.setGui(this);
        }
        chatController.updateChatReceive(message);
    }
    public void updateChatSend(ChatMessage message){
        if(chatController == null){
            chatController = new ChatController();
            chatController.setGui(this);
        }
        chatController.updateChatSend(message);
    }


    @Override
    public void secretObjectiveCardChoice(List<ObjectiveCard> objectiveCards) throws IOException {
        this.objectiveCards = objectiveCards;
        if(playerObjectiveSeen){
            chooseObjectiveController = new ChooseObjectiveController();
            chooseObjectiveController.setGui(this);
            chooseObjectiveController.choseObjective(objectiveCards);
        }
    }

    @Override
    public void showStarterCard(StarterCard starterCard) {
    }

    @Override
    public void starterCardSideChoice(StarterCard starterCard) throws IOException {
        this.starterCard = starterCard;
        if(playerVisibleseen){
            starterCardSideController = new StarterCardSideController();
            starterCardSideController.setGui(this);
            starterCardSideController.sideChoice(starterCard);
        }
    }

    public void activateChatButton(){
    }

    @Override
    public void showMyHand() {

    }

    @Override
    public void showMyPlayArea() {
        visibleGoldCards = getVisibleGoldCards();
        visiblePlayableCards = getVisibleResourceCards();
        commonObjectiveCards = getCommonObjectiveCards();
        secretObjectiveCard = getSecretObjectiveCard();
        starterCard = getStarterCard();

        String nick = client.getNickname();
        Map<String, List<PlayableCard>> Hands = getHandsMap();
        List<PlayableCard> hand = Hands.get(nick);
        List<PlayableCard> handMine = Hands.entrySet().stream().filter(e -> e.getKey().equals(nick)).findFirst().get().getValue();

        gamePhaseController =  new GamePhaseController();
        //gamePhaseController.putStarterCard(starterCard);
        gamePhaseController.setGui(this);


        //playerHand, (secret)objectiveCards,commonObjectiveCards, deckG, deckRes,
        //gamePhaseController.playerHandChanged(hand);
        gamePhaseController.initializeScene (secretObjectiveCard, commonObjectiveCards, visibleGoldCards, visiblePlayableCards);

    }

    @Override
    public void showOthersHandsAndPlayAreas() {

    }

    @Override
    public void chooseCardToPlay() throws IOException {
        gamePhaseController.activateTurn();
    }

    @Override
    public void invalidMove() throws IOException {
        gamePhaseController.invalidMove();
        // riattiva le immagini per giocare
        // alert hai sbagliato mossa
    }

    @Override
    public void showUpdatedPlayArea(String nickname, Card[][] playArea) {
        gamePhaseController.updateScene();
    }

    @Override
    public void chooseCardToDraw() {
        gamePhaseController.drawPhase();
        gamePhaseController.clickOnCardToDraw();
    }

    @Override
    public void showUpdatedHand(String nickname) {

    }

    @Override
    public void showScores(Map<String, Integer> scores) {

        endGameController = new EndGame();
        endGameController.setGui(this);
        endGameController.showEndGame(scores);
    }

    @Override
    public void showEndGame(String winner) {

    }

}
