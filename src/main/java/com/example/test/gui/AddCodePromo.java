package com.example.test.gui;

import com.example.test.models.CodePromo;
import com.example.test.models.User;
import com.example.test.services.GestionCodePromo;
import com.example.test.services.GestionUser;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCodePromo {
    /*
    @FXML
    private TextField valueTextField;

    @FXML
    private TextField codeTextField;

    @FXML
    private ComboBox<User> userComboBox;

    private CodePromo codePromo;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GestionUser gs = new GestionUser();
            userComboBox.setItems(FXCollections.observableArrayList(gs.findAll()));

    }//ResourceBundle resourceBundle: C'est une classe utilisée pour localiser des objets

    @FXML
    private void saveChanges() {
      CodePromo codePromo = new CodePromo();
      codePromo.setValeur(Integer.parseInt(valueTextField.getText()));
      codePromo.setCode(codeTextField.getText());
      codePromo.setActive(true);
      User  u = userComboBox.getSelectionModel().getSelectedItem();
      codePromo.setUserId(u.getId());
      GestionCodePromo gestionCodePromo = new GestionCodePromo();
      gestionCodePromo.Create(codePromo);
    }

     */
}
