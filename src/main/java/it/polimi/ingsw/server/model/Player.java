package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.decks.cards.*;
import it.polimi.ingsw.server.model.enumerations.*;
import it.polimi.ingsw.server.model.exceptions.InvalidMoveException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a player in the game.
 * Each player has a nickname, a connection status, a hand of cards, a play area, a secret objective card,
 * and a map that traces the number of resources and special objects in his play area.
 */
public class Player {
    private final String nickname;
    private List<PlayableCard> hand;
    private Card[][] playArea;
    private ObjectiveCard secretObjective;
    private Map<CornerContent, Integer> stock;

    /**
     * Constructs a player with the specified nickname.
     * The player is initially connected, and their play area is initialized with dimensions of 81x81 cells.
     * The stock map is initialized with default value of 0 for each type of resource and special object.
     *
     * @param nickname The nickname of the player.
     */
    public Player(String nickname) {
        this.nickname = nickname;
        this.hand = new ArrayList<>();
        this.playArea = new Card[81][81];
        initializeStock();
    }

    /**
     * Initializes the stock map with default values for each resource and special object enum value.
     */
    private void initializeStock() {
        stock = new HashMap<>();
        for (Resource resource : Resource.values()) {
            stock.put(resource, 0);
        }
        for (SpecialObject specialObject : SpecialObject.values()) {
            stock.put(specialObject, 0);
        }
    }

    /**
     * Gets the nickname of the player.
     *
     * @return The nickname of the player.
     */
    public String getNickname() {
        return nickname;
    }

    public Map<CornerContent, Integer> getStock() {
        return stock;
    }

    public void setStock(Map<CornerContent, Integer> stock) {
        this.stock = stock;
    }

    /**
     * Initializes the player's hand with the specified list of cards.
     *
     * @param hand The list of cards to initialize the player's hand.
     */
    public void initializeHand(List<PlayableCard> hand) {
        this.hand = hand;
    }

    /**
     * Initializes the player's play area with the provided starter card.
     * Places the starter card in the centre of the play area on the specified side.
     * Updates the resources and special objects stock depending on the played side of the card.
     *
     * @param starterCard The starter card to initialize the player's play area.
     */
    public void initializePlayArea(StarterCard starterCard) {
        this.playArea[41][41] = starterCard;
        if (starterCard.getCorners() != null) {
            // updating the player stock with the corner contents
            for (CornerPosition cornerPosition : starterCard.getCorners().keySet()) {
                CornerContent cornerContent = starterCard.getCorners().get(cornerPosition).getCornerContent();

                if (cornerContent != null) {//messo io
                    // updating the value only where it exists
                    if (cornerContent != CornerVisibility.EMPTY && cornerContent != CornerVisibility.FULL) {
                        stock.compute(cornerContent, (k, v) -> {
                            if (v == null) {
                                throw new NullPointerException("Value 'v' is null while computing stock.");
                            }
                            return v + 1;
                        });
                    }
                }
            }
        }
        // updating the player stock with the starter card resources
        if (!starterCard.isPlayedBack()) {
            for (CornerContent cornerContent : starterCard.getResource()) {
                stock.compute(cornerContent, (k, v) -> {
                    if (v == null) {
                        throw new NullPointerException("Value 'v' is null while computing stock.");
                    }
                    return v + 1;
                });
            }
        }
    }

    public void initializePlayArea(Card[][] playArea) {
        this.playArea = playArea;
    }

    /**
     * Sets the secret objective card for the player.
     *
     * @param secretObjective The secret objective card to set for the player.
     */
    public void setSecretObjective(ObjectiveCard secretObjective) {
        this.secretObjective = secretObjective;
    }

    public ObjectiveCard getSecretObjective() {
        return secretObjective;
    }

    /**
     * Gets the player's hand of cards.
     *
     * @return The player's hand of cards.
     */
    public List<PlayableCard> getHand() {
        return hand;
    }

    /**
     * Gets the player's play area.
     *
     * @return The player's play area.
     */
    public Card[][] getPlayArea() {
        return playArea;
    }

