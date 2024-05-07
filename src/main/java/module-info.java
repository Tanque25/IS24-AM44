module org.example.myversion {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires jakarta.json;
    requires java.rmi;

    opens org.example.myversion to javafx.fxml;
    exports org.example.myversion;
}