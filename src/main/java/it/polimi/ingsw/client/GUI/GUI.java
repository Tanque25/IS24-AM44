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

/**
 * GUI class that handles the graphical user interface for the game.
 */
public class GUI extends GameView {

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

    private PlayableCard selectedCard;

    /**
     * Gets the selected card.
     *
     * @return the selected card.
     */
    public PlayableCard getSelectedCard() {
        return selectedCard;
    }

    /**
     * Sets the selected card.
     *
     * @param selectedCard the selected card to set.
     */
    public void setSelectedCard(PlayableCard selectedCard) {
        this.selectedCard = selectedCard;
    }

    /**
     * Constructor for the GUI class.
     */
    public GUI() {
        CodexNaturalis.setParameters(this);
    }
    /**
     * Sets the client.
     *
     * @param client the selected card to set.
     */
    @Override
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Gets the scores of players.
     *
     * @return a map containing player names and their scores.
     */
    public Map<String, Integer> getScores() {
        return scores;
    }
    /**
     * Sets the boolean that indicates if the player as seen the drawable
     * cards at the beginning of the game.
     *
     * @param playerVisibleseen the selected card to set.
     */
    public void setPlayerVisibleseen(boolean playerVisibleseen) {
        this.playerVisibleseen = playerVisibleseen;
    }
    /**
     * Sets the boolean that indicates if the player has chosen the starter
     * card at the beginning of the game.
     *
     * @param playerStarterChosen the selected card to set.
     */
    public void setPlayerStarterChosen(boolean playerStarterChosen) {
        this.playerStarterChosen = playerStarterChosen;
    }
    /**
     * Sets the boolean that indicates if the player as seen the common objective
     * cards at the beginning of the game.
     *
     * @param playerObjectiveSeen the selected card to set.
     */
    public void setPlayerObjectiveSeen(boolean playerObjectiveSeen) {
        this.playerObjectiveSeen = playerObjectiveSeen;
    }
    /**
     * Sets the boolean that indicates if the first player has chosen
     * the number of players at the beginning of the game.
     *
     * @param playerNumberChoosen the selected card to set.
     */
    public void setPlayerNumberChoosen(boolean playerNumberChoosen) {
        this.playerNumberChoosen = playerNumberChoosen;
    }

    /**
     * Gets the client instance.
     *
     * @return the client instance.
     */
    public Client getClient() {
        return client;
    }

    /**
     * Gets the stage.
     *
     * @return the stage.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Gets the starter card.
     *
     * @return the starter card.
     */
    public StarterCard getStarterCard() {
        return starterCard;
    }

    /**
     * Checks if the player starter is chosen.
     *
     * @return true if the player starter is chosen, false otherwise.
     */
    public boolean isPlayerStarterChosen() {
        return playerStarterChosen;
    }

    /**
     * Gets the list of objective cards.
     *
     * @return the list of objective cards.
     */
    public List<ObjectiveCard> getObjectiveCards() {
        return objectiveCards;
    }

    /**
     * Gets the list of common objective cards.
     *
     * @return the list of common objective cards.
     */
    public List<ObjectiveCard> getCommonObjectiveCards() {
        return commonObjectiveCards;
    }

    /**
     * Gets the secret objective card.
     *
     * @return the secret objective card.
     */
    public ObjectiveCard getSecretObjectiveCard() {
        return secretObjectiveCard;
    }

    /**
     * Sets the secret objective card.
     *
     * @param secretObjectiveCard the secret objective card to set.
     */
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

    public void showAlert(String title, String content) {
    }

    @Override
    public void startView() throws IOException {
        launch();
    }
    /**
     * Handle the login phase in the gui
     */
    @Override
    public void clientLogin() throws IOException {
        loginController = new LoginController();
        loginController.setGui(this);
        loginController.login();
    }
    /**
     * Called when a player chose a nickname already used
     */
    @Override
    public void showTakenUsername() {
        loginController.loginFailed();
    }

