package tn.esprit.affariety.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.affariety.Cellule.ProduitCell;
import tn.esprit.affariety.models.Categorie;
import tn.esprit.affariety.models.Produit;
import tn.esprit.affariety.services.CategorieService;
import tn.esprit.affariety.services.ProduitService;

import javax.mail.internet.AddressException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class InterfaceProduit {
    @FXML
    private TextField descriptionTF;
    @FXML
    private ImageView imageTF;
private int prouitsid;
    @FXML
    private ListView<Produit> listView;

    @FXML
    private TextField nomCTF;
    private File selectedFile;
   private String path_image;
    @FXML
    private TextField nomTF;

    @FXML
    private TextField prixTF;
    public void setProduit(int produit) {
        this.prouitsid = produit;
    }
    public int getId(){
        return  this.prouitsid;
    }
    @FXML
    void initialize() {
        ProduitService produitService = new ProduitService();
        try {
            List<Produit> produits = produitService.recuperer();
            listView.setCellFactory(param -> new ProduitCell());            ObservableList<Produit> observableList = FXCollections.observableList(produits);
            listView.setItems(observableList);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                Produit selectedProduit = listView.getSelectionModel().getSelectedItem();
                nomTF.setText(selectedProduit.getNom_p());
                nomCTF.setText(String.valueOf(selectedProduit.getCategorie().getNom_c()));
                descriptionTF.setText(selectedProduit.getDescription_p());
                prixTF.setText(String.valueOf(selectedProduit.getPrix_p()));
            }
        });
    }

        @FXML
        void ajouterProduit (ActionEvent event){
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
            produit.setImage_p(path_image);
            System.out.println(this.path_image.toString());
            System.out.println(produit.getImage_p());

            produit.setDescription_p(descriptionTF.getText());
            produit.setPrix_p(Float.parseFloat(prixTF.getText()));

            produit.setCategorie(c);
            try {
                produitService.ajouter(produit);
                Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Succes");
                alert.setContentText("Produit ajoutée");
                alert.showAndWait();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText(e.getMessage());
                alert.showAndWait();

            } catch (AddressException e) {
                throw new RuntimeException(e);
            }
            initialize();

        }


    @FXML
    void modifierProduit(ActionEvent event) {
        try {

            int productId = listView.getSelectionModel().getSelectedItem().getId_p();
            System.out.println(productId);


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/affariety/ModifierSupprimerProduit.fxml"));
            Parent root = loader.load();


            Scene scene = new Scene(root);


            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();


            stage.setScene(scene);


            ModifierSupprimerProduit modifierSupprimerProduit = loader.getController();


            modifierSupprimerProduit.setProduit(productId);


            modifierSupprimerProduit.initializeDetails();


            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();

        }
    }

    @FXML
        void supprimerProduit (ActionEvent event){
            ProduitService produitService = new ProduitService();
            Produit produit = new Produit();
            produit.setNom_p(nomTF.getText());
            try {
                produitService.supprimer(produit.getNom_p());
                Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Succes");
                alert.setContentText("Produit supprimé");
                alert.showAndWait();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText(e.getMessage());
                alert.showAndWait();

            }
            initialize();

        }


    public void clearFields(ActionEvent event) {
        nomTF.clear();
        descriptionTF.clear();
        nomCTF.clear();
        prixTF.clear();
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
    public void importerImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();


        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String imagePath = saveImage(selectedFile);

            this.path_image=imagePath;
            // Display the selected image
            Image image = new Image(selectedFile.toURI().toString());
            imageTF.setImage(image);



        }
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

