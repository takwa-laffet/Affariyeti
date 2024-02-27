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

public class EditCodePromo  {
/*
    @FXML
    private TextField valueTextField;

    @FXML
    private TextField codeTextField;

    @FXML
    private ComboBox<User> userComboBox;

    private CodePromo codePromo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void  initData(CodePromo codePromo) {
        setCodePromo(codePromo);
    }
    public void setCodePromo(CodePromo codePromo) {
        GestionUser gs = new GestionUser();
        this.codePromo = codePromo;

        if (codePromo != null) {
            valueTextField.setText(String.valueOf(codePromo.getValeur()));
            codeTextField.setText(codePromo.getCode());
            User  u = gs.findById(codePromo.getUserId());
            userComboBox.setItems(FXCollections.observableArrayList(gs.findAll()));
            userComboBox.getSelectionModel().select( u);
        }
    }

    @FXML
    private void saveChanges() {
        if (codePromo != null) {
            // Update the codePromo object with the new values
            codePromo.setValeur(Integer.parseInt(valueTextField.getText()));
            codePromo.setCode(codeTextField.getText());
            // Get the selected user ID from the ComboBox
            User selectedUser = userComboBox.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                codePromo.setUserId(selectedUser.getId());
            }
            // Update the codePromo object in the database
            GestionCodePromo gestionCodePromo = new GestionCodePromo();
            gestionCodePromo.update(codePromo);
            // Close the window or handle other actions as needed
        }
    }
*/

}