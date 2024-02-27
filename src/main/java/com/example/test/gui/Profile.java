package com.example.test.gui;


import com.example.test.models.User;
import com.example.test.services.GestionUser;
import com.example.test.utils.Session;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class Profile   implements Initializable {

    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField email;

    @FXML
    private Button editButton;
    @FXML
    private Button updateButton;
    @FXML
    private ImageView profileImageView;

    @FXML
    private Button uploadButton;

    public void enabelEditing(){
        nom.setDisable(false);
        prenom.setDisable(false);
        email.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User user= Session.getSession().getUser();
        if(!user.getRole().equals("admin")){
            nom.setText(user.getNom());
            prenom.setText(user.getPrenom());
            email.setText(user.getEmail());
            updateButton.setVisible(true);
        }
    }

    public void UpdateData(){
        GestionUser us = new GestionUser();
        User currentUser = Session.getSession().getUser();
        currentUser.setNom(nom.getText());
        currentUser.setPrenom(prenom.getText());
        currentUser.setEmail(email.getText());
        System.out.println(currentUser);
        us.update(currentUser);

    }
}
