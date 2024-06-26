package org.example.myversion.server.model;

import org.example.myversion.server.model.decks.*;
import org.example.myversion.server.model.decks.cards.*;
import org.example.myversion.server.model.enumerations.ParameterType;
import org.example.myversion.server.model.enumerations.PointsParameter;
import org.example.myversion.server.model.enumerations.SpecialObject;
import org.example.myversion.server.model.exceptions.InvalidChoiceException;
import org.example.myversion.server.model.exceptions.InvalidMoveException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Represents the game logic.
 * Manages the players, decks, common objectives, visible cards, and game progress.
 */
public class Game {
    private Board board;
    private List<Player> players;
    private Player currentPlayer;
    private StarterDeck starterDeck;
    private ObjectiveDeck objectiveDeck;
    private ResourceDeck resourceDeck;
    private GoldDeck goldDeck;
    private List<ObjectiveCard> commonObjectives;
    private List<PlayableCard> visibleResourceCards;
    private List<GoldCard> visibleGoldCards;
    private boolean lastTurn;

    /**
     * Constructs a Game object and initializes the game components.
     */
    public Game() {
        this.board = new Board();
        this.starterDeck = new StarterDeck();
        this.objectiveDeck = new ObjectiveDeck();
        this.resourceDeck = new ResourceDeck();
        this.goldDeck = new GoldDeck();
        this.commonObjectives = new ArrayList<>();
        this.visibleGoldCards = new ArrayList<>();
        this.visibleResourceCards = new ArrayList<>();
        this.players = new ArrayList<>();
        this.lastTurn = false;
    }

    /**
     * Initializes the common objectives and visible cards at the beginning of the game.
     */
    public void initializeGame() {
        // drawing the common objectives
        commonObjectives.add(objectiveDeck.drawCard());
        commonObjectives.add(objectiveDeck.drawCard());

        // drawing the visible cards
        visibleResourceCards.add(resourceDeck.drawCard());
        visibleResourceCards.add(resourceDeck.drawCard());
        visibleGoldCards.add(goldDeck.drawCard());
        visibleGoldCards.add(goldDeck.drawCard());

        //initialize the player scores
        board.initializePlayerScores(getPlayers());
    }

    /**
     * Adds a new player to the game with the specified nickname.
     *
     * @param nickname The nickname of the new player.
     */
    public void newPlayer(String nickname) {
        Player player = new Player(nickname);

        // Initializing the player's hand before adding it to the players list
        player.initializeHand(generatePlayerHand());

        // Adding the player to the player list
        players.add(player);

        // If the player is the first one to join the game, set them as the current player
        if (players.size() == 1) {
            currentPlayer = player;
        }
    }

    /**
     * Generates the hand of a player
     *
     * @return The hand of the player
     */
    private List<PlayableCard> generatePlayerHand() {
        List<PlayableCard> hand = new ArrayList<>();

        hand.add(resourceDeck.drawCard());
        hand.add(resourceDeck.drawCard());
        hand.add(goldDeck.drawCard());

        return hand;
    }

    /**
     * Retrieves the list of players in the game.
     *
     * @return The list of players.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Retrieves the current player whose turn it is.
     *
     * @return The current player.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Retrieves the board of the game.
     *
     * @return The board of the game.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Initializes the play area for the specified player and places the starter card.
     * The starter card is placed based on the specified side chosen by the player.
     *
     * @param player      The player for whom the play area is initialized.
     * @param starterCard The starter card to be placed in the player's play area.
     */
    public void placeStarterCard(Player player, StarterCard starterCard) {
        player.initializePlayArea(starterCard);
    }

    /**
     * Draws two secret objectives for the player.
     * The player will choose one of these.
     *
     * @return A list containing two drawn secret objectives.
     */
    public List<ObjectiveCard> drawSecretObjectives() {
        List<ObjectiveCard> drawnObjectives = new ArrayList<>();

        // Draw two objective cards
        drawnObjectives.add(objectiveDeck.drawCard());
        drawnObjectives.add(objectiveDeck.drawCard());

        return drawnObjectives;
    }

    /**
     * Draws the starter card for the player.
     *
     * @return the drawn starter card
     */
    public StarterCard drawStarterCard() {
        StarterCard starterCard;

        starterCard = starterDeck.drawCard();
        return starterCard;
    }

    /**
     * Sets the secret objective for the specified player.
     *
     * @param player          The player whose secret objective is being set.
     * @param secretObjective The secret objective card to set for the player.
     */
    public void setPlayerSecretObjective(Player player, ObjectiveCard secretObjective) {
        player.setSecretObjective(secretObjective);
    }

