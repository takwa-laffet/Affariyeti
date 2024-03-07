/*package tn.esprit.affarietygui.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import tn.esprit.affarietygui.models.Commentaire;
import tn.esprit.affarietygui.models.Publication;
import tn.esprit.affarietygui.services.CommentaireService;
import tn.esprit.affarietygui.services.GrosMotsService;
import tn.esprit.affarietygui.services.PublicationService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class UserComments {

    @FXML
    private VBox commentContainer;

    @FXML
    private TextArea commentTextArea;
    @FXML
    private TextField idclientfield;
    private Commentaire commentaire;
    private Publication publicationSelectionnee;
    @FXML
    private VBox notificationsBox;
    @FXML
    public void initialize() {
        // Vérifier si une publication est sélectionnée
        if (publicationSelectionnee != null) {
            // Afficher les commentaires de la publication sélectionnée
            CommentaireService commentaireService = new CommentaireService();
            try {
                List<Commentaire> commentaires = commentaireService.recuperer(publicationSelectionnee.getId_pub());
                afficherCommentaires(commentaires, publicationSelectionnee);
            } catch (SQLException e) {
                e.printStackTrace(); // Gérer l'exception correctement
            }
        }


    }

    public void afficherCommentaires(List<Commentaire> commentaires, Publication publication) {
        this.publicationSelectionnee = publication;
        commentContainer.getChildren().clear(); // Clear existing comments
        supprimerCommentairesAvecGrosMots(commentaires);
        // Load the image
        Image backgroundImage = new Image("file:C:\\Users\\marie\\IdeaProjects\\AffarietyGUI\\src\\main\\resources\\tn\\esprit\\affarietygui\\Top_Card1.png");

        for (Commentaire commentaire : commentaires) {
            String clientId = Integer.toString(commentaire.getId_client());
            Label label1 = new Label("Client: ");
            Label labelClientId = new Label(clientId);
            Label label = new Label("commentaire: ");
            Label labelContenu = new Label(commentaire.getContenu());
            Label label2 = new Label("date: ");
            Label labelDate = new Label();

            // Apply the CSS style to make the text bold or italic
            label1.setStyle("-fx-font-weight: bold;");
            labelClientId.setStyle("-fx-font-style: italic;");
            label.setStyle("-fx-font-weight: bold;");
            labelContenu.setStyle("-fx-font-style: italic;");
            label2.setStyle("-fx-font-weight: bold;");

            // Set the text for labelDate
            labelDate.setText(String.valueOf(commentaire.getDate_com()));
            labelDate.setStyle("-fx-font-style: italic;");

            MenuButton optionsButton = new MenuButton("Options");
            MenuItem supprimerMenuItem = new MenuItem("Supprimer");
            supprimerMenuItem.setOnAction(this::SupprimerCommentaire);
            MenuItem modifierMenuItem = new MenuItem("Modifier");
            modifierMenuItem.setOnAction(this::ModifierCommentaire);
            optionsButton.getItems().addAll(supprimerMenuItem, modifierMenuItem);

            optionsButton.setStyle("-fx-font-size: 10px;");

            HBox clientcontenubox = new HBox(10);
            clientcontenubox.getChildren().addAll(label1, labelClientId, label, labelContenu, label2, labelDate);

            // Create a VBox to hold the comment and set its background image
            VBox commentbox = new VBox(10);
            commentbox.setId("commentbox" + commentaire.getId_com());
            commentbox.setAlignment(Pos.CENTER_LEFT); // Align children to the left
            commentbox.setSpacing(5); // Set spacing between children
            commentbox.getChildren().addAll(clientcontenubox, optionsButton);

            // Set the background image for the VBox
            commentbox.setBackground(new Background(new BackgroundImage(backgroundImage,
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));


            // Set the height of the comment box
            commentbox.setMinHeight(100); // Set the desired height
            String optionImagePath = "C:\\Users\\marie\\IdeaProjects\\AffarietyGUI\\src\\main\\resources\\tn\\esprit\\affarietygui\\reglages.png";
            File optionImageFile = new File(optionImagePath);
            if (optionImageFile.exists()) {
                Image optionImage = new Image(optionImageFile.toURI().toString());
                ImageView optionImageView = new ImageView(optionImage);
                optionImageView.setFitWidth(22.0);
                optionImageView.setFitHeight(22.0);
                optionsButton.setGraphic(optionImageView);
            } else {
                System.out.println("File not found: " + optionImagePath);
            }

            // Add the comment box to the commentContainer
            commentContainer.getChildren().add(commentbox);
        }
    }

    public void supprimerCommentairesAvecGrosMots(List<Commentaire> commentaires) {
        // Récupérer les gros mots depuis le service GrosMotsService
        GrosMotsService grosMotsService = new GrosMotsService();
        List<String> grosMots = grosMotsService.getGrosMots();

        // Parcourir tous les commentaires
        for (Commentaire commentaire : commentaires) {
            // Vérifier si le commentaire contient des gros mots
            boolean commentaireAvecGrosMots = false;
            for (String mot : grosMots) {
                if (commentaire.getContenu().toLowerCase().contains(mot.toLowerCase())) {
                    commentaireAvecGrosMots = true;
                    break;
                }
            }

            // Si le commentaire contient des gros mots, le supprimer de la base de données
            if (commentaireAvecGrosMots) {
                // Appeler le service pour supprimer le commentaire de la base de données
                CommentaireService commentaireService = new CommentaireService();
                try {
                    commentaireService.supprimer(commentaire.getId_com());
                } catch (SQLException e) {
                    e.printStackTrace(); // Gérer l'exception correctement
                }
            }
        }
    }

    public void afficherNotification(String message) {
        Label notificationLabel = new Label(message);
        notificationsBox.getChildren().add(notificationLabel);

        // Masquer la notification après quelques secondes
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            notificationsBox.getChildren().remove(notificationLabel);
        }));
        timeline.play();
    }

    @FXML
    public void ajouterCommentaire(ActionEvent actionEvent) {
        if (publicationSelectionnee != null) {
            String clientText = idclientfield.getText();
            String commentaireText = commentTextArea.getText();
            if (!commentaireText.isEmpty()) {
                // Vérifier les gros mots
                GrosMotsService grosMotsService = new GrosMotsService();
                List<String> grosMots = grosMotsService.getGrosMots();
                for (String mot : grosMots) {
                    if (commentaireText.toLowerCase().contains(mot.toLowerCase())) {
                        // Afficher un message d'erreur
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur");
                        alert.setHeaderText(null);
                        alert.setContentText("Le commentaire contient des gros mots. Veuillez le modifier.");
                        alert.showAndWait();
                        return; // Sortir de la méthode si un gros mot est trouvé
                    }
                }

                // Afficher une boîte de dialogue de confirmation
                Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationDialog.setTitle("Confirmation");
                confirmationDialog.setHeaderText(null);
                confirmationDialog.setContentText("Voulez-vous vraiment ajouter ce commentaire ?");

                // Obtenir la réponse de l'utilisateur depuis la boîte de dialogue
                Optional<ButtonType> result = confirmationDialog.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Si l'utilisateur clique sur "OK", ajouter le commentaire
                    Commentaire commentaire = new Commentaire();
                    commentaire.setId_client(Integer.parseInt(clientText));
                    commentaire.setContenu(commentaireText);
                    commentaire.setPublication(publicationSelectionnee); // Set the associated publication

                    // Call the service to add the comment
                    CommentaireService commentaireService = new CommentaireService();

                    try {
                        commentaireService.ajouter(commentaire);
                        afficherNotification("vous avez reçu un commentaire sur votre publication");
                        // Afficher une boîte de dialogue de confirmation
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Commentaire ajouté");
                        alert.setHeaderText(null);
                        alert.setContentText("Commentaire ajouté avec succès!");
                        alert.showAndWait();

                        // Clear the text area after adding the comment
                        commentTextArea.clear();

                        // Refresh the comments view
                        List<Commentaire> commentaires = commentaireService.recuperer(publicationSelectionnee.getId_pub());
                        afficherCommentaires(commentaires, publicationSelectionnee);
                    } catch (SQLException e) {
                        e.printStackTrace(); // Handle the exception appropriately
                    }
                }
            } else {
                // Display an error message if the comment text is empty
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("entrez un commentaire!");
                alert.showAndWait();
            }
        } else {
            // Display an error message if no publication is selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("pas de publication selectionné.");
            alert.showAndWait();
        }
    }


    public void SupprimerCommentaire(ActionEvent actionEvent) {
        // Récupérer l'item de menu sur lequel l'utilisateur a cliqué
        MenuItem menuItem = (MenuItem) actionEvent.getSource();

        // Récupérer le parent du MenuItem, qui est le MenuButton
        MenuButton menuButton = (MenuButton) menuItem.getParentPopup().getOwnerNode();

        // Récupérer le parent du MenuButton, qui est le VBox contenant le commentaire
        VBox commentBox = (VBox) menuButton.getParent();

        // Récupérer l'ID du commentaire à supprimer à partir de l'ID du VBox
        String commentId = commentBox.getId().replace("commentbox", "");

        // Afficher une boîte de dialogue de confirmation
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.setHeaderText(null);
        confirmationDialog.setContentText("Voulez-vous vraiment supprimer ce commentaire ?");

        // Obtenir la réponse de l'utilisateur depuis la boîte de dialogue
        Optional<ButtonType> result = confirmationDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Appeler le service pour supprimer le commentaire de la base de données
            CommentaireService commentaireService = new CommentaireService();
            try {
                commentaireService.supprimer(Integer.parseInt(commentId));

                // Si la suppression réussit dans la base de données, supprimer le VBox du commentContainer
                commentContainer.getChildren().remove(commentBox);

                // Afficher un message de confirmation
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Commentaire supprimé");
                alert.setHeaderText(null);
                alert.setContentText("Le commentaire a été supprimé avec succès!");
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace(); // Gérer l'exception correctement
            }
        }
    }

    public void ModifierCommentaire(ActionEvent actionEvent) {
        // Retrieve the selected comment ID
        MenuItem menuItem = (MenuItem) actionEvent.getSource();
        MenuButton menuButton = (MenuButton) menuItem.getParentPopup().getOwnerNode();
        VBox commentBox = (VBox) menuButton.getParent();
        String commentId = commentBox.getId().replace("commentbox", "");

        // Retrieve the corresponding Commentaire object from the database
        CommentaireService commentaireService = new CommentaireService();
        try {
            Commentaire commentaire = commentaireService.getCommentsById(Integer.parseInt(commentId));
            if (commentaire != null) {
                // Pass the comment object and a reference to this controller to the UserModifCom interface
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/affarietygui/UserModifCom.fxml"));
                Parent root = loader.load();
                UserModifCom controller = loader.getController();
                controller.initData(commentaire, this); // Pass a reference to this controller
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                System.out.println("Aucun commentaire trouvé avec l'ID : " + commentId);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update comments after modification
    public void updateCommentaire(Commentaire commentaire) {
        // Refresh the comments view
        CommentaireService commentaireService = new CommentaireService();
        try {
            List<Commentaire> commentaires = commentaireService.recuperer(publicationSelectionnee.getId_pub());
            afficherCommentaires(commentaires, publicationSelectionnee);
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    public void retour(ActionEvent actionEvent) {
        commentTextArea.getScene().getWindow().hide();
    }

    public void filtrerParPlusRecentes(ActionEvent actionEvent) {
        if (publicationSelectionnee != null) {
            CommentaireService commentaireService = new CommentaireService();
            try {
                List<Commentaire> commentaires = commentaireService.recuperer(publicationSelectionnee.getId_pub());
                Collections.sort(commentaires, Comparator.comparing(Commentaire::getDate_com).reversed());
                afficherCommentaires(commentaires, publicationSelectionnee);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void filtrerParPlusAnciennes(ActionEvent actionEvent) {
        if (publicationSelectionnee != null) {
            CommentaireService commentaireService = new CommentaireService();
            try {
                List<Commentaire> commentaires = commentaireService.recuperer(publicationSelectionnee.getId_pub());
                Collections.sort(commentaires, Comparator.comparing(Commentaire::getDate_com));
                afficherCommentaires(commentaires, publicationSelectionnee);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void clearFilter(ActionEvent actionEvent) {
       initialize();
    }
}*/
package tn.esprit.affariety.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import tn.esprit.affariety.models.Commentaire;
import tn.esprit.affariety.models.Publication;
import tn.esprit.affariety.services.Chat;
import tn.esprit.affariety.services.CommentaireService;
import tn.esprit.affariety.services.GrosMotsService;
import tn.esprit.affariety.services.PublicationService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class UserComments {

    @FXML
    private VBox commentContainer;

    @FXML
    private TextArea commentTextArea;
    @FXML
    private TextField idclientfield;
    private Commentaire commentaire;
    private Publication publicationSelectionnee;
    @FXML
    private VBox notificationsBox;
    @FXML
    public void initialize() {
        // Vérifier si une publication est sélectionnée
        if (publicationSelectionnee != null) {
            // Afficher les commentaires de la publication sélectionnée
            CommentaireService commentaireService = new CommentaireService();
            try {
                List<Commentaire> commentaires = commentaireService.recuperer(publicationSelectionnee.getId_pub());
                afficherCommentaires(commentaires, publicationSelectionnee);
            } catch (SQLException e) {
                e.printStackTrace(); // Gérer l'exception correctement
            }
        }


    }

    public void afficherCommentaires(List<Commentaire> commentaires, Publication publication) {
        this.publicationSelectionnee = publication;
        commentContainer.getChildren().clear(); // Clear existing comments
        supprimerCommentairesAvecGrosMots(commentaires);
        // Load the image
        Image backgroundImage = new Image("file:C:\\Users\\marie\\IdeaProjects\\AffarietyGUI\\src\\main\\resources\\tn\\esprit\\affarietygui\\Top_Card1.png");

        for (Commentaire commentaire : commentaires) {
            String clientId = commentaire.getUser().getPrenom() + " " + commentaire.getUser().getNom();
            Label label1 = new Label("User: ");
            Label labelClientId = new Label(clientId);
            Label label = new Label("commentaire: ");
            Label labelContenu = new Label(commentaire.getContenu());
            Label label2 = new Label("date: ");
            Label labelDate = new Label();

            // Apply the CSS style to make the text bold or italic
            label1.setStyle("-fx-font-weight: bold;");
            labelClientId.setStyle("-fx-font-style: italic;");
            label.setStyle("-fx-font-weight: bold;");
            labelContenu.setStyle("-fx-font-style: italic;");
            label2.setStyle("-fx-font-weight: bold;");

            // Set the text for labelDate
            labelDate.setText(String.valueOf(commentaire.getDate_com()));
            labelDate.setStyle("-fx-font-style: italic;");

            MenuButton optionsButton = new MenuButton("Options");
            MenuItem supprimerMenuItem = new MenuItem("Supprimer");
            supprimerMenuItem.setOnAction(this::SupprimerCommentaire);
            MenuItem modifierMenuItem = new MenuItem("Modifier");
            modifierMenuItem.setOnAction(this::ModifierCommentaire);
            optionsButton.getItems().addAll(supprimerMenuItem, modifierMenuItem);

            optionsButton.setStyle("-fx-font-size: 10px;");

            HBox clientcontenubox = new HBox(10);
            clientcontenubox.getChildren().addAll(label1, labelClientId, label, labelContenu, label2, labelDate);

            // Create a VBox to hold the comment and set its background image
            VBox commentbox = new VBox(10);
            commentbox.setId("commentbox" + commentaire.getId_com());
            commentbox.setAlignment(Pos.CENTER_LEFT); // Align children to the left
            commentbox.setSpacing(5); // Set spacing between children
            commentbox.getChildren().addAll(clientcontenubox, optionsButton);

            // Set the background image for the VBox
            commentbox.setBackground(new Background(new BackgroundImage(backgroundImage,
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));


            // Set the height of the comment box
            commentbox.setMinHeight(100); // Set the desired height
            String optionImagePath = "C:\\Users\\marie\\IdeaProjects\\AffarietyGUI\\src\\main\\resources\\tn\\esprit\\affarietygui\\reglages.png";
            File optionImageFile = new File(optionImagePath);
            if (optionImageFile.exists()) {
                Image optionImage = new Image(optionImageFile.toURI().toString());
                ImageView optionImageView = new ImageView(optionImage);
                optionImageView.setFitWidth(22.0);
                optionImageView.setFitHeight(22.0);
                optionsButton.setGraphic(optionImageView);
            } else {
                System.out.println("File not found: " + optionImagePath);
            }

            // Add the comment box to the commentContainer
            commentContainer.getChildren().add(commentbox);
        }
    }

    public void supprimerCommentairesAvecGrosMots(List<Commentaire> commentaires) {
        // Récupérer les gros mots depuis le service GrosMotsService
        GrosMotsService grosMotsService = new GrosMotsService();
        List<String> grosMots = grosMotsService.getGrosMots();

        // Parcourir tous les commentaires
        for (Commentaire commentaire : commentaires) {
            // Vérifier si le commentaire contient des gros mots
            boolean commentaireAvecGrosMots = false;
            for (String mot : grosMots) {
                if (commentaire.getContenu().toLowerCase().contains(mot.toLowerCase())) {
                    commentaireAvecGrosMots = true;
                    break;
                }
            }

            // Si le commentaire contient des gros mots, le supprimer de la base de données
            if (commentaireAvecGrosMots) {
                // Appeler le service pour supprimer le commentaire de la base de données
                CommentaireService commentaireService = new CommentaireService();
                try {
                    commentaireService.supprimer(commentaire.getId_com());
                } catch (SQLException e) {
                    e.printStackTrace(); // Gérer l'exception correctement
                }
            }
        }
    }

    public void afficherNotification(String message) {
        Label notificationLabel = new Label(message);
        notificationsBox.getChildren().add(notificationLabel);

        // Masquer la notification après quelques secondes
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            notificationsBox.getChildren().remove(notificationLabel);
        }));
        timeline.play();
    }

    @FXML
    public void ajouterCommentaire(ActionEvent actionEvent) {
        if (publicationSelectionnee != null) {
            String clientText = idclientfield.getText();
            String commentaireText = commentTextArea.getText();
            if (!commentaireText.isEmpty()) {
                // Vérifier les gros mots
                Chat chat = new Chat();
                String resultatDetection = chat.badword(commentaireText);

                // Vérifier le résultat de la détection des mots haineux
                if (resultatDetection.equals("1")) {
                    // Afficher un message d'erreur si le contenu contient un discours haineux
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Le contenu de la publication contient un discours haineux. Veuillez le modifier.");
                    alert.showAndWait();
                } else if (resultatDetection.equals("0")) {

                // Afficher une boîte de dialogue de confirmation
                Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationDialog.setTitle("Confirmation");
                confirmationDialog.setHeaderText(null);
                confirmationDialog.setContentText("Voulez-vous vraiment ajouter ce commentaire ?");

                // Obtenir la réponse de l'utilisateur depuis la boîte de dialogue
                Optional<ButtonType> result = confirmationDialog.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Si l'utilisateur clique sur "OK", ajouter le commentaire
                    Commentaire commentaire = new Commentaire();
                    commentaire.setId_client(Integer.parseInt(clientText));
                    commentaire.setContenu(commentaireText);
                    commentaire.setPublication(publicationSelectionnee); // Set the associated publication

                    // Call the service to add the comment
                    CommentaireService commentaireService = new CommentaireService();

                    try {
                        commentaireService.ajouter(commentaire);
                        afficherNotification("vous avez reçu un commentaire sur votre publication");
                        // Afficher une boîte de dialogue de confirmation
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Commentaire ajouté");
                        alert.setHeaderText(null);
                        alert.setContentText("Commentaire ajouté avec succès!");
                        alert.showAndWait();

                        // Clear the text area after adding the comment
                        commentTextArea.clear();

                        // Refresh the comments view
                        List<Commentaire> commentaires = commentaireService.recuperer(publicationSelectionnee.getId_pub());
                        afficherCommentaires(commentaires, publicationSelectionnee);
                    } catch (SQLException e) {
                        e.printStackTrace(); // Handle the exception appropriately
                    }
                }}
            } else {
                // Display an error message if the comment text is empty
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("entrez un commentaire!");
                alert.showAndWait();
            }
        } else {
            // Display an error message if no publication is selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("pas de publication selectionné.");
            alert.showAndWait();
        }
    }


    public void SupprimerCommentaire(ActionEvent actionEvent) {
        // Récupérer l'item de menu sur lequel l'utilisateur a cliqué
        MenuItem menuItem = (MenuItem) actionEvent.getSource();

        // Récupérer le parent du MenuItem, qui est le MenuButton
        MenuButton menuButton = (MenuButton) menuItem.getParentPopup().getOwnerNode();

        // Récupérer le parent du MenuButton, qui est le VBox contenant le commentaire
        VBox commentBox = (VBox) menuButton.getParent();

        // Récupérer l'ID du commentaire à supprimer à partir de l'ID du VBox
        String commentId = commentBox.getId().replace("commentbox", "");

        // Afficher une boîte de dialogue de confirmation
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.setHeaderText(null);
        confirmationDialog.setContentText("Voulez-vous vraiment supprimer ce commentaire ?");

        // Obtenir la réponse de l'utilisateur depuis la boîte de dialogue
        Optional<ButtonType> result = confirmationDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Appeler le service pour supprimer le commentaire de la base de données
            CommentaireService commentaireService = new CommentaireService();
            try {
                commentaireService.supprimer(Integer.parseInt(commentId));

                // Si la suppression réussit dans la base de données, supprimer le VBox du commentContainer
                commentContainer.getChildren().remove(commentBox);

                // Afficher un message de confirmation
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Commentaire supprimé");
                alert.setHeaderText(null);
                alert.setContentText("Le commentaire a été supprimé avec succès!");
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace(); // Gérer l'exception correctement
            }
        }
    }

    public void ModifierCommentaire(ActionEvent actionEvent) {
        // Retrieve the selected comment ID
        MenuItem menuItem = (MenuItem) actionEvent.getSource();
        MenuButton menuButton = (MenuButton) menuItem.getParentPopup().getOwnerNode();
        VBox commentBox = (VBox) menuButton.getParent();
        String commentId = commentBox.getId().replace("commentbox", "");

        // Retrieve the corresponding Commentaire object from the database
        CommentaireService commentaireService = new CommentaireService();
        try {
            Commentaire commentaire = commentaireService.getCommentsById(Integer.parseInt(commentId));
            if (commentaire != null) {
                // Pass the comment object and a reference to this controller to the UserModifCom interface
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/affarietygui/UserModifCom.fxml"));
                Parent root = loader.load();
                UserModifCom controller = loader.getController();
                controller.initData(commentaire, this); // Pass a reference to this controller
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                System.out.println("Aucun commentaire trouvé avec l'ID : " + commentId);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update comments after modification
    public void updateCommentaire(Commentaire commentaire) {
        // Refresh the comments view
        CommentaireService commentaireService = new CommentaireService();
        try {
            List<Commentaire> commentaires = commentaireService.recuperer(publicationSelectionnee.getId_pub());
            afficherCommentaires(commentaires, publicationSelectionnee);
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    public void retour(ActionEvent actionEvent) {
        commentTextArea.getScene().getWindow().hide();
    }

    public void filtrerParPlusRecentes(ActionEvent actionEvent) {
        if (publicationSelectionnee != null) {
            CommentaireService commentaireService = new CommentaireService();
            try {
                List<Commentaire> commentaires = commentaireService.recuperer(publicationSelectionnee.getId_pub());
                Collections.sort(commentaires, Comparator.comparing(Commentaire::getDate_com).reversed());
                afficherCommentaires(commentaires, publicationSelectionnee);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void filtrerParPlusAnciennes(ActionEvent actionEvent) {
        if (publicationSelectionnee != null) {
            CommentaireService commentaireService = new CommentaireService();
            try {
                List<Commentaire> commentaires = commentaireService.recuperer(publicationSelectionnee.getId_pub());
                Collections.sort(commentaires, Comparator.comparing(Commentaire::getDate_com));
                afficherCommentaires(commentaires, publicationSelectionnee);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void clearFilter(ActionEvent actionEvent) {
        initialize();
    }


}
