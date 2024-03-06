package com.example.test.gui;

import com.example.test.models.User;
import com.example.test.services.GestionUser;
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

public class UsersList implements Initializable {

    @FXML
    private ListView<User> list;

    private ObservableList<User> userList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeListView();
        populateListView();
    }

    private void initializeListView() {
        userList = FXCollections.observableArrayList();
        list.setItems(userList);

        // Set custom cell factory to display attributes and actions
        list.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
            @Override
            public ListCell<User> call(ListView<User> listView) {
                return new ListCell<User>() {
                    @Override
                    protected void updateItem(User user, boolean empty) {
                        super.updateItem(user, empty);
                        if (empty || user == null) {
                            setGraphic(null);
                        } else {
                            // Create a GridPane to display attributes and actions
                            GridPane gridPane = new GridPane();
                            gridPane.setHgap(15);
                            gridPane.setVgap(5);

                            // Add labels for attribute names as titles at the top of each column

                            gridPane.add(new Label("Nom"), 1, 0);
                            gridPane.add(new Label("Prénom"), 2, 0);
                            gridPane.add(new Label("Status"), 3, 0);


                            gridPane.add(new Label("Actions"), 7, 0);

                            // Add values for each attribute below the titles

                            gridPane.add(new Label(user.getNom()), 1, 1);
                            gridPane.add(new Label(user.getPrenom()), 2, 1);
                            gridPane.add(new Label(String.valueOf(user.getStatus())), 3, 1);


                            // Create edit and delete buttons
                            Button editButton = new Button("Edit");
                            Button deleteButton = new Button("Delete");

                            // Set actions for edit and delete buttons
                            editButton.setOnAction(event -> {
                                // Handle edit action here
                                try {
                                    editUser(user.getId());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });

                            deleteButton.setOnAction(event -> {
                                // Handle delete action here
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Delete Confirmation");
                                alert.setHeaderText("Confirm Delete");
                                alert.setContentText("Are you sure you want to delete this user?");

                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.isPresent() && result.get() == ButtonType.OK) {
                                    // Call delete method if "OK" is clicked
                                    deleteUser(user.getId());
                                }
                            });

                            // Create an HBox to hold the action buttons
                            HBox actionButtons = new HBox(5, editButton, deleteButton);

                            // Add action buttons to the "Actions" column
                            gridPane.add(actionButtons, 7, 1);

                            setGraphic(gridPane);
                        }
                    }
                };
            }
        });
    }

    private void populateListView() {
        GestionUser gestionUser = new GestionUser();
        userList.addAll(gestionUser.findAll());
    }

    private void deleteUser(int id) {
        GestionUser gs = new GestionUser();
        gs.delete(gs.findById(id));
        initializeListView();
        populateListView();
    }

    private void editUser(int id) throws IOException {
        GestionUser gs = new GestionUser();
        User user = new User();
        user = gs.findById(id);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/test/editUser.fxml"));
        Parent profileInterface = loader.load();
        EditUser profileController = loader.getController();
        profileController.initData(user);
        Scene profileScene = new Scene(profileInterface);
        Stage profileStage = new Stage();
        profileStage.setScene(profileScene);
        profileStage.show();
    }

    @FXML
    public void BackToDashboard() {try {
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
