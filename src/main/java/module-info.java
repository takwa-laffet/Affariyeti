module com.example.affariyetii {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;
    requires mail;

    opens com.example.affariyetii to javafx.fxml;
    opens com.example.test.main to javafx.graphics; // Add this line
    exports com.example.affariyetii;
    opens com.example.test.gui to javafx.fxml;
}