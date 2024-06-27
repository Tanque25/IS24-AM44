package org.example.myversion.client.GUI.Controllers;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.myversion.client.Client;
import org.example.myversion.client.GUI.GUI;
import org.example.myversion.client.GUI.GUIController;
import org.example.myversion.messages.Message;
import org.example.myversion.server.model.Coordinates;
import org.example.myversion.server.model.decks.cards.GoldCard;
import org.example.myversion.server.model.decks.cards.ObjectiveCard;
import org.example.myversion.server.model.decks.cards.PlayableCard;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import org.example.myversion.server.model.decks.cards.StarterCard;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class GamePhaseController extends GUIController {


    @FXML
    private AnchorPane mainAnchor;
    @FXML
    private GridPane gridPL;
    @FXML
    private Label nicknameP1;
    @FXML
    private Label yourPoints;
    @FXML
    private ScrollPane myPlayArea;


    @FXML
    private Label nicknameP2;
    @FXML
    private Label player2Points;
    @FXML
    private Pane player2Pane;
    @FXML
    private Pane playArea2;


    @FXML
    private Label nicknameP3;
    @FXML
    private Label player3Points;
    @FXML
    private Pane player3Pane;
    @FXML
    private Pane playArea3;


    @FXML
    private Label nicknameP4;
    @FXML
    private Label player4Points;
    @FXML
    private Pane player4Pane;
    @FXML
    private Pane playArea4;

    //hand cards
    @FXML
    private ImageView cdf1;
    @FXML
    private ImageView cardFront2;
    @FXML
    private ImageView cardFront3;

    @FXML
    private ImageView cdb1;
    @FXML
    private ImageView cardBack2;
    @FXML
    private ImageView cardBack3;

    @FXML
    private ImageView co02;
    @FXML
    private ImageView co01;

    @FXML
    private ImageView co03;
    @FXML
    private ImageView co04;

    @FXML
    private ImageView gc01;
    @FXML
    private ImageView gc02;


    @FXML
    private ImageView pc01;
    @FXML
    private ImageView pc02;

    @FXML
    private ImageView deckG;
    @FXML
    private ImageView deckP;

    //chat
    @FXML
    private Label labelMessage;
    @FXML
    private TextField messageText;
    @FXML
    private ListView chatList;

    //important events
    @FXML
    private ListView importantEventsList;
    @FXML
    private ComboBox comboBoxMessage;

    private Map<String, List<PlayableCard>> playerHand;
    private List<ObjectiveCard> personalObjectiveCards;
    private List<ObjectiveCard> commonObjectiveCards;
    private List<GoldCard> goldCards;
    private List<PlayableCard> playableCards;
    private List<PlayableCard> myHand = new ArrayList<>();
    private StarterCard starterCard;
    private int xCard = 94;
    private int yCard = 57;
    private PlayableCard selectedCard;
    private PlayableCard drawnCard;
    private Point2D selectedCell;
    private ObjectiveCard personalObjectiveCard;
    private Boolean yourTurn = false;
    private Boolean yourDraw = false;
    private boolean isPlacingCard = false;


    public GamePhaseController() {
        super();
    }

    //phase: choose a card to play from the hand
    public void activateTurn() {
        yourTurn = true;
        Platform.runLater(() -> {
            showAlert("Your turn", "Make your move");
        });
    }

    public void clickOnButton() {
        //Disattiva gli altri bottoni
        // salva coordinate
        // invia coordinate salvate e carta al server
        yourTurn = false;
        sendCoordinatesToServer();
        disableAllButtons();

    }

    private void disableAllButtons() {
        for (Node node : gridPL.getChildren()) {
            if (node instanceof Button) {
                node.setDisable(true);
            }
        }
    }

    private void saveCoordinates(Point2D cell) {
        selectedCell = cell;
    }

    private void sendCoordinatesToServer() {
        try {
            gridPL.getChildren().removeIf(node -> node instanceof Button); // Rimuovi gli altri bottoni
            if (gui.getSelectedCard() instanceof GoldCard)
                gui.getClient().sendMessage(new Message("CardToPlayChoice", (GoldCard)gui.getSelectedCard() , new Coordinates((int) selectedCell.getX(), (int) selectedCell.getY())));
            else
                gui.getClient().sendMessage(new Message("CardToPlayChoice", gui.getSelectedCard(), new Coordinates((int) selectedCell.getX(), (int) selectedCell.getY())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showPlacementOptions() {
        List<Point2D> occupiedCells = getOccupiedCells(); // Ottieni le celle occupate
        Set<Point2D> placementOptions = new HashSet<>();

        for (Point2D cell : occupiedCells) {
            // Controlla le celle diagonali
            addIfValid(placementOptions, cell.add(1, 1)); // Basso-Destra
            addIfValid(placementOptions, cell.add(-1, -1)); // Alto-Sinistra
            addIfValid(placementOptions, cell.add(-1, 1)); // Basso-Sinistra
            addIfValid(placementOptions, cell.add(1, -1)); // Alto-Destra
        }

        // Aggiungi bottoni alle celle di piazzamento
        for (Point2D option : placementOptions) {
            Button placeButton = new Button("Place");
            placeButton.setOnMouseClicked(event -> {
                saveCoordinates(option); // Salva le coordinate del bottone cliccato
                clickOnButton(); // Gestisce il click sul bottone
            });
            gridPL.add(placeButton, (int) option.getX(), (int) option.getY());
        }
    }

    private void addIfValid(Set<Point2D> options, Point2D cell) {
        if (isValidCell(cell) && !isOccupied(cell)) {
            options.add(cell);
        }
    }

    private boolean isValidCell(Point2D cell) {
        // Controlla che la cella sia all'interno dei limiti della griglia
        return cell.getX() >= 0 && cell.getX() < gridPL.getColumnCount() &&
                cell.getY() >= 0 && cell.getY() < gridPL.getRowCount();
    }

    private boolean isOccupied(Point2D cell) {
        // Controlla se la cella è occupata
        for (Node node : gridPL.getChildren()) {
            if (GridPane.getColumnIndex(node) == (int) cell.getX() &&
                    GridPane.getRowIndex(node) == (int) cell.getY()) {
                return true;
            }
        }
        return false;
    }

    private List<Point2D> getOccupiedCells() {
        List<Point2D> occupiedCells = new ArrayList<>();
        for (Node node : gridPL.getChildren()) {
            Integer col = GridPane.getColumnIndex(node);
            Integer row = GridPane.getRowIndex(node);
            if (col != null && row != null) {
                occupiedCells.add(new Point2D(col, row));
            }
        }
        return occupiedCells;
    }

    public void invalidMove() {
        yourTurn = true;
        Platform.runLater(() -> {
            showAlert("Invalid move", "Please make another move");
        });
    }

    public void updateScene() {
        addCardToPlayArea();
        Platform.runLater(() -> {

            try {
                URL fxmlLocation = getClass().getResource("/org/example/myversion/FXML/GamePhase.fxml");
                FXMLLoader loader = new FXMLLoader(fxmlLocation);
                loader.setController(this);
                Parent root = loader.load();

                // Aggiungiamo la carta alle coordinate che ci siamo salvati nel gridpane

                initializeGameScene(personalObjectiveCard, commonObjectiveCards, goldCards, playableCards);

                gui.getStage().setTitle("Codex Naturalis");
                gui.getStage().setScene(new Scene(root));
                gui.getStage().show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void drawPhase() {
        yourDraw = true;
    }

    public void clickOnCardToDraw() {
        if (yourDraw) {
            // Quando clicchi su una carta aggiorni myhand
            clickOnCardToD();
            // Mandi al server cosa hai pescato
            try {
                gui.getClient().sendMessage(new Message("CardToDrawChoice", drawnCard));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Platform.runLater(() -> {
                try {
                    URL fxmlLocation = getClass().getResource("/org/example/myversion/FXML/GamePhase.fxml");
                    FXMLLoader loader = new FXMLLoader(fxmlLocation);
                    loader.setController(this);
                    Parent root = loader.load();

                    initializeGameScene(personalObjectiveCard, commonObjectiveCards, goldCards, playableCards);

                    gui.getStage().setTitle("Codex Naturalis");
                    gui.getStage().setScene(new Scene(root));
                    gui.getStage().show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void clickOnCardToD() {
        gc01.setOnMouseClicked(event -> {
            drawnCard = goldCards.get(0);
            myHand.add(drawnCard);
        });

        gc02.setOnMouseClicked(event -> {
            drawnCard = goldCards.get(1);
            myHand.add(drawnCard);
        });
        pc01.setOnMouseClicked(event -> {
            drawnCard = playableCards.get(0);
            myHand.add(drawnCard);
        });
        pc02.setOnMouseClicked(event -> {
            drawnCard = playableCards.get(1);
            myHand.add(drawnCard);
        });
        deckG.setOnMouseClicked(event -> {
            drawnCard = gui.getCoveredGoldCard();
            myHand.add(drawnCard);
        });
        deckP.setOnMouseClicked(event -> {
            drawnCard = gui.getCoveredResourceCard();
            myHand.add(drawnCard);
        });
    }

    /**
     * Initialize the game scene
     *
     * @param personalObjectiveCard
     * @param commonObjectiveCards
     * @param goldCards
     * @param playableCards
     */
    public void initializeScene(ObjectiveCard personalObjectiveCard, List<ObjectiveCard> commonObjectiveCards, List<GoldCard> goldCards, List<PlayableCard> playableCards) {
        Platform.runLater(() -> {
            try {
                URL fxmlLocation = getClass().getResource("/org/example/myversion/FXML/GamePhase.fxml");
                FXMLLoader loader = new FXMLLoader(fxmlLocation);
                loader.setController(this);
                Parent root = loader.load();

                gridPL.setMinSize((81 * xCard), (81 * yCard));
                gridPL.setMaxSize((81 * xCard), (81 * yCard));

                for (int i = 0; i < 81; i++) {
                    ColumnConstraints column = new ColumnConstraints();
                    column.setPercentWidth(100 / 81);
                    gridPL.getColumnConstraints().add(column);

                    RowConstraints row = new RowConstraints();
                    row.setPercentHeight(100 / 81);
                    gridPL.getRowConstraints().add(row);
                    updatePlayerHand();

                }
                putStarterCard(gui.getStarterCard());
                initializeGameScene(personalObjectiveCard, commonObjectiveCards, goldCards, playableCards);

                this.personalObjectiveCard = personalObjectiveCard;
                gui.getStage().setTitle("Codex Naturalis");
                gui.getStage().setScene(new Scene(root));
                gui.getStage().show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public void initializeGameScene(ObjectiveCard personalObjectiveCard, List<ObjectiveCard> commonObjectiveCards, List<GoldCard> goldCards, List<PlayableCard> playableCards) {

        //Common Objective
        Image CommonObjective1 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + gui.getCommonObjectiveCards().get(0).getId() + ".png"));
        co01.setImage(CommonObjective1);
        Image CommonObjective2 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + gui.getCommonObjectiveCards().get(1).getId() + ".png"));
        co02.setImage(CommonObjective2);

        //Personal Objective
        Image PersonalObjective1 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + gui.getSecretObjectiveCard().getId() + ".png"));
        co03.setImage(PersonalObjective1);

        this.goldCards = goldCards;
        updateGoldCards();

        this.playableCards = playableCards;
        updatePlayableCards();

        updatePlayerHand();
        nicknameP1.setText(gui.getClient().getNickname());

    }

    private List<PlayableCard> getMyHand() {
        return myHand;
    }

    private void updateGoldCards() {
        //Gold cards
        Image deckGold = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_back/back" + gui.getCoveredGoldCard().getId() + ".png"));
        deckG.setImage(deckGold);

        Image visibleGold1 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + goldCards.get(0).getId() + ".png"));
        gc01.setImage(visibleGold1);

        Image visibleGold2 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + goldCards.get(1).getId() + ".png"));
        gc02.setImage(visibleGold2);

    }

    private void updatePlayableCards() {
        //Playable cards
        Image deckResource = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_back/back" + gui.getCoveredResourceCard().getId() + ".png"));
        deckP.setImage(deckResource);

        Image visibleResource1 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + playableCards.get(0).getId() + ".png"));
        pc01.setImage(visibleResource1);

        Image visibleResource2 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + playableCards.get(1).getId() + ".png"));
        pc02.setImage(visibleResource2);
    }


    //StarterCard
    public void putStarterCard(StarterCard starterCard) {

        this.starterCard = starterCard;
        ImageView imgStarterCard = new ImageView();

        if (starterCard.isPlayedBack()) {
            Image imgBack = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_back/back" + starterCard.getId() + ".png"));
            imgStarterCard.setImage(imgBack);
        } else {
            Image imgFront = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + starterCard.getId() + ".png"));
            imgStarterCard.setImage(imgFront);
        }

        imgStarterCard.setPreserveRatio(true);
        imgStarterCard.setFitWidth(xCard);
        imgStarterCard.setFitHeight(yCard);


        gridPL.add(imgStarterCard, 41, 41);


                /*myPlayArea.layout();
                double gridWidth = gridPL.getWidth();
                double gridHeight = gridPL.getHeight();
                double viewportWidth = myPlayArea.getViewportBounds().getWidth();
                double viewportHeight = myPlayArea.getViewportBounds().getHeight();

                // Adjust offsets
                double horizontalOffset = xCard * 3;
                double verticalOffset = yCard * 9 ;

                double hValue = (40 * xCard - viewportWidth / 2 + horizontalOffset) / (gridWidth - viewportWidth);
                double vValue = (40 * yCard - viewportHeight / 2 + verticalOffset) / (gridHeight - viewportHeight);

                myPlayArea.setHvalue(hValue);
                myPlayArea.setVvalue(vValue);*/


    }

    public void updatePlayerHand() {
        // Controlla il numero di carte nella mano e aggiorna le immagini di conseguenza
        myHand = gui.getHandsMap().get(gui.getClient().getNickname());
        Image cardFront1 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + myHand.get(0).getId() + ".png"));
        cdf1.setImage(cardFront1);
        Image cardBack1 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_back/back" + myHand.get(0).getId() + ".png"));
        cdb1.setImage(cardBack1);
        Image cdf2 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + myHand.get(1).getId() + ".png"));
        cardFront2.setImage(cdf2);
        Image cdb2 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_back/back" + myHand.get(1).getId() + ".png"));
        cardBack2.setImage(cdb2);
        Image cdf3 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + myHand.get(2).getId() + ".png"));
        cardFront3.setImage(cdf3);
        Image cdb3 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_back/back" + myHand.get(2).getId() + ".png"));
        cardBack3.setImage(cdb3);

        cdf1.setOnMouseClicked(event -> {
            if (yourTurn) {
                gui.setSelectedCard(myHand.get(0));
                //selectedCard = myHand.get(0);
                gui.getSelectedCard().setPlayedBack(false);
                //myHand.remove(selectedCard);
                //playerHandChanged(new ArrayList<>(myHand));
                showPlacementOptions();
            }
        });
        cdb1.setOnMouseClicked(event -> {
            if (yourTurn) {
                gui.setSelectedCard(myHand.get(0));
                //selectedCard = myHand.get(0);
                gui.getSelectedCard().setPlayedBack(true);
                //myHand.remove(selectedCard);
                //playerHandChanged(new ArrayList<>(myHand));
                showPlacementOptions();
            }
        });
        cardFront2.setOnMouseClicked(event -> {
            if (yourTurn) {
                gui.setSelectedCard(myHand.get(1));
                //selectedCard = myHand.get(1);
                gui.getSelectedCard().setPlayedBack(false);
                //myHand.remove(selectedCard);
                //playerHandChanged(new ArrayList<>(myHand));
                showPlacementOptions();
            }
        });
        cardBack2.setOnMouseClicked(event -> {
            if (yourTurn) {
                gui.setSelectedCard(myHand.get(1));
                //selectedCard = myHand.get(1);
                gui.getSelectedCard().setPlayedBack(true);
                //myHand.remove(selectedCard);
                //playerHandChanged(new ArrayList<>(myHand));
                showPlacementOptions();
            }
        });
        cardFront3.setOnMouseClicked(event -> {
            if (yourTurn) {
                gui.setSelectedCard(myHand.get(2));
                //selectedCard = myHand.get(2);
                gui.getSelectedCard().setPlayedBack(false);
                //myHand.remove(selectedCard);
                //playerHandChanged(new ArrayList<>(myHand));
                showPlacementOptions();
            }
        });
        cardBack3.setOnMouseClicked(event -> {
            if (yourTurn) {
                gui.setSelectedCard(myHand.get(2));
                //selectedCard = myHand.get(2);
                gui.getSelectedCard().setPlayedBack(true);
                //myHand.remove(selectedCard);
                //playerHandChanged(new ArrayList<>(myHand));
                showPlacementOptions();
            }
        });
    }

    public void addCardToPlayArea() {
    Platform.runLater(() -> {
            Point2D coordinates = selectedCell;

            ImageView cardImageView = new ImageView();
            Image cardImage = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + gui.getSelectedCard().getId() + ".png"));
            cardImageView.setImage(cardImage);
            cardImageView.setFitWidth(xCard);
            cardImageView.setFitHeight(yCard);

            gridPL.add(cardImageView, (int) coordinates.getX(), (int) coordinates.getY());

        });
    }
}

