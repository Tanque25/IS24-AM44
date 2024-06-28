package it.polimi.ingsw.server.serverController;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.decks.cards.*;
import it.polimi.ingsw.server.model.exceptions.InvalidChoiceException;
import it.polimi.ingsw.server.model.exceptions.InvalidGameStateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest{

    private GameController gameController;
    private StarterCard SC_002;
    private StarterCard SC_003;
    private StarterCard SC_004;

    private Game testGame;
    private Player testPlayer;

    private StarterCard testStarterCard;
    private ObjectiveCard OC_087;
    private ObjectiveCard OC_088;

    @BeforeEach
    void setUp() throws InvalidGameStateException {
        gameController = new GameController();
        testPlayer = new Player("TestPlayer");
        //testStarterCard = new StarterCard("TestStarterCard", 5, 3, 2);
        /*SC_002 = new StarterCard(
                // Resources of the card
                new Resource[]{Resource.FUNGI_KINGDOM},
                // Corners of the card
                Map.of(
                        CornerPosition.UP_LEFT, new Corner(Resource.ANIMAL_KINGDOM),
                        CornerPosition.UP_RIGHT, new Corner(null),
                        CornerPosition.BOTTOM_LEFT, new Corner(null),
                        CornerPosition.BOTTOM_RIGHT, new Corner(Resource.FUNGI_KINGDOM)
                ),
                // Back corners of the card
                Map.of(
                        CornerPosition.UP_LEFT, new Corner(Resource.PLANT_KINGDOM),
                        CornerPosition.UP_RIGHT, new Corner(Resource.ANIMAL_KINGDOM),
                        CornerPosition.BOTTOM_LEFT, new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT, new Corner(Resource.INSECT_KINGDOM)
                )
        );

        SC_003 = new StarterCard(
                new Resource[]{Resource.PLANT_KINGDOM, Resource.FUNGI_KINGDOM},
                Map.of(
                        CornerPosition.UP_LEFT, new Corner(null),
                        CornerPosition.UP_RIGHT, new Corner(null),
                        CornerPosition.BOTTOM_LEFT, new Corner(null),
                        CornerPosition.BOTTOM_RIGHT, new Corner(null)
                ),
                Map.of(
                        CornerPosition.UP_LEFT, new Corner(Resource.INSECT_KINGDOM),
                        CornerPosition.UP_RIGHT, new Corner(Resource.ANIMAL_KINGDOM),
                        CornerPosition.BOTTOM_LEFT, new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT, new Corner(Resource.PLANT_KINGDOM)
                )
        );

        SC_004 = new StarterCard(
                new Resource[]{Resource.ANIMAL_KINGDOM, Resource.INSECT_KINGDOM},
                Map.of(
                        CornerPosition.UP_LEFT, new Corner(null),
                        CornerPosition.UP_RIGHT, new Corner(null),
                        CornerPosition.BOTTOM_LEFT, new Corner(null),
                        CornerPosition.BOTTOM_RIGHT, new Corner(null)
                ),
                Map.of(
                        CornerPosition.UP_LEFT, new Corner(Resource.PLANT_KINGDOM),
                        CornerPosition.UP_RIGHT, new Corner(Resource.INSECT_KINGDOM),
                        CornerPosition.BOTTOM_LEFT, new Corner(Resource.ANIMAL_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT, new Corner(Resource.FUNGI_KINGDOM)
                )
        );

        OC_087 = new PatternObjectiveCard(
                2,
                new Resource[][]{
                        {null, null, Resource.FUNGI_KINGDOM},
                        {null, Resource.FUNGI_KINGDOM, null},
                        {Resource.FUNGI_KINGDOM, null, null}
                }
                );

        OC_088 = new PatternObjectiveCard(2, new Resource[][]{
                {Resource.PLANT_KINGDOM, null, null},
                {null, Resource.PLANT_KINGDOM, null},
                {null, null, Resource.PLANT_KINGDOM}
        });*/

    }
    @Test
    void availableNickname() {// Setup
        String nickname = "Giulio";
        int result = gameController.checkNickname(nickname);
        assertEquals(1, result, "checkNickname should return 1 when the nickname is available");

    }

    @Test
    void noAvailableNickname(){

        String nickname = "Giulio";
        gameController.addPlayer(nickname); // Aggiunge un giocatore con lo stesso nickname

        int result = gameController.checkNickname(nickname);

        assertEquals(0, result, "checkNickname should return 0 when the nickname is already used by a player");
        assertNotEquals(1, result);
    }

    @Test
    void chooseNumberPlayer_caseTrue() {
        int numPlayers = 4; // Un numero valido di giocatori

        boolean result = false;
        result = gameController.checkNumberOfPlayer(numPlayers);
        // Assert
        assertTrue(result, "chooseNumberPlayer should return true for a valid number of players");

    }

    @Test
    void gameIsStarted() {
        gameController.setGameState(GameState.LOGIN);
        assertFalse(gameController.isGameStarted());
        gameController.setGameState(GameState.INITIALIZATION);
        assertFalse(gameController.isGameStarted());
        gameController.setGameState(GameState.IN_GAME);
        assertTrue(gameController.isGameStarted());
        gameController.setGameState(GameState.LAST_ROUND);
        assertTrue(gameController.isGameStarted());
        gameController.setGameState(GameState.END);
        assertFalse(gameController.isGameStarted());
    }

    @Test
    void playCard() {
    }

    @Test
    void drawCard() {
    }

    @Test
    void changeTurn() throws InvalidGameStateException {
        gameController.setPlayersNumber(2);
        gameController.addPlayer("Lautaro");
        gameController.addPlayer("Handanovic");
        gameController.setGameState(GameState.IN_GAME);
        gameController.changeTurn();
        assertEquals("Handanovic", gameController.getGame().getCurrentPlayer().getNickname());
        gameController.changeTurn();
        assertEquals("Lautaro", gameController.getGame().getCurrentPlayer().getNickname());
    }

    @Test
    void setWinner() {
        gameController.setGameState(GameState.END);

    }

    @Test
    void chooseObjectiveCard() {
        gameController.addPlayer("Giulio");

        gameController.newGame();

        gameController.chooseObjectiveCard(gameController.getGame().getPlayers().getFirst(), OC_087);
        assertEquals(OC_087, gameController.getGame().getPlayers().getFirst().getSecretObjective());

        gameController.chooseObjectiveCard(gameController.getGame().getPlayers().getFirst(), OC_088);
        assertEquals(OC_088, gameController.getGame().getPlayers().getFirst().getSecretObjective());

    }


    @Test
    void getLastPlayer() {
        gameController.setPlayersNumber(4);
        gameController.addPlayer("Palacio");
        gameController.addPlayer("Milito");
        gameController.addPlayer("Cambiasso");
        gameController.addPlayer("Stankovic");

        assertEquals("Stankovic", gameController.getLastPlayer().getNickname());

    }

    @Test
    void getFirstPlayer() {
        gameController.setPlayersNumber(4);
        gameController.addPlayer("Palacio");
        gameController.addPlayer("Milito");
        gameController.addPlayer("Cambiasso");
        gameController.addPlayer("Stankovic");

        //assertEquals("Palacio", gameController.getFirstPlayer().getNickname());
    }

    @Test
    void gameIsFull() {
        gameController.setPlayersNumber(4);
        gameController.addPlayer("Palacio");
        gameController.addPlayer("Milito");
        gameController.addPlayer("Cambiasso");
        gameController.addPlayer("Stankovic");

        assertTrue(gameController.gameIsFull());
    }

    @Test
    void gameIsEmpty() {
        assertTrue(gameController.gameIsEmpty());
        gameController.addPlayer("Palacio");
        assertFalse(gameController.gameIsEmpty());
    }

    @Test
    void checkScores() {
        gameController.setPlayersNumber(3);
        gameController.addPlayer("Palacio");
        gameController.addPlayer("Milito");
        gameController.addPlayer("Cambiasso");

        gameController.newGame();

        assertEquals(gameController.getGame().getBoard().getScore(gameController.getGame().getPlayers().getFirst()), 0);
        assertEquals(gameController.getGame().getBoard().getScore(gameController.getGame().getPlayers().get(1)), 0);
        assertEquals(gameController.getGame().getBoard().getScore(gameController.getGame().getPlayers().getLast()), 0);

        gameController.getGame().getBoard().updateScore(gameController.getGame().getPlayers().getFirst(), 10, 1);
        gameController.getGame().getBoard().updateScore(gameController.getGame().getPlayers().get(1), 20, 1);
        gameController.getGame().getBoard().updateScore(gameController.getGame().getPlayers().getLast(), 19, 1);

        assertTrue(gameController.checkScores());
    }

    @Test
    void checkEmptyDeck() {
        gameController.setPlayersNumber(3);
        gameController.addPlayer("Palacio");
        gameController.addPlayer("Milito");
        gameController.addPlayer("Cambiasso");

        gameController.newGame();

        assertFalse(gameController.checkEmptyDeck());

    }
}