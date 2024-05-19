package org.example.myversion.client.GUI;

import javafx.stage.Stage;
import org.example.myversion.client.Client;
import org.example.myversion.client.view.GameView;
import org.example.myversion.server.model.decks.cards.ObjectiveCard;
import org.example.myversion.server.model.decks.cards.StarterCard;

import java.io.IOException;
import java.util.List;

public class GUI extends GameView {

    Stage stage;
    public static GUI INSTANCE;

    public GUI() {
    }

    /**
     * Singleton pattern
     * @return the instance of the GUI
     */
    public static GUI getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GUI();
        }
        return INSTANCE;
    }

    @Override
    public void setClient(Client client) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void startView() throws IOException {

    }

    @Override
    public void clientLogin() throws IOException {

    }

    @Override
    public void showGameAlreadyStartedMessage() {

    }

    @Override
    public void playersNumberChoice() throws IOException {

    }

    @Override
    public void invalidPlayersNumberChoice() throws IOException {

    }

    @Override
    public void waitForOtherPlayers() {

    }

    @Override
    public void showCommonObjectives(List<ObjectiveCard> objectiveCards) {

    }

    @Override
    public void showSecretObjectives(List<ObjectiveCard> objectiveCards) {

    }

    @Override
    public void secretObjectiveCardChoice(List<ObjectiveCard> objectiveCards) throws IOException {

    }

    @Override
    public void showStarterCard(StarterCard starterCard) {

    }

    @Override
    public void starterCardSideChoice(StarterCard starterCard) throws IOException {

    }

    @Override
    public void showMyHand() {

    }

    @Override
    public void showMyPlayArea() {

    }

    @Override
    public void showOthersHandsAndPlayAreas() {

    }

    @Override
    public void chooseCardToPlay() throws IOException {

    }
}
