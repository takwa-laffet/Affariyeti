module com.example.affariyetii {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.affariyetii to javafx.fxml;
    exports com.example.affariyetii;
}