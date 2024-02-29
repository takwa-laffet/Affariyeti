package tn.esprit.affarietygui.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/affarietygui/Ajouterpub.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 822, 495);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        /* Parent root = loader.load();

        // Créer la scène
        Scene scene = new Scene(root);

        // Passer la scène à votre contrôleur
        AfficherPub controller = loader.getController();
        controller.setScene(scene); // Ajoutez cette ligne pour passer la scène au contrôleur AfficherPub

        // Définir la scène principale
        stage.setScene(scene);

        // Afficher la fenêtre principale
        stage.setTitle("Votre titre");
        stage.show();*/
    }

    public static void main(String[] args) {
        launch();
    }
}