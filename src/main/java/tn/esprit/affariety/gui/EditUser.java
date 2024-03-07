package tn.esprit.affariety.gui;

import tn.esprit.affariety.models.User;
import tn.esprit.affariety.services.GestionUser;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

public class EditUser {

    @FXML
    private Label userEmailLabel;

    @FXML
    private CheckBox userActiveCheckBox;

    private User user;
    private GestionUser userService;

    public EditUser() {
        userService = new GestionUser();
    }

    public void initData(User user) {
        this.user = user;
        userEmailLabel.setText(user.getEmail());
        userActiveCheckBox.setSelected(user.getStatus().booleanValue());
    }

    @FXML
    private void saveChanges() {
        if (user != null) {
            user.setStatus(userActiveCheckBox.isSelected());
            userService.update(user); // Assuming you have a method to update the user in your UserService
        }
        closeDialog();
    }

    @FXML
    private void closeDialog() {
        userActiveCheckBox.getScene().getWindow().hide();
    }

}
