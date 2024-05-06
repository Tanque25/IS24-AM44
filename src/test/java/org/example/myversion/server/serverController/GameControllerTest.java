package org.example.myversion.server.serverController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    private GameController gameController;

    @BeforeEach
    void setUp() {
        gameController = new GameController();
        gameController.newGame(); // Inizializza un nuovo gioco

    }

    @Test
    void addPlayer() {
        gameController.addPlayer("Player1");
        assertEquals(1, gameController.getGame().getPlayers().size());
    }

    @Test
    void avaibleNickname() {
    }

    @Test
    void isFirstPlayer() {
    }

    @Test
    void chooseNumberPlayer() {
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