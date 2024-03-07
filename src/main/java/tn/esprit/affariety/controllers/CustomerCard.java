package tn.esprit.affariety.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tn.esprit.affariety.models.Article;
import tn.esprit.affariety.utils.MyDataBase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerCard extends Pane {

    protected final ImageView photo;
    protected final Label name;
    protected final Label mobile;
    protected final Label label;
    protected final Label date;
    private int productId;
    private Article article;
    protected final Label visits;
    protected final Label label0;
    protected final HBox iconContainer;

    protected final Button deleteButton;
    protected final Button modifyButton;
    private int ID;
    public void setData(Article article) {
        this.article = article;

        productId = article.getId();

    }
    public CustomerCard(int Id, String Name, Float Prix, String Image) {
        setLayoutY(10.0);
        photo = new ImageView();
        name = new Label();
        mobile = new Label();
        label = new Label();
        date = new Label();
        visits = new Label();
        label0 = new Label();
        iconContainer = new HBox();
        deleteButton = new Button();
        modifyButton = new Button();

        setId(Id + "");
        setPrefHeight(233.0);
        setPrefWidth(245.0);
        setStyle("-fx-background-color:#FFF; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        DropShadow dropShadow = new DropShadow();
        dropShadow.setHeight(3);
        dropShadow.setWidth(3);
        dropShadow.setBlurType(BlurType.TWO_PASS_BOX);
        setEffect(dropShadow);

        photo.setImage(new Image("C://Users/Lenovo/IdeaProjects/Affariety/"+Image));
        photo.setLayoutX(50);
        photo.setLayoutY(25);
        photo.setFitHeight(100);
        photo.setFitWidth(150);

        name.setAlignment(Pos.TOP_LEFT);
        name.setContentDisplay(ContentDisplay.CENTER);
        name.setLayoutX(14.0);
        name.setLayoutY(130.0);
        name.setPrefHeight(26.0);
        name.setPrefWidth(215.0);
        name.setText(Name);
        name.setFont(Font.font("System", FontWeight.BOLD, 17.0));
        mobile.setAlignment(Pos.TOP_LEFT);
        mobile.setContentDisplay(ContentDisplay.CENTER);
        mobile.setLayoutX(14.0);
        mobile.setLayoutY(150);
        mobile.setPrefHeight(26.0);
        mobile.setPrefWidth(215.0);
        mobile.setText(String.valueOf(Prix)+" dt");
        mobile.setFont(Font.font("System", FontWeight.BOLD, 17.0));



        iconContainer.setAlignment(Pos.CENTER_RIGHT);
        iconContainer.setPrefHeight(40.0);
        iconContainer.setPrefWidth(200.0);
        iconContainer.setLayoutX(24);
        iconContainer.setLayoutY(206);
        deleteButton.setText("ADD");
        deleteButton.setPrefHeight(32.0);
        deleteButton.setPrefWidth(80);
        deleteButton.getStyleClass().add("delete-button");
        deleteButton.setOnAction(e -> {



            try {
                Connection connection = MyDataBase.getInstance().getConnection();
                String insertData = "INSERT INTO panier (nom_article, prix) VALUES (?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertData);
                preparedStatement.setString(1, Name);
                preparedStatement.setFloat(2, Prix);
                preparedStatement.executeUpdate();

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("Produit ajouté avec succès !");
                alert.showAndWait();



            } catch (Exception ex) {
                // Gérez les exceptions SQL
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur est survenue lors de l'ajout du produit au panier.");
                alert.showAndWait();
                ex.printStackTrace();
            }

        });
     modifyButton.setText("Details");
        modifyButton.setPrefHeight(32.0);
        modifyButton.setPrefWidth(85);
        modifyButton.getStyleClass().add("modify-button");
        modifyButton.setOnAction(e -> {
            try {
                int productId = Integer.parseInt(getId());
                System.out.println(productId);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/affariety/DetailsProduits.fxml"));

                Parent root = loader.load();

               // DetailsProduits detailsProduit = new DetailsProduits(productId);

                Scene scene = new Scene(root);

                Stage stage = (Stage) modifyButton.getScene().getWindow();
                stage.setScene(scene);
                DetailsProduits detailsProduitsController = loader.getController();
                detailsProduitsController.setProduit(productId);
                detailsProduitsController.initializeDetails();
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


        iconContainer.getChildren().addAll(deleteButton, modifyButton);

        getChildren().addAll(photo, name,mobile, label, label0, iconContainer);

    }
}
