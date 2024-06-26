package org.example.myversion.client.view;

import javafx.stage.Stage;
import org.example.myversion.client.Client;
import org.example.myversion.client.CodexNaturalis;
import org.example.myversion.messages.Message;
import org.example.myversion.server.model.Coordinates;
import org.example.myversion.server.model.decks.cards.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

/**
 * The CLIView class represents the Command Line Interface view of the game.
 * It interacts with the user through the console, displaying game information and reading user input.
 */
public class CLIView extends GameView {
    private Client client;

    private static final String RESET_COLOR = "\u001B[0m";
    private static final String YELLOW_COLOR = "\u001B[93m";

    /**
     * Constructs a new CLIView instance.
     * Prompts the user for the server IP address and protocol type.
     */
    public CLIView() {
        String ip = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Insert server IP: ");
        do {
            try {
                ip = reader.readLine();
            } catch (IOException e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
            if (!CodexNaturalis.isIpValid(ip)) {
                System.err.print("Insert a valid server IP: ");
            }
        } while (!CodexNaturalis.isIpValid(ip));

        String protocolType = null;
        showMessage("Choose a protocol (tcp '0' or rmi '1'): ");
        while (protocolType == null) {
            int protocolChoice = readNumber();
            if (protocolChoice == 0) protocolType = "tcp";
            else if (protocolChoice == 1) protocolType = "rmi";
            else System.err.print("Choose '0' or '1': ");
        }

        CodexNaturalis.setParameters(ip, protocolType, this);
    }

    @Override
    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public void start(Stage stage) throws Exception {
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

    /**
     * Starts the CLI view, prompting the user for login.
     *
     * @throws IOException if an I/O error occurs
     */
    public void startView() throws IOException {
        clientLogin();
    }

    /**
     * Prompts the user for login and sends a login message to the server.
     *
     * @throws IOException if an I/O error occurs
     */
    public void clientLogin() throws IOException {
        showGameTitle();
        String nickname = showLogin();
        client.sendMessage(new Message("Login", nickname));
    }

    @Override
    public void showTakenUsername() throws IOException {
        System.err.print("Taken username, insert your nickname: ");
        client.sendMessage(new Message("Login", readNickname()));
    }

    /**
     * Displays the game title.
     */
    private void showGameTitle() {
        String gameTitle = """

                 _____           _           _   _       _                   _ _    \s
                /  __ \\         | |         | \\ | |     | |                 | (_)   \s
                | /  \\/ ___   __| | _____  _|  \\| | __ _| |_ _   _ _ __ __ _| |_ ___\s
                | |    / _ \\ / _` |/ _ \\ \\/ / . ` |/ _` | __| | | | '__/ _` | | / __|
                | \\__/\\ (_) | (_| |  __/>  <| |\\  | (_| | |_| |_| | | | (_| | | \\__ \\
                 \\____/\\___/ \\__,_|\\___/_/\\_\\_| \\_/\\__,_|\\__|\\__,_|_|  \\__,_|_|_|___/

                """;
        System.out.print("Welcome to...\n" + YELLOW_COLOR + gameTitle + RESET_COLOR);
    }

    private String showLogin() {
        showMessage("Please, insert your nickname: ");
        return readNickname();
    }

    /**
     * Prompts the user for a nickname.
     *
     * @return the entered nickname
     */
    private String readNickname() {
        String nickname = null;

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            nickname = in.readLine();
            client.setNickname(nickname);
        } catch (IOException e) {
            System.err.println("An error occurred while reading the nickname.");
        }

        return nickname;
    }

    /**
     * Displays a message indicating the game has already started.
     */
    public void showGameAlreadyStartedMessage() {
        showMessage("Game already started! You have to wait for the next one :(\n");
        client.stop();
    }

    /**
     * Prompts the user to choose the number of players.
     *
     * @throws IOException if an I/O error occurs
     */
    public void playersNumberChoice() throws IOException {
        int playersNumber = askForPlayersNumber();
        client.sendMessage(new Message("NumberOfPlayers", playersNumber));
    }

    /**
     * Displays a message indicating an invalid player number choice and prompts the user to try again.
     *
     * @throws IOException if an I/O error occurs
     */
    public void invalidPlayersNumberChoice() throws IOException {
        showMessage("Invalid player number! Please try again: ");
        int playersNumber = askForPlayersNumber();
        client.sendMessage(new Message("NumberOfPlayers", playersNumber));
    }

    /**
     * Prompts the user to enter the number of players.
     *
     * @return the number of players entered
     */
    private int askForPlayersNumber() {
        showMessage("Please enter the number of players: ");
        int playersNumber = 0;

        playersNumber = readNumber();

        while (playersNumber < 2 || playersNumber > 4) {
            System.err.print("Please enter a number between 2 and 4: ");
            playersNumber = readNumber();
        }

        return playersNumber;
    }

    /**
     * Reads an integer number from the console.
     *
     * @return the number read from the console
     */
    private int readNumber() {
        int number = 0;

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            number = Integer.parseInt(in.readLine());
        } catch (IOException e) {
            System.err.println("An error occurred while reading the number.");
        }

        return number;
    }

