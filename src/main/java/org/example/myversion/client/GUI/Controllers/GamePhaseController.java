package org.example.myversion.client.GUI.Controllers;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import org.example.myversion.server.model.decks.cards.GoldCard;
import org.example.myversion.server.model.decks.cards.ObjectiveCard;
import org.example.myversion.server.model.decks.cards.PlayableCard;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import org.example.myversion.server.model.decks.cards.StarterCard;

import java.io.File;
import java.net.URL;
import java.util.*;

public class GamePhaseController extends GUIController {

    //public GUI gui;
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


    public GamePhaseController() {
        super();
    }

    public void initialize(ObjectiveCard personalObjectiveCard, List<ObjectiveCard> commonObjectiveCards, List<GoldCard> goldCards, List<PlayableCard> PlayableCards) {
        Platform.runLater(()->{
            try {
                URL fxmlLocation = getClass().getResource("/org/example/myversion/FXML/GamePhase.fxml");
                FXMLLoader loader = new FXMLLoader(fxmlLocation);
                loader.setController(this);
                Parent root = loader.load();

                initializeGameScene(personalObjectiveCard, commonObjectiveCards, goldCards, PlayableCards);

                gui.getStage().setTitle("Codex Naturalis");
                gui.getStage().setScene(new Scene(root));
                gui.getStage().show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public void initializeGameScene(ObjectiveCard personalObjectiveCard, List<ObjectiveCard> commonObjectiveCards, List<GoldCard> goldCards, List<PlayableCard> PlayableCards) {
        //Common Objective
        Image CommonObjective1 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + commonObjectiveCards.get(0).getId() + ".png"));
        co01.setImage(CommonObjective1);
        Image CommonObjective2 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + commonObjectiveCards.get(1).getId() + ".png"));
        co02.setImage(CommonObjective2);

        //Personal Objective
        Image PersonalObjective1 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + personalObjectiveCard.getId() + ".png"));
        co03.setImage(PersonalObjective1);


        //Gold cards
        Image deckGold = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_back/back" + gui.getCoveredGoldCard().getId() + ".png"));
        deckG.setImage(deckGold);
        Image visibleGold1 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + goldCards.get(0).getId() + ".png"));
        gc01.setImage(visibleGold1);
        Image visibleGold2 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + goldCards.get(1).getId() + ".png"));
        gc02.setImage(visibleGold2);

        //Playable cards
        Image deckResource = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_back/back" + gui.getCoveredResourceCard().getId() + ".png"));
        deckP.setImage(deckResource);
        Image visibleResource1 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + PlayableCards.get(0).getId() + ".png"));
        pc01.setImage(visibleResource1);
        Image visibleResource2 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + PlayableCards.get(1).getId() + ".png"));
        pc02.setImage(visibleResource2);

        updatePlayerHand();
        nicknameP1.setText(gui.getClient().getNickname()); //farlo per gli altri giocatori


    }

    private List<PlayableCard> getMyHand() {
        return myHand;
    }

    //StarterCard
    public void putStarterCard(StarterCard starterCard){
        Platform.runLater(()->{

        this.starterCard = starterCard;
        ImageView imgStarterCard = new ImageView();

        if (starterCard.isPlayedBack()){
            Image imgBack = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_back/back" + starterCard.getId() + ".png"));
            imgStarterCard.setImage(imgBack);
        } else {
            Image imgFront = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + starterCard.getId() + ".png"));
            imgStarterCard.setImage(imgFront);
        }

        imgStarterCard.setPreserveRatio(true);
        imgStarterCard.setFitWidth(xCard);
        imgStarterCard.setFitHeight(yCard);

        gridPL.setMinSize((81*xCard),(81*yCard));
        gridPL.setMaxSize((81*xCard), (81*yCard));

        for(int i=0; i<81; i++){
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100/81);
            gridPL.getColumnConstraints().add(column);

            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100/81);
            gridPL.getRowConstraints().add(row);
        }

        //initialize matrix
        for(int i=0; i<81; i++){
            for(int j=0; j<81; j++){
                ImageView img = new ImageView();
                img.setPreserveRatio(true);
                img.setFitWidth(xCard);
                img.setFitHeight(yCard);

                img.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if(!selectedCard.isPlayedBack()) {
                            Image sC = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + selectedCard.getId() + ".png"));
                            img.setImage(sC);
                        } else {
                            Image sC = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_back/back" + selectedCard.getId() + ".png"));
                            img.setImage(sC);
                        }
                    }
                });

                gridPL.add(img, i, j);
            }
        }

        gridPL.add(imgStarterCard, 40, 40);

            Platform.runLater(() -> {
                myPlayArea.layout();
                double gridWidth = gridPL.getWidth();
                double gridHeight = gridPL.getHeight();
                double viewportWidth = myPlayArea.getViewportBounds().getWidth();
                double viewportHeight = myPlayArea.getViewportBounds().getHeight();

                // Adjust offsets
                double horizontalOffset = xCard * (4);
                double verticalOffset = yCard *(4);

                double hValue = (40 * xCard - viewportWidth / 2 + horizontalOffset) / (gridWidth - viewportWidth);
                double vValue = (40 * yCard - viewportHeight / 2 + verticalOffset) / (gridHeight - viewportHeight);

                myPlayArea.setHvalue(hValue);
                myPlayArea.setVvalue(vValue);
            });

        });



    }
    //player
    public void playerHandChanged(List<PlayableCard> hand){
        Platform.runLater(()->{
            myHand.clear();
            myHand.addAll(hand);
            clearPlayerHand();
            updatePlayerHand();
        });
    }

        public void clearPlayerHand(){
            Platform.runLater(() -> {
                // Cancella completamente l'immagine della carta giocata
                if (selectedCard != null) {
                    if (selectedCard == myHand.get(0)) {
                        cdf1.setImage(null);  // Cancella il fronte della carta giocata
                        cdb1.setImage(null);  // Cancella il retro della carta giocata
                    } else if (selectedCard == myHand.get(1)) {
                        cardFront2.setImage(null);
                        cardBack2.setImage(null);
                    } else if (selectedCard == myHand.get(2)) {
                        cardFront3.setImage(null);
                        cardBack3.setImage(null);
                    }
                }
            });
        }




    public void updatePlayerHand(){
        Platform.runLater(()->{
            //Hand cards
            Image cardFront1 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + myHand.get(0).getId() + ".png"));
            cdf1.setImage(cardFront1);

            cdf1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    selectedCard = myHand.get(0);
                    selectedCard.setPlayedBack(false);
                    myHand.remove(selectedCard);

                    List<PlayableCard> newHand = new ArrayList<>(myHand);

                    playerHandChanged(newHand);

                    ImageView k = cardBack2;
                    ImageView h = cardBack3;
                }
            });


            Image cdf2 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + myHand.get(1).getId() + ".png"));
            cardFront2.setImage(cdf2);
            cardFront2.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    selectedCard = myHand.get(1);
                    selectedCard.setPlayedBack(false);
                    myHand.remove(selectedCard);
                    List<PlayableCard> newHand = new ArrayList<>(myHand);
                    playerHandChanged(newHand);
                }
            });
            Image cdf3 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + myHand.get(2).getId() + ".png"));
            cardFront3.setImage(cdf3);
            cardFront3.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    selectedCard = myHand.get(2);
                    selectedCard.setPlayedBack(false);
                    myHand.remove(selectedCard);
                    List<PlayableCard> newHand = new ArrayList<>(myHand);
                    playerHandChanged(newHand);
                }
            });

            Image cardBack1 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_back/back" + myHand.get(0).getId() + ".png"));
            cdb1.setImage(cardBack1);
            cdb1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    selectedCard = myHand.get(0);
                    selectedCard.setPlayedBack(true);
                    myHand.remove(selectedCard);
                    List<PlayableCard> newHand = new ArrayList<>(myHand);
                    playerHandChanged(newHand);
                }
            });
            Image cdb2 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_back/back" + myHand.get(1).getId() + ".png"));
            cardBack2.setImage(cdb2);
            cardBack2.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    selectedCard = myHand.get(1);
                    selectedCard.setPlayedBack(true);
                    myHand.remove(selectedCard);
                    List<PlayableCard> newHand = new ArrayList<>(myHand);
                    playerHandChanged(newHand);
                }
            });
            Image cdb3 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_back/back" + myHand.get(2).getId() + ".png"));
            cardBack3.setImage(cdb3);
            cardBack3.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    selectedCard = myHand.get(2);
                    selectedCard.setPlayedBack(true);
                    myHand.remove(selectedCard);
                    List<PlayableCard> newHand = new ArrayList<>(myHand);
                    playerHandChanged(newHand);
                }
            });
        });
    }






    //play area
    public void displayOtherPlayArea(){
        //player2
        nicknameP2.setOnMouseClicked (event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/myversion/FXML/OtherPlayArea.fxml"));
                Parent root = loader.load();

                // Crea una nuova scena con il root caricato
                Scene newScene = new Scene(root);

                // Imposta una nuova stage
                Stage newStage = new Stage();
                newStage.setTitle("Other Play Area");
                newStage.setScene(newScene);
                newStage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }




    //points



    //turn




}
