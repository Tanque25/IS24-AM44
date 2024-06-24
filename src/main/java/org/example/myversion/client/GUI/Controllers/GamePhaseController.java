package org.example.myversion.client.GUI.Controllers;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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

import java.io.File;
import java.net.URL;
import java.util.*;

public class GamePhaseController extends GUIController {

    //public GUI gui;
    @FXML
    private AnchorPane mainAnchor;
    @FXML
    private Label nicknameP1;
    @FXML
    private Label yourPoints;


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

    //player
    public void playerHandChanged(List<PlayableCard> hand){
            myHand.clear();
            myHand.addAll(hand);
            updatePlayerHand();
    }

    public void updatePlayerHand(){
            //Hand cards
            Image cardFront1 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + myHand.get(0).getId() + ".png"));
            cdf1.setImage(cardFront1);
            /*cdf1.setOnMouseClicked(event -> {
                try {
                    gui.getClient().sendMessage(new Message("",));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });*/
            Image cdf2 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + myHand.get(1).getId() + ".png"));
            cardFront2.setImage(cdf2);
            Image cdf3 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + myHand.get(2).getId() + ".png"));
            cardFront3.setImage(cdf3);

            Image cardBack1 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_back/back" + myHand.get(0).getId() + ".png"));
            cdb1.setImage(cardBack1);
            Image cdb2 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_back/back" + myHand.get(1).getId() + ".png"));
            cardBack2.setImage(cdb2);
            Image cdb3 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_back/back" + myHand.get(2).getId() + ".png"));
            cardBack3.setImage(cdb3);
    }



    //play area





    //points



    //turn




}