    /**
     * Displays a message indicating that the client is waiting for other players to join.
     */
    public void waitForOtherPlayers() {
        showMessage("Waiting for other players to join...\n");
    }

    /**
     * Displays the visible cards.
     */
    public void showVisibleCards() {

        List<PlayableCard> visiblePlayableCards = getVisibleResourceCards();
        List<GoldCard> visibleGoldCards = getVisibleGoldCards();

        showMessage("\nThese are the visible resource cards:\n");
        for (PlayableCard resourceCard : visiblePlayableCards) {
            CardView.displayCardFrontTopLine(resourceCard);
            System.out.print("  ");
        }
        System.out.println();
        for (PlayableCard resourceCard : visiblePlayableCards) {
            CardView.displayCardFrontMiddleLine(resourceCard);
            System.out.print("  ");
        }
        System.out.println();
        for (PlayableCard resourceCard : visiblePlayableCards) {
            CardView.displayCardFrontBottomLine(resourceCard);
            System.out.print("  ");
        }
        System.out.println();
        showMessage("[   0   ]  [   1   ]\n");

        showMessage("\nThese are the visible gold cards:\n");
        for (GoldCard goldCard : visibleGoldCards) {
            CardView.displayCardFrontTopLine(goldCard);
            System.out.print("  ");
        }
        System.out.println();
        for (GoldCard goldCard : visibleGoldCards) {
            CardView.displayCardFrontMiddleLine(goldCard);
            System.out.print("  ");
        }
        System.out.println();
        for (GoldCard goldCard : visibleGoldCards) {
            CardView.displayCardFrontBottomLine(goldCard);
            System.out.print("  ");
        }
        System.out.println();
        showMessage("[   2   ]  [   3   ]\n");

        showMessage("\nThese are the covered cards:\n");
        CardView.displayCardBackTopLine(getCoveredResourceCard());
        System.out.print("  ");
        CardView.displayCardBackTopLine(getCoveredGoldCard());
        System.out.println();
        CardView.displayCardBackMiddleLine(getCoveredResourceCard());
        System.out.print("  ");
        CardView.displayCardBackMiddleLine(getCoveredGoldCard());
        System.out.println();
        CardView.displayCardBackBottomLine(getCoveredResourceCard());
        System.out.print("  ");
        CardView.displayCardBackBottomLine(getCoveredGoldCard());
        System.out.println();
        showMessage("[   4   ]  [   5   ]\n");
    }

    @Override
    public void showStarterCard(StarterCard starterCard) {
        showMessage("\nThis is your starter card:\n");

        CardView.displayCardFrontTopLine(starterCard);
        System.out.print("  ");
        CardView.displayCardBackTopLine(starterCard);
        System.out.print("\n");

        CardView.displayCardFrontMiddleLine(starterCard);
        System.out.print("  ");
        CardView.displayCardBackMiddleLine(starterCard);
        System.out.print("\n");

        CardView.displayCardFrontBottomLine(starterCard);
        System.out.print("  ");
        CardView.displayCardBackBottomLine(starterCard);
        System.out.print("\n");

        showMessage("[   0   ]  [   1   ]\n");
    }

    /**
     * Prompts the user to choose the side of the starter card and sends the choice to the server.
     *
     * @param starterCard the starter card to choose the side for
     * @throws IOException if an I/O error occurs
     */
    public void starterCardSideChoice(StarterCard starterCard) throws IOException {
        int sideChoice = askForSideChoice();

        if (sideChoice == 1) starterCard.setPlayedBack(true);

        client.sendMessage(new Message("StarterCard", starterCard));
    }

