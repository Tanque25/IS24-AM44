package org.example.am24is44.model;
import static org.example.am24is44.model.CornerPosition.*;
import static org.example.am24is44.model.Resource.*;
import static org.example.am24is44.model.CardPoints.*;

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

    // Constructor
    public Game() {
        this.board = new Board();
        this.players = new ArrayList<>();
    }

    // Method to initialize the game
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

    // Method to initialize ResourcePile
    private void initializeStarterDeck() {
        this.starterDeck = new Stack<>();

        Resource[] resources;
        Map<CornerPosition, Corner> corners;
        Map<CornerPosition, Corner> backCorners;

        // Card #81
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null));
        corners.put(UP_RIGHT,new Corner(true, PLANT_KINGDOM));
        corners.put(BOTTOM_LEFT,new Corner(true, INSECT_KINGDOM));
        corners.put(BOTTOM_RIGHT,new Corner(true, null));
        backCorners = new HashMap<>();
        backCorners.put(UP_LEFT,new Corner(true, FUNGI_KINGDOM));
        backCorners.put(UP_RIGHT,new Corner(true, PLANT_KINGDOM));
        backCorners.put(BOTTOM_LEFT,new Corner(true, INSECT_KINGDOM));
        backCorners.put(BOTTOM_RIGHT,new Corner(true, ANIMAL_KINGDOM));
        resources = new Resource[]{INSECT_KINGDOM};
        starterDeck.push(new StarterCard(resources, ZERO, corners, backCorners));

        // Card #82
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, ANIMAL_KINGDOM));
        corners.put(UP_RIGHT,new Corner(true, null));
        corners.put(BOTTOM_LEFT,new Corner(true, null));
        corners.put(BOTTOM_RIGHT,new Corner(true, FUNGI_KINGDOM));
        backCorners = new HashMap<>();
        backCorners.put(UP_LEFT,new Corner(true, PLANT_KINGDOM));
        backCorners.put(UP_RIGHT,new Corner(true, ANIMAL_KINGDOM));
        backCorners.put(BOTTOM_LEFT,new Corner(true, FUNGI_KINGDOM));
        backCorners.put(BOTTOM_RIGHT,new Corner(true, INSECT_KINGDOM));
        resources = new Resource[]{FUNGI_KINGDOM};
        starterDeck.push(new StarterCard(resources, ZERO, corners, backCorners));

        // Card #83
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null));
        corners.put(UP_RIGHT,new Corner(true, null));
        corners.put(BOTTOM_LEFT,new Corner(true, null));
        corners.put(BOTTOM_RIGHT,new Corner(true, null));
        backCorners = new HashMap<>();
        backCorners.put(UP_LEFT,new Corner(true, INSECT_KINGDOM));
        backCorners.put(UP_RIGHT,new Corner(true, ANIMAL_KINGDOM));
        backCorners.put(BOTTOM_LEFT,new Corner(true, FUNGI_KINGDOM));
        backCorners.put(BOTTOM_RIGHT,new Corner(true, PLANT_KINGDOM));
        resources = new Resource[]{PLANT_KINGDOM, FUNGI_KINGDOM};
        starterDeck.push(new StarterCard(resources, ZERO, corners, backCorners));

        // Next cards...

        // Shuffle starterDeck
        Collections.shuffle(starterDeck);
    }

    // Method to initialize ResourcePile
    private void initializeResourcePile() {
        this.resourcePile = new Stack<>();

        Resource[] resources;
        Map<CornerPosition, Corner> corners;

        // Card #1
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, FUNGI_KINGDOM));
        corners.put(UP_RIGHT,new Corner(true, null));
        corners.put(BOTTOM_LEFT,new Corner(true, FUNGI_KINGDOM));
        corners.put(BOTTOM_RIGHT,new Corner(true, null));
        resources = new Resource[]{FUNGI_KINGDOM};
        resourcePile.push(new Card(resources, ZERO, corners));

        // Card 2
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, FUNGI_KINGDOM));
        corners.put(UP_RIGHT,new Corner(true, FUNGI_KINGDOM));
        corners.put(BOTTOM_LEFT,new Corner(true, null));
        corners.put(BOTTOM_RIGHT,new Corner(true, null));
        resources = new Resource[]{FUNGI_KINGDOM};
        resourcePile.push(new Card(resources, ZERO, corners));

        // Card 3
        corners = new HashMap<>();
        corners.put(UP_LEFT,new Corner(true, null));
        corners.put(UP_RIGHT,new Corner(false, null));
        corners.put(BOTTOM_LEFT,new Corner(true, FUNGI_KINGDOM));
        corners.put(BOTTOM_RIGHT,new Corner(true, FUNGI_KINGDOM));
        resources = new Resource[]{FUNGI_KINGDOM};
        resourcePile.push(new Card(resources, ZERO, corners));

        // Next cards...

        // Shuffle resourcePile
        Collections.shuffle(resourcePile);
    }

    // Method to initialize GoldPile
    private void initializeGoldPile() {
        this.goldPile = new Stack<>();

        Resource[] resources;
        Map<CornerPosition, Corner> corners;
        Resource[] cost;

        // Card
    }

    // Method to add a player to the game
    public void addPlayer(String nickname) {

        // Istantiate a new player object
        Player player = new Player(nickname);

        // Create the player hand
        List<Card> hand = null;
        Card card;

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

    // Method to get the current player
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    // Method to choose secret objectives
    public void chooseSecretObjective(Player player) {
        // Prepare two objective options
        ObjectiveCard objectiveOption1 = objectiveDeck.drawCard();
        ObjectiveCard objectiveOption2 = objectiveDeck.drawCard();

        // Display options to the player via the view - COULD BE SOMETHING LIKE THIS
        // view.displayObjectiveOptions(player, objectiveOption1, objectiveOption2);

        // Wait for player input (handled by the controller)

        // Once player has chosen, update the player's secret objective - COULD BE SOMETHING LIKE THIS
        // ObjectiveCard chosenObjective = controller.getPlayerInput();
        // player.setSecretObjective(chosenObjective);
    }

    // Method to choose starting side
    public void chooseStartingSide() {
        // Prepare the starter card
        StarterCard starterCard = starterDeck.pop();

        // Display to the player both sides of the starter card via the view

        // Wait for the player choice

        // Once the player has chosen, update the player's playArea
    }

    // Getter for common objectives
    public List<ObjectiveCard> getCommonObjectives() {
        return commonObjectives;

    }

    // Method to play a card
    public void playCard() {
        // Implement logic to play a card
    }

    // Method to draw a card
    public void drawCard() {
        // Implement logic to draw a card
    }

    // Getter for visible cards
    public List<Card> getVisibleCards() {
        return visibleCards;
    }

    // Method to determine the winner
    public Player Winner() {
        // Implement logic to determine the winner
        return null; // Placeholder, replace with actual winner
    }

}
