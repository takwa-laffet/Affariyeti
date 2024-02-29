package com.example.test.gui;

import com.example.test.models.CategorieCodePromo;
import com.example.test.services.GestionCategorieCodePromo;
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
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AffichageCcpClient implements Initializable {
    @FXML
    private ListView<CategorieCodePromo> listView;

    private ObservableList<CategorieCodePromo> codeList;
    public void initData (){



    }
    private void initializeListView() {
        codeList = FXCollections.observableArrayList();
        listView.setItems(codeList);

        // Set custom cell factory to display attributes and actions
        listView.setCellFactory(new Callback<ListView<CategorieCodePromo>, ListCell<CategorieCodePromo>>() {
            @Override
            public ListCell<CategorieCodePromo> call(ListView<CategorieCodePromo> listView) {
                return new ListCell<CategorieCodePromo>() {
                    @Override
                    protected void updateItem(CategorieCodePromo codePromo, boolean empty) {
                        super.updateItem(codePromo, empty);

                        if (empty || codePromo == null) {
                            setGraphic(null);
                        } else {
                           GestionCategorieCodePromo us = new GestionCategorieCodePromo();
                            //  int id = codePromo.getId();
                            // codePromo = us.getUserWithInformation(codePromo.getId());

                            // Create a GridPane to display attributes and actions
                            GridPane gridPane = new GridPane();
                            gridPane.setHgap(10);
                            gridPane.setVgap(5);

                            // Add labels for attribute names as titles at the top of each column
                            gridPane.add(new Label("ID"), 0, 0);
                            gridPane.add(new Label("Valeur"), 1, 0);
                            gridPane.add(new Label("Code"), 2, 0);
                            gridPane.add(new Label("Limite"), 3, 0);
                            gridPane.add(new Label(String.valueOf(codePromo.getIdCcp())), 0, 1);

                            gridPane.add(new Label(String.valueOf(codePromo.getValeur())), 1, 1);
                            gridPane.add(new Label(String.valueOf(codePromo.getCode())), 2, 1);

                            gridPane.add(new Label(String.valueOf(codePromo.getLimite())), 3, 1);


                            // Create edit and delete buttons
                            Button editButton = new Button("Select");

                            // Set actions for edit and delete buttons


                            editButton.setOnAction(event -> {

                                goToAdd(codePromo.getIdCcp());


                            });

                            HBox actionButtons = new HBox(6, editButton);

                            // Add action buttons to the "Actions" column
                            gridPane.add(actionButtons, 6, 1);

                            setGraphic(gridPane);
                        }
                    }
                };
            }
        });
    }
    private void deleteCodePromo(int id) throws IOException {
        GestionCategorieCodePromo gs = new GestionCategorieCodePromo();
        CategorieCodePromo user = new CategorieCodePromo();

        user = gs.findById(id);
        gs.delete(user);

    }
    private void editCodePromo(int id) throws IOException {
        GestionCategorieCodePromo gs = new GestionCategorieCodePromo();
        CategorieCodePromo user = new CategorieCodePromo();
        user = gs.findById(id);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/test/EditCategorieCodePromo.fxml"));
        Parent profileInterface = loader.load();
        EditCategorieCodePromo profileController = loader.getController();
        profileController.initData(user);
        Scene profileScene = new Scene(profileInterface);
        Stage profileStage = new Stage();
        profileStage.setScene(profileScene);
        profileStage.show();
        //Stage currentStage = (Stage) listUsers.getScene().getWindow();
        //currentStage.close();


    }

    private void populateListView( ) {
        GestionCategorieCodePromo gestionCodePromo = new GestionCategorieCodePromo();
        codeList.addAll(gestionCodePromo.findAll());
    }

public void goToAdd(int id){

    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/test/SelectCodePROMO.fxml"));
        Parent profileInterface = loader.load();

        GestionCategorieCodePromo gs = new GestionCategorieCodePromo();
        SelectCodePROMO codePromoListController = loader.getController();
        codePromoListController.initData(gs.findById(id));




        Scene profileScene = new Scene(profileInterface);
        Stage profileStage = new Stage();
        profileStage.setScene(profileScene);
        profileStage.show();


       // Stage currentStage = (Stage) listView.getScene().getWindow();
        //currentStage.close();
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {initializeListView();
        populateListView();

    }
}
