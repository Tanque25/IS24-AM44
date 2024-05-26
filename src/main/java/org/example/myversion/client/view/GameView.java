package org.example.myversion.client.view;

import org.example.myversion.client.Client;
import org.example.myversion.server.model.Player;
import org.example.myversion.server.model.decks.cards.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This is the interface used for the view (cli or gui)
 */
public abstract class GameView {
    private List<PlayableCard> visibleResourceCards;
    private PlayableCard coveredResourceCard;
    private List<GoldCard> visibleGoldCards;
    private GoldCard coveredGoldCard;

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

    protected Map<String, List<PlayableCard>> getHandsMap() {
        return handsMap;
    }

    public void setHandsMap(Map<String, List<PlayableCard>> handsMap) {
        this.handsMap = handsMap;
    }

    protected Map<String, Card[][]> getPlayAreasMap() {
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

    public abstract void setClient(Client client);

    public abstract void showMessage(String message);

    public abstract void startView() throws IOException;

    public abstract void clientLogin() throws IOException;

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

    public abstract void chooseCardToDraw() throws IOException;
}