    /**
     * Places a card on the player's play area at the specified position.
     * Removes the placed card from the player's hand.
     * Updates the player stock.
     *
     * @param placedCard  The card to be placed.
     * @param coordinates The x-coordinate and y-coordinate position.
     */
    public int placeCard(PlayableCard placedCard, Coordinates coordinates) {
        if (hand == null)
            hand = new ArrayList<>();

        // Retrieve the coordinates
        int x = coordinates.getX();
        int y = coordinates.getY();

        playArea[x][y] = placedCard;

        // Remove the played card from the player's hand
        for (PlayableCard card : hand)
            if (card.getId() == placedCard.getId()) {
                hand.remove(card);
                break;
            }

        updateStock(placedCard);

        int coveredCorners = 0;

        // Update corner status of adjacent cards and remove content from stock
        if (playArea[x - 1][y - 1] != null) {
            Corner corner = playArea[x - 1][y - 1].getCorners().get(CornerPosition.BOTTOM_RIGHT);
            corner.setCovered(true);
            removeFromStock(corner.getCornerContent());
            coveredCorners++;
        }
        if (playArea[x - 1][y + 1] != null) {
            Corner corner = playArea[x - 1][y + 1].getCorners().get(CornerPosition.BOTTOM_LEFT);
            corner.setCovered(true);
            removeFromStock(corner.getCornerContent());
            coveredCorners++;
        }
        if (playArea[x + 1][y - 1] != null) {
            Corner corner = playArea[x + 1][y - 1].getCorners().get(CornerPosition.UP_RIGHT);
            corner.setCovered(true);
            removeFromStock(corner.getCornerContent());
            coveredCorners++;
        }
        if (playArea[x + 1][y + 1] != null) {
            Corner corner = playArea[x + 1][y + 1].getCorners().get(CornerPosition.UP_LEFT);
            corner.setCovered(true);
            removeFromStock(corner.getCornerContent());
            coveredCorners++;
        }

        return coveredCorners;
    }

    /**
     * Checks if the player has enough stock to play the specified gold card.
     *
     * @param playedCard The card to be played.
     * @throws InvalidMoveException If the player doesn't have enough stock to play the card.
     */
    public void hasEnoughStock(GoldCard playedCard) throws InvalidMoveException {
        Resource[] cost = playedCard.getCost();

        // Create a map to count the required quantities of each resource
        Map<Resource, Integer> requiredResources = new HashMap<>();

        for (Resource resource : cost) {
            requiredResources.put(resource, requiredResources.getOrDefault(resource, 0) + 1);
        }

        // Check if the stock has enough of each required resource
        for (Map.Entry<Resource, Integer> entry : requiredResources.entrySet()) {
            Resource resource = entry.getKey();
            int requiredQuantity = entry.getValue();

            int availableQuantity = stock.getOrDefault(resource, 0);

            if (availableQuantity < requiredQuantity) {
                throw new InvalidMoveException("The player doesn't have enough stock to play this card.");
            }
        }

    }

    /**
     * Checks if a move to place a card at the specified coordinates is valid.
     *
     * @param coordinates The coordinates where the card is to be placed.
     * @return True if the move is valid, otherwise False.
     * @throws InvalidMoveException If the move is not valid.
     */
    public boolean isValidMove(Coordinates coordinates) throws InvalidMoveException {
        // Check if the position is valid
        if (!isValidPosition(coordinates)) {
            throw new InvalidMoveException("Not valid position.");
        }

        // Check if any of the adjacent corners are full
        if (isAdjacentCornerFull(coordinates)) {
            throw new InvalidMoveException("Adjacent corner is full.");
        }

        return true;
    }

    /**
     * Checks if the specified position is beside, beneath or above any existing card.
     *
     * @param coordinates The coordinates to check.
     * @return True if the position is adjacent to any existing card, otherwise False.
     */
    private boolean isValidPosition(Coordinates coordinates) {
        int x = coordinates.getX();
        int y = coordinates.getY();

        // Check if there's a card above, beneath, to the left, or to the right of the specified coordinates
        // If any adjacent position contains a card, return true
        return !(isCardPresent(new Coordinates(x, y)) ||
                isCardPresent(new Coordinates(x, y - 1)) ||
                isCardPresent(new Coordinates(x, y + 1)) ||
                isCardPresent(new Coordinates(x - 1, y)) ||
                isCardPresent(new Coordinates(x + 1, y))) &&
                // Check if the chosen card can be connected to a placed card
                (isCardPresent(new Coordinates(x - 1, y - 1)) ||
                        isCardPresent(new Coordinates(x - 1, y + 1)) ||
                        isCardPresent(new Coordinates(x + 1, y - 1)) ||
                        isCardPresent(new Coordinates(x + 1, y + 1)));
    }

