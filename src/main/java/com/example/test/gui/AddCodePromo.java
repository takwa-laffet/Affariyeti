package com.example.test.gui;

import com.example.test.models.CategorieCodePromo;
import com.example.test.models.CodePromo;
import com.example.test.models.User;
import com.example.test.services.GestionCategorieCodePromo;
import com.example.test.services.GestionCodePromo;
import com.example.test.services.GestionUser;
import com.example.test.utils.Session;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AddCodePromo implements Initializable {

    @FXML
    private TextField valueTextField;

    @FXML
    private TextField codeTextField;

    @FXML
    private ComboBox<CategorieCodePromo> userComboBox;

    private CodePromo codePromo;

    @Deprecated
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GestionCategorieCodePromo gs = new GestionCategorieCodePromo();
        System.out.println(gs.findAll());
        userComboBox.setConverter(new StringConverter<CategorieCodePromo>() {
            @Override
            public String toString(CategorieCodePromo object) {
                return (object != null) ? object.getCode() : "";
            }

            @Override
            public CategorieCodePromo fromString(String string) {
                // If needed, implement conversion from string to CategorieCodePromo
                return null;
            }
        });

        userComboBox.setItems(FXCollections.observableArrayList(gs.findAll()));

    }//ResourceBundle resourceBundle: C'est une classe utilisée pour localiser des objets

    @FXML
    private void saveChanges() {
      CodePromo codePromo = new CodePromo();
     codePromo.setUser(Session.getSession().getUser());


      CategorieCodePromo u = userComboBox.getSelectionModel().getSelectedItem();
      codePromo.setCategorieCodePromo(u);
     GestionCodePromo gs = new GestionCodePromo();
     gs.Create(codePromo);


    }


    @FXML
    public void BackToAffichageCodePromo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/test/CodePromoList.fxml"));
            Parent profileInterface = loader.load();

            // Get the controller instance

            // Initialize data using the controller's method

            Scene profileScene = new Scene(profileInterface);
            Stage profileStage = new Stage();
            profileStage.setScene(profileScene);

            // Close the current stage (assuming loginButton is accessible from here)
            Stage currentStage = (Stage) userComboBox.getScene().getWindow();
            currentStage.close();

            // Show the profile stage
            profileStage.show();
        }catch (Exception e ){
            System.out.println(e.getMessage());
        }
    }
}
