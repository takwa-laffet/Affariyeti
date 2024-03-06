package com.example.test.gui;

import com.example.test.models.CodePromo;
import com.example.test.services.GestionCodePromo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class CodePromoList implements Initializable {

    @FXML
    private ListView<CodePromo> list;

    private ObservableList<CodePromo> codeList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeListView();
        populateListView();
    }

    private void initializeListView() {
        codeList = FXCollections.observableArrayList();
        list.setItems(codeList);

        // Set custom cell factory to display attributes and actions
        list.setCellFactory(new Callback<ListView<CodePromo>, ListCell<CodePromo>>() {
            @Override
            public ListCell<CodePromo> call(ListView<CodePromo> listView) {
                return new ListCell<CodePromo>() {
                    @Override
                    protected void updateItem(CodePromo codePromo, boolean empty) {
                        super.updateItem(codePromo, empty);
                        if (empty || codePromo == null) {
                            setGraphic(null);
                        } else {
                            // Create a GridPane to display attributes and actions
                            GridPane gridPane = new GridPane();
                            gridPane.setHgap(10);
                            gridPane.setVgap(5);

                            // Add labels for attribute names as titles at the top of each column
                            gridPane.add(new Label("ID"), 0, 0);
                            gridPane.add(new Label("User"), 1, 0);
                            gridPane.add(new Label("Categorie"), 2, 0);


                            // Add values for each attribute below the titles
                            gridPane.add(new Label(String.valueOf(codePromo.getIdCode())), 0, 1);
                            gridPane.add(new Label(String.valueOf(codePromo.getUser().getNom())), 1, 1);
                            gridPane.add(new Label(codePromo.getCategorieCodePromo().getCode()), 2, 1);


                            // Create edit and delete buttons
                           // Button editButton = new Button("Edit");
                            Button deleteButton = new Button("Delete");

                            // Set actions for edit and delete buttons
                          /*  editButton.setOnAction(event -> {
                                // Handle edit action here

                                editCodePromo(codePromo.getIdCode());
                            });
*/
                            deleteButton.setOnAction(event -> {
                                // Handle delete action here
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Delete Confirmation");
                                alert.setHeaderText("Confirm Delete");
                                alert.setContentText("Are you sure you want to delete this item?");

                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.isPresent() && result.get() == ButtonType.OK) {
                                    // Call delete method if "OK" is clicked
                                    deleteCodePromo(codePromo.getIdCode());
                                }
                            });

                            // Create an HBox to hold the action buttons
                            HBox actionButtons = new HBox(5, deleteButton);

                            // Add action buttons to the "Actions" column
                            gridPane.add(actionButtons, 5, 1);

                            setGraphic(gridPane);
                        }
                    }
                };
            }
        });
    }

    private void populateListView() {
        GestionCodePromo gestionCodePromo = new GestionCodePromo();
        codeList.addAll(gestionCodePromo.findAll());
    }

    private void deleteCodePromo(int id) {
       GestionCodePromo gs = new GestionCodePromo();
       gs.delete(gs.findById(id));
        initializeListView();
        populateListView();
    }
    private void editCodePromo(int id) {
       GestionCodePromo gs = new GestionCodePromo();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/test/EditCodePromo.fxml"));
            Parent root = loader.load();
            EditCodePromo editCodePromo = loader.getController();
            //editCodePromo.initData(gs.findById(id));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void addCodePromo() {
        //GestionCodePromo gs = new GestionCodePromo();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/test/addCodePromo.fxml"));
            Parent root = loader.load();
           AddCodePromo editCodePromo = loader.getController();
            //editCodePromo.initData(gs.findById(id));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void BackToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/test/dashboardAdmin.fxml"));
            Parent profileInterface = loader.load();

            // Get the controller instance

            // Initialize data using the controller's method

            Scene profileScene = new Scene(profileInterface);
            Stage profileStage = new Stage();
            profileStage.setScene(profileScene);

            // Close the current stage (assuming loginButton is accessible from here)
            Stage currentStage = (Stage) list.getScene().getWindow();
            currentStage.close();

            // Show the profile stage
            profileStage.show();
        }catch (Exception e ){
            System.out.println(e.getMessage());
        }

    }
}
