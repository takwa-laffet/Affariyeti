module com.example.affariyetii {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
<<<<<<< HEAD
    requires jbcrypt;
    requires mail;
=======

    requires java.mail;
    requires org.apache.commons.codec;
    requires com.google.api.client;
    requires com.google.api.client.json.jackson2;
    requires com.google.api.client.auth;
    requires google.api.client;
    requires org.controlsfx.controls;
    requires commons.lang3;
    requires restfb;

>>>>>>> enchere

    opens com.example.affariyetii to javafx.fxml;
    opens com.example.test.main to javafx.graphics; // Add this line
    exports com.example.affariyetii;
<<<<<<< HEAD
    opens com.example.test.gui to javafx.fxml;
}
=======
    exports com.example.affariyetii.test;
    opens com.example.affariyetii.test to javafx.fxml;
}
>>>>>>> enchere
