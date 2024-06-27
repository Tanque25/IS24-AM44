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

class PlayerTest {

    private Player player;
    private List<PlayableCard> hand;
    private Map<CornerPosition, Corner> corners;
    private Game game;
    private StarterCard SC_002;
    private StarterCard SC_003;
    private StarterCard SC_004;
    private ObjectiveCard OC_087;
    private ObjectiveCard OC_088;
    private ResourceObjectiveCard OC_096;
    private PlayableCard PC_001;
    private PlayableCard PC_002;
    private PlayableCard PC_003;
    private PlayableCard PC_007;
    private PlayableCard PC_051;
    private List<Player> players;
    private Board board;

    @BeforeEach
    void setUp() {
        player = new Player("TestPlayer");
        game = new Game();
        board = new Board();
        players = new ArrayList<>();

        // Creazione di carte di esempio per i test
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

        PC_007 = new PlayableCard(
                Resource.FUNGI_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.UP_RIGHT,new Corner(Resource.INSECT_KINGDOM),
                        CornerPosition.BOTTOM_LEFT,new Corner(SpecialObject.MANUSCRIPT),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                0, 7
        ); // 7

        OC_087 = new PatternObjectiveCard(2, new Resource[][]{
                {null, null, Resource.FUNGI_KINGDOM},
                {null, Resource.FUNGI_KINGDOM, null},
                {Resource.FUNGI_KINGDOM, null, null}
        }, 87);

        OC_088 = new PatternObjectiveCard(2, new Resource[][]{
                {Resource.PLANT_KINGDOM, null, null},
                {null, Resource.PLANT_KINGDOM, null},
                {null, null, Resource.PLANT_KINGDOM}
        }, 88);

        OC_096 = new ResourceObjectiveCard(2, new Resource[]{
                Resource.PLANT_KINGDOM, Resource.PLANT_KINGDOM, Resource.PLANT_KINGDOM}, 96
        );

        PC_001 = new PlayableCard(
                Resource.FUNGI_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT,new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.FULL)
                ),
                0, 1
        );

        PC_002 = new PlayableCard(
                Resource.FUNGI_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.UP_RIGHT,new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.BOTTOM_LEFT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_RIGHT,new Corner(CornerVisibility.EMPTY)
                ),
                0, 2
        );

        PC_003 = new PlayableCard(
                Resource.FUNGI_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT,new Corner(CornerVisibility.EMPTY),
                        CornerPosition.UP_RIGHT,new Corner(CornerVisibility.FULL),
                        CornerPosition.BOTTOM_LEFT,new Corner(Resource.FUNGI_KINGDOM),
                        CornerPosition.BOTTOM_RIGHT,new Corner(Resource.FUNGI_KINGDOM)
                ),
                0, 3
        );

        PC_051 = new GoldCard(
                Resource.PLANT_KINGDOM,
                Map.of(
                        CornerPosition.UP_LEFT, new Corner(SpecialObject.QUILL),
                        CornerPosition.UP_RIGHT, new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_LEFT, new Corner(CornerVisibility.EMPTY),
                        CornerPosition.BOTTOM_RIGHT, new Corner(CornerVisibility.FULL)
                ),
                1,
                new Resource[]{Resource.PLANT_KINGDOM, Resource.PLANT_KINGDOM, Resource.INSECT_KINGDOM},
                SpecialObject.QUILL, 51
        );
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
    void initializeHand() {

        List<PlayableCard> hand = new ArrayList<>();

        //aggiungo alla mano del player 3 playable card
        hand.add(PC_001);
        hand.add(PC_002);
        hand.add(PC_003);

        player.initializeHand(hand);

        //controllo che il numero sia uguale
        assertEquals(3, player.getHand().size());
        //controllo che il primo elemento della lista si = a cio cre restituisce player.getHand-->inizializzazione corretta
        assertEquals(hand.get(0), player.getHand().get(0));
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

        StarterCard starterCard = SC_002;

        //posiziono la carta al centro
        player.initializePlayArea(starterCard);
        Card[][] playArea = player.getPlayArea();

        // Verifica che lo starter card sia stato posizionato al centro
        assertEquals(starterCard, playArea[41][41]);

        //Verifica che il player stock sia stato aggiornato correttamente
        Map<CornerContent, Integer> expectedStock = new HashMap<>();
        expectedStock.put(Resource.PLANT_KINGDOM, 0);
        expectedStock.put(Resource.INSECT_KINGDOM, 0);
        expectedStock.put(Resource.FUNGI_KINGDOM, 2);
        expectedStock.put(Resource.ANIMAL_KINGDOM, 1);
        expectedStock.put(SpecialObject.MANUSCRIPT, 0);
        expectedStock.put(SpecialObject.INKWELL, 0);
        expectedStock.put(SpecialObject.QUILL, 0);

        assertEquals(expectedStock, player.getStock());
    }

    @Test
    void setSecretObjective() {

        player.setSecretObjective(OC_096);
        assertNotNull(player.getSecretObjective());//verifico che non sia null
        assertEquals(OC_096, player.getSecretObjective());//verifico che objective card si uguale a quella restituita dal get-->set funzia

    }

    @Test
    void getHand() {

        List<PlayableCard> hand = new ArrayList<>();

        hand.add(PC_001);
        hand.add(PC_051);

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
        //pesca
        player.drawCard(PC_051);
        assertEquals(1, player.getHand().size());//vediamo se la mano diventa uguale a 1
        //piazza la carta alle cordinate
        player.placeCard(PC_051, new Coordinates(40, 40));
        //controlla la size della hand
        assertEquals(0, player.getHand().size());

    }

    @Test
    public void testPlaceCardUpdatesPlayArea() {
         PlayableCard playableCard2 = PC_051;
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
        PlayableCard playableCard3 = PC_002;
        PlayableCard playableCard4 = PC_007;

        //aggiungo starter card alla playarea del player
        player.initializePlayArea(SC_002);
        player.getPlayArea()[41][41] = SC_002;

        //gioco la carta PC_002 in una posizione già occupata e verifico che venga lanciata l'eccezione
        player.placeCard(playableCard3, new Coordinates(41, 41));
        player.getPlayArea()[41][41] = playableCard3;
        assertThrows(InvalidMoveException.class, () -> {
            player.isValidMove(new Coordinates(41, 41));
        });

        //gioco la carta PC_002 in una posizione adiacente, non corretta e verifico che venga lanciata l'eccezione
        player.placeCard(playableCard3, new Coordinates(41, 40));
        Coordinates adjacentCoordinates = new Coordinates(41, 40);
        assertThrows(InvalidMoveException.class, () -> {
               player.isValidMove(adjacentCoordinates);
            });

        //verifico che la carta invece possa essere giocata nella posizione corretta e che venga dunque aggiunta alla play area
        player.placeCard(playableCard4, new Coordinates(42, 40));
        Coordinates validCoordinates = new Coordinates(42, 40);
        assertTrue(player.isValidMove(validCoordinates));
        player.getPlayArea()[40][42] = playableCard4;
    }

    @Test
    void drawCard() {

        // Verifico che all'inizio il giocatore non abbia carte in mano
        assertEquals(0, player.getHand().size());

        PlayableCard playableCard2 = PC_051;

        // Aggiunge la carta alla mano del giocatore
        player.drawCard(playableCard2);

        // Verifica che la carta PC_051 sia stata aggiunta correttamente alla mano del giocatore
        assertEquals(1, player.getHand().size());
        assertTrue(player.getHand().contains(playableCard2));
    }
    @Test
    void objectiveScoreCalculator() {
        PlayableCard playableCard = PC_007;
        // Inizializza lo stock del giocatore
        Map<CornerContent, Integer> stock = new HashMap<>();
        stock.put(Resource.PLANT_KINGDOM, 0);
        stock.put(Resource.INSECT_KINGDOM, 0);
        stock.put(Resource.FUNGI_KINGDOM, 0);
        stock.put(Resource.ANIMAL_KINGDOM, 0);
        stock.put(SpecialObject.MANUSCRIPT, 0);
        stock.put(SpecialObject.INKWELL, 0);
        stock.put(SpecialObject.QUILL, 0);
        player.setStock(stock);

        player.initializePlayArea(SC_002);
        player.drawCard(playableCard);
        player.placeCard(playableCard, new Coordinates(42, 42));



        ;
    }
}