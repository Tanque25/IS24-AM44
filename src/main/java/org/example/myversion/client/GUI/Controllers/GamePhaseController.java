package org.example.myversion.client.GUI.Controllers;

import javafx.application.Platform;
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

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
    private ImageView cardFront1;
    @FXML
    private ImageView cardFront2;
    @FXML
    private ImageView cardFront3;

    @FXML
    private ImageView cardBack1;
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
    private ImageView decKG;
    @FXML
    private ImageView decKP;

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


    public GamePhaseController() {
        super();
    }

    public void showGame(Map<String, List<PlayableCard>> playerHand, List<ObjectiveCard> personalObjectiveCards, List<ObjectiveCard> commonObjectiveCards, List<GoldCard> goldCards, List<PlayableCard> PlayableCards) {
        Platform.runLater(()->{
            try {
                URL fxmlLocation = getClass().getResource("/org/example/myversion/FXML/GamePhase.fxml");

                FXMLLoader loader = new FXMLLoader(fxmlLocation);

                Parent root = loader.load();


                //Common Objective
                Image CommonObjective1 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + gui.getCommonObjectiveCards().get(0).getId() + ".png"));
                co01.setImage(CommonObjective1);
                Image CommonObjective2 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + gui.getCommonObjectiveCards().get(1).getId() + ".png"));
                co02.setImage(CommonObjective2);

                //Personal Objective
                Image PersonalObjective1 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + gui.getObjectiveCards().get(0).getId() + ".png"));
                co03.setImage(PersonalObjective1);
                Image PersonalObjective2 = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + gui.getObjectiveCards().get(1).getId() + ".png"));
                co04.setImage(PersonalObjective2);

                //Hand cards
                /*nicknameP1.setText(gui.getClient().getNickname());
                String nickname = nicknameP1.getText();

                cardFront1.setImage(new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + playerHand.get(nickname).get(0).getId() + ".png")));
                cardFront2.setImage(new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + playerHand.get(nickname).get(1).getId() + ".png")));
                cardFront3.setImage(new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + playerHand.get(nickname).get(2).getId() + ".png")));

                cardBack1.setImage(new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_back/back" + playerHand.get(nickname).get(0).getId() + ".png")));
                cardBack2.setImage(new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_back/back" + playerHand.get(nickname).get(1).getId() + ".png")));
                cardBack3.setImage(new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_back/back" + playerHand.get(nickname).get(2).getId() + ".png")));

                //Gold cards
                decKG.setImage(new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_back/back" + gui.getCoveredGoldCard() + ".png")));
                gc01.setImage(new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + goldCards.get(0).getId() + ".png")));
                gc02.setImage(new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + goldCards.get(1).getId() + ".png")));

                //Playable cards
                decKP.setImage(new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_back/back" + gui.getCoveredResourceCard() + ".png")));
                pc01.setImage(new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + PlayableCards.get(0).getId() + ".png")));
                pc02.setImage(new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + PlayableCards.get(1).getId() + ".png")));*/

                gui.getStage().setTitle("Codex Naturalis");
                gui.getStage().setScene(new Scene(root));
                gui.getStage().show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }





}
