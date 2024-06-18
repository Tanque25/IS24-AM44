module org.example.myversion {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires jakarta.json;
    requires java.rmi;

    opens org.example.myversion to javafx.fxml;

    exports org.example.myversion to java.rmi;
    exports org.example.myversion.client to java.rmi;
    exports org.example.myversion.client.view to java.rmi;
    exports org.example.myversion.server to java.rmi;
    exports org.example.myversion.server.serverController to java.rmi;
    exports org.example.myversion.messages to java.rmi;
    exports  org.example.myversion.server.model.decks.cards to java.rmi;
    exports  org.example.myversion.server.model to java.rmi;
    exports  org.example.myversion.client.GUI;
}