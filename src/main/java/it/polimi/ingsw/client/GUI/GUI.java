package it.polimi.ingsw.client.GUI;

import javafx.scene.Parent;
import javafx.stage.Stage;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.CodexNaturalis;
import it.polimi.ingsw.client.GUI.Controllers.*;
import it.polimi.ingsw.client.view.GameView;
import it.polimi.ingsw.messages.ChatMessage;
import it.polimi.ingsw.server.model.decks.cards.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
    private EndGameController endGameController;
    private PersonalPlayAreaController personalPlayAreaController;
    private ChatController chatController;
    private Map<String, Integer> scores;

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

    //private static final String GOLD_BACK_PATH = "it/polimi/ingsw/cards_gold_back/";
    //private static final String GOLD_FRONT_PATH = "it/polimi/ingsw/cards_gold_front";
    @Override
    public void setClient(Client client) {
        this.client = client;
    }

    public Map<String, Integer> getScores() {
        return scores;
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

        if(playerStarterChosen == false){
            if (showVisibleCardsController == null) {
                showVisibleCardsController = new ShowVisibleCardsController();
                showVisibleCardsController.setGui(this);
            }
            showVisibleCardsController.displayCards();
        }
        if(gamePhaseController!=null){
            gamePhaseController.updateScene();
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
        gamePhaseController = new GamePhaseController();
        gamePhaseController.setGui(this);
        gamePhaseController.chargeScene();
        //drawPhaseController = new DrawPhaseController();
        //drawPhaseController.setGui(this);

    }

    @Override
    public void showOthersHandsAndPlayAreas() {

    }

    @Override
    public void chooseCardToPlay() throws IOException {
        gamePhaseController.activateTurn();
        gamePhaseController.gamePhase();
    }

    @Override
    public void invalidMove() throws IOException {
        gamePhaseController.invalidMove();
        gamePhaseController.gamePhase();
    }

    @Override
    public void showUpdatedPlayArea(String nickname, Card[][] playArea) {
    }

    @Override
    public void chooseCardToDraw() {
        gamePhaseController.activateDraw();
        gamePhaseController.drawPhase();

    }

    @Override
    public void showUpdatedHand(String nickname) {
    }

    @Override
    public void showScores(Map<String, Integer> playerScores) {
        scores = playerScores;
        gamePhaseController.updateScene();

    }

    @Override
    public void showEndGame(String winner) {
        endGameController = new EndGameController();
        endGameController.setGui(this);
        endGameController.showEndGame(winner);
    }

}