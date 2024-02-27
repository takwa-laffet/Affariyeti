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
        error.setText("");
        String email = inputEmail.getText();
        String password = passwordField.getText();
        String cpassword = confirmPassword.getText();
        String name = inputName.getText();
        String firstname = inputFirstName.getText();

        if (email.isEmpty() || password.isEmpty() || name.isEmpty() || firstname.isEmpty() || cpassword.isEmpty()) {
            error.setText("Les champs sont obligatoires");}
            else {
            if (!password.equals(cpassword)) {
                error.setText("Les mots de passe ne sont pas identiques");
            } else if (gs.userExists(name, firstname, password, email)) {
                error.setText("Cet utilisateur existe déjà");
            } else {
                // Ajoutez une vérification de la forme de l'e-mail
                if (isValidEmail(email)) {
                    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
                    System.out.printf(hashedPassword);
                    // Utilisez ces variables pour créer un utilisateur ou effectuer d'autres actions
                    User user = new User(email, hashedPassword, false, name, firstname,"client");
                    gs.Create(user);
                    welcome.setText("Utilisateur créé avec succès!");
                    // Vous pouvez également réinitialiser les champs de saisie ou effectuer d'autres actions après la création
                    inputEmail.clear();
                    passwordField.clear();
                    confirmPassword.clear();
                    inputName.clear();
                    inputFirstName.clear();
                } else {
                    error.setText("Veuillez entrer une adresse e-mail valide");
                }
            }
        }
    }

    // Validation de la forme de l'e-mail
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+(com|tn|esprit\\.tn)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public void backAction(ActionEvent event) throws IOException {
        Stage signupStage = new Stage();
        Parent signupInterface = FXMLLoader.load(getClass().getResource("/com/example/test/login.fxml"));
        Scene signupScene = new Scene(signupInterface);
        signupStage.setScene(signupScene);
        Stage currentStage = (Stage) passwordField.getScene().getWindow();
        currentStage.close();
        signupStage.show();
    }
}