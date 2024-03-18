module org.example.am24is44 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens org.example.am24is44 to javafx.fxml;
    exports org.example.am24is44;
}