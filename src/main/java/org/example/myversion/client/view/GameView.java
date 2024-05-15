package org.example.myversion.client.view;

import org.example.myversion.client.Client;
import org.example.myversion.server.model.decks.cards.ObjectiveCard;
import org.example.myversion.server.model.decks.cards.StarterCard;

import java.io.IOException;
import java.util.List;

/**
 * This is the interface used for the view (cli or gui)
 */
public interface GameView {

    void setClient(Client client);

    void showMessage(String message);

    void startView() throws IOException;

    void clientLogin() throws IOException;

    void showGameAlreadyStartedMessage();

    void playersNumberChoice() throws IOException;

    void invalidPlayersNumberChoice() throws IOException;

    void waitForOtherPlayers();

    void showCommonObjectives(List<ObjectiveCard> objectiveCards);

    void showSecretObjectives(List<ObjectiveCard> objectiveCards);

    void secretObjectiveCardChoice();

    void showStarterCard(StarterCard starterCard);

    void starterCardSideChoice(StarterCard starterCard) throws IOException;
}
