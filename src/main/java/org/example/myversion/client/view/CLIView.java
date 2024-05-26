package org.example.myversion.client.view;

import org.example.myversion.client.Client;
import org.example.myversion.client.CodexNaturalis;
import org.example.myversion.messages.Message;
import org.example.myversion.server.model.Coordinates;
import org.example.myversion.server.model.Player;
import org.example.myversion.server.model.decks.cards.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CLIView extends GameView {
    private Client client;

    // private Map<String, List<PlayableCard>> handsMap;
    private Map<String, Card> playAreasMap;

    public CLIView() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

//        System.out.print("Insert server IP: ");
//        try {
//            String ipAddress = reader.readLine();
//        } catch (IOException e) {
//            System.err.println(e.getMessage());
//            System.exit(1);
//        }

        CodexNaturalis.setParameters("127.0.0.1", "rmi", this);

    }

    @Override
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Shows a generic message.
     *
     * @param message the message to be shown
     */
    @Override
    public void showMessage(String message) {
        System.out.print(message);
    }

    public void startView() throws IOException {
        clientLogin();
    }

    public void clientLogin() throws IOException {
        showGameTitle();
        String nickname = showLogin();
        client.sendMessage(new Message("Login", nickname));
    }

    public void showGameTitle() {
        String gameTitle = "\n" +
                " _____           _           _   _       _                   _ _     \n" +
                "/  __ \\         | |         | \\ | |     | |                 | (_)    \n" +
                "| /  \\/ ___   __| | _____  _|  \\| | __ _| |_ _   _ _ __ __ _| |_ ___ \n" +
                "| |    / _ \\ / _` |/ _ \\ \\/ / . ` |/ _` | __| | | | '__/ _` | | / __|\n" +
                "| \\__/\\ (_) | (_| |  __/>  <| |\\  | (_| | |_| |_| | | | (_| | | \\__ \\\n" +
                " \\____/\\___/ \\__,_|\\___/_/\\_\\_| \\_/\\__,_|\\__|\\__,_|_|  \\__,_|_|_|___/\n" +
                "\n";
        System.out.println("Welcome to...\n" + gameTitle);
    }

    public String showLogin() throws IOException {
        showMessage("Please, insert your nickname: ");
        return readNickname();
    }

    public String readNickname() throws IOException {
        String nickname = null;

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            nickname = in.readLine();
        } catch (IOException e) {
            System.err.println("An error occurred while reading the nickname.");
        }

        return nickname;
    }

    public void showGameAlreadyStartedMessage() {
        showMessage("Game already started! You have to wait for the next one :(\n");
        client.stop();
    }

    public void playersNumberChoice() throws IOException {
        int playersNumber = askForPlayersNumber();
        client.sendMessage(new Message("NumberOfPlayers", playersNumber));
//        String playersNum = "zero";
//        if (playersNumber==2){
//            client.sendMessage(new Message("due"));
//        }
//        else if (playersNumber == 3){
//            client.sendMessage(new Message("tre"));
//        }
//        else if (playersNumber == 4){ client.sendMessage(new Message("quattro"));}
//        else {
//            client.sendMessage(new Message("uno"));
//        }
    }

    public void invalidPlayersNumberChoice() throws IOException {
        showMessage("Invalid player number! Please try again: ");
        int playersNumber = askForPlayersNumber();
        client.sendMessage(new Message("NumberOfPlayers", playersNumber));
    }

    public int askForPlayersNumber() {
        showMessage("Please enter the number of players: ");
        int playersNumber = 0;

        playersNumber = readNumber();

        while (playersNumber<2 || playersNumber>4) {
            System.err.print("Please enter a number between 2 and 4: ");
            playersNumber = readNumber();
        }

        return playersNumber;
    }

    public int readNumber() {
        int number = 0;

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            number = Integer.parseInt(in.readLine());
        } catch (IOException e) {
            System.err.println("An error occurred while reading the number.");
        }

        return number;
    }

    public void waitForOtherPlayers() {
        showMessage("Waiting for other players to join...\n");
    }

    public void showVisibleCards() {
        CardView cardView = new CardView();

        List<PlayableCard> visiblePlayableCards = getVisibleResourceCards();
        List<GoldCard> visibleGoldCards = getVisibleGoldCards();

        showMessage("\nThese are the visible resource cards:\n");
        for(PlayableCard resourceCard : visiblePlayableCards) {
            cardView.displayCardFrontTopLine(resourceCard);
            System.out.print("  ");
        }
        System.out.println();
        for(PlayableCard resourceCard : visiblePlayableCards) {
            cardView.displayCardFrontMiddleLine(resourceCard);
            System.out.print("  ");
        }
        System.out.println();
        for(PlayableCard resourceCard : visiblePlayableCards) {
            cardView.displayCardFrontBottomLine(resourceCard);
            System.out.print("  ");
        }
        System.out.println();
        showMessage("[   0   ]  [   1   ]\n");

        showMessage("\nThese are the visible gold cards:\n");
        for(GoldCard goldCard : visibleGoldCards) {
            cardView.displayCardFrontTopLine(goldCard);
            System.out.print("  ");
        }
        System.out.println();
        for(GoldCard goldCard : visibleGoldCards) {
            cardView.displayCardFrontMiddleLine(goldCard);
            System.out.print("  ");
        }
        System.out.println();
        for(GoldCard goldCard : visibleGoldCards) {
            cardView.displayCardFrontBottomLine(goldCard);
            System.out.print("  ");
        }
        System.out.println();
        showMessage("[   2   ]  [   3   ]\n");

        showMessage("\nThese are the covered cards:\n");
        cardView.displayCardBackTopLine(getCoveredResourceCard());
        System.out.print("  ");
        cardView.displayCardBackTopLine(getCoveredGoldCard());
        System.out.println();
        cardView.displayCardBackMiddleLine(getCoveredResourceCard());
        System.out.print("  ");
        cardView.displayCardBackMiddleLine(getCoveredGoldCard());
        System.out.println();
        cardView.displayCardBackBottomLine(getCoveredResourceCard());
        System.out.print("  ");
        cardView.displayCardBackBottomLine(getCoveredGoldCard());
        System.out.println();
        showMessage("[   4   ]  [   5   ]\n");
    }

    @Override
    public void showStarterCard(StarterCard starterCard) {
        CardView cardView = new CardView();

        showMessage("\nThis is your starter card:\n");

        cardView.displayCardFrontTopLine(starterCard);
        System.out.print("  ");
        cardView.displayCardBackTopLine(starterCard);
        System.out.print("\n");

        cardView.displayCardFrontMiddleLine(starterCard);
        System.out.print("  ");
        cardView.displayCardBackMiddleLine(starterCard);
        System.out.print("\n");

        cardView.displayCardFrontBottomLine(starterCard);
        System.out.print("  ");
        cardView.displayCardBackBottomLine(starterCard);
        System.out.print("\n");

        showMessage("[   0   ]  [   1   ]\n");
    }

    public void starterCardSideChoice(StarterCard starterCard) throws IOException {
        int sideChoice = askForSideChoice();

        if (sideChoice == 1) starterCard.setPlayedBack(true);

        client.sendMessage(new Message("StarterCard", starterCard));
    }

    public int askForSideChoice() {
        showMessage("Please enter '0' for the front side, '1' for the back side: ");
        int sideChoice = readNumber();

        while (sideChoice!=0 && sideChoice!=1){
            System.err.println("Please enter '0' or '1': ");
            sideChoice = readNumber();
        }

        return sideChoice;
    }

    @Override
    public void showCommonObjectives(List<ObjectiveCard> objectiveCards) {
        showMessage("\nThese are the common objectives:\n");
        showObjectives(objectiveCards);
    }

    public void showSecretObjectives(List<ObjectiveCard> objectiveCards) {
        showMessage("\nThese are your secret objective cards options:\n");
        showObjectives(objectiveCards);
        showMessage("[   0   ]  [   1   ]\n");
    }

    private void showObjectives(List<ObjectiveCard> objectiveCards) {
        ObjectiveCardView objectiveCardView = new ObjectiveCardView();

        for (ObjectiveCard objectiveCard : objectiveCards) {
            if (objectiveCard instanceof ResourceObjectiveCard) {
                objectiveCardView.displayObjectiveCardTopLine((ResourceObjectiveCard) objectiveCard);
            } else if (objectiveCard instanceof SpecialObjectiveCard) {
                objectiveCardView.displayObjectiveCardTopLine((SpecialObjectiveCard) objectiveCard);
            } else if (objectiveCard instanceof PatternObjectiveCard) {
                objectiveCardView.displayObjectiveCardTopLine((PatternObjectiveCard) objectiveCard);
            } else {
                System.out.println("Objective card error");
            }
            System.out.print("  ");
        }
        System.out.print("\n");

        for (ObjectiveCard objectiveCard : objectiveCards) {
            if (objectiveCard instanceof ResourceObjectiveCard) {
                objectiveCardView.displayObjectiveCardMiddleLine((ResourceObjectiveCard) objectiveCard);
            } else if (objectiveCard instanceof SpecialObjectiveCard) {
                objectiveCardView.displayObjectiveCardMiddleLine((SpecialObjectiveCard) objectiveCard);
            } else if (objectiveCard instanceof PatternObjectiveCard) {
                objectiveCardView.displayObjectiveCardMiddleLine((PatternObjectiveCard) objectiveCard);
            } else {
                System.out.println("Objective card error");
            }
            System.out.print("  ");
        }
        System.out.print("\n");

        for (ObjectiveCard objectiveCard : objectiveCards) {
            if (objectiveCard instanceof ResourceObjectiveCard) {
                objectiveCardView.displayObjectiveCardBottomLine((ResourceObjectiveCard) objectiveCard);
            } else if (objectiveCard instanceof SpecialObjectiveCard) {
                objectiveCardView.displayObjectiveCardBottomLine((SpecialObjectiveCard) objectiveCard);
            } else if (objectiveCard instanceof PatternObjectiveCard) {
                objectiveCardView.displayObjectiveCardBottomLine((PatternObjectiveCard) objectiveCard);
            } else {
                System.out.println("Objective card error");
            }
            System.out.print("  ");
        }
        System.out.print("\n");
    }

    public void secretObjectiveCardChoice(List<ObjectiveCard> objectiveCards) throws IOException {
        int objectiveCardChoice = askForObjectiveCardNumber();

        client.sendMessage(new Message("ObjectiveCardChoice", objectiveCards.get(objectiveCardChoice)));
    }

    public int askForObjectiveCardNumber() {
        showMessage("Please enter '0' or '1' to choose your secret objective card: ");
        int objectiveCardChoice = readNumber();

        while (objectiveCardChoice!=0 && objectiveCardChoice!=1) {
            System.err.println("Please enter '0' or '1': ");
            objectiveCardChoice = readNumber();
        }

        return objectiveCardChoice;
    }

    public void showMyHand() {
        showMessage("\nHere is your hand:\n");
        showPlayerHand(getHandsMap().get(client.getNickname()));
    }

    public void showMyPlayArea() {
        showMessage("\nHere is your play area:\n");
        showOtherPlayerPlayArea(getPlayAreasMap().get(client.getNickname()));
    }

    public void showOthersHandsAndPlayAreas() {
        for (String nickname : getHandsMap().keySet()) {
            if (!(nickname.equals(client.getNickname()))) {
                showMessage("\nHere is " + nickname + "'s hand:\n");
                showPlayerHand(getHandsMap().get(nickname));

                showMessage("\nHere is " + nickname + "'s play area:\n");
                showOtherPlayerPlayArea(getPlayAreasMap().get(nickname));
            }
        }
    }

    public void showPlayerPlayArea(Card[][] playArea) {
        PlayAreaView playAreaView = new PlayAreaView();
        playAreaView.displayMyPlayArea(playArea);
    }

    public void showOtherPlayerPlayArea(Card[][] playArea) {
        PlayAreaView playAreaView = new PlayAreaView();
        playAreaView.displayOtherPlayArea(playArea);
    }

    public void showPlayerHand(List<PlayableCard> playerHand) {
        HandView handView = new HandView();
        handView.displayHand(playerHand);
    }

    public void chooseCardToPlay() throws IOException {
        showMyHand();
        showMessage("[   0   ]  [   1   ]  [   2   ]\n");

        showMessage("\nHere is your play area:\n");
        showPlayerPlayArea(getPlayAreasMap().get(client.getNickname()));

        int cardToPlayChoice = askForCardToPlayChoice();
        int sideChoide = askForSideChoice();
        Coordinates coordinates = askForCoordinatesChoice();

        List<PlayableCard> hand = getHandsMap().get(client.getNickname());
        PlayableCard chosenCard = hand.get(cardToPlayChoice);
        if (sideChoide == 1) chosenCard.setPlayedBack(true);

        client.sendMessage(new Message("CardToPlayChoice", chosenCard, coordinates));
    }

    public void invalidMove() throws IOException {
        showMessage("\nYou can't place that card in the selected position. Make another choice.\n");
        chooseCardToPlay();
    }

    public int askForCardToPlayChoice() {
        showMessage("\nPlease enter '0', '1' or '2' to choose the card to play: ");
        int cardToPlayChoice = readNumber();

        while (cardToPlayChoice!=0 && cardToPlayChoice!=1 && cardToPlayChoice!=2) {
            System.err.print("Please enter '0', '1' or '2': ");
            cardToPlayChoice = readNumber();
        }

        return cardToPlayChoice;
    }

    public Coordinates askForCoordinatesChoice() {
        showMessage("Please enter the x coordinate where to place the card: ");
        int xCoordinate = readNumber();
        showMessage("Please enter the y coordinate where to place the card: ");
        int yCoordinate = readNumber();

        return new Coordinates(xCoordinate, yCoordinate);
    }

    public void chooseCardToDraw() throws IOException {
        showMessage("\nPlease enter '0', '1', '2', '3', '4' or '5' to choose the card to draw: ");
        int cardToDrawChoice = readNumber();

        while (cardToDrawChoice!=0 && cardToDrawChoice!=1 && cardToDrawChoice!=2 && cardToDrawChoice!=3 && cardToDrawChoice!=4 && cardToDrawChoice!=5) {
            System.err.print("Please enter '0', '1', '2', '3', '4' or '5': ");
            cardToDrawChoice = readNumber();
        }

        client.sendMessage(new Message("CardToDrawChoice", cardToDrawChoice));

    }

}
