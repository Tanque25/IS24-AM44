package org.example.am24is44.model;
import static org.example.am24is44.model.CornerPosition.*;
import static org.example.am24is44.model.Resource.*;
import static org.example.am24is44.model.CardPoints.*;
import static org.example.am24is44.model.SpecialObject.*;
import static org.example.am24is44.model.Player.*;
import static org.example.am24is44.model.Board.*;

import java.util.*;

public class Game {
    private Board board;
    private List<Player> players;
    private Player startingPlayer;
    private Player currentPlayer;
    private GameStatus status;
    private Stack<StarterCard> starterDeck;
    private ObjectiveDeck objectiveDeck;
    private Stack<Card> resourcePile;
    private Stack<Card> goldPile;
    private List<ObjectiveCard> commonObjectives;
    private List<Card> visibleCards;

    /**
     * Game's constructor
     */
    public Game() {
        this.board = new Board(new HashMap<>());
        this.players = new ArrayList<>();
    }

    /**
     * Method to initialize the game
     * This method sets up the game environment by performing the following tasks:
     * - Sets the game status to initialization.
     * - Initializes the starter deck with starter cards.
     * - Initializes the objective deck and sets its cards.
     * - Initializes the resourcePile.
     * - Initializes the goldPile.
     * - Draws two common objectives from the objective deck.
     * - Draws four visible cards from the resource and gold piles.
     */
    public void initializeGame() {

        // Set game status
        status = GameStatus.INITIALIZATION;

        // StarterCard initialization
        initializeStarterDeck();

        // ObjectiveDeck initialization
        objectiveDeck = new ObjectiveDeck();
        objectiveDeck.setCards();

        // ResourcePile initialization
        initializeResourcePile();

        // GoldPile initialization
        initializeGoldPile();

        // Draw common objectives
        commonObjectives.add(objectiveDeck.drawCard());
        commonObjectives.add(objectiveDeck.drawCard());

        // Draw visible cards
        visibleCards.add(resourcePile.pop());
        visibleCards.add(resourcePile.pop());
        visibleCards.add(goldPile.pop());
        visibleCards.add(goldPile.pop());
    }

