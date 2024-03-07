package tn.esprit.affariety.gui;

import tn.esprit.affariety.models.User;
import tn.esprit.affariety.services.GestionUser;
import tn.esprit.affariety.utils.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class Login  implements Initializable {

    private GestionUser gs = new GestionUser();

    @FXML
    private PasswordField passwordTextfield;
    @FXML
    private  Button logginButton;
    @FXML
    private TextField input2Email;

    @FXML
    private Label error;

    private Stage primaryStage;
    private User user;
    @FXML
    private Hyperlink forgetP;

    // Set the primaryStage from your main application class
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Deprecated


    @FXML
    public void LOGIN(ActionEvent actionEvent) throws IOException {
        error.setText("");

        // Get user input
        String email = input2Email.getText();
        String password = passwordTextfield.getText();

        // Validate input (you may want to add more sophisticated validation)
        if (email.isEmpty() || password.isEmpty()) {
            error.setText("Please enter both email and password.");
            return;
        }

        // Validate email format using regex
        if (!isValidEmail(email)) {
            error.setText("Invalid email format.");
            return;
        }
        User user = gs.getUserByEmail(email);
        if(user == null) {
            error.setText("User not found.");
            return;
        }
        String enteredPassword = passwordTextfield.getText();
        boolean passwordMatch = BCrypt.checkpw(enteredPassword, user.getMdp());
        if(!passwordMatch) {
            error.setText("Invalid password.");
            return;
        }else {
Session.StartSession(user);
            if (user.getRole().equals("admin")) {


                FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/affariety/AjouterCommande.fxml"));
                Parent profileInterface = loader.load();

                // Get the controller instance

                // Initialize data using the controller's method

                Scene profileScene = new Scene(profileInterface);
                Stage profileStage = new Stage();
                profileStage.setScene(profileScene);

                // Close the current stage (assuming loginButton is accessible from here)
                Stage currentStage = (Stage) logginButton.getScene().getWindow();
                currentStage.close();

                // Show the profile stage
                profileStage.show();
            }else {
                goToProfile();
            }

        }

    }
    public void navigateToMainScene() {
        try {
            // Load the FXML file for the main scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("inscription.fxml"));
            Parent root = loader.load();

            // Create a new scene with the loaded FXML file
            Scene mainScene = new Scene(root);

            // Set the new scene to the primary stage
            primaryStage.setScene(mainScene);

            // Show the stage with the new scene
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    }
    private boolean isValidEmail(String email) {
        // Simple email format validation using regex
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
    @FXML
    private void forgetPassword(ActionEvent event){
        GestionUser userService = new GestionUser();
        Stage resetPasswordStage = new Stage();
        Parent resetPasswordInterface;
        try {
            resetPasswordInterface = FXMLLoader.load(getClass().getResource("/tn/esprit/affariety/ForgetPassword.fxml"));
            Scene resetPasswordScene = new Scene(resetPasswordInterface);
            resetPasswordStage.setScene(resetPasswordScene);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
//afficher une nouvelle fenêtre
            // Show the UserInterface stage
            resetPasswordStage.show();

        } catch (IOException ex) {
        }

    }
    @FXML
    public void registeraction(ActionEvent actionEvent) throws IOException {
        // Your registration logic here
        navigateToRegistrationScene();
    }

    public void navigateToRegistrationScene() throws IOException {
        Stage signupStage = new Stage();
        Parent signupInterface = FXMLLoader.load(getClass().getResource("/tn/esprit/affariety/inscreption.fxml"));
        Scene signupScene = new Scene(signupInterface);
        signupStage.setScene(signupScene);
        Stage currentStage = (Stage) logginButton.getScene().getWindow();
        currentStage.close();
        signupStage.show();
    }
    public void goToProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/affariety/CarCards.fxml"));
            Parent profileInterface = loader.load();

            // Get the controller instance

            // Initialize data using the controller's method

            Scene profileScene = new Scene(profileInterface);
            Stage profileStage = new Stage();
            profileStage.setScene(profileScene);

            // Close the current stage (assuming loginButton is accessible from here)
            Stage currentStage = (Stage) logginButton.getScene().getWindow();
            currentStage.close();

            // Show the profile stage
            profileStage.show();
        }catch (Exception e ){
            System.out.println(e.getMessage());
        }
    }
    public void goToCodePromo() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/test/DashboardAdmin.fxml"));
        Parent profileInterface = loader.load();


      DashboardAdmin codePromoListController = loader.getController();





        Scene profileScene = new Scene(profileInterface);
        Stage profileStage = new Stage();
        profileStage.setScene(profileScene);


        Stage currentStage = (Stage) logginButton.getScene().getWindow();
        currentStage.close();




        profileStage.show();

}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Session.clearSession();
    }
}