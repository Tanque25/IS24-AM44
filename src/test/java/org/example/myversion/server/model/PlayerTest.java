package org.example.myversion.server.model;

import org.example.myversion.server.model.decks.ResourceDeck;
import org.example.myversion.server.model.decks.cards.PlayableCard;
import org.example.myversion.server.model.decks.cards.*;
import java.util.ArrayList;

import org.example.myversion.server.model.enumerations.*;
import org.example.myversion.server.model.enumerations.Resource;
import org.example.myversion.server.model.exceptions.InvalidMoveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link Player}.
 */
/*
class PlayerTest {

    private Player player;
    private List<PlayableCard> hand;

    private Map<CornerPosition, Corner> corners;

    @BeforeEach
    void setUp() {
        player = new Player("TestPlayer");
    }

    @Test
    void getNickname() {
        String expectedNickname = "TestPlayer";
        //primo parametro expected
        Player player = new Player(expectedNickname);
        //parametri actual
        String actualNickname = player.getNickname();
        // Assertion:
        assertEquals(expectedNickname, actualNickname);
    }

    @Test
    void setConnected() {
        assertTrue(player.isConnected());//all'inizio è connesso

        player.setConnected(false);//non piu connesso

        player.setConnected(true);//si riconnette, ho fatto piu volte test passato

        //assert:
        assertTrue(player.isConnected());
    }

    @Test
    void isConnected() {
        // Creazione di un giocatore connesso
        Player connectedPlayer = new Player("ConnectedPlayer");
        assertTrue(connectedPlayer.isConnected());//true

        // Creazione di un giocatore non connesso
        Player disconnectedPlayer = new Player("DisconnectedPlayer");
        disconnectedPlayer.setConnected(false);
        assertFalse(disconnectedPlayer.isConnected());//false
    }

    @Test
    void initializeHand() {

        List<PlayableCard> hand = new ArrayList<>();
        hand.add(new PlayableCard(Resource.FUNGI_KINGDOM,null,1));//41
        hand.add(new PlayableCard(Resource.FUNGI_KINGDOM,null,1));//42
        hand.add(new PlayableCard(Resource.INSECT_KINGDOM,null,0));//31
        player.initializeHand(hand);
        assertEquals(3, player.getHand().size());//controllo che il numero sia uguale
        assertEquals(hand.get(0), player.getHand().get(0));//controllo che il primo elemento della lista si = a cio cre restituisce player.getHand-->inizializzazione corretta
        assertEquals(hand.get(1), player.getHand().get(1));//...
        assertEquals(hand.get(2), player.getHand().get(2));//...

    }

    @Test
    void initializePlayArea() {

        corners=new HashMap<>();
        corners.put(CornerPosition.UP_LEFT,new Corner(null));//full ha senso utilizzarlo ???
        corners.put(CornerPosition.UP_RIGHT,new Corner(Resource.PLANT_KINGDOM));
        corners.put(CornerPosition.BOTTOM_LEFT,new Corner(Resource.INSECT_KINGDOM));
        corners.put(CornerPosition.BOTTOM_RIGHT,new Corner(null));

        StarterCard starterCard = new StarterCard(new Resource[]{ Resource.INSECT_KINGDOM},corners,null);
        player.initializePlayArea(starterCard);//verificato anch caso in cui Corner =null,

        Card[][] playArea = player.getPlayArea();

        // Verifica che lo starter card sia stato posizionato al centro
        assertEquals(starterCard, playArea[41][41]);

        //Verifica che il player stock sia stato aggiornato correttamente
        Map<CornerContent, Integer> expectedStock = new HashMap<>();
        expectedStock.put(Resource.PLANT_KINGDOM, 1);
        expectedStock.put(Resource.INSECT_KINGDOM, 2);
        expectedStock.put(SpecialObject.MANUSCRIPT, 0);
        expectedStock.put(Resource.FUNGI_KINGDOM, 0);
        expectedStock.put(Resource.ANIMAL_KINGDOM, 0);
        expectedStock.put(SpecialObject.INKWELL, 0);
        expectedStock.put(SpecialObject.QUILL, 0);
        //non ho controllato se tui resituisce FULL o  empty o non -->dove controllare!!!???

        assertEquals(expectedStock, player.getStock());
    }

    @Test
    void setSecretObjective() {

        ObjectiveCard objectiveCard = new ResourceObjectiveCard(2,new Resource[]{
                Resource.FUNGI_KINGDOM, Resource.FUNGI_KINGDOM, Resource.FUNGI_KINGDOM});//card 96

        player.setSecretObjective(objectiveCard);
        assertNotNull(player.getSecretObjective());//verifico che non sia null
        assertEquals(objectiveCard, player.getSecretObjective());//verifico che objective card si uguale a quella restituita dal get-->set funzia

    }

    @Test
    void getHand() {

        List<PlayableCard> hand = new ArrayList<>();

        PlayableCard playableCard1 = new PlayableCard(Resource.FUNGI_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT,new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                0
        ); // card 1

        PlayableCard playableCard2 =new GoldCard(
                Resource.PLANT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT, new Corner(SpecialObject.QUILL),
                        CornerPosition.UP_RIGHT, new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_LEFT, new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT, new Corner(CornerVisibility.EMPTY)
                ),
                1,
                new Resource[]{Resource.PLANT_KINGDOM, Resource.PLANT_KINGDOM, Resource.INSECT_KINGDOM},
                SpecialObject.QUILL
        );//card 51

        hand.add(playableCard1);
        hand.add(playableCard2);

        player.initializeHand(hand);

        assertEquals(2, player.getHand().size());// controllo la dimensione della mano
        assertTrue(player.getHand().contains(hand.get(0)));//controllo se primo elemento restituisce lo stesso del get
        assertTrue(player.getHand().contains(hand.get(1)));//...
    }

    @Test
    void getPlayArea() {

        // Verifica che il campo di gioco sia correttamente inizializzato
        Card[][] playArea = player.getPlayArea();
        assertNotNull(playArea);
        assertEquals(81, playArea.length);
        assertEquals(81, playArea[0].length);

        // Verifica che tutti gli elementi del campo di gioco siano null all'inizio
        for (int i = 0; i < 81; i++) {
            for (int j = 0; j < 81; j++) {
                assertNull(playArea[i][j]);
            }
        }
    }

    @Test
    void placeCardRemoveFromHand() {

        PlayableCard playableCard2 =new GoldCard(
                Resource.PLANT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT, new Corner(SpecialObject.QUILL),
                        CornerPosition.UP_RIGHT, new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_LEFT, new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT, new Corner(CornerVisibility.EMPTY)
                ),
                1,
                new Resource[]{Resource.PLANT_KINGDOM, Resource.PLANT_KINGDOM, Resource.INSECT_KINGDOM},
                SpecialObject.QUILL
        );//card 51

        //pesca
        player.drawCard(playableCard2);
        assertEquals(1, player.getHand().size());//vediamo se la mano diventa uguale a 1
        //piazza la carta alle cordinate
        player.placeCard(playableCard2, new Coordinates(40, 40));
        //controlla la size della hand
        assertEquals(0, player.getHand().size());

    }

    @Test
    public void testPlaceCardUpdatesPlayArea() {

        PlayableCard playableCard2 =new GoldCard(
                Resource.PLANT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT, new Corner(SpecialObject.QUILL),
                        CornerPosition.UP_RIGHT, new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_LEFT, new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT, new Corner(CornerVisibility.EMPTY)
                ),
                1,
                new Resource[]{Resource.PLANT_KINGDOM, Resource.PLANT_KINGDOM, Resource.INSECT_KINGDOM},
                SpecialObject.QUILL
        );//card 51

        //pesca
        player.drawCard(playableCard2);

        player.drawCard(playableCard2);
        assertNull(player.getPlayArea()[40][40]);//controllo che sia null all'inzio
        //piazzo la carta
        player.placeCard(playableCard2, new Coordinates(40, 40));
        //controllo che la play area si not null
        assertNotNull(player.getPlayArea()[40][40]);
    }
    //implementare anche metodo per updateStock correttamente??-->dovrebbe funzionare gia comunque per controllato
    //in un altro su

    @Test
    void isValidMove() throws InvalidMoveException {
        PlayableCard playableCard2 =new GoldCard(
                Resource.PLANT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT, new Corner(SpecialObject.QUILL),
                        CornerPosition.UP_RIGHT, new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_LEFT, new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT, new Corner(CornerVisibility.EMPTY)
                ),
                1,
                new Resource[]{Resource.PLANT_KINGDOM, Resource.PLANT_KINGDOM, Resource.INSECT_KINGDOM},
                SpecialObject.QUILL
        );//card 51

        // Test when the position is valid and no adjacent corners are full
        assertTrue(player.isValidMove(new Coordinates(40, 40)));

        // Test when the position is not valid, gia occupata
        player.placeCard(playableCard2, new Coordinates(40, 40));
        assertFalse(player.isValidMove(new Coordinates(40, 40)));// mettere a posto eccezione

        // corner è non visibile
        PlayableCard adjacentCard = playableCard2;
        player.placeCard(adjacentCard, new Coordinates(41, 41));
        assertFalse(player.isValidMove(new Coordinates(40, 40)));

        // Test when position is not valid due to card presence nearby
        assertFalse(player.isValidMove(new Coordinates(41, 40)));

        //aggiungerene altri tipo quando tutti i corner sono pieni?????
    }

    @Test
    void drawCard() {

        // Verifico che all'inizio il giocatore non abbia carte in mano
        assertEquals(0, player.getNumberCardHand());//mettere per getNumberCardHand eccezioni non if

        PlayableCard playableCard2 =new GoldCard(
                Resource.PLANT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT, new Corner(SpecialObject.QUILL),
                        CornerPosition.UP_RIGHT, new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_LEFT, new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT, new Corner(CornerVisibility.EMPTY)
                ),
                1,
                new Resource[]{Resource.PLANT_KINGDOM, Resource.PLANT_KINGDOM, Resource.INSECT_KINGDOM},
                SpecialObject.QUILL
        );//card 51

        // Aggiunge la carta alla mano del giocatore
        player.drawCard(playableCard2);

        // Verifica che la carta sia stata aggiunta correttamente alla mano del giocatore
        assertEquals(1, player.getHand().size());
        assertTrue(player.getHand().contains(playableCard2));
    }

    @Test
    void finalScoreCalculator() {

        ObjectiveCard objectiveCard1 =new ResourceObjectiveCard(2, new Resource[]{
                Resource.FUNGI_KINGDOM, Resource.FUNGI_KINGDOM, Resource.FUNGI_KINGDOM}
        ); // 95

        ObjectiveCard objectiveCard2 =new ResourceObjectiveCard(2, new Resource[]{
                Resource.INSECT_KINGDOM, Resource.INSECT_KINGDOM, Resource.INSECT_KINGDOM}
        ); // 98

        ObjectiveCard objectiveCard3=new ResourceObjectiveCard(2, new Resource[]{
                Resource.ANIMAL_KINGDOM, Resource.ANIMAL_KINGDOM, Resource.ANIMAL_KINGDOM}
        ); // 97

        // Creazione di carte obiettivo comuni per il test
        List<ObjectiveCard> commonObjectives = new ArrayList<>();
        commonObjectives.add(objectiveCard1);
        commonObjectives.add(objectiveCard2);

        // Simulazione di uno stock per il test
        Map<CornerContent, Integer> stock = new HashMap<>();
        stock.put(Resource.ANIMAL_KINGDOM, 3);
        stock.put(Resource.INSECT_KINGDOM, 4);

        // Calcolo del punteggio finale
        int finalScore = player.finalScoreCalculator(commonObjectives,stock,objectiveCard3);

        // Verifica che il punteggio finale sia corretto
        assertEquals(4, finalScore);

        //fare altri test in altri casi, PatternObjective...
    }

}*/