    /**
     * Checks if there is a card present at the specified coordinates.
     *
     * @param coordinates The coordinates to check.
     * @return True if there is a card present at the specified coordinates, otherwise False.
     */
    private boolean isCardPresent(Coordinates coordinates) {
        int x = coordinates.getX();
        int y = coordinates.getY();

        // Check if the specified position is within the bounds of the play area
        // and if there's a card present at that position
        return x >= 0 && x < playArea.length &&
                y >= 0 && y < playArea[0].length &&
                playArea[x][y] != null;
    }

    /**
     * Checks if any of the adjacent corners of existing cards are full.
     *
     * @param coordinates The coordinates where the new card will be placed.
     * @return True if any of the adjacent corners are full, otherwise False.
     */
    private boolean isAdjacentCornerFull(Coordinates coordinates) {
        int x = coordinates.getX();
        int y = coordinates.getY();

        // Check if the corners of the adjacent cards are full
        // Return true if any adjacent corner is full
        return isCornerFull(playArea[x + 1][y + 1], CornerPosition.UP_LEFT) ||
                isCornerFull(playArea[x + 1][y - 1], CornerPosition.UP_RIGHT) ||
                isCornerFull(playArea[x - 1][y + 1], CornerPosition.BOTTOM_LEFT) ||
                isCornerFull(playArea[x - 1][y - 1], CornerPosition.BOTTOM_RIGHT);
    }

    /**
     * Checks if the specified corner of the given card is full.
     *
     * @param card           The card to check.
     * @param cornerPosition The position of the corner to check.
     * @return True if the specified corner is full, otherwise False.
     */
    private boolean isCornerFull(Card card, CornerPosition cornerPosition) {
        // If the adjacent card is null, the corner cannot be full
        if (card == null) {
            return false;
        }

        // Get the corner content of the specified position on the card
        Corner corner = card.getCorners().get(cornerPosition);

        // Check if the corner content is set to FULL
        return corner.getCornerContent() == CornerVisibility.FULL;
    }

    /**
     * Updates the player's stock based on the corner contents of the played card.
     *
     * @param playedCard The card played by the player.
     */
    private void updateStock(PlayableCard playedCard) {
        if (!playedCard.isPlayedBack()) {
            // updating the player stock with the corner contents
            for (CornerPosition cornerPosition : playedCard.getCorners().keySet()) {
                CornerContent cornerContent = playedCard.getCorners().get(cornerPosition).getCornerContent();

                // updating the value only where it exists
                if (cornerContent != CornerVisibility.EMPTY && cornerContent != CornerVisibility.FULL)
                    stock.compute(cornerContent, (k, v) -> v + 1);
            }
        } else {
            // updating the player stock with the card resource if played on the back
            stock.compute(playedCard.getResource(), (k, v) -> v + 1);
        }
    }

    /**
     * This method removes the given corner content from the stock
     * @param cornerContent
     */
    private void removeFromStock(CornerContent cornerContent) {
        // Only remove from stock if the cornerContent is not an instance of CornerVisibility
        if (!(cornerContent instanceof CornerVisibility)) {
            stock.computeIfPresent(cornerContent, (k, v) -> v > 0 ? v - 1 : 0);
        }
    }

    /**
     * Adds a drawn card to the player's hand.
     *
     * @param drawnCard The card drawn by the player.
     */
    public void drawCard(PlayableCard drawnCard) {
        if (hand != null)
            hand.add(drawnCard);
        else {
            hand = new ArrayList<>();
            hand.add(drawnCard);
        }
    }

    /**
     * Calculates the final score of the player based on common objective cards and the resource stock.
     *
     * @param commonObjectives A list of common objective cards.
     * @param secretObjective  is the secretObjective of the player
     * @return The player's final score calculated based on common objective cards and the resource stock.
     */
    public int objectiveScoreCalculator(List<ObjectiveCard> commonObjectives, ObjectiveCard secretObjective) {
        int objectiveScore = 0;

        // Points given by common objective cards
        for (ObjectiveCard objective : commonObjectives) {
            int commonObjectiveScore = objective.calculateObjectiveCardPoints(stock, playArea, objective);
            objectiveScore += commonObjectiveScore;
        }

        // Points given by secret objective card
        int secretObjectiveScore = secretObjective.calculateObjectiveCardPoints(stock, playArea, secretObjective);
        objectiveScore += secretObjectiveScore;

        return objectiveScore;
    }
}
