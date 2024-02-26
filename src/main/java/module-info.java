module com.example.affariyetii {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.affariyetii to javafx.fxml;
    exports com.example.affariyetii;
    exports com.example.affariyetii.test;
    opens com.example.affariyetii.test to javafx.fxml;
}
