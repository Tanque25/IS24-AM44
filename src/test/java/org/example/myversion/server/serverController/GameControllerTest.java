package org.example.myversion.server.serverController;

import org.example.myversion.server.model.Player;
import org.example.myversion.server.model.exceptions.InvalidChoiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    private GameController gameController;

    @BeforeEach
    void setUp() {
        gameController = new GameController();

        gameController.addPlayer("Pippo");
        gameController.addPlayer("Giulio");
        gameController.addPlayer("Claudio");

        gameController.newGame(); // Inizializza un nuovo gioco

    }

    @Test
    void addPlayer() {

        gameController.addPlayer("Player1");
        assertEquals(4, gameController.getGame().getPlayers().size());

        gameController.addPlayer("Player2");
        gameController.addPlayer("Player3");
        assertEquals(6, gameController.getGame().getPlayers().size());

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
    void isFirstPlayer() {
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
    void changeTurn() {
       /* Player firstPlayer = gameController.getFirstPlayer();
        Player currentPlayer = gameController.getCurrentPlayer();
        assertNotNull(firstPlayer);
        //assertNotNull(currentPlayer);
        assertEquals(firstPlayer, currentPlayer, "Il primo giocatore dovrebbe essere il giocatore corrente iniziale");

        // Esecuzione
        gameController.changeTurn();

        // Assert
        assertNotEquals(firstPlayer, gameController.getCurrentPlayer(), "Il turno dovrebbe essere cambiato dopo chiamata a changeTurn()");*/

        // Verifica che il turno sia inizialmente sul primo giocatore
        assertEquals("Pippo", gameController.getCurrentPlayer().getNickname());

        // Cambia il turno una volta
        gameController.changeTurn();
        // Verifica che il turno sia passato al secondo giocatore
        assertEquals("Giulio", gameController.getCurrentPlayer().getNickname());

        // Cambia il turno due volte
        gameController.changeTurn();
        gameController.changeTurn();
        // Verifica che il turno sia passato al terzo giocatore
        assertEquals("Claudio", gameController.getCurrentPlayer().getNickname());

        // Cambia il turno tre volte (tornerà al primo giocatore)
        gameController.changeTurn();
        gameController.changeTurn();
        gameController.changeTurn();
        // Verifica che il turno sia tornato al primo giocatore
        assertEquals("Pippo", gameController.getCurrentPlayer().getNickname());

    }

    @Test
    void isLastTurn() {
    }

    @Test
    void gameOver() {
    }

    @Test
    void setWinner() {
    }

    @Test
    void checkLastTurn() {
    }

    @Test
    void playExtraRound() {
    }

    @Test
    void endGame() {
    }
}