    /**
     * Prompts the user to choose the side of a card.
     *
     * @return the chosen side (0 for front, 1 for back)
     */
    public int askForSideChoice() {
        showMessage("Please enter '0' for the front side, '1' for the back side: ");
        int sideChoice = readNumber();

        while (sideChoice != 0 && sideChoice != 1) {
            System.err.print("Please enter '0' or '1': ");
            sideChoice = readNumber();
        }

        return sideChoice;
    }

    @Override
    public void showCommonObjectives(List<ObjectiveCard> objectiveCards) {
        showMessage("\nThese are the common objectives:\n");
        showObjectives(objectiveCards);
    }

    /**
     * Displays the user's secret objective cards.
     *
     * @param objectiveCards the list of secret objective cards
     */
    public void showSecretObjectives(List<ObjectiveCard> objectiveCards) {
        showMessage("\nThese are your secret objective cards options:\n");
        showObjectives(objectiveCards);
        showMessage("[   0   ]  [   1   ]\n");
    }

    /**
     * Displays a list of objective cards.
     *
     * @param objectiveCards the list of objective cards to display
     */
    private void showObjectives(List<ObjectiveCard> objectiveCards) {
        ObjectiveCardView objectiveCardView = new ObjectiveCardView();

        for (ObjectiveCard objectiveCard : objectiveCards) {
            switch (objectiveCard) {
                case ResourceObjectiveCard resourceObjectiveCard ->
                        objectiveCardView.displayObjectiveCardTopLine(resourceObjectiveCard);
                case SpecialObjectiveCard specialObjectiveCard ->
                        objectiveCardView.displayObjectiveCardTopLine(specialObjectiveCard);
                case PatternObjectiveCard patternObjectiveCard ->
                        objectiveCardView.displayObjectiveCardTopLine(patternObjectiveCard);
                case null, default -> System.out.println("Objective card error.");
            }
            System.out.print("  ");
        }
        System.out.print("\n");

        for (ObjectiveCard objectiveCard : objectiveCards) {
            switch (objectiveCard) {
                case ResourceObjectiveCard resourceObjectiveCard ->
                        objectiveCardView.displayObjectiveCardMiddleLine(resourceObjectiveCard);
                case SpecialObjectiveCard specialObjectiveCard ->
                        objectiveCardView.displayObjectiveCardMiddleLine(specialObjectiveCard);
                case PatternObjectiveCard patternObjectiveCard ->
                        objectiveCardView.displayObjectiveCardMiddleLine(patternObjectiveCard);
                case null, default -> System.out.println("Objective card error.");
            }
            System.out.print("  ");
        }
        System.out.print("\n");

        for (ObjectiveCard objectiveCard : objectiveCards) {
            switch (objectiveCard) {
                case ResourceObjectiveCard resourceObjectiveCard ->
                        objectiveCardView.displayObjectiveCardBottomLine(resourceObjectiveCard);
                case SpecialObjectiveCard specialObjectiveCard ->
                        objectiveCardView.displayObjectiveCardBottomLine(specialObjectiveCard);
                case PatternObjectiveCard patternObjectiveCard ->
                        objectiveCardView.displayObjectiveCardBottomLine(patternObjectiveCard);
                case null, default -> System.out.println("Objective card error.");
            }
            System.out.print("  ");
        }
        System.out.print("\n");
    }

    /**
     * Prompts the user to choose a secret objective card and sends the choice to the server.
     *
     * @param objectiveCards the list of secret objective cards
     * @throws IOException if an I/O error occurs
     */
    public void secretObjectiveCardChoice(List<ObjectiveCard> objectiveCards) throws IOException {
        int objectiveCardChoice = askForObjectiveCardNumber();

        client.sendMessage(new Message("ObjectiveCardChoice", objectiveCards.get(objectiveCardChoice)));
    }

    /**
     * Prompts the user to choose a secret objective card.
     *
     * @return the chosen objective card number (0 or 1)
     */
    private int askForObjectiveCardNumber() {
        showMessage("Please enter '0' or '1' to choose your secret objective card: ");
        int objectiveCardChoice = readNumber();

        while (objectiveCardChoice != 0 && objectiveCardChoice != 1) {
            System.err.print("Please enter '0' or '1': ");
            objectiveCardChoice = readNumber();
        }

        return objectiveCardChoice;
    }

    /**
     * Displays the user's hand of cards.
     */
    public void showMyHand() {
        showMessage("\nHere is your hand:\n");
        showPlayerHand(getHandsMap().get(client.getNickname()));
    }

