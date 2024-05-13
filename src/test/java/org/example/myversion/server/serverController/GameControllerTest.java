package org.example.myversion.server.serverController;

import org.example.myversion.server.model.Player;
import org.example.myversion.server.model.exceptions.InvalidChoiceException;
import org.example.myversion.server.model.exceptions.InvalidGameStateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest{

    private GameController gameController;

    @BeforeEach
    void setUp() throws InvalidGameStateException {
        gameController = new GameController();
    }

    @Test
    void addPlayer() {

        gameController.setMaxPlayerNumber(4);
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
        try {
            result = gameController.checkNumberOfPlayer(numPlayers);
        } catch (InvalidChoiceException e) {
            fail("An unexpected InvalidChoiceException was thrown");
        }
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

    }

    @Test
    void gameIsStarted() {

    }

    @Test
    void playCard() {
    }

    @Test
    void drawCard() {
    }

    @Test
    void changeTurn() throws InvalidGameStateException {
        gameController.setMaxPlayerNumber(2);
        gameController.addPlayer("Lautaro");
        gameController.addPlayer("Handanovic");
        gameController.changeTurn();
        assertEquals(gameController.getGame().getCurrentPlayer().getNickname(), "Handanovic");
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
    }

    @Test
    void endGame() {
    }
}