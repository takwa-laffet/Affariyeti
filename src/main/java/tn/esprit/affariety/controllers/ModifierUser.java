package tn.esprit.affariety.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.esprit.affariety.models.Categorie;
import tn.esprit.affariety.models.Produit;
import tn.esprit.affariety.models.User;
import tn.esprit.affariety.services.CategorieService;
import tn.esprit.affariety.services.ProduitService;
import tn.esprit.affariety.utils.Session;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ModifierUser {
    @FXML
    private ListView<Produit> listView;

    @FXML
    private TextField descriptionTF;

    @FXML
    private ImageView imageTF;

    @FXML
    private TextField nomCTF;

    @FXML
    private TextField nomTF;

    @FXML
    private TextField prixTF;
    private File selectedFile;
    private String path_image;
    private int prouitsid;
    private User user;
    private int idprod;
    private ObservableList<Produit> observableList;

    public void setProduit(int produit) {
        this.prouitsid = produit;
    }

    public int getId() {
        return this.prouitsid;
    }
    @FXML
    void initialize() {
        this.user = Session.getSession().getUser();
        int iduser= user.getId();
        ProduitService produitService = new ProduitService();
        try {
            List<Produit> produits = produitService.recupererproduituser(iduser);
            //listView.setCellFactory(param -> new ProduitCell());
            listView.setCellFactory(new InterfaceProduit.ProduitCellFactory());

            observableList = FXCollections.observableList(produits);
            listView.setItems(observableList);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                Produit selectedProduit = listView.getSelectionModel().getSelectedItem();
                this.idprod=selectedProduit.getId_p();
                nomTF.setText(selectedProduit.getNom_p());
                nomCTF.setText(String.valueOf(selectedProduit.getCategorie().getNom_c()));
                descriptionTF.setText(selectedProduit.getDescription_p());
                prixTF.setText(String.valueOf(selectedProduit.getPrix_p()));

            }
        });

    }

    public void retournerPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/affariety/CarCards.fxml"));
            Parent root = loader.load();

            FXMLDocumentController fxmlDocumentController = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static class ProduitCellFactory implements Callback<ListView<Produit>, ListCell<Produit>> {
        @Override
        public ListCell<Produit> call(ListView<Produit> param) {
            return new ListCell<Produit>() {
                @Override
                protected void updateItem(Produit produit, boolean empty) {
                    super.updateItem(produit, empty);
                    if (empty || produit == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        VBox vBox = new VBox();
                        vBox.setAlignment(Pos.CENTER_LEFT);

                        Separator separator = new Separator();
                        separator.setMaxWidth(Double.MAX_VALUE);
                        ImageView imageView = new ImageView("C://Users/Lenovo/IdeaProjects/Affariety/"+produit.getImage_p());
                        imageView.setFitWidth(100);
                        imageView.setFitHeight(100);

                        TextFlow textFlow = new TextFlow();

                        // Nom produit
                        Text nomProduitText = new Text("Nom produit: ");
                        nomProduitText.setStyle("-fx-font-weight: bold;");
                        Text nomProduitValueText = new Text(produit.getNom_p()  + "\n");
                        nomProduitValueText.setStyle("-fx-font-weight: normal;");
                        textFlow.getChildren().addAll(nomProduitText, nomProduitValueText);

                        // Nom Catégorie
                        Text nomCategorieText = new Text("Nom Catégorie: ");
                        nomCategorieText.setStyle("-fx-font-weight: bold;");
                        Text nomCategorieValueText = new Text(produit.getCategorie().getNom_c() + "\n");
                        nomCategorieValueText.setStyle("-fx-font-weight: normal;");
                        textFlow.getChildren().addAll(nomCategorieText, nomCategorieValueText);

                        // Description
                        Text descriptionText = new Text("Description: ");
                        descriptionText.setStyle("-fx-font-weight: bold;");
                        Text descriptionValueText = new Text(produit.getDescription_p() + "\n");
                        descriptionValueText.setStyle("-fx-font-weight: normal;");
                        textFlow.getChildren().addAll(descriptionText, descriptionValueText);

                        // Prix
                        Text prixText = new Text("Prix: ");
                        prixText.setStyle("-fx-font-weight: bold;");
                        Text prixValueText = new Text(String.valueOf(produit.getPrix_p()) + "\n");
                        prixValueText.setStyle("-fx-font-weight: normal;");
                        textFlow.getChildren().addAll(prixText, prixValueText);

                        vBox.getChildren().addAll(textFlow, imageView, separator);
                        setGraphic(vBox);
                    }
                }
            };
        }
    }


    @FXML
    void importerImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();


        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String imagePath = saveImage(selectedFile);

            this.path_image = imagePath;
            // Display the selected image
            Image image = new Image(selectedFile.toURI().toString());
            imageTF.setImage(image);

        }


    }
    private String saveImage(File file) {
        // Specify the target directory to save the images
        String targetDirectory = "tn.esprit.affariety.image/";

        // Ensure the target directory exists, create it if not
        File directory = new File(targetDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Generate a unique name for the image file to avoid overwriting
        String imageName = System.currentTimeMillis() + "_" + file.getName();
        this.path_image = targetDirectory + imageName;


        try {
            java.nio.file.Files.copy(file.toPath(), new File(path_image).toPath());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return path_image;
    }

    @FXML
    void modifierProduit(ActionEvent event) {
        ProduitService produitService = new ProduitService();
        Produit produit = new Produit();
        Categorie c = new Categorie();
        CategorieService cs = new CategorieService();
        try {
            c=cs.getCategoryByName(nomCTF.getText());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        produit.setNom_p(nomTF.getText());
        produit.setDescription_p(descriptionTF.getText());
        produit.setPrix_p(Float.parseFloat(prixTF.getText()));
        produit.setImage_p(path_image);
        produit.setId_p(this.idprod);
        produit.setCategorie(c);
        try {
            produitService.modifier(produit);
            Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succes");
            alert.setContentText("Produit modifié");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }

    }


    public void clearFields(ActionEvent event) {
        nomTF.clear();
        descriptionTF.clear();
        nomCTF.clear();
        prixTF.clear();
    }
}
