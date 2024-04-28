package org.example.myversion.server.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class BoardTest {
    private Board board;
    private Player player1;
    private Player player2;
    private Player player3;

    @BeforeEach
    void setUp() {
        board = new Board();
        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
        player3 = new Player("Player 3");
    }

    @Test
    void initializePlayerScores() {
        List<Player> players = new ArrayList<>();

        //aggiungi giocatori list
        players.add(player1);
        players.add(player2);
        players.add(player3);

        board.initializePlayerScores(players);

        assertEquals(0, board.getScore(player1));//controllo del primo inzializzazione
        assertEquals(0, board.getScore(player2));//...
        assertEquals(0, board.getScore(player3));//...
    }

    @Test
    void updateScore() {
        board.updateScore(player1, 10);
        board.updateScore(player2, 20);

        assertEquals(10, board.getScore(player1));
        assertEquals(20, board.getScore(player2));

    }

    @Test
    void getScore() {
    }

    @Test
    void findWinner() {
    }
}