    @Override
    public void showGameAlreadyStartedMessage() {
    }
    /**
     * Handle the chose of the player's number in the gui
     */
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
    /**
     * Handle the wait for other players scene
     */
    @Override
    public void waitForOtherPlayers() {
        if (playerNumberChoosen) {
            waitForOtherPlayersController = new WaitForOtherPlayersController();
            waitForOtherPlayersController.setGui(this);
            waitForOtherPlayersController.waitScreen();
        }
    }
    /**
     * Shows only the visible cards at the beginning of the game and
     * update the game scene during the game phase
     */
    @Override
    public void showVisibleCards() {
        if (!playerStarterChosen) {
            if (showVisibleCardsController == null) {
                showVisibleCardsController = new ShowVisibleCardsController();
                showVisibleCardsController.setGui(this);
            }
            showVisibleCardsController.displayCards();
        }
        if (gamePhaseController != null) {
            gamePhaseController.updateScene();
        }
    }
    /**
     * Shows only the common objective cards at the beginning of the game
     */
    @Override
    public void showCommonObjectives(List<ObjectiveCard> objectiveCards) {
        if (playerStarterChosen) {
            showCommonObjectivesController = new ShowCommonObjectivesController();
            showCommonObjectivesController.setGui(this);
            showCommonObjectivesController.showObjectives();
        } else {
            this.commonObjectiveCards = objectiveCards;
        }
    }

    @Override
    public void showSecretObjectives(List<ObjectiveCard> objectiveCards) {
        //List<ObjectiveCard> secretObjectiveCards = this.objectiveCards;
    }

    // metodo che viene chiamato quando si clicca sul bottone chat
    public void openChat() {
        if (chatController == null) {
            chatController = new ChatController();
            chatController.setGui(this);
        }
        chatController.showChat();
    }

    // metodo che viene chiamato quando si riceve un nuovo messaggio
    public void updateChatReceive(ChatMessage message) {
        if (chatController == null) {
            chatController = new ChatController();
            chatController.setGui(this);
        }
        chatController.updateChatReceive(message);
    }

    public void updateChatSend(ChatMessage message) {
        if (chatController == null) {
            chatController = new ChatController();
            chatController.setGui(this);
        }
        chatController.updateChatSend(message);
    }
    /**
     * Shows two objective cards to the player and handle his choice
     */
    @Override
    public void secretObjectiveCardChoice(List<ObjectiveCard> objectiveCards) throws IOException {
        this.objectiveCards = objectiveCards;
        if (playerObjectiveSeen) {
            chooseObjectiveController = new ChooseObjectiveController();
            chooseObjectiveController.setGui(this);
            chooseObjectiveController.choseObjective(objectiveCards);
        }
    }

    @Override
    public void showStarterCard(StarterCard starterCard) {
    }
    /**
     * Shows the two sides of the starter card and handle the player choice
     */
    @Override
    public void starterCardSideChoice(StarterCard starterCard) throws IOException {
        this.starterCard = starterCard;
        if (playerVisibleseen) {
            starterCardSideController = new StarterCardSideController();
            starterCardSideController.setGui(this);
            starterCardSideController.sideChoice(starterCard);
        }
    }

    public void activateChatButton() {
    }

    @Override
    public void showMyHand() {
    }
    /**
     * Called when we pass from the setup phase to the game phase.
     * Instanciate the gamephase controller and set up the initial scene
     */
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
    /**
     * Called when it's the client time to play a card
     */
    @Override
    public void chooseCardToPlay() throws IOException {
        gamePhaseController.activateTurn();
        gamePhaseController.gamePhase();
    }
    /**
     * Called when the client perform an invalid move
     */
    @Override
    public void invalidMove() throws IOException {
        gamePhaseController.invalidMove();
        gamePhaseController.gamePhase();
    }

    @Override
    public void showUpdatedPlayArea(String nickname, Card[][] playArea) {
    }
    /**
     * Called when it's the client time to draw a card
     */
    @Override
    public void chooseCardToDraw() {
        gamePhaseController.activateDraw();
        gamePhaseController.drawPhase();

    }

    @Override
    public void showUpdatedHand(String nickname) {
    }
    /**
     * Called when the client turn is finished. Updates the scoreBoard and
     * sets up and charge the scene with the updated parameters
     */
    @Override
    public void showScores(Map<String, Integer> playerScores) {
        scores = playerScores;
        gamePhaseController.updateScene();

    }
    /**
     * Called when the game is finished
     */
    @Override
    public void showEndGame(String winner) {
        endGameController = new EndGameController();
        endGameController.setGui(this);
        endGameController.showEndGame(winner);
    }

}