    /**
     * Displays the user's play area.
     */
    public void showMyPlayArea() {
        showMessage("\nHere is your play area:\n");
        showOtherPlayerPlayArea(getPlayAreasMap().get(client.getNickname()));
    }

    /**
     * Displays the hands and play areas of other players.
     */
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

    /**
     * Displays the play area of a player.
     *
     * @param playArea the play area to display
     */
    public void showPlayerPlayArea(Card[][] playArea) {
        PlayAreaView.displayMyPlayArea(playArea);
    }

    /**
     * Displays the play area of another player.
     *
     * @param playArea the play area to display
     */
    public void showOtherPlayerPlayArea(Card[][] playArea) {
        PlayAreaView.displayOtherPlayArea(playArea);
    }

    /**
     * Displays the hand of a player.
     *
     * @param playerHand the hand of cards to display
     */
    public void showPlayerHand(List<PlayableCard> playerHand) {
        HandView handView = new HandView();
        handView.displayHand(playerHand);
    }

    /**
     * Prompts the user to choose a card to play and sends the choice to the server.
     *
     * @throws IOException if an I/O error occurs
     */
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

        if (chosenCard instanceof GoldCard)
            client.sendMessage(new Message("CardToPlayChoice", (GoldCard) chosenCard, coordinates));
        else
            client.sendMessage(new Message("CardToPlayChoice", chosenCard, coordinates));


    }

    /**
     * Displays a message indicating an invalid move and prompts the user to make another choice.
     *
     * @throws IOException if an I/O error occurs
     */
    public void invalidMove() throws IOException {
        showMessage("\nYou can't place that card in the selected position. Make another choice.\n");
        chooseCardToPlay();
    }

    @Override
    public void showUpdatedPlayArea(String nickname, Card[][] playArea) {
        if (nickname.equals(client.getNickname()))
            showMessage("\nHere is your updated play area:\n");
        else showMessage("\nHere is " + nickname + "'s updated play area:\n");

        PlayAreaView.displayOtherPlayArea(playArea);
    }

    /**
     * Prompts the user to choose a card to play.
     *
     * @return the chosen card number (0, 1, or 2)
     */
    private int askForCardToPlayChoice() {
        showMessage("\nPlease enter '0', '1' or '2' to choose the card to play: ");
        int cardToPlayChoice = readNumber();

        while (cardToPlayChoice != 0 && cardToPlayChoice != 1 && cardToPlayChoice != 2) {
            System.err.print("Please enter '0', '1' or '2': ");
            cardToPlayChoice = readNumber();
        }

        return cardToPlayChoice;
    }

    /**
     * Prompts the user to choose coordinates for placing a card.
     *
     * @return the chosen coordinates
     */
    private Coordinates askForCoordinatesChoice() {
        showMessage("Please enter the x coordinate where to place the card: ");
        int xCoordinate = readNumber();
        showMessage("Please enter the y coordinate where to place the card: ");
        int yCoordinate = readNumber();

        return new Coordinates(xCoordinate, yCoordinate);
    }

    /**
     * Prompts the user to choose a card to draw and sends the choice to the server.
     *
     * @throws IOException if an I/O error occurs
     */
    public void chooseCardToDraw() throws IOException {
        showMessage("\nPlease enter '0', '1', '2', '3', '4' or '5' to choose the card to draw: ");
        int cardToDrawChoice = readNumber();

        while (cardToDrawChoice != 0 && cardToDrawChoice != 1 && cardToDrawChoice != 2 && cardToDrawChoice != 3 && cardToDrawChoice != 4 && cardToDrawChoice != 5) {
            System.err.print("Please enter '0', '1', '2', '3', '4' or '5': ");
            cardToDrawChoice = readNumber();
        }

        client.sendMessage(new Message("CardToDrawChoice", cardToDrawChoice));

    }

    @Override
    public void showUpdatedHand(String nickname) {
        if (nickname.equals(client.getNickname()))
            showMessage("\nHere is your updated hand:\n");
        else showMessage("\nHere is " + nickname + "'s updated hand:\n");

        List<PlayableCard> hand = getHandsMap().get(nickname);

        HandView handView = new HandView();
        handView.displayHand(hand);
    }

    @Override
    public void showScores(Map<String, Integer> scores) {
        for (String nickname : scores.keySet()) {
            System.out.println(nickname + ": " + scores.get(nickname));
        }
    }

    @Override
    public void showEndGame(String winner) {
        showMessage("\nThe game is over!\n");
        showMessage("\nThe winner is: " + winner + "!");
    }

}
