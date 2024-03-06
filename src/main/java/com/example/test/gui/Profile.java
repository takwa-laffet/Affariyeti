package com.example.test.gui;


import com.example.test.models.User;
import com.example.test.services.GestionUser;
import com.example.test.utils.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Profile   implements Initializable {

    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField email;
    @FXML
    private ImageView profileImage;
    @FXML
    private Button editButton;
    @FXML
    private Button updateButton;
    @FXML
    private ImageView profileImageView;
    @FXML
    private Button logoutButton;

    @FXML
    private Button uploadButton;
private User user;

    @FXML
    private void uploadImage(ActionEvent event) throws IOException {
        GestionUser us = new GestionUser();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        // Set the initial directory to the user's home directory
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        // Restrict file types to images
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        // Show the file chooser dialog
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            // Display the selected image in the ImageView
            Image image = new Image(selectedFile.toURI().toString());
            profileImageView.setImage(image);
            saveImageAndGetUrl(selectedFile.toPath().toUri().toURL().openStream(), selectedFile.getName());
            us.updateImage(this.user,selectedFile.getName());
            this.user.setImage(selectedFile.getName());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Image updated successfully");

        }
    }
    private void populateProfileInformation(User user) {

    }

    private void handleEditButtonAction() {
        // Implement edit profile action
    }

    public void enabelEditing(){
        nom.setDisable(false);
        prenom.setDisable(false);
        email.setDisable(false);

        updateButton.setVisible(true);
    }
    public void UpdateData(){
        GestionUser us = new GestionUser();
        User u = new User();
        u.setImage(this.user.getImage());
        u.setEmail(email.getText());
        u.setNom(nom.getText());
        u.setPrenom(prenom.getText());
        u.setStatus(true);
        u.setId(this.user.getId());
        u.setMdp(this.user.getMdp());
        u.setRole(this.user.getRole());

        us.update(u);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Informations Updated");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

        }
    }
    private String saveImageAndGetUrl(InputStream inputStream, String filename) throws IOException {
        // Define the directory where images will be stored
        String uploadDirectory = "src/main/resources/static/images"; // Change this to your desired directory path
        File directory = new File(uploadDirectory);
        if (!directory.exists()) {
            directory.mkdirs(); // Create the directory if it doesn't exist
        }

        // Save the image file to the upload directory
        String filePath = uploadDirectory + File.separator + filename;
        try (OutputStream outputStream = new FileOutputStream(new File(filePath))) {
            int read;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }

        // Return the URL of the saved image
        return  filename; // Adjust the URL format as needed
    }
    public void logout(){
        Stage stage = (Stage) updateButton.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/test/login.fxml"));
            Scene scene = new Scene(root);
            newStage.setScene(scene);
            newStage.show();
        } catch ( IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.user = Session.getSession().getUser();
        nom.setText(user.getNom());
        prenom.setText(user.getPrenom());
        email.setText(user.getEmail());
        String imageUrl = "/static/images/" + user.getImage(); // Assuming user.getImage() returns the image filename
        Image image = new Image(getClass().getResource(imageUrl).toExternalForm());
        profileImageView.setImage(image);
/*if(!user.getImage().isEmpty() && user.getImage()!=null) {
    String imageUrl = "/static/images/" + user.getImage(); // Assuming user.getImage() returns the image filename
    Image image = new Image(getClass().getResource(imageUrl).toExternalForm());
    profileImageView.setImage(image);
}*/

        System.out.println(user);
        populateProfileInformation(user);


    }
}