    /**
     * Method to initialize the ResourcePile
     */
    private void initializeStarterDeck() {
        this.starterDeck = new Stack<>();

        Resource[] resources;
        Map<CornerPosition, Corner> corners;
        Map<CornerPosition, Corner> backCorners;

        // Card #81
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,null));
        corners.put(UP_RIGHT,new Corner(true, PLANT_KINGDOM,null));
        corners.put(BOTTOM_LEFT,new Corner(true, INSECT_KINGDOM,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, null,null));
        backCorners = new HashMap<>();
        backCorners.put(UP_LEFT,new Corner(true, FUNGI_KINGDOM,null));
        backCorners.put(UP_RIGHT,new Corner(true, PLANT_KINGDOM,null));
        backCorners.put(BOTTOM_LEFT,new Corner(true, INSECT_KINGDOM,null));
        backCorners.put(BOTTOM_RIGHT,new Corner(true, ANIMAL_KINGDOM,null));
        resources = new Resource[]{INSECT_KINGDOM};
        starterDeck.push(new StarterCard(resources, ZERO, corners, backCorners));

        // Card #82
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, ANIMAL_KINGDOM,null));
        corners.put(UP_RIGHT,new Corner(true, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true, null,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, FUNGI_KINGDOM,null));
        backCorners = new HashMap<>();
        backCorners.put(UP_LEFT,new Corner(true, PLANT_KINGDOM,null));
        backCorners.put(UP_RIGHT,new Corner(true, ANIMAL_KINGDOM,null));
        backCorners.put(BOTTOM_LEFT,new Corner(true, FUNGI_KINGDOM,null));
        backCorners.put(BOTTOM_RIGHT,new Corner(true, INSECT_KINGDOM,null));
        resources = new Resource[]{FUNGI_KINGDOM};
        starterDeck.push(new StarterCard(resources, ZERO, corners, backCorners));

        // Card #83
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,null));
        corners.put(UP_RIGHT,new Corner(true, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true, null,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, null,null));
        backCorners = new HashMap<>();
        backCorners.put(UP_LEFT,new Corner(true, INSECT_KINGDOM,null));
        backCorners.put(UP_RIGHT,new Corner(true, ANIMAL_KINGDOM,null));
        backCorners.put(BOTTOM_LEFT,new Corner(true, FUNGI_KINGDOM,null));
        backCorners.put(BOTTOM_RIGHT,new Corner(true, PLANT_KINGDOM,null));
        resources = new Resource[]{PLANT_KINGDOM, FUNGI_KINGDOM};
        starterDeck.push(new StarterCard(resources, ZERO, corners, backCorners));

        //Card 84
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,null));
        corners.put(UP_RIGHT,new Corner(true, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true, null,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, null,null));
        backCorners = new HashMap<>();
        backCorners.put(UP_LEFT,new Corner(true, PLANT_KINGDOM,null));
        backCorners.put(UP_RIGHT,new Corner(true, INSECT_KINGDOM,null));
        backCorners.put(BOTTOM_LEFT,new Corner(true, ANIMAL_KINGDOM,null));
        backCorners.put(BOTTOM_RIGHT,new Corner(true, FUNGI_KINGDOM,null));
        resources = new Resource[]{ANIMAL_KINGDOM, INSECT_KINGDOM};
        starterDeck.push(new StarterCard(resources, ZERO, corners, backCorners));

        //Card 85
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,null));
        corners.put(UP_RIGHT,new Corner(true, null,null));
        corners.put(BOTTOM_LEFT,new Corner(false, null,null));
        corners.put(BOTTOM_RIGHT,new Corner(false, null,null));
        backCorners = new HashMap<>();
        backCorners.put(UP_LEFT,new Corner(true, INSECT_KINGDOM,null));
        backCorners.put(UP_RIGHT,new Corner(true, FUNGI_KINGDOM,null));
        backCorners.put(BOTTOM_LEFT,new Corner(true, PLANT_KINGDOM,null));
        backCorners.put(BOTTOM_RIGHT,new Corner(true, ANIMAL_KINGDOM,null));
        resources = new Resource[]{ANIMAL_KINGDOM, INSECT_KINGDOM,PLANT_KINGDOM};
        starterDeck.push(new StarterCard(resources, ZERO, corners, backCorners));

        //Card 86
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,null));
        corners.put(UP_RIGHT,new Corner(true, null,null));
        corners.put(BOTTOM_LEFT,new Corner(false, null,null));
        corners.put(BOTTOM_RIGHT,new Corner(false, null,null));
        backCorners = new HashMap<>();
        backCorners.put(UP_LEFT,new Corner(true, FUNGI_KINGDOM,null));
        backCorners.put(UP_RIGHT,new Corner(true, ANIMAL_KINGDOM,null));
        backCorners.put(BOTTOM_LEFT,new Corner(true, PLANT_KINGDOM,null));
        backCorners.put(BOTTOM_RIGHT,new Corner(true, INSECT_KINGDOM,null));
        resources = new Resource[]{PLANT_KINGDOM, ANIMAL_KINGDOM,FUNGI_KINGDOM,};
        starterDeck.push(new StarterCard(resources, ZERO, corners, backCorners));

        // Shuffle starterDeck
        Collections.shuffle(starterDeck);
    }

    /**
     * Method to initialize the ResourcePile
     */
    private void initializeResourcePile() {
        this.resourcePile = new Stack<>();

        Resource[] resources;
        Map<CornerPosition, Corner> corners;

        // Card #1
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, FUNGI_KINGDOM,null));
        corners.put(UP_RIGHT,new Corner(true, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true, FUNGI_KINGDOM,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, null,null));
        resources = new Resource[]{FUNGI_KINGDOM};
        resourcePile.push(new Card(resources, ZERO, corners));

        // Card 2
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, FUNGI_KINGDOM,null));
        corners.put(UP_RIGHT,new Corner(true, FUNGI_KINGDOM,null));
        corners.put(BOTTOM_LEFT,new Corner(true, null,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, null,null));
        resources = new Resource[]{FUNGI_KINGDOM};
        resourcePile.push(new Card(resources, ZERO, corners));

        // Card 3
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,null));
        corners.put(UP_RIGHT,new Corner(false, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true, FUNGI_KINGDOM,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, FUNGI_KINGDOM,null));
        resources = new Resource[]{FUNGI_KINGDOM};
        resourcePile.push(new Card(resources, ZERO, corners));

        //card 4
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(false, null,null));
        corners.put(UP_RIGHT,new Corner(true, FUNGI_KINGDOM,null));
        corners.put(BOTTOM_LEFT,new Corner(true, null,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, FUNGI_KINGDOM,null));
        resources = new Resource[]{FUNGI_KINGDOM};
        resourcePile.push(new Card(resources, ZERO, corners));

        //card 5
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(false, null,null));
        corners.put(UP_RIGHT,new Corner(true, null,QUILL));
        corners.put(BOTTOM_LEFT,new Corner(true, PLANT_KINGDOM,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, FUNGI_KINGDOM,null));
        resources = new Resource[]{FUNGI_KINGDOM};
        resourcePile.push(new Card(resources, ZERO, corners));

        //card 6
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,INKWELL));
        corners.put(UP_RIGHT,new Corner(true, FUNGI_KINGDOM,null));
        corners.put(BOTTOM_LEFT,new Corner(false, null,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, ANIMAL_KINGDOM,null));
        resources = new Resource[]{FUNGI_KINGDOM};
        resourcePile.push(new Card(resources, ZERO, corners));

        //card 7
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, FUNGI_KINGDOM,null));
        corners.put(UP_RIGHT,new Corner(true, INSECT_KINGDOM,null));
        corners.put(BOTTOM_LEFT,new Corner(true, null,MANUSCRIPT));
        corners.put(BOTTOM_RIGHT,new Corner(true, null,null));
        resources = new Resource[]{FUNGI_KINGDOM};
        resourcePile.push(new Card(resources, ZERO, corners));

        //card 8
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,null));
        corners.put(UP_RIGHT,new Corner(true, FUNGI_KINGDOM,null));
        corners.put(BOTTOM_LEFT,new Corner(true, null,null));
        corners.put(BOTTOM_RIGHT,new Corner(false, null,null));
        resources = new Resource[]{FUNGI_KINGDOM};
        resourcePile.push(new Card(resources, ONE, corners));

        //card 9
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, FUNGI_KINGDOM,null));
        corners.put(UP_RIGHT,new Corner(false,null ,null));
        corners.put(BOTTOM_LEFT,new Corner(true, null,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, null,null));
        resources = new Resource[]{FUNGI_KINGDOM};
        resourcePile.push(new Card(resources, ONE, corners));

        //card 10
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(false, null,null));
        corners.put(UP_RIGHT,new Corner(true,null ,null));
        corners.put(BOTTOM_LEFT,new Corner(true, FUNGI_KINGDOM,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, null,null));
        resources = new Resource[]{FUNGI_KINGDOM};
        resourcePile.push(new Card(resources, ONE, corners));

        //card 11
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,null));
        corners.put(UP_RIGHT,new Corner(true,PLANT_KINGDOM ,null));
        corners.put(BOTTOM_LEFT,new Corner(true, PLANT_KINGDOM,null));
        corners.put(BOTTOM_RIGHT,new Corner(false, null,null));
        resources = new Resource[]{PLANT_KINGDOM};
        resourcePile.push(new Card(resources, ZERO, corners));

        //card 12
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, PLANT_KINGDOM,null));
        corners.put(UP_RIGHT,new Corner(true,PLANT_KINGDOM ,null));
        corners.put(BOTTOM_LEFT,new Corner(false, null,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, null,null));
        resources = new Resource[]{PLANT_KINGDOM};
        resourcePile.push(new Card(resources, ZERO, corners));

        //card 13
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,null));
        corners.put(UP_RIGHT,new Corner(false, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true, PLANT_KINGDOM,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, PLANT_KINGDOM,null));
        resources = new Resource[]{PLANT_KINGDOM};
        resourcePile.push(new Card(resources, ZERO, corners));

        //card 14
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(false, null,null));
        corners.put(UP_RIGHT,new Corner(true, PLANT_KINGDOM,null));
        corners.put(BOTTOM_LEFT,new Corner(true, null,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, PLANT_KINGDOM,null));
        resources = new Resource[]{PLANT_KINGDOM};
        resourcePile.push(new Card(resources, ZERO, corners));

        //card 15
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(false, null,null));
        corners.put(UP_RIGHT,new Corner(true, INSECT_KINGDOM,null));
        corners.put(BOTTOM_LEFT,new Corner(true, null,QUILL));
        corners.put(BOTTOM_RIGHT,new Corner(true, PLANT_KINGDOM,null));
        resources = new Resource[]{PLANT_KINGDOM};
        resourcePile.push(new Card(resources, ZERO, corners));

        //card 16
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, FUNGI_KINGDOM,null));
        corners.put(UP_RIGHT,new Corner(true, PLANT_KINGDOM,null));
        corners.put(BOTTOM_LEFT,new Corner(false, null,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, null,INKWELL));
        resources = new Resource[]{PLANT_KINGDOM};
        resourcePile.push(new Card(resources, ZERO, corners));

        //card 17
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,MANUSCRIPT));
        corners.put(UP_RIGHT,new Corner(false, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true, PLANT_KINGDOM,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, ANIMAL_KINGDOM,null));
        resources = new Resource[]{PLANT_KINGDOM};
        resourcePile.push(new Card(resources, ZERO, corners));

        //card 18
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,null));
        corners.put(UP_RIGHT,new Corner(true, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true, PLANT_KINGDOM,null));
        corners.put(BOTTOM_RIGHT,new Corner(false, null,null));
        resources = new Resource[]{PLANT_KINGDOM};
        resourcePile.push(new Card(resources, ONE, corners));

        //card 19
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,null));
        corners.put(UP_RIGHT,new Corner(true, null,null));
        corners.put(BOTTOM_LEFT,new Corner(false,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, PLANT_KINGDOM,null));
        resources = new Resource[]{PLANT_KINGDOM};
        resourcePile.push(new Card(resources, ONE, corners));

        //card 20
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(false, null,null));
        corners.put(UP_RIGHT,new Corner(true, PLANT_KINGDOM,null));
        corners.put(BOTTOM_LEFT,new Corner(true,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(true,null ,null));
        resources = new Resource[]{PLANT_KINGDOM};
        resourcePile.push(new Card(resources, ONE, corners));

        //card 21
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, ANIMAL_KINGDOM,null));
        corners.put(UP_RIGHT,new Corner(true, ANIMAL_KINGDOM,null));
        corners.put(BOTTOM_LEFT,new Corner(true,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(false,null ,null));
        resources = new Resource[]{ANIMAL_KINGDOM};
        resourcePile.push(new Card(resources, ZERO, corners));

        //card 22
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(false, null,null));
        corners.put(UP_RIGHT,new Corner(true, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true,ANIMAL_KINGDOM ,null));
        corners.put(BOTTOM_RIGHT,new Corner(false,ANIMAL_KINGDOM ,null));
        resources = new Resource[]{ANIMAL_KINGDOM};
        resourcePile.push(new Card(resources, ZERO, corners));

        //card 23
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, ANIMAL_KINGDOM,null));
        corners.put(UP_RIGHT,new Corner(false, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true,ANIMAL_KINGDOM ,null));
        corners.put(BOTTOM_RIGHT,new Corner(false,null ,null));
        resources = new Resource[]{ANIMAL_KINGDOM};
        resourcePile.push(new Card(resources, ZERO, corners));

        //card 24
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,null));
        corners.put(UP_RIGHT,new Corner(true, ANIMAL_KINGDOM,null));
        corners.put(BOTTOM_LEFT,new Corner(false,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(true,ANIMAL_KINGDOM ,null));
        resources = new Resource[]{ANIMAL_KINGDOM};
        resourcePile.push(new Card(resources, ZERO, corners));

        //card 25
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(false, null,null));
        corners.put(UP_RIGHT,new Corner(true, INSECT_KINGDOM,null));
        corners.put(BOTTOM_LEFT,new Corner(true,null ,INKWELL));
        corners.put(BOTTOM_RIGHT,new Corner(true,ANIMAL_KINGDOM ,null));
        resources = new Resource[]{ANIMAL_KINGDOM};
        resourcePile.push(new Card(resources, ZERO, corners));

        //card 26
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, PLANT_KINGDOM,null));
        corners.put(UP_RIGHT,new Corner(true, ANIMAL_KINGDOM,null));
        corners.put(BOTTOM_LEFT,new Corner(false,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(true,null ,MANUSCRIPT));
        resources = new Resource[]{ANIMAL_KINGDOM};
        resourcePile.push(new Card(resources, ZERO, corners));

        //card 27
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,QUILL));
        corners.put(UP_RIGHT,new Corner(false, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true,ANIMAL_KINGDOM ,null));
        corners.put(BOTTOM_RIGHT,new Corner(true,FUNGI_KINGDOM ,null));
        resources = new Resource[]{ANIMAL_KINGDOM};
        resourcePile.push(new Card(resources, ZERO, corners));

        //card 28
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(false, null,null));
        corners.put(UP_RIGHT,new Corner(true, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true,ANIMAL_KINGDOM ,null));
        corners.put(BOTTOM_RIGHT,new Corner(true,null ,null));
        resources = new Resource[]{ANIMAL_KINGDOM};
        resourcePile.push(new Card(resources, ONE, corners));

        //card 29
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,null));
        corners.put(UP_RIGHT,new Corner(false, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true, null,null));
        corners.put(BOTTOM_RIGHT,new Corner(true,ANIMAL_KINGDOM ,null));
        resources = new Resource[]{ANIMAL_KINGDOM};
        resourcePile.push(new Card(resources, ONE, corners));

        //card 30
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,null));
        corners.put(UP_RIGHT,new Corner(true, ANIMAL_KINGDOM,null));
        corners.put(BOTTOM_LEFT,new Corner(true, null,null));
        corners.put(BOTTOM_RIGHT,new Corner(false, null,null));
        resources = new Resource[]{ANIMAL_KINGDOM};
        resourcePile.push(new Card(resources, ONE, corners));

        //card 31
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, INSECT_KINGDOM,null));
        corners.put(UP_RIGHT,new Corner(true, INSECT_KINGDOM,null));
        corners.put(BOTTOM_LEFT,new Corner(true,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(false,null ,null));
        resources = new Resource[]{INSECT_KINGDOM};
        resourcePile.push(new Card(resources, ZERO, corners));

        //card 32
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(false, null,null));
        corners.put(UP_RIGHT,new Corner(true, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true,INSECT_KINGDOM ,null));
        corners.put(BOTTOM_RIGHT,new Corner(false,INSECT_KINGDOM ,null));
        resources = new Resource[]{INSECT_KINGDOM};
        resourcePile.push(new Card(resources, ZERO, corners));

        //card 33
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, INSECT_KINGDOM,null));
        corners.put(UP_RIGHT,new Corner(false, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true,INSECT_KINGDOM ,null));
        corners.put(BOTTOM_RIGHT,new Corner(false,null ,null));
        resources = new Resource[]{INSECT_KINGDOM};
        resourcePile.push(new Card(resources, ZERO, corners));

        //card 34
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,null));
        corners.put(UP_RIGHT,new Corner(true, INSECT_KINGDOM,null));
        corners.put(BOTTOM_LEFT,new Corner(false,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(true,INSECT_KINGDOM ,null));
        resources = new Resource[]{INSECT_KINGDOM};
        resourcePile.push(new Card(resources, ZERO, corners));

        //card 35
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(false, null,null));
        corners.put(UP_RIGHT,new Corner(true, null,QUILL));
        corners.put(BOTTOM_LEFT,new Corner(true,ANIMAL_KINGDOM ,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, INSECT_KINGDOM,null));
        resources = new Resource[]{INSECT_KINGDOM};
        resourcePile.push(new Card(resources, ZERO, corners));

        //card 36
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,MANUSCRIPT));
        corners.put(UP_RIGHT,new Corner(true, INSECT_KINGDOM,null));
        corners.put(BOTTOM_LEFT,new Corner(false,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, FUNGI_KINGDOM,null));
        resources = new Resource[]{INSECT_KINGDOM};
        resourcePile.push(new Card(resources, ZERO, corners));

        //card 37
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, INSECT_KINGDOM,null));
        corners.put(UP_RIGHT,new Corner(true, PLANT_KINGDOM,null));
        corners.put(BOTTOM_LEFT,new Corner(true,null ,INKWELL));
        corners.put(BOTTOM_RIGHT,new Corner(false, null,null));
        resources = new Resource[]{INSECT_KINGDOM};
        resourcePile.push(new Card(resources, ZERO, corners));

        //card 38
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, INSECT_KINGDOM,null));
        corners.put(UP_RIGHT,new Corner(false, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true, null,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, null,null));
        resources = new Resource[]{INSECT_KINGDOM};
        resourcePile.push(new Card(resources, ONE, corners));

        //card 39
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,null));
        corners.put(UP_RIGHT,new Corner(true, null,null));
        corners.put(BOTTOM_LEFT,new Corner(false,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, INSECT_KINGDOM,null));
        resources = new Resource[]{INSECT_KINGDOM};
        resourcePile.push(new Card(resources, ONE, corners));

        //card 40
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(false, null,null));
        corners.put(UP_RIGHT,new Corner(true, INSECT_KINGDOM,null));
        corners.put(BOTTOM_LEFT,new Corner(true,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(true,null ,null));
        resources = new Resource[]{INSECT_KINGDOM};
        resourcePile.push(new Card(resources, ONE, corners));

        // Shuffle resourcePile
        Collections.shuffle(resourcePile);
    }

    /**
     * Method to initialize the GoldPile
     */
    private void initializeGoldPile() {
        this.goldPile = new Stack<>();

        Resource[] resources;
        Map<CornerPosition, Corner> corners;
        Resource[] cost;
        SpecialObject specialObject;

        //card 41
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(false, null,null));
        corners.put(UP_RIGHT,new Corner(true, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, null, QUILL));
        resources = new Resource[]{FUNGI_KINGDOM};
        cost = new Resource[]{FUNGI_KINGDOM, FUNGI_KINGDOM, ANIMAL_KINGDOM};
        goldPile.push(new GoldCard(resources, ONE, corners, cost, QUILL));

        //card 42
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,INKWELL));
        corners.put(UP_RIGHT,new Corner(true, null,null));
        corners.put(BOTTOM_LEFT,new Corner(false,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, null, null));
        resources = new Resource[]{FUNGI_KINGDOM};
        cost = new Resource[]{FUNGI_KINGDOM, FUNGI_KINGDOM, PLANT_KINGDOM};
        goldPile.push(new GoldCard(resources, ONE, corners, cost, INKWELL));

        //card 43
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,MANUSCRIPT));
        corners.put(UP_RIGHT,new Corner(true, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(false, null, null));
        resources = new Resource[]{FUNGI_KINGDOM};
        cost = new Resource[]{FUNGI_KINGDOM, FUNGI_KINGDOM, INSECT_KINGDOM};
        goldPile.push(new GoldCard(resources, ONE, corners, cost, MANUSCRIPT));

        //card 44
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,null));
        corners.put(UP_RIGHT,new Corner(true, null,null));
        corners.put(BOTTOM_LEFT,new Corner(false,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, null, null));
        resources = new Resource[]{FUNGI_KINGDOM};
        cost = new Resource[]{FUNGI_KINGDOM, FUNGI_KINGDOM, FUNGI_KINGDOM, ANIMAL_KINGDOM};
        goldPile.push(new GoldCard(resources, TWO, corners, cost, CORNER));

        //card 45
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,null));
        corners.put(UP_RIGHT,new Corner(true, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(false, null, null));
        resources = new Resource[]{FUNGI_KINGDOM};
        cost = new Resource[]{FUNGI_KINGDOM, FUNGI_KINGDOM, FUNGI_KINGDOM, PLANT_KINGDOM};
        goldPile.push(new GoldCard(resources, TWO, corners, cost, CORNER));

        //card 46
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,null));
        corners.put(UP_RIGHT,new Corner(false, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, null, null));
        resources = new Resource[]{FUNGI_KINGDOM};
        cost = new Resource[]{FUNGI_KINGDOM, FUNGI_KINGDOM, FUNGI_KINGDOM, INSECT_KINGDOM};
        goldPile.push(new GoldCard(resources, TWO, corners, cost, CORNER));

        //card 47
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,null));
        corners.put(UP_RIGHT,new Corner(false, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true,null ,INKWELL));
        corners.put(BOTTOM_RIGHT,new Corner(false, null, null));
        resources = new Resource[]{FUNGI_KINGDOM};
        cost = new Resource[]{FUNGI_KINGDOM, FUNGI_KINGDOM, FUNGI_KINGDOM};
        goldPile.push(new GoldCard(resources, THREE, corners, cost, null));

        //card 48
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,QUILL));
        corners.put(UP_RIGHT,new Corner(true, null,null));
        corners.put(BOTTOM_LEFT,new Corner(false,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(false, null, null));
        resources = new Resource[]{FUNGI_KINGDOM};
        cost = new Resource[]{FUNGI_KINGDOM, FUNGI_KINGDOM, FUNGI_KINGDOM};
        goldPile.push(new GoldCard(resources, THREE, corners, cost, null));

        //card 49
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(false, null,null));
        corners.put(UP_RIGHT,new Corner(true, null, MANUSCRIPT));
        corners.put(BOTTOM_LEFT,new Corner(false,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, null, null));
        resources = new Resource[]{FUNGI_KINGDOM};
        cost = new Resource[]{FUNGI_KINGDOM, FUNGI_KINGDOM, FUNGI_KINGDOM};
        goldPile.push(new GoldCard(resources, THREE, corners, cost, null));

        //card 50
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,null));
        corners.put(UP_RIGHT,new Corner(false, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true,null , null));
        corners.put(BOTTOM_RIGHT,new Corner(false, null, null));
        resources = new Resource[]{FUNGI_KINGDOM};
        cost = new Resource[]{FUNGI_KINGDOM, FUNGI_KINGDOM, FUNGI_KINGDOM, FUNGI_KINGDOM, FUNGI_KINGDOM};
        goldPile.push(new GoldCard(resources, FIVE, corners, cost, null));

        //card 51
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null, QUILL));
        corners.put(UP_RIGHT,new Corner(true, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(false, null, null));
        resources = new Resource[]{PLANT_KINGDOM};
        cost = new Resource[]{PLANT_KINGDOM, PLANT_KINGDOM, INSECT_KINGDOM};
        goldPile.push(new GoldCard(resources, ONE, corners, cost, QUILL));

        //card 52
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null, null));
        corners.put(UP_RIGHT,new Corner(true, null,MANUSCRIPT));
        corners.put(BOTTOM_LEFT,new Corner(false,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, null, null));
        resources = new Resource[]{PLANT_KINGDOM};
        cost = new Resource[]{PLANT_KINGDOM, PLANT_KINGDOM, FUNGI_KINGDOM};
        goldPile.push(new GoldCard(resources, ONE, corners, cost, MANUSCRIPT));

        //card 53
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null, null));
        corners.put(UP_RIGHT,new Corner(false, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true,null ,INKWELL));
        corners.put(BOTTOM_RIGHT,new Corner(true, null, null));
        resources = new Resource[]{PLANT_KINGDOM};
        cost = new Resource[]{PLANT_KINGDOM, PLANT_KINGDOM, ANIMAL_KINGDOM};
        goldPile.push(new GoldCard(resources, ONE, corners, cost, INKWELL));

        //card 54
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(false, null, null));
        corners.put(UP_RIGHT,new Corner(true, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, null, null));
        resources = new Resource[]{PLANT_KINGDOM};
        cost = new Resource[]{PLANT_KINGDOM, PLANT_KINGDOM, PLANT_KINGDOM, INSECT_KINGDOM};
        goldPile.push(new GoldCard(resources, TWO, corners, cost, CORNER));

        //card 55
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null, null));
        corners.put(UP_RIGHT,new Corner(true, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(false, null, null));
        resources = new Resource[]{PLANT_KINGDOM};
        cost = new Resource[]{PLANT_KINGDOM, PLANT_KINGDOM, PLANT_KINGDOM, ANIMAL_KINGDOM};
        goldPile.push(new GoldCard(resources, TWO, corners, cost, CORNER));

        //card 56
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null, null));
        corners.put(UP_RIGHT,new Corner(false, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, null, null));
        resources = new Resource[]{PLANT_KINGDOM};
        cost = new Resource[]{PLANT_KINGDOM, PLANT_KINGDOM, PLANT_KINGDOM, FUNGI_KINGDOM};
        goldPile.push(new GoldCard(resources, TWO, corners, cost, CORNER));

        //card 57
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null, null));
        corners.put(UP_RIGHT,new Corner(false, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true,null ,QUILL));
        corners.put(BOTTOM_RIGHT,new Corner(false, null, null));
        resources = new Resource[]{PLANT_KINGDOM};
        cost = new Resource[]{PLANT_KINGDOM, PLANT_KINGDOM, PLANT_KINGDOM};
        goldPile.push(new GoldCard(resources, THREE, corners, cost, null));

        //card 58
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null, MANUSCRIPT));
        corners.put(UP_RIGHT,new Corner(true, null,null));
        corners.put(BOTTOM_LEFT,new Corner(false,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(false, null, null));
        resources = new Resource[]{PLANT_KINGDOM};
        cost = new Resource[]{PLANT_KINGDOM, PLANT_KINGDOM, PLANT_KINGDOM};
        goldPile.push(new GoldCard(resources, THREE, corners, cost, null));

        //card 59
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(false, null, null));
        corners.put(UP_RIGHT,new Corner(true, null,INKWELL));
        corners.put(BOTTOM_LEFT,new Corner(false,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, null, null));
        resources = new Resource[]{PLANT_KINGDOM};
        cost = new Resource[]{PLANT_KINGDOM, PLANT_KINGDOM, PLANT_KINGDOM};
        goldPile.push(new GoldCard(resources, THREE, corners, cost, null));

        //card 60
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null, null));
        corners.put(UP_RIGHT,new Corner(true, null,null));
        corners.put(BOTTOM_LEFT,new Corner(false,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(false, null, null));
        resources = new Resource[]{PLANT_KINGDOM};
        cost = new Resource[]{PLANT_KINGDOM, PLANT_KINGDOM, PLANT_KINGDOM, PLANT_KINGDOM, PLANT_KINGDOM};
        goldPile.push(new GoldCard(resources, FIVE, corners, cost, null));

        //card 61
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,INKWELL));
        corners.put(UP_RIGHT,new Corner(true, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(false, null, null));
        resources = new Resource[]{ANIMAL_KINGDOM};
        cost = new Resource[]{ANIMAL_KINGDOM, ANIMAL_KINGDOM, INSECT_KINGDOM};
        goldPile.push(new GoldCard(resources, ONE, corners, cost, INKWELL));

        //card 62
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(false, null,null));
        corners.put(UP_RIGHT,new Corner(true, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, null, MANUSCRIPT));
        resources = new Resource[]{ANIMAL_KINGDOM};
        cost = new Resource[]{ANIMAL_KINGDOM, ANIMAL_KINGDOM, PLANT_KINGDOM};
        goldPile.push(new GoldCard(resources, ONE, corners, cost, MANUSCRIPT));

        //card 63
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,null));
        corners.put(UP_RIGHT,new Corner(false, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true,null ,QUILL));
        corners.put(BOTTOM_RIGHT,new Corner(true, null, null));
        resources = new Resource[]{ANIMAL_KINGDOM};
        cost = new Resource[]{ANIMAL_KINGDOM, ANIMAL_KINGDOM, FUNGI_KINGDOM};
        goldPile.push(new GoldCard(resources, ONE, corners, cost, QUILL));

        //card 64
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,null));
        corners.put(UP_RIGHT,new Corner(true, null,null));
        corners.put(BOTTOM_LEFT,new Corner(false,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, null, null));
        resources = new Resource[]{ANIMAL_KINGDOM};
        cost = new Resource[]{ANIMAL_KINGDOM, ANIMAL_KINGDOM, ANIMAL_KINGDOM, INSECT_KINGDOM};
        goldPile.push(new GoldCard(resources, TWO, corners, cost, CORNER));

        //card 65
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,null));
        corners.put(UP_RIGHT,new Corner(false, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, null, null));
        resources = new Resource[]{ANIMAL_KINGDOM};
        cost = new Resource[]{ANIMAL_KINGDOM, ANIMAL_KINGDOM, ANIMAL_KINGDOM, FUNGI_KINGDOM};
        goldPile.push(new GoldCard(resources, TWO, corners, cost, CORNER));

        //card 66
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(false, null,null));
        corners.put(UP_RIGHT,new Corner(true, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, null, null));
        resources = new Resource[]{ANIMAL_KINGDOM};
        cost = new Resource[]{ANIMAL_KINGDOM, ANIMAL_KINGDOM, ANIMAL_KINGDOM, PLANT_KINGDOM};
        goldPile.push(new GoldCard(resources, TWO, corners, cost, CORNER));

        //card 67
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,null));
        corners.put(UP_RIGHT,new Corner(false, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true,null ,MANUSCRIPT));
        corners.put(BOTTOM_RIGHT,new Corner(false, null, null));
        resources = new Resource[]{ANIMAL_KINGDOM};
        cost = new Resource[]{ANIMAL_KINGDOM, ANIMAL_KINGDOM, ANIMAL_KINGDOM};
        goldPile.push(new GoldCard(resources, THREE, corners, cost, null));

        //card 68
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,null));
        corners.put(UP_RIGHT,new Corner(true, null,INKWELL));
        corners.put(BOTTOM_LEFT,new Corner(false,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(false, null, null));
        resources = new Resource[]{ANIMAL_KINGDOM};
        cost = new Resource[]{ANIMAL_KINGDOM, ANIMAL_KINGDOM, ANIMAL_KINGDOM};
        goldPile.push(new GoldCard(resources, THREE, corners, cost, null));

        //card 69
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(false, null,null));
        corners.put(UP_RIGHT,new Corner(true, null,null));
        corners.put(BOTTOM_LEFT,new Corner(false,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, null, QUILL));
        resources = new Resource[]{ANIMAL_KINGDOM};
        cost = new Resource[]{ANIMAL_KINGDOM, ANIMAL_KINGDOM, ANIMAL_KINGDOM};
        goldPile.push(new GoldCard(resources, THREE, corners, cost, null));

        //card 70
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(false, null,null));
        corners.put(UP_RIGHT,new Corner(true, null,null));
        corners.put(BOTTOM_LEFT,new Corner(false,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, null, null));
        resources = new Resource[]{ANIMAL_KINGDOM};
        cost = new Resource[]{ANIMAL_KINGDOM, ANIMAL_KINGDOM, ANIMAL_KINGDOM,ANIMAL_KINGDOM, ANIMAL_KINGDOM};
        goldPile.push(new GoldCard(resources, FIVE, corners, cost, null));

        //card 71
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,null));
        corners.put(UP_RIGHT,new Corner(true, null,INKWELL));
        corners.put(BOTTOM_LEFT,new Corner(false,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, null, null));
        resources = new Resource[]{INSECT_KINGDOM};
        cost = new Resource[]{INSECT_KINGDOM, INSECT_KINGDOM, PLANT_KINGDOM};
        goldPile.push(new GoldCard(resources, ONE, corners, cost, INKWELL));

        //card 72
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,null));
        corners.put(UP_RIGHT,new Corner(false, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true,null ,MANUSCRIPT));
        corners.put(BOTTOM_RIGHT,new Corner(true, null, null));
        resources = new Resource[]{INSECT_KINGDOM};
        cost = new Resource[]{INSECT_KINGDOM, INSECT_KINGDOM, ANIMAL_KINGDOM};
        goldPile.push(new GoldCard(resources, ONE, corners, cost, MANUSCRIPT));

        //card 73
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(false, null,null));
        corners.put(UP_RIGHT,new Corner(true, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, null, INKWELL));
        resources = new Resource[]{INSECT_KINGDOM};
        cost = new Resource[]{INSECT_KINGDOM, INSECT_KINGDOM, FUNGI_KINGDOM};
        goldPile.push(new GoldCard(resources, ONE, corners, cost, INKWELL));

        //card 74
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,null));
        corners.put(UP_RIGHT,new Corner(true, null,null));
        corners.put(BOTTOM_LEFT,new Corner(false,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, null, null));
        resources = new Resource[]{INSECT_KINGDOM};
        cost = new Resource[]{INSECT_KINGDOM, INSECT_KINGDOM, INSECT_KINGDOM, ANIMAL_KINGDOM};
        goldPile.push(new GoldCard(resources, TWO, corners, cost, CORNER));

        //card 75
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,null));
        corners.put(UP_RIGHT,new Corner(true, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(false, null, null));
        resources = new Resource[]{INSECT_KINGDOM};
        cost = new Resource[]{INSECT_KINGDOM, INSECT_KINGDOM, INSECT_KINGDOM, PLANT_KINGDOM};
        goldPile.push(new GoldCard(resources, TWO, corners, cost, CORNER));

        //card 76
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,null));
        corners.put(UP_RIGHT,new Corner(false, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(true, null, null));
        resources = new Resource[]{INSECT_KINGDOM};
        cost = new Resource[]{INSECT_KINGDOM, INSECT_KINGDOM, INSECT_KINGDOM, FUNGI_KINGDOM};
        goldPile.push(new GoldCard(resources, TWO, corners, cost, CORNER));

        //card 77
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,INKWELL));
        corners.put(UP_RIGHT,new Corner(false, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(false, null, null));
        resources = new Resource[]{INSECT_KINGDOM};
        cost = new Resource[]{INSECT_KINGDOM, INSECT_KINGDOM, INSECT_KINGDOM};
        goldPile.push(new GoldCard(resources, THREE, corners, cost, null));

        //card 78
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,null));
        corners.put(UP_RIGHT,new Corner(true, null,MANUSCRIPT));
        corners.put(BOTTOM_LEFT,new Corner(false,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(false, null, null));
        resources = new Resource[]{INSECT_KINGDOM};
        cost = new Resource[]{INSECT_KINGDOM, INSECT_KINGDOM, INSECT_KINGDOM};
        goldPile.push(new GoldCard(resources, THREE, corners, cost, null));

        //card 79
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(false, null,null));
        corners.put(UP_RIGHT,new Corner(false, null,null));
        corners.put(BOTTOM_LEFT,new Corner(true,null ,QUILL));
        corners.put(BOTTOM_RIGHT,new Corner(true, null, null));
        resources = new Resource[]{INSECT_KINGDOM};
        cost = new Resource[]{INSECT_KINGDOM, INSECT_KINGDOM, INSECT_KINGDOM};
        goldPile.push(new GoldCard(resources, THREE, corners, cost, null));

        //card 80
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null,null));
        corners.put(UP_RIGHT,new Corner(true, null,null));
        corners.put(BOTTOM_LEFT,new Corner(false,null ,null));
        corners.put(BOTTOM_RIGHT,new Corner(false, null, null));
        resources = new Resource[]{INSECT_KINGDOM};
        cost = new Resource[]{INSECT_KINGDOM, INSECT_KINGDOM, INSECT_KINGDOM, INSECT_KINGDOM, INSECT_KINGDOM};
        goldPile.push(new GoldCard(resources, FIVE, corners, cost, null));

    }

    /**
     * Method to add a player to the game with the specified nickname
     * @param nickname returns the nickname of the player to be added
     */
    public void addPlayer(String nickname) {

        // Istantiate a new player object
        Player player = new Player(nickname);

        // Create the player hand
        List<Card> hand = null;
        Card card;

        //per ogni player creo una chiave nella board
            board.createKey(player);


        for (int i = 1; i <= 2; i++){
            card = resourcePile.pop();
            hand.add(card);
        }

        for (int i = 1; i <= 2; i++){
            card = goldPile.pop();
            hand.add(card);
        }

        player.initializeHand(hand);

        // I add the player to the player list
        this.players.add(player);
    }

    /**
     * Method to get the current player
     * @return currentPlayer
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Method to draw and choose one of the two secret objectives
     * @param player current Player
     * @param objChoice returns the player's choice that represents one of the two objective cards
     *
     * @throws IllegalArgumentException for invalid choice of the player
     */
    public void chooseSecretObjective(Player player, int objChoice) throws IllegalArgumentException {
        // Prepare two objective options
        ObjectiveCard objectiveOption1 = objectiveDeck.drawCard();
        ObjectiveCard objectiveOption2 = objectiveDeck.drawCard();

        if(objChoice == 0){ //player choose objCard 1
            player.setSecretObjective(objectiveOption1);

        } else if (objChoice == 1) { // player choose objCard 2
            player.setSecretObjective(objectiveOption2);

        } else
            throw new IllegalArgumentException("Invalid choice" + objChoice);

    }

    /**
     * Method for choosing the side of the started card and playing it in the empty play area
     * @param player current player
     * @param x returns the coordinate x in the play area where the player wants to play his card
     * @param y returns the coordinate y in the play area where the player wants to play his card
     */
    public void chooseStartingSide(Player player, int x, int y) {
        // Prepare the starter card

        StarterCard starterCard = starterDeck.pop();

        player.initializePlayArea(starterCard, x,y);
        //la sto finendo
        // Update player's score
    }

    /**
     * Getter for common objectives
     * @return commonObjectives of the players
     */
    public List<ObjectiveCard> getCommonObjectives() {
        return commonObjectives;

    }

    /**
     * Method to play a card
     * @param player current player
     * @param cardChoice returns the position of the card in the player's hand (player's choice)
     * @param x returns the possible x coordinate of the play area of the card (the player wants to play)
     * @param y returns the possible y coordinate of the play area of the card (the player wants to play)
     * @param frontSide returns true if the player chooses the front side
     *
     * @throws IllegalArgumentException for invalid position or invalid choice of the card to play
     */
    public void playCard(Player player, int cardChoice, int x, int y, boolean frontSide) throws IllegalArgumentException {
        // the 4 int came from controller
        // frontSide returns true if the player chooses the front side (controller will call the method getPlayback)
        if(player.checkXY(x,y) && player.checkCard(cardChoice, frontSide)){
                player.playCard(cardChoice, x, y);
                //update the Player's score

        } else if (!player.checkXY(x,y)) {
            throw new IllegalArgumentException("Invalid position" + x + y);

        } else {
            throw new IllegalArgumentException("Invalid Card" + cardChoice);
        }
    }

    /**
     * Method to draw a card
     * @param player current player
     * @param choice returns the choice of deck from which the player wants to draw
     *
     * @throws IllegalArgumentException for invalid choice or empty Pile
     */

    public void drawCard(Player player, int choice) throws IllegalArgumentException {

        // The controller manages the gameflow, so it will check that the player first plays the card
        // Then he draws another one

        if(!resourcePile.empty() && !goldPile.empty() && !visibleCards.isEmpty() ) {

            // int choice is a variable which cames from controller
            // it will return 0-> resourcePile, 1-> goldPile, 2,3,4,5-> visible
            if(choice >= 0 && choice <= 5){
                switch (choice) {
                    case 0:
                        player.drawCard(resourcePile.getFirst());
                        resourcePile.removeFirst();
                        break;

                    case 1:
                        player.drawCard(goldPile.getFirst());
                        goldPile.removeFirst();
                        break;

                    case 2: // the resource card at the top of the list
                        player.drawCard(visibleCards.getFirst());
                        visibleCards.removeFirst();
                        visibleCards.addFirst(resourcePile.pop());
                        break;

                    case 3: // the second resource card
                        player.drawCard(visibleCards.get(1));
                        visibleCards.remove(1);
                        visibleCards.add(1, resourcePile.pop());
                        break;

                    case 4: // the gold card in the third position of the list
                        player.drawCard(visibleCards.get(2));
                        visibleCards.remove(2);
                        visibleCards.add(2, goldPile.pop());
                        break;

                    case 5: // the gold card at the end of the list
                        player.drawCard(visibleCards.getLast());
                        visibleCards.removeLast();
                        visibleCards.addLast(goldPile.pop());
                        break;
                }
            }
            else
                throw new IllegalArgumentException("Invalid choice" + choice);

            }
        else
            throw new IllegalStateException("One of the piles is empty. Impossible to draw");
    }

    /**
     * Getter for visible cards
     * @return visibleCards
     */
    public List<Card> getVisibleCards() {
        return visibleCards;
    }

    /**
     * Method to determine the winner
     * @return null
     */
    public Player Winner() {
        int ObjectiveScore=0, finalScore=0, score=0;

        //biusogna gestire parità??????-->

        //per ogni player calcolo il punteggio finale
        for (Player elemento : players) {
            //passo al finalScoreCalculator gli obbiettivi comuni cosi che possa controllare quello
            ObjectiveScore=elemento.finalScoreCalculator(commonObjectives);
            score=board.getScore(elemento);
            elemento.setScore(ObjectiveScore+score);
        }

        //stabilire il vincitore....

        return null; // Placeholder, replace with actual winner
    }

}