    /**
     * Retrieves the list of common objectives in the game.
     *
     * @return The list of common objectives.
     */
    public List<ObjectiveCard> getCommonObjectives() {
        return commonObjectives;
    }
    /**
     * Retrieves the list of the visible gold cards
     *
     * @return The list of the visible gold cards
     */
    public List<GoldCard> getVisibleGoldCards() {
        return visibleGoldCards;
    }
    /**
     * Retrieves the list of the visible resource cards
     *
     * @return The list of the visible resource cards
     */
    public List<PlayableCard> getVisibleResourceCards() {
        return visibleResourceCards;
    }

    /**
     * The played card is placed on the player's play area at the specified coordinates.
     * It is then removed from the player's hand.
     * Updates the player stock.
     * The player's score is updated on the board.
     *
     * @param player      The player who is playing the card.
     * @param playedCard  The card being played.
     * @param coordinates The coordinates on the board where the card will be placed.
     * @throws InvalidMoveException If the move is not valid.
     */
    public void playCard(Player player, PlayableCard playedCard, Coordinates coordinates) throws InvalidMoveException {
        // Check if it's possible to place the card at the specified coordinates
        if(player.isValidMove(coordinates)){

            // Check if the player has enough stock in case of GoldCard
            if(playedCard instanceof GoldCard && !playedCard.isPlayedBack())
                player.hasEnoughStock((GoldCard) playedCard);

            // placing the card in the play area, removing it from the player's hand and updating his stock
            int coveredCorners = player.placeCard(playedCard, coordinates);

            int pointsMultiplication = 1;

            if(playedCard instanceof GoldCard) {
                PointsParameter pointsParameter = ((GoldCard) playedCard).getPointsParameter();

                switch (pointsParameter) {
                    case ParameterType.EMPTY -> {
                        break;
                    }
                    case ParameterType.CORNER -> {
                        pointsMultiplication = coveredCorners;
                        break;
                    }
                    case SpecialObject.INKWELL -> {
                        pointsMultiplication = player.getStock().get(SpecialObject.INKWELL);
                        break;
                    }
                    case SpecialObject.MANUSCRIPT -> {
                        pointsMultiplication = player.getStock().get(SpecialObject.MANUSCRIPT);
                        break;
                    }
                    case SpecialObject.QUILL -> {
                        pointsMultiplication = player.getStock().get(SpecialObject.QUILL);
                        break;
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + pointsParameter);
                }
            }

            // updating the player score
            if(!playedCard.isPlayedBack()){
                board.updateScore(player, playedCard.getCardPoints(), pointsMultiplication);
            }

        }
    }

    /**
     * Retrieves the card on the peek of the resource card deck
     *
     * @return The card on the peek of the resource card deck
     */
    public PlayableCard getResourceDeckPeek() {
        return resourceDeck.getResourceDeck().peek();
    }
    /**
     * Retrieves the card on the peek of the gold card deck
     *
     * @return The card on the peek of the resource deck
     */
    public GoldCard getGoldDeckPeek() {
        return goldDeck.getGoldDeck().peek();
    }

    /**
     * Draws a card for the player and manages card replacement for visible and top deck cards.
     *
     * @param player     The player who draws the card.
     * @param chosenCard The card chosen by the player.
     * @throws InvalidChoiceException If the chosen card is invalid.
     */
    public void drawCard(Player player, PlayableCard chosenCard) throws InvalidChoiceException {
        // Add the chosen card to the player hand
        player.drawCard(chosenCard);

        if (visibleResourceCards.contains(chosenCard)) {
            // If the chosen card is one of the visible resource cards, replace it with a new card
            replaceVisibleResourceCard(chosenCard);
        } else if (chosenCard instanceof GoldCard && visibleGoldCards.contains(chosenCard)) {
            // If the chosen card is one of the visible gold cards, replace it with a new card
            replaceVisibleGoldCard(chosenCard);
        } else if (chosenCard.equals(resourceDeck.getResourceDeck().peek())) {
            // If the chosen card is a resource card, draw a card from the resource deck
            resourceDeck.drawCard();
        } else if (chosenCard.equals(goldDeck.getGoldDeck().peek())) {
            // If the chosen card is a gold card, draw a card from the gold deck
            goldDeck.drawCard();
        } else {
            throw new InvalidChoiceException("Invalid choice.");
        }

    }

