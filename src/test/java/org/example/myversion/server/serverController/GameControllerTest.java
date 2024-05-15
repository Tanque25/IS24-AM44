package org.example.myversion.server.serverController;

import org.example.myversion.server.model.Player;
import org.example.myversion.server.model.decks.cards.*;
import org.example.myversion.server.model.enumerations.CornerPosition;
import org.example.myversion.server.model.enumerations.Resource;
import org.example.myversion.server.model.exceptions.InvalidChoiceException;
import org.example.myversion.server.model.exceptions.InvalidGameStateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest{

    private GameController gameController;
    private StarterCard SC_002;
    private StarterCard SC_003;
    private StarterCard SC_004;

    private ObjectiveCard OC_087;
    private ObjectiveCard OC_088;

    @BeforeEach
    void setUp() throws InvalidGameStateException {
        gameController = new GameController();

        SC_002 = new StarterCard(
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
        });

    }

    @Test
    void addPlayer() {

        gameController.setPlayersNumber(4);
        gameController.addPlayer("Palacio");
        assertEquals(1, gameController.getGame().getPlayers().size());
        assertEquals(gameController.getGame().getPlayers().getFirst(), gameController.getFirstPlayer());
        assertEquals("Palacio", gameController.getFirstPlayer().getNickname());
        gameController.addPlayer("Milito");
        assertEquals(2, gameController.getGame().getPlayers().size());
        assertEquals(gameController.getGame().getPlayers().get(1).getNickname(), "Milito");
        gameController.addPlayer("Cambiasso");
        assertEquals(3, gameController.getGame().getPlayers().size());
        assertEquals(gameController.getGame().getPlayers().get(2).getNickname(), "Cambiasso");
        gameController.addPlayer("Stankovic");
        assertEquals(4, gameController.getGame().getPlayers().size());
        assertEquals(gameController.getGame().getPlayers().getLast(), gameController.getLastPlayer());
        assertEquals("Stankovic", gameController.getLastPlayer().getNickname());
        gameController.addPlayer("Mancini");
        assertEquals(4, gameController.getGame().getPlayers().size());

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
    void chooseNumberPlayer_caseFalse() {
        int numPlayers = 100;

        assertThrows(InvalidChoiceException.class, () -> {
            gameController.checkNumberOfPlayer(numPlayers);
        }, "chooseNumberPlayer should throw InvalidChoiceException for an invalid number of players");

    }

    @Test
    void newGame() {
        gameController.setGameState(GameState.LOGIN);
        assertTrue(gameController.getGame().getCommonObjectives().isEmpty());
        assertTrue(gameController.getGame().getVisibleResourceCards().isEmpty());
        assertTrue(gameController.getGame().getVisibleGoldCards().isEmpty());


        gameController.newGame();

        // Verify that the game state is properly initialized
        assertEquals(GameState.INITIALIZATION, gameController.getGameState());

        // Verify that the rounds played counter is reset to 0
        assertEquals(0, gameController.getRoundsPlayed());

        // Verify that the game has been initialized
        for (Player player : gameController.getGame().getPlayers()) {
            assertEquals(0, gameController.getGame().getBoard().getScore(player));
        }

        // Verify that the round map is properly initialized
        for (Player player : gameController.getGame().getPlayers()) {
            assertEquals(0, gameController.getPlayerRoundsPlayed().get(player));
        }

        // Assert that the lists have been populated
        assertEquals(2, gameController.getCommonObjectiveCards().size());
        assertEquals(2, gameController.getGame().getVisibleResourceCards().size());
        assertEquals(2, gameController.getGame().getVisibleGoldCards().size());


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
    void changeTurnException() {
        gameController.setGameState(GameState.LOGIN);
        // Verifica che il turno sia inizialmente sul primo giocatore
        assertEquals("Pippo", gameController.getGame().getCurrentPlayer().getNickname());
        // Verifica che venga lanciata un'eccezione quando si tenta di cambiare il turno
        assertThrows(InvalidGameStateException.class, () -> gameController.changeTurn());

    }

    @Test
    void isLastTurn() {
        gameController.setGameState(GameState.LAST_ROUND);
        assertTrue(gameController.isLastTurn());
        gameController.setGameState(GameState.END);
        assertFalse(gameController.isLastTurn());
    }

    @Test
    void isGameOver() {
        gameController.setGameState(GameState.END);
        assertTrue(gameController.isGameOver());
        gameController.setGameState(GameState.LAST_ROUND);
        assertFalse(gameController.isLastTurn());
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
    void playStarterCard() throws InvalidGameStateException { //con il currentPlayer dà problemi
        gameController.setPlayersNumber(3);
        gameController.addPlayer("Giulio");
        gameController.addPlayer("Pippo");
        gameController.addPlayer("Lucia");

        Player CurrentPlayer = gameController.getGame().getCurrentPlayer();
        gameController.setGameState(GameState.INITIALIZATION);

        gameController.playStarterCard(CurrentPlayer,SC_002, 0);
        assertEquals("Giulio", gameController.getFirstPlayer().getNickname());
        assertEquals(SC_002, gameController.getGame().getCurrentPlayer().getPlayArea()[41][41]);
        assertTrue(SC_002.isPlayedBack());

        gameController.changeTurn();
        assertEquals("Pippo", gameController.getGame().getCurrentPlayer().getNickname());
        assertEquals(gameController.getGame().getPlayers().get(1), gameController.getGame().getCurrentPlayer());
        //gameController.playStarterCard(CurrentPlayer,SC_003, 1);
        gameController.playStarterCard(gameController.getGame().getPlayers().get(1),SC_003, 1);
        //assertEquals(SC_003, gameController.getGame().getPlayers().getFirst().getPlayArea()[41][41]);
        assertEquals(SC_003, gameController.getGame().getPlayers().get(1).getPlayArea()[41][41]);
        assertFalse(SC_003.isPlayedBack());

        gameController.changeTurn();
        //gameController.playStarterCard(CurrentPlayer,SC_004, 0);
        assertEquals("Lucia", gameController.getGame().getCurrentPlayer().getNickname());
        //assertEquals(SC_004, gameController.getGame().getCurrentPlayer().getPlayArea()[41][41]);
        gameController.playStarterCard(gameController.getGame().getPlayers().get(2),SC_004, 1);
        //assertEquals(SC_003, gameController.getGame().getCurrentPlayer().getPlayArea()[41][41]);
        assertEquals(SC_004, gameController.getGame().getPlayers().get(2).getPlayArea()[41][41]);
        assertFalse(SC_004.isPlayedBack());

        //Lucia is the last player, so the gameState should be changed to IN_GAME
        assertEquals("Lucia", gameController.getLastPlayer().getNickname());
        assertEquals(gameController.getGameState(), GameState.IN_GAME);

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

        assertEquals("Palacio", gameController.getFirstPlayer().getNickname());
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

        gameController.getGame().getBoard().updateScore(gameController.getGame().getPlayers().getFirst(), 10);
        gameController.getGame().getBoard().updateScore(gameController.getGame().getPlayers().get(1), 20);
        gameController.getGame().getBoard().updateScore(gameController.getGame().getPlayers().getLast(), 19);

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