module org.example.myversion {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens org.example.myversion to javafx.fxml;
    exports org.example.myversion;
}