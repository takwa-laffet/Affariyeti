package com.example.test.gui;

import com.example.test.models.User;
import com.example.test.services.GestionUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Signup {
    private GestionUser gs = new GestionUser();

    @FXML
    private TextField inputFirstName;

    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private TextField inputName;

    @FXML
    private TextField inputEmail;

    @FXML
    private Label error;
    @FXML
    private Label welcome;

    @FXML

    public void register(ActionEvent event) {
        error.setText("");

        // Récupérer les valeurs des champs
        String email = inputEmail.getText();
        String password = passwordField.getText();
        String cpassword = confirmPassword.getText();
        String name = inputName.getText();
        String firstname = inputFirstName.getText();

        // Vérifier si tous les champs sont remplis
        if (email.isEmpty() || password.isEmpty() || name.isEmpty() || firstname.isEmpty() || cpassword.isEmpty()) {
            error.setText("Les champs sont obligatoires");
        } else {
            // Vérifier si les mots de passe correspondent
            if (!password.equals(cpassword)) {
                error.setText("Les mots de passe ne sont pas identiques");
            } else if (gs.userExists(name, firstname, password, email)) {
                error.setText("Cet utilisateur existe déjà");
            } else {
                // Vérifier la complexité du mot de passe
                if (isValidPassword(password)) {
                    // Hasher le mot de passe avant de le stocker
                    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
                    System.out.printf(hashedPassword);

                    // Créer un utilisateur et l'ajouter à la base de données
                    User user = new User(email, hashedPassword, false, name, firstname, "client",null);
                    gs.Create(user);

                    // Afficher un message de réussite
                    welcome.setText("Utilisateur créé avec succès!");

                    // Réinitialiser les champs de saisie
                    inputEmail.clear();
                    passwordField.clear();
                    confirmPassword.clear();
                    inputName.clear();
                    inputFirstName.clear();
                } else {
                    error.setText("Veuillez choisir un mot de passe valide (au moins une majuscule, un nombre, un caractère spécial et une longueur minimale de 10 caractères)");
                }
            }
        }
    }

    // Vérification de la complexité du mot de passe
    private boolean isValidPassword(String password) {
        // Utiliser une expression régulière pour vérifier la complexité du mot de passe
        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&*()_+{}:;,.<>?/\\\\|\\[\\]~]).{10,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
    public void backAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/test/login.fxml"));
            Parent profileInterface = loader.load();

            // Get the controller instance

            // Initialize data using the controller's method

            Scene profileScene = new Scene(profileInterface);
            Stage profileStage = new Stage();
            profileStage.setScene(profileScene);

            // Close the current stage (assuming loginButton is accessible from here)
            Stage currentStage = (Stage) inputEmail.getScene().getWindow();
            currentStage.close();

            // Show the profile stage
            profileStage.show();
        }catch (Exception e ){
            System.out.println(e.getMessage());
        }
    }
}