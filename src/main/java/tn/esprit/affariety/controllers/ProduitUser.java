package tn.esprit.affariety.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.affariety.models.Categorie;
import tn.esprit.affariety.models.Produit;
import tn.esprit.affariety.models.User;
import tn.esprit.affariety.services.CategorieService;
import tn.esprit.affariety.services.GestionUser;
import tn.esprit.affariety.services.ProduitService;
import tn.esprit.affariety.utils.Session;

import javax.mail.internet.AddressException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProduitUser implements Initializable {
    @FXML
    private TextField descriptionTF;

    @FXML
    private ImageView imageTF;
private User user;
    @FXML
    private TextField nomCTF;
private  int iduser;
    @FXML
    private TextField nomTF;

    @FXML
    private TextField prixTF;
    private File selectedFile;
    private String path_image;
    private int prouitsid;
    public void setProduit(int produit) {
        this.prouitsid = produit;
    }
    public int getId(){
        return  this.prouitsid;
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
        produit.setUser(this.user);
        System.out.println(produit.getUser().getId());
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

        } catch (Exception e) {
            throw new RuntimeException(e);
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


    public void clearFields(ActionEvent event) {
        nomTF.clear();
        descriptionTF.clear();
        nomCTF.clear();
        prixTF.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.user = Session.getSession().getUser();
        this.iduser=user.getId();
        User user1=new User();
        user1.setId(this.iduser);


    }
}
