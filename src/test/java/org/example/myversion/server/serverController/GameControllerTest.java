package org.example.myversion.server.serverController;

import org.example.myversion.server.model.Coordinates;
import org.example.myversion.server.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {
    public GameController controller;

    @BeforeEach
    void setUp() {
        controller = new GameController();
    }
    @Test
    void addPlayer() {
        assertEquals(0, controller.getGame().getPlayers().size());
        controller.addPlayer("lautaroMartinez");
        assertEquals(controller.getGameState(), GameState.LOGIN);
        assertEquals(1, controller.getGame().getPlayers().size());
        assertEquals("lautaroMartinez", controller.getGame().getPlayers().get(0).getNickname());
        assertFalse(controller.isFirstPlayer());
        controller.addPlayer("diegoMilito");
        assertEquals(2, controller.getGame().getPlayers().size());
        assertEquals("diegoMilito", controller.getGame().getPlayers().get(1).getNickname());
    }

    @Test
    void newGame() {
        controller.newGame();
        for(Player player : controller.getGame().getPlayers()){
            assertEquals(0, controller.getPlayerRoundsPlayed().get(player));
        }
        assertEquals(0, controller.getRoundsPlayed());
        for(Player player : controller.getGame().getPlayers()){
            assertEquals(0, controller.getPlayerRoundsPlayed().get(player));
        }
        assert(controller.gameIsStarted());
        assertEquals(controller.getGameState(), GameState.IN_GAME);
    }

    @Test
    void playCard() {
        controller.addPlayer("rodrigoPalacio");
        controller.addPlayer("julioCesar");
        controller.addPlayer("Maicon");
        controller.newGame();
    }

    @Test
    void checkNickname() {
    }



    @Test
    void chooseNumberPlayer() {
    }


    @Test
    void gameIsStarted() {
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