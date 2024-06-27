package org.example.myversion.server.model;

import org.example.myversion.server.model.decks.cards.Corner;
import org.example.myversion.server.model.decks.cards.ObjectiveCard;
import org.example.myversion.server.model.decks.cards.StarterCard;
import org.example.myversion.server.model.enumerations.CornerContent;
import org.example.myversion.server.model.enumerations.CornerPosition;
import org.example.myversion.server.model.enumerations.Resource;
import org.example.myversion.server.model.enumerations.SpecialObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Player player1;
    private Player player2;

    private Player player3;
    private Game game;
    private Board board;
    private StarterCard SC_002;
    private StarterCard SC_003;
    private StarterCard SC_004;



    private List<Player> players;

    @BeforeEach
    void setUp() {
        player1 = new Player("Pippo");
        player2 = new Player("Pluto");
        player3 = new Player("Minnie");

        game = new Game();
        board = new Board();
        players = new ArrayList<>();

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
                ),81
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
                ),82
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
                ),83
        );
    }

    @Test
    void newPlayer() {
        // Check if the players list is initially empty
        assertEquals(0, game.getPlayers().size());

        // Add players
        game.newPlayer("Pippo");
        game.newPlayer("Pluto");
        game.newPlayer("Minnie");


        // Check if the players list contains the correct number of players
        assertEquals(3, game.getPlayers().size());
        //assertEquals(player1, game.getPlayers().getFirst());

        // Check if the players added are the expected ones
        assertEquals("Pippo", game.getPlayers().get(0).getNickname());
        assertEquals("Pluto", game.getPlayers().get(1).getNickname());
        assertEquals("Minnie", game.getPlayers().get(2).getNickname());

    }



    @Test
    void getPlayers() {
        // Check if the players list is initially empty
        assertEquals(0, game.getPlayers().size());

        // Add players
        game.newPlayer("Pippo");
        game.newPlayer("Pluto");
        game.newPlayer("Minnie");

        // Retrieve the list of players
        List<Player> players = game.getPlayers();

        // Check if the players list contains the correct number of players
        assertEquals(3, players.size());

        // Check if the players added are the expected ones
        assertEquals("Pippo", players.get(0).getNickname());
        assertEquals("Pluto", players.get(1).getNickname());
        assertEquals("Minnie", players.get(2).getNickname());


    }

    @Test
    void getCurrentPlayer() {
        // Verifica che non ci sia un giocatore corrente all'inizio
        assertNull(game.getCurrentPlayer());

        // Aggiungi giocatori
        game.newPlayer("Pippo");
        game.newPlayer("Pluto");
        game.newPlayer("Minnie");

        // Verifica che il primo giocatore aggiunto sia il giocatore corrente
        assertEquals("Pippo", game.getCurrentPlayer().getNickname());

        game.updateCurrentPlayer();
        assertEquals("Pluto", game.getCurrentPlayer().getNickname());

        game.updateCurrentPlayer();
        assertEquals("Minnie", game.getCurrentPlayer().getNickname());

    }

    @Test
    void placeStarterCard() { //verifico che la carta venga piazzata o anche i punti??
        // Add players
        game.newPlayer("Pippo");
        game.newPlayer("Pluto");
        game.newPlayer("Minnie");

        player1 = game.getPlayers().get(0);
        player2 = game.getPlayers().get(1);
        player3 = game.getPlayers().get(2);

        // Place the starter card for each player
        game.placeStarterCard(player1, SC_002);
        game.placeStarterCard(player2, SC_003);
        game.placeStarterCard(player3, SC_004);



        // Check if the starter card is placed correctly for each player
        assertEquals(SC_002, player1.getPlayArea()[41][41]);
        assertEquals(SC_003, player2.getPlayArea()[41][41]);
        assertEquals(SC_004, player3.getPlayArea()[41][41]);

    }

    @Test
    void drawSecretObjectives() {
        // Draw secret objective cards for the player
        List<ObjectiveCard> secretObjectives = game.drawSecretObjectives();

        // Check that two secret objective cards have been drawn
        assertEquals(2, secretObjectives.size());
    }

    @Test
    void setPlayerSecretObjective() {
        // Add a player
        game.newPlayer("Pippo");

        // Draw a secret objective card
        List<ObjectiveCard> secretObjectives = game.drawSecretObjectives();
        ObjectiveCard objectiveCard = secretObjectives.get(0);

        // Set the secret objective card for the player
        game.setPlayerSecretObjective(game.getPlayers().get(0), objectiveCard);

        // Verify that the player has the correct secret objective card
        assertEquals(objectiveCard, game.getPlayers().get(0).getSecretObjective());
    }


    @Test
    void getCommonObjectives() {
        // Check that there are two initial common objective cards
        assertEquals(2, game.getCommonObjectives().size());
    }

    @Test
    void playCard() {
    }

    @Test
    void drawCard() {
    }

    @Test
    void updateCurrentPlayer() {
        game.newPlayer("Pippo");
        game.newPlayer("Pluto");
        game.newPlayer("Minnie");

        // Assicurati che i giocatori siano inizializzati correttamente nel gioco
        assertNotNull(game.getPlayers());
        assertFalse(game.getPlayers().isEmpty());


        // Verifica che il currentPlayer sia inizialmente player1
        assertEquals("Pippo", game.getCurrentPlayer().getNickname());

        // Chiama il metodo updateCurrentPlayer() per avanzare al prossimo giocatore
        game.updateCurrentPlayer();

        // Verifica che il currentPlayer sia ora player2
        assertEquals("Pluto", game.getCurrentPlayer().getNickname());

        game.updateCurrentPlayer();
        assertEquals("Minnie", game.getCurrentPlayer().getNickname());

        game.updateCurrentPlayer();
        assertEquals("Pippo", game.getCurrentPlayer().getNickname());

    }

    @Test
    void isLastTurn() {
        // Initially, the last turn should be false
        assertFalse(game.isLastTurn());

        // Add players
        game.newPlayer("Pippo");
        game.newPlayer("Pluto");
        game.newPlayer("Minnie");

        // Check if the last turn remains false after adding players
        assertFalse(game.isLastTurn());

        // Simulate the last turn condition
        game.updateCurrentPlayer(); // Move to the next player
        game.updateCurrentPlayer(); // Move to the next player again (last player in the list)
        assertFalse(game.isLastTurn());

        // Move to the next player (the first player in the list)
        game.updateCurrentPlayer();
        assertFalse(game.isLastTurn()); // Should be false again
    }

    @Test
    void changePlayerConnectionStatus() {
        // Add players
        game.newPlayer("Pippo");
        game.newPlayer("Pluto");
        game.newPlayer("Minnie");

        // Retrieve the players
        List<Player> players = game.getPlayers();

        // Change the connection status of player1 to true
        //game.changePlayerConnectionStatus(player1, true);

        // Check if the connection status of player1 is updated correctly
        //assertTrue(player1.isConnected());

        // Change the connection status of player2 to false
        //game.changePlayerConnectionStatus(player2, false);

        // Check if the connection status of player2 is updated correctly
        //assertFalse(player2.isConnected());

        // Change the connection status of player3 to true
        //game.changePlayerConnectionStatus(player3, true);

        // Check if the connection status of player3 is updated correctly
        //assertTrue(player3.isConnected());
    }

    @Test
    void winner() {
        // Add players
        game.newPlayer("Pippo");
        game.newPlayer("Pluto");
        game.newPlayer("Minnie");


        // Assicurati che ci siano giocatori nel gioco
        List<Player> players = game.getPlayers();
        assertNotNull(players);
        assertFalse(players.isEmpty());

        // Assicurati che i punteggi dei giocatori siano inizializzati correttamente
        for (Player player : players) {
            assertNotNull(game.getBoard().getScore(player));
            assertEquals(0, game.getBoard().getScore(player).intValue());
        }

        // Aggiungi punteggi ai giocatori
        game.getBoard().updateScore(players.get(0), 10, 1); // Player1
        game.getBoard().updateScore(players.get(1), 15, 1); // Player2
        game.getBoard().updateScore(players.get(2), 20, 1); // Player3

        // Verifica che il giocatore con il punteggio più alto sia corretto
        Player winner = game.winner();
        assertNotNull(winner);
        assertEquals("Minnie", winner.getNickname());

    }
    @Test
    void noWinner() {
        // Aggiungi giocatori
        game.newPlayer("Pippo");
        game.newPlayer("Pluto");
        game.newPlayer("Minnie");

        // Assicurati che ci siano giocatori nel gioco
        List<Player> players = game.getPlayers();
        assertNotNull(players);
        assertFalse(players.isEmpty());

        // Imposta il punteggio per tutti i giocatori allo stesso valore
        int sameScore = 10; // Puoi scegliere qualsiasi valore
        for (Player player : players) {
            game.getBoard().updateScore(player, sameScore, 1);
        }

        // Verifica che non ci sia un vincitore
        assertNull(game.winner());
    }

    @Test
    void initializeGame() {
        assertTrue(game.getCommonObjectives().isEmpty());
        assertTrue(game.getVisibleResourceCards().isEmpty());
        assertTrue(game.getVisibleGoldCards().isEmpty());

        // Initialize the game
        game.initializeGame();

        // Verify that the game has been initialized
        for (Player player : game.getPlayers()) {
            assertEquals(0, game.getBoard().getScore(player));
        }

        // Verify that the round map is properly initialized
        for (Player player : game.getPlayers()) {
            assertEquals(0, game.getBoard().getScore(player));
        }

        // Assert that the lists have been populated
        assertEquals(2, game.getCommonObjectives().size());
        assertEquals(2, game.getVisibleGoldCards().size());
        assertEquals(2, game.getVisibleResourceCards().size());
    }
}