    /**
     * Replaces the visible resource card with a new card from the resource deck.
     *
     * @param chosenCard The card to be replaced.
     */
    private void replaceVisibleResourceCard(PlayableCard chosenCard) {
        // Remove the chosen card from visible resource cards
        visibleResourceCards.remove(chosenCard);

        // Draw a new card from the resource deck and add it to the visible resource cards
        visibleResourceCards.add(resourceDeck.drawCard());
    }

    /**
     * Replaces the visible gold card with a new card from the gold deck.
     *
     * @param chosenCard The card to be replaced.
     */
    private void replaceVisibleGoldCard(PlayableCard chosenCard) {
        // Remove the chosen card from visible gold cards
        visibleGoldCards.remove(chosenCard);

        // Draw a new card from the resource deck and add it to the visible gold cards
        visibleGoldCards.add(goldDeck.drawCard());
    }

    /**
     * Updates the current player to the next player in the list.
     * If the current player is the last player in the list, the next player will be the first player in the list.
     */
    public void updateCurrentPlayer() {
        int currentIndex = players.indexOf(currentPlayer);
        int nextIndex = (currentIndex + 1) % players.size(); // Calculate the index of the next player

        currentPlayer = players.get(nextIndex); // Set the next player as the current player
    }

    /**
     * Checks if it is the last turn of the game.
     *
     * @return true if it is the last turn, false otherwise.
     */
    public boolean isLastTurn() {
        return lastTurn;
    }

    /**
     * Finds the winner of the game based on the player with the highest score.
     *
     * @return The player with the highest score. Returns null if there are no players or if the scores map is empty.
     */
    public Player winner() {
        return board.findWinner(commonObjectives);
    }

    /**
     * Sets the current player to the specified player.
     *
     * @param player The player to set as the current player.
     */
    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    /**
     * Checks if it is the last turn of the game.
     * The game is considered to be in the last turn if any of the following conditions are met:
     * - A player has reached 20 points.
     * - The gold deck is empty.
     * - The resource deck is empty.
     *
     * @return true if it is the last turn, false otherwise.
     */
    public boolean checkLastTurn() {
        return goldDeck.getGoldDeck().empty() || resourceDeck.getResourceDeck().empty();
    }
    /**
     * When you upload an already started match this function is used to restore
     * the hands of the players as they were when the match was interrupted
     */
    public void restorePlayersHands(Map<String, List<PlayableCard>> playersHands) {
        for (Player player : players) {
            String nickname = player.getNickname();
            if (playersHands.containsKey(nickname)) {
                player.initializeHand(playersHands.get(nickname));
            }
        }
    }
    /**
     * When you upload an already started match this function is used to restore
     * the play areas of the players as they were when the match was interrupted
     */
    public void restorePlayersPlayAreas(Map<String, Card[][]> playersPlayAreas) {
        for (Player player : players) {
            String nickname = player.getNickname();
            if (playersPlayAreas.containsKey(nickname)) {
                player.initializePlayArea(playersPlayAreas.get(nickname));
            }
        }
    }


    /**
     * Retrieves the resource deck of the game
     *
     * @return The resource deck of the game
     */
    public Stack<PlayableCard> getResourceDeck() {
        return resourceDeck.getResourceDeck();
    }
    /**
     * Sets the resource deck
     *
     * @param resourceDeck The resource deck to set as the game resource deck
     */
    public void setResourceDeck(ResourceDeck resourceDeck) {
        this.resourceDeck = resourceDeck;
    }
    /**
     * Retrieves the gold deck of the game
     *
     * @return The gold deck of the game
     */
    public Stack<GoldCard> getGoldDeck() {
        return goldDeck.getGoldDeck();
    }
    /**
     * Sets the gold deck
     *
     * @param goldDeck The gold deck to set as the game resource deck
     */
    public void setGoldDeck(GoldDeck goldDeck) {
        this.goldDeck = goldDeck;
    }
    /**
     * Sets the common objectives card
     *
     * @param objectiveCards The objective cards to set as the game common objective cards
     */
    public void setCommonObjectives(List<ObjectiveCard> objectiveCards) {
        this.commonObjectives = objectiveCards;
    }
    /**
     * Sets the visible resource cards
     *
     * @param visibleResourceCards The visible resource cards to set as the game visible resource cards
     */
    public void setVisibleResourceCards(List<PlayableCard> visibleResourceCards) {
        this.visibleResourceCards = visibleResourceCards;
    }
    /**
     * Sets the visible gold cards
     *
     * @param visibleGoldCards The visible gold cards to set as the game visible gold cards
     */
    public void setVisibleGoldCards(List<GoldCard> visibleGoldCards) {
        this.visibleGoldCards = visibleGoldCards;
    }
}
