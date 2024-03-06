module com.example.affariyetii {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires java.mail;
    requires org.apache.commons.codec;
    requires com.google.api.client;
    requires com.google.api.client.json.jackson2;
    requires com.google.api.client.auth;
    requires google.api.client;
    requires org.controlsfx.controls;
    requires commons.lang3;
    requires restfb;


    opens com.example.affariyetii to javafx.fxml;
    exports com.example.affariyetii;
    exports com.example.affariyetii.test;
    opens com.example.affariyetii.test to javafx.fxml;
}
