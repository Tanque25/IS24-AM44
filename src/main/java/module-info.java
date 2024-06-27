module it.polimi.ingsw {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires jakarta.json;
    requires java.rmi;
    requires commons.cli;

    opens it.polimi.ingsw to javafx.fxml;

    exports it.polimi.ingsw.client to java.rmi;
    exports it.polimi.ingsw.client.view to java.rmi;

    exports it.polimi.ingsw.server to java.rmi;
    exports it.polimi.ingsw.server.serverController to java.rmi;
    exports it.polimi.ingsw.messages to java.rmi;

    exports it.polimi.ingsw.server.model to java.rmi;
    exports it.polimi.ingsw.server.model.decks.cards to java.rmi;

    exports it.polimi.ingsw.client.GUI to java.rmi;
    exports it.polimi.ingsw.client.GUI.Controllers to java.rmi;
    opens it.polimi.ingsw.client.GUI.Controllers to java.rmi;
}