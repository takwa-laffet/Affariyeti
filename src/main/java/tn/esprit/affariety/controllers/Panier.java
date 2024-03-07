package tn.esprit.affariety.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import tn.esprit.affariety.models.Article;
import tn.esprit.affariety.utils.MyDataBase;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Panier implements Initializable {
    @FXML
    private Button confirmButton;

    @FXML
    private AnchorPane menuForm;

    @FXML
    private GridPane menuGrid;

    @FXML
    private TableColumn<Panier, String> menuName;

    @FXML
    private TableColumn<Panier, Float> menuPrice;

    @FXML
    private TableColumn<Panier, Integer> menuQuantity;

    @FXML
    private ScrollPane menuScroll;

    @FXML
    private TableView<Panier> menuTable;

    @FXML
    private Label menuTotal;
    private String imageUrl;
    private ObservableList<Article> article = FXCollections.observableArrayList();
    private Connection connection;


    public Panier() {
        this.connection = MyDataBase.getInstance().getConnection();
    }
    @FXML
    void Affiche_Panier(javafx.event.ActionEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle vue
            FXMLLoader loader = new FXMLLoader(getClass().getResource("tn/esprit/affariety/carte.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la nouvelle vue chargée
            Scene scene = new Scene(root);

            // Obtenir la fenêtre actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Définir la nouvelle scène dans la fenêtre
            stage.setScene(scene);

            // Montrer la nouvelle vue
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'erreur de chargement de la vue
        }
    }


    public ObservableList<Article> getMenuData() {
        String sql = "SELECT * FROM article";
        ObservableList<Article> dataList = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            Article article;
            while (resultSet.next()) {
                article = new Article(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("categorie"),
                        resultSet.getFloat("prix"));


                dataList.add(article);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dataList;
    }
    public void displayMenu() {
        article.clear();
        article.addAll(getMenuData());
        int row = 0;
        int column = 0;
        menuGrid.getRowConstraints().clear();
        menuGrid.getColumnConstraints().clear();
        for (int i = 0; i < article.size(); i++) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("tn/esprit/affariety/carte.fxml"));
                AnchorPane pane = loader.load();
                Carte carteController = loader.getController();
                carteController.setData(article.get(i));
                if (column == 4) {
                    column = 0;
                    row += 1;
                }
                menuGrid.add(pane, column++, row);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayMenu();
    }


}
