module org.example.myversion {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.json;
    requires java.rmi;

    opens org.example.myversion to javafx.fxml;
    exports org.example.myversion;
}