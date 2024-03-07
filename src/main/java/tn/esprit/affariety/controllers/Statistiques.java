package tn.esprit.affariety.controllers;


        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Node;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.chart.PieChart;
        import javafx.scene.control.ListView;
        import javafx.stage.Stage;
        import tn.esprit.affariety.models.Produit;
        import tn.esprit.affariety.services.ProduitService;

        import java.io.IOException;
        import java.sql.SQLException;
        import java.util.List;

public class Statistiques {

    @FXML
    private ListView<String> ListPie; // Utilisez String pour ListView au lieu de ?

    @FXML
    private PieChart adressePie;

    private ProduitService produitService = new ProduitService();

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
        List<Produit> produits = produitService.recuperer();
        ObservableList<String> adresses = FXCollections.observableArrayList();

        for (Produit produit : produits) {
            adresses.add(String.valueOf((produit.getPrix_p())));
        }

        ListPie.setItems(adresses);
    }

    private void afficherPieChart() throws SQLException {
        List<Produit> produits = produitService.recuperer();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (Produit produit : produits) {
            String prix = String.valueOf(produit.getPrix_p());
            boolean adresseExistante = false;

            // Vérifier si l'adresse existe déjà dans le PieChart
            for (PieChart.Data data : pieChartData) {
                if (data.getName().equals(prix)) {
                    adresseExistante = true;
                    data.setPieValue(data.getPieValue() + 1);
                    break;
                }
            }

            // Si l'adresse n'existe pas, l'ajouter dans le PieChart
            if (!adresseExistante) {
                pieChartData.add(new PieChart.Data(prix, 1));
            }
        }

        adressePie.setData(pieChartData);
    }

    public void retournerPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/affariety/InterfaceCategorie.fxml"));
            Parent root = loader.load();

            InterfaceCategorie interfaceCategorie = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
