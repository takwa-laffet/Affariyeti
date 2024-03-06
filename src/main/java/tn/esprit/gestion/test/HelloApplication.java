package tn.esprit.gestion.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Ajouterlivraison.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        stage.setTitle("GESTION DES LIVRAISONS!");
        stage.setScene(scene);
        stage.setWidth(stage.getWidth()+50);
        stage.setHeight(stage.getHeight()+50);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
