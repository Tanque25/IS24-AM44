package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    void getScore() {
    }

    @Test
    void findWinner() {
    }
}