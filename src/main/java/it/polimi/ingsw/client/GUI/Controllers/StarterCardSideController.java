package it.polimi.ingsw.client.GUI.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import it.polimi.ingsw.client.GUI.GUIController;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.server.model.decks.cards.StarterCard;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Controller class for choosing the side of the starter card in the GUI.
 */
public class StarterCardSideController extends GUIController {

    @FXML
    private Button playedFront;
    @FXML
    private Button playedBack;
    @FXML
    private Button send;
    @FXML
    private AnchorPane starterSides;
    @FXML
    private ImageView imgViewFront;
    @FXML
    private ImageView imgViewBack;

    /**
     * Default constructor for the StarterCardSideController class.
     */
    public StarterCardSideController() {
        super();
    }

    /**
     * Initializes the controller class. This method is automatically called after the FXML file has been loaded.
     * It sets the action executed when you press the ok button.
     */
    public void initialize() {
        // Initialization code, if any, goes here
    }

    /**
     * Sets up the GUI for choosing the side of the starter card and displays the corresponding scene.
     * It sends the chosen side to the server
     * @param starterCard the starter card to choose the side for
     */
    public void sideChoice(StarterCard starterCard) {
        Platform.runLater(() -> {
            try {
                URL fxmlLocation = (new File("src/main/resources/org/example/myversion/FXML/StarterCardSide.fxml")).toURI().toURL();
                FXMLLoader loader = new FXMLLoader(fxmlLocation);
                loader.setController(this);
                Parent root = loader.load();

                Image imgFront = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_front/front" + starterCard.getId() + ".png"));
                imgViewFront.setImage(imgFront);
                Image imgBack = new Image(getClass().getResourceAsStream("/org/example/myversion/Images/cards_gold_back/back" + starterCard.getId() + ".png"));
                imgViewBack.setImage(imgBack);

                gui.getStage().setTitle("Codex Naturalis");
                gui.getStage().setScene(new Scene(root));
                gui.getStage().show();
            } catch (IOException e) {
                e.printStackTrace();
                return; // Exit method if FXML loading fails
            }

            playedFront.setOnMouseClicked(event -> starterCard.setPlayedBack(false));
            playedBack.setOnMouseClicked(event -> starterCard.setPlayedBack(true));

            send.setOnMouseClicked(event -> {
                try {
                    gui.getClient().sendMessage(new Message("StarterCard", starterCard));
                    playedBack.setDisable(true);
                    playedFront.setDisable(true);
                    send.setDisable(true);
                    gui.setPlayerStarterChosen(true);
                    gui.showCommonObjectives(gui.getCommonObjectiveCards());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
    }
}
