package tn.esprit.affarietygui.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import tn.esprit.affarietygui.models.Commentaire;
import tn.esprit.affarietygui.models.Publication;
import tn.esprit.affarietygui.services.CommentaireService;
import tn.esprit.affarietygui.services.PublicationService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class UserAffichePub {

    @FXML
    private VBox publicationContainer; // Pour contenir toutes les publications
    @FXML
    private TextField searchField;
    @FXML
    private VBox notificationBox;

    private final PublicationService publicationService = new PublicationService();
    private List<Publication> publications;

    @FXML
    public void initialize() {
        // Au chargement de la vue, récupérer et afficher les publications
        afficherPublications();

        // Ajouter un écouteur d'événements sur le champ de recherche
        searchField.setOnKeyPressed(event -> {
            // Vérifier si la touche pressée est Entrée
            if (event.getCode().equals(KeyCode.ENTER)) {
                // Récupérer le texte saisi dans le champ de recherche
                String searchTerm = searchField.getText().trim().toLowerCase();

                // Filtrer les publications en fonction du texte saisi dans le champ de recherche
                List<Publication> filteredPublications = publications.stream()
                        .filter(publication -> publication.getContenu().toLowerCase().contains(searchTerm))
                        .collect(Collectors.toList());

                // Afficher les publications filtrées
                afficherPublications(filteredPublications);
            }
        });
    }

    private void afficherPublications() {
        try {
            publications = publicationService.recuperer();
            afficherPublications(publications);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Méthode pour afficher une notification en temps réel
    public void afficherNotification(String message) {
        Label notificationLabel = new Label(message);
        notificationBox.getChildren().add(notificationLabel);

        // Masquer la notification après quelques secondes
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            notificationBox.getChildren().remove(notificationLabel);
        }));
        timeline.play();
    }

    private void afficherPublications(List<Publication> publicationsToDisplay) {
        publicationContainer.getChildren().clear();

        for (Publication publication : publicationsToDisplay) {
            Label contenuLabel = new Label(publication.getContenu());
            contenuLabel.setStyle("-fx-font-weight: bold;");

            Label dateLabel = new Label("Date: " + publication.getDate_pub().toString());

            ImageView imageView = new ImageView(publication.getPhoto());
            imageView.setFitWidth(300);
            imageView.setFitHeight(200);

            Label likesLabel = new Label("\uD83D\uDC4D:" + publication.getNb_likes());
            Label dislikesLabel = new Label("\uD83D\uDC4E: " + publication.getNb_dislike());

            Button likeButton = new Button("j'aime");
            likeButton.setUserData(publication.getId_pub());
            likeButton.setOnAction(this::ClickLike);

            Button dislikeButton = new Button("j'aime pas");
            dislikeButton.setUserData(publication.getId_pub());
            dislikeButton.setOnAction(this::ClickDislike);

            MenuButton optionsButton = new MenuButton("Options");
            MenuItem supprimerMenuItem = new MenuItem("Supprimer");
            supprimerMenuItem.setOnAction(this::SupprimerPublication);
            MenuItem modifierMenuItem = new MenuItem("Modifier");
            modifierMenuItem.setOnAction(this::ModifierPublication);
            optionsButton.getItems().addAll(supprimerMenuItem, modifierMenuItem);

            HBox likesDislikesBox = new HBox(65);
            likesDislikesBox.getChildren().addAll(likesLabel, dislikesLabel);

            Button commentButton = new Button("Commentaires");
            commentButton.setOnAction(this::ClickComments);
            HBox likeDislikeBox = new HBox(10);
            likeDislikeBox.getChildren().addAll(likeButton, dislikeButton);

            HBox commentOptionsBox = new HBox(10);
            commentOptionsBox.getChildren().addAll(commentButton, optionsButton);

            VBox publicationBox = new VBox(10);
            publicationBox.setId("publicationBox_" + publication.getId_pub());
            publicationBox.getChildren().addAll(contenuLabel, imageView, likesDislikesBox, likeDislikeBox, commentOptionsBox, dateLabel);

            publicationContainer.getChildren().add(publicationBox);
        }
    }

    @FXML
    public void ClickLike(ActionEvent actionEvent) {
        Button likeButton = (Button) actionEvent.getSource();
        int publicationId = (int) likeButton.getUserData(); // Récupérer l'identifiant de la publication à partir des données personnalisées

        // Trouver la publication correspondante dans la liste des publications
        Publication publicationSelectionnee = publications.stream()
                .filter(pub -> pub.getId_pub() == publicationId)
                .findFirst()
                .orElse(null);

        if (publicationSelectionnee != null) {
            // Incrémenter le nombre de likes
            publicationSelectionnee.setNb_likes(publicationSelectionnee.getNb_likes() + 1);

            // Modifier la publication dans la base de données
            try {
                publicationService.modifier(publicationSelectionnee);
            } catch (SQLException e) {
                e.printStackTrace(); // Gérer l'exception
            }

            // Actualiser l'affichage
            afficherPublications();
            afficherNotification("Vous avez reçu un like sur votre publication");
        }
    }

    @FXML
    public void ClickDislike(ActionEvent actionEvent) {
        Button dislikeButton = (Button) actionEvent.getSource();
        int publicationId = (int) dislikeButton.getUserData(); // Récupérer l'identifiant de la publication à partir des données personnalisées

        // Trouver la publication correspondante dans la liste des publications
        Publication publicationSelectionnee = publications.stream()
                .filter(pub -> pub.getId_pub() == publicationId)
                .findFirst()
                .orElse(null);

        if (publicationSelectionnee != null) {
            // Incrémenter le nombre de dislikes
            publicationSelectionnee.setNb_dislike(publicationSelectionnee.getNb_dislike() + 1);

            // Modifier la publication dans la base de données
            try {
                publicationService.modifier(publicationSelectionnee);
            } catch (SQLException e) {
                e.printStackTrace(); // Gérer l'exception
            }

            // Actualiser l'affichage
            afficherPublications();
            afficherNotification("Vous avez reçu un dislike sur votre publication");
        }
    }

    @FXML
    public void ClickComments(ActionEvent actionEvent) {
        Button commentButton = (Button) actionEvent.getSource();
        HBox hbox = (HBox) commentButton.getParent();
        VBox vbox = (VBox) hbox.getParent(); // Get the parent VBox

        // Get the index of the VBox in the container
        int index = publicationContainer.getChildren().indexOf(vbox);
        Publication publicationSelectionnee = null;

        if (index >= 0 && index < publications.size()) {
            publicationSelectionnee = publications.get(index);
        }

        // Check if the selected publication is not null
        if (publicationSelectionnee != null) {
            // Retrieve comments for the selected publication
            try {
                CommentaireService commentaireService = new CommentaireService();
                List<Commentaire> commentaires = commentaireService.recuperer(publicationSelectionnee.getId_pub());

                // Load the UserComments view
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/affarietygui/UserComments.fxml"));
                Parent root = loader.load();
                UserComments userCommentsController = loader.getController();

                // Call the method to display the comments
                userCommentsController.afficherCommentaires(commentaires, publicationSelectionnee);

                // Display the UserComments interface in a new window
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        } else {
            // If the selected publication is null, display an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No publication selected.");
            alert.showAndWait();
        }
    }

    public void SupprimerPublication(ActionEvent actionEvent) {
        MenuItem menuItem = (MenuItem) actionEvent.getSource();
        ContextMenu menu = menuItem.getParentPopup();
        MenuButton menuButton = (MenuButton) menu.getOwnerNode();
        HBox hBox = (HBox) menuButton.getParent();
        VBox vbox = (VBox) hBox.getParent();
        Publication publicationSelectionnee = getPublicationSelectionnee(vbox);

        // Créer une boîte de dialogue de confirmation
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation de suppression");
        confirmationDialog.setHeaderText(null);
        confirmationDialog.setContentText("Êtes-vous sûr de vouloir supprimer cette publication ?");

        // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
        confirmationDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // L'utilisateur a cliqué sur OK, procéder à la suppression
                try {
                    publicationService.supprimer(publicationSelectionnee.getId_pub());
                    publicationContainer.getChildren().remove(vbox);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    private void ModifierPublication(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        ContextMenu menu = menuItem.getParentPopup();
        // Récupérer le MenuButton parent du MenuItem
        MenuButton menuButton = (MenuButton) menu.getOwnerNode();
        HBox hBox = (HBox) menuButton.getParent();
        VBox vbox = (VBox) hBox.getParent();
        Publication publicationSelectionnee = getPublicationSelectionnee(vbox);

        // Charger l'interface de modification avec les détails de la publication sélectionnée
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/affarietygui/UserModifPub.fxml"));
        Parent root;
        try {
            root = loader.load();
            UserModifPub controller = loader.getController();
            controller.initData(publicationSelectionnee, this); // Passer les données à UserModifPub
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour récupérer la publication sélectionnée
    private Publication getPublicationSelectionnee(VBox vbox) {
        // Trouver l'index de la VBox cliquée
        int index = publicationContainer.getChildren().indexOf(vbox);
        // Vérifier si l'index est valide
        if (index >= 0 && index < publications.size()) {
            // Récupérer la publication correspondante dans votre liste de publications
            return publications.get(index);
        } else {
            return null; // Retourner null si l'index est invalide
        }
    }
}
