package tn.esprit.affarietygui.controllers;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ListView;
import tn.esprit.affarietygui.models.Publication;
import tn.esprit.affarietygui.services.PublicationService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Statistique {

    @FXML
    private ListView<String> ListPie;

    @FXML
    private PieChart adressePie;

    private PublicationService publicationService = new PublicationService();

    @FXML
    public void initialize() {
        try {
            afficherListeAdresse();
            afficherPieChart();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void afficherListeAdresse() throws SQLException {
        List<Publication> publications = publicationService.recuperer();
        ObservableList<String> publicationStrings = FXCollections.observableArrayList();

        for (Publication publication : publications) {
            publicationStrings.add("nombre de j'aimes : " + publication.getNb_likes() + ", Contenu: " + publication.getContenu());
        }

        ListPie.setItems(publicationStrings);
    }

    private void afficherPieChart() throws SQLException {
        List<Publication> publications = publicationService.recuperer();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (Publication publication : publications) {
            String nb_likes = String.valueOf(publication.getNb_likes());
            boolean nb_likesexist = false;

            // Vérifier si le nombre de likes existe déjà dans le PieChart
            for (PieChart.Data data : pieChartData) {
                if (data.getName().equals(nb_likes)) {
                    nb_likesexist = true;
                    data.setPieValue(data.getPieValue() + 1);
                    break;
                }
            }

            // Si le nombre de likes n'existe pas, l'ajouter dans le PieChart
            if (!nb_likesexist) {
                pieChartData.add(new PieChart.Data(nb_likes, 1));
            }
        }

        adressePie.setData(pieChartData);
    }

    public void retourbtn(ActionEvent actionEvent) {
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/affarietygui/afficher_pub.fxml"));
            Parent root = loader.load();

            // Obtenir la scène depuis le bouton source
            Scene scene = ((Node) actionEvent.getSource()).getScene();

            // Obtenir la fenêtre actuelle et la fermer
            Stage stage = (Stage) scene.getWindow();
            stage.close();

            // Afficher la nouvelle interface utilisateur dans une nouvelle fenêtre
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'exception en affichant la trace
        }
    }
}
