package org.example.myversion.client.view;

import javafx.application.Application;
import org.example.myversion.client.Client;
import org.example.myversion.server.model.Coordinates;
import org.example.myversion.server.model.Player;
import org.example.myversion.server.model.decks.cards.*;
import org.example.myversion.server.model.enumerations.CornerPosition;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is the interface used for the view (CLI or GUI).
 * It provides methods for interacting with the game and handling player actions.
 */
public abstract class GameView extends Application{
    private List<PlayableCard> visibleResourceCards;
    private PlayableCard coveredResourceCard;
    private List<GoldCard> visibleGoldCards;
    private GoldCard coveredGoldCard;

    protected Map<String, Player> players = new HashMap<>();

    private Map<String, List<PlayableCard>> handsMap = new HashMap<>();
    private Map<String, Card[][]> playAreasMap = new HashMap<>();
    public List<PlayableCard> getVisibleResourceCards() {
        return visibleResourceCards;
    }

    public void setVisibleResourceCards(List<PlayableCard> visibleResourceCards) {
        this.visibleResourceCards = visibleResourceCards;
    }

    public PlayableCard getCoveredResourceCard() {
        return coveredResourceCard;
    }

    public void setCoveredResourceCard(PlayableCard coveredResourceCard) {
        this.coveredResourceCard = coveredResourceCard;
    }

    public GoldCard getCoveredGoldCard() {
        return coveredGoldCard;
    }



    public void setCoveredGoldCard(GoldCard coveredGoldCard) {
        this.coveredGoldCard = coveredGoldCard;
    }

    public List<GoldCard> getVisibleGoldCards() {
        return visibleGoldCards;
    }

    public void setVisibleGoldCards(List<GoldCard> visibleGoldCards) {
        this.visibleGoldCards = visibleGoldCards;
    }

    public Map<String, List<PlayableCard>> getHandsMap() {
        return handsMap;
    }

    public void setHandsMap(Map<String, List<PlayableCard>> handsMap) {
        this.handsMap = handsMap;
    }

    public Map<String, Card[][]> getPlayAreasMap() {
        return playAreasMap;
    }

    public void setPlayAreasMap(Map<String, Card[][]> playAreasMap) {
        this.playAreasMap = playAreasMap;
    }

    public void initializePlayAreas(Map<String, StarterCard> starterCards) {
        for (String nickname : starterCards.keySet()) {
            Card[][] playArea = new Card[81][81];
            playArea[41][41] = starterCards.get(nickname);
            playAreasMap.put(nickname, playArea);
        }
    }

    public void playCard(String nickname, PlayableCard playedCard, Coordinates coordinates) {
        // Remove the played card from the player's hand
        // Retrive the hand for the given nickname
        List<PlayableCard> hand = handsMap.get(nickname);

        // Remove the card from the hand
        for (PlayableCard card : hand)
            if (card.getId() == playedCard.getId()) {
                hand.remove(card);
                break;
            }

        // Add the played card to the player's play area
        // Retrieve the play area for the given nickname
        Card[][] playArea = playAreasMap.get(nickname);

        // Retrieve the coordinates
        int x = coordinates.getX();
        int y = coordinates.getY();

        // Place the card in the specified coordinates in the play area
        playArea[x][y] = playedCard;

        // Update corner status of adjacent cards
        if(playArea[x-1][y-1] != null)
            playArea[x-1][y-1].getCorners().get(CornerPosition.BOTTOM_RIGHT).setCovered(true);
        if(playArea[x-1][y+1] != null)
            playArea[x-1][y+1].getCorners().get(CornerPosition.BOTTOM_LEFT).setCovered(true);
        if(playArea[x+1][y-1] != null)
            playArea[x+1][y-1].getCorners().get(CornerPosition.UP_RIGHT).setCovered(true);
        if(playArea[x+1][y+1] != null){
            playArea[x+1][y+1].getCorners().get(CornerPosition.UP_LEFT).setCovered(true);
        }
    }

    public void drawCard(String nickname, PlayableCard drawnCard) {
        // Add the drawn card to the player's hand
        // Retrive the hand for the given nickname
        List<PlayableCard> hand = handsMap.get(nickname);

        // Add the drawn card to the player's hand
        hand.add(drawnCard);
    }

    public abstract void setClient(Client client);

    public abstract void showMessage(String message);

    public abstract void startView() throws IOException;

    public abstract void clientLogin() throws IOException;

    public abstract void showTakenUsername() throws IOException;

    public abstract void showGameAlreadyStartedMessage();

    public abstract void playersNumberChoice() throws IOException;

    public abstract void invalidPlayersNumberChoice() throws IOException;

    public abstract void waitForOtherPlayers();

    public abstract void showVisibleCards();

    public abstract void showCommonObjectives(List<ObjectiveCard> objectiveCards);

    public abstract void showSecretObjectives(List<ObjectiveCard> objectiveCards);

    public abstract void secretObjectiveCardChoice(List<ObjectiveCard> objectiveCards) throws IOException;

    public abstract void showStarterCard(StarterCard starterCard);

    public abstract void starterCardSideChoice(StarterCard starterCard) throws IOException;

    public abstract void showMyHand();

    public abstract void showMyPlayArea();

    public abstract void showOthersHandsAndPlayAreas();

    public abstract void chooseCardToPlay() throws IOException;

    public abstract void invalidMove() throws IOException;

    public abstract void showUpdatedPlayArea(String nickname, Card[][] playArea);

    public abstract void chooseCardToDraw() throws IOException;

    public abstract void showUpdatedHand(String nickname);

    public abstract void showScores(Map<String, Integer> scores);

    public abstract void showEndGame(String winner);
}
