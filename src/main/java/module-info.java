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


    opens tn.esprit.gestion to javafx.fxml;
    exports tn.esprit.gestion;
    exports tn.esprit.gestion.test;
    opens tn.esprit.gestion.test to javafx.fxml;
}
