package tn.esprit.affariety.controllers;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
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
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import tn.esprit.affariety.models.Commentaire;
import tn.esprit.affariety.models.Publication;
import tn.esprit.affariety.services.Chat;
import tn.esprit.affariety.services.CommentaireService;
import tn.esprit.affariety.services.GrosMotsService;
import tn.esprit.affariety.services.PublicationService;

import javax.security.auth.callback.ConfirmationCallback;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class
UserAffichePub {

    @FXML
    private VBox publicationContainer; // Pour contenir toutes les publications
    @FXML
    private TextField searchField;
    @FXML
    private VBox notificationBox;
    @FXML
    private Label cheminphoto;

    @FXML
    private TextField idclientTF;
    @FXML
    private Menu accueilMenu;
    @FXML
    private TextArea pubTF;
    private String photoPath;
    private final PublicationService publicationService = new PublicationService();
    private List<Publication> publications;
    public static final String ACCOUNT_SID = "AC0e50a95d059ca72acfdba6ebe123798f";
    public static final String AUTH_TOKEN = "00f511bca710adcb6c72eb7a17250f26";

    @FXML
    public void initialize() {


        // Au chargement de la vue, récupérer et afficher les publications
        afficherPublications();
        try {
            supprimerPublicationsAvecGrosMots();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
    @FXML
    void ajouter_photo(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une photo");
        // Filtres pour les fichiers image
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif")
        );
        // Afficher la boîte de dialogue de sélection de fichier
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            // Enregistrer le chemin de la photo sélectionnée
            photoPath = selectedFile.getAbsolutePath();
            cheminphoto.setText(photoPath);
        }}



    /*@FXML
    void ajouter_pub(ActionEvent event) {
        // Vérifier si les champs sont vides
        if (idclientTF.getText().isEmpty() || pubTF.getText().isEmpty() || cheminphoto.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "entrez votre demande et donnez un exemple.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return; // Sortir de la méthode si un champ est vide
        }

        // Récupérer les valeurs depuis les champs de l'interface utilisateur
        int idClient = Integer.parseInt(idclientTF.getText());
        String contenu = pubTF.getText();
        String cheminPhoto = cheminphoto.getText(); // Assurez-vous que la label cheminphoto contient le chemin de la photo sélectionnée
        Chat chat =new Chat() ;
        // Vérifier si le contenu de la publication contient des gros mots
       *//* GrosMotsService grosMotsService = new GrosMotsService();
        List<String> grosMots = grosMotsService.getGrosMots();
        for (String mot : grosMots) {
            if (contenu.toLowerCase().contains(mot.toLowerCase())) {
                // Afficher un message d'erreur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Le contenu de la publication contient des gros mots. Veuillez le modifier.");
                alert.showAndWait();
                return; // Sortir de la méthode si un gros mot est trouvé
            }
        }*//*


        // Créer une instance de Publication avec les valeurs récupérées
        Publication nouvellePublication = new Publication();
        nouvellePublication.setId_client(idClient);
        nouvellePublication.setContenu(contenu);
        nouvellePublication.setPhoto(cheminPhoto);
        nouvellePublication.setNb_likes(0); // Mettre nb_likes à 0
        nouvellePublication.setNb_dislike(0); // Mettre nb_dislike à 0

        // Créer une boîte de dialogue de confirmation
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation de publication");
        confirmationDialog.setHeaderText("Êtes-vous sûr de vouloir publier cette publication ?");

        // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
        confirmationDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Utiliser PublicationService pour ajouter la nouvelle publication à la base de données
                PublicationService publicationService = new PublicationService();
                try {
                    publicationService.ajouter(nouvellePublication);
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Publié");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("La publication a été ajoutée avec succès !");
                    successAlert.showAndWait();
                    initialize();
                } catch (SQLException e) {
                    System.err.println("Erreur lors de l'ajout de la publication : " + e.getMessage());
                }
            }
        });}
*/
    @FXML
    void ajouter_pub(ActionEvent event) {
        // Vérifier si les champs sont vides
        if (idclientTF.getText().isEmpty() || pubTF.getText().isEmpty() || cheminphoto.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Entrez votre demande et donnez un exemple.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return; // Sortir de la méthode si un champ est vide
        }

        // Récupérer les valeurs depuis les champs de l'interface utilisateur
        int idClient = Integer.parseInt(idclientTF.getText());
        String contenu = pubTF.getText();
        String cheminPhoto = cheminphoto.getText(); // Assurez-vous que la label cheminphoto contient le chemin de la photo sélectionnée

        // Utiliser le service Chat pour détecter les mots haineux dans le contenu de la publication
        Chat chat = new Chat();
        String resultatDetection = chat.badword(contenu);

        // Vérifier le résultat de la détection des mots haineux
        if (resultatDetection.equals("1")) {
            // Afficher un message d'erreur si le contenu contient un discours haineux
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le contenu de la publication contient un discours haineux. Veuillez le modifier.");
            alert.showAndWait();
        } else if (resultatDetection.equals("0")) {
            // Si le contenu ne contient pas de discours haineux, ajouter la publication normalement

            // Créer une instance de Publication avec les valeurs récupérées
            Publication nouvellePublication = new Publication();
            nouvellePublication.setId_client(idClient);
            nouvellePublication.setContenu(contenu);
            nouvellePublication.setPhoto(cheminPhoto);
            nouvellePublication.setNb_likes(0); // Mettre nb_likes à 0
            nouvellePublication.setNb_dislike(0); // Mettre nb_dislike à 0

            // Créer une boîte de dialogue de confirmation
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation de publication");
            confirmationDialog.setHeaderText("Êtes-vous sûr de vouloir publier cette publication ?");

            // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
            confirmationDialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Utiliser PublicationService pour ajouter la nouvelle publication à la base de données
                    PublicationService publicationService = new PublicationService();
                    try {
                        publicationService.ajouter(nouvellePublication);
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Publié");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("La publication a été ajoutée avec succès !");
                        successAlert.showAndWait();
                        initialize();
                    } catch (SQLException e) {
                        System.err.println("Erreur lors de l'ajout de la publication : " + e.getMessage());
                    }
                }
            });
        } else if (resultatDetection.equals("-1")) {
            // Gérer les cas où la détection des mots haineux a échoué ou a rencontré une erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur s'est produite lors de la détection des mots haineux.");
            alert.showAndWait();
        }
    }

   @FXML
    void supprimerPublicationsAvecGrosMots() throws SQLException {
        // Récupérer les publications existantes depuis la base de données
        List<Publication> publications = publicationService.recuperer();

        // Récupérer les gros mots depuis la base de données
        GrosMotsService grosMotsService = new GrosMotsService();
        List<String> grosMots = grosMotsService.getGrosMots();

        // Parcourir toutes les publications
        for (Publication publication : publications) {
            // Vérifier si le contenu de la publication contient des gros mots
            for (String mot : grosMots) {
                if (publication.getContenu().toLowerCase().contains(mot.toLowerCase())) {
                    // Si un gros mot est trouvé, supprimer la publication de la base de données
                    try {
                        publicationService.supprimer(publication.getId_pub());
                        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

                        Message message = Message
                                .creator(
                                        new com.twilio.type.PhoneNumber("+21697161495"),
                                        new com.twilio.type.PhoneNumber("+12232049811"),
                                        "la publication de mr/mme" + publication.getUser().getPrenom().toUpperCase() + " qui contient un contenu haineux a été supprimé avec succés"
                                )
                                .create();

                        System.out.println(message.getSid());
                    } catch (SQLException e) {
                        e.printStackTrace();
                        // Gérer l'erreur de suppression de la publication
                    }
                    afficherPublications();
                    // Sortir de la boucle pour passer à la publication suivante
                    break;
                }
            }
        }
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
        Image backgroundImage = new Image("file:C:\\Users\\marie\\IdeaProjects\\AffarietyGUI\\src\\main\\resources\\tn\\esprit\\affarietygui\\Card.png");

        for (Publication publication : publicationsToDisplay) {
            String client = publication.getUser().getPrenom() + " " + publication.getUser().getNom();
            Label nomprenomlabel =new Label (client);
            Label contenuLabel = new Label(publication.getContenu());
            nomprenomlabel.setStyle("-fx-font-weight: bold;");
            contenuLabel.setStyle("-fx-font-style: italic;");
            Label dateLabel = new Label("Date: " + publication.getDate_pub().toString());

            ImageView imageView = new ImageView(publication.getPhoto());
            imageView.setFitWidth(358);
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

            HBox likesDislikesBox = new HBox(90);
            likesDislikesBox.getChildren().addAll(likesLabel, dislikesLabel);

            Button commentButton = new Button("Commentaires");
            commentButton.setOnAction(this::ClickComments);
            Button traduireButton = new Button("Traduction");
            traduireButton.setOnAction(this::Traduire);
            HBox likeDislikeBox = new HBox(10);
            likeDislikeBox.getChildren().addAll(likeButton, dislikeButton);
            
            HBox commentOptionsBox = new HBox(10);
            commentOptionsBox.getChildren().addAll(commentButton, optionsButton);
            
            HBox TraductionContenuBox = new HBox(260);
            TraductionContenuBox.getChildren().addAll(contenuLabel, traduireButton);

            VBox publicationBox = new VBox(10);
            publicationBox.setId("publicationBox_" + publication.getId_pub());
            publicationBox.getChildren().addAll(nomprenomlabel,TraductionContenuBox, imageView, likesDislikesBox, likeDislikeBox, commentOptionsBox, dateLabel);

            publicationBox.setBackground(new Background(new BackgroundImage(backgroundImage,
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));

            // Set the absolute path of the like image
            String likeImagePath = "C:\\Users\\marie\\IdeaProjects\\AffarietyGUI\\src\\main\\resources\\tn\\esprit\\affarietygui\\like.png";
            File likeImageFile = new File(likeImagePath);
            if (likeImageFile.exists()) {
                Image likeImage = new Image(likeImageFile.toURI().toString());
                ImageView likeImageView = new ImageView(likeImage);
                likeImageView.setFitWidth(22.0);
                likeImageView.setFitHeight(22.0);
                likeButton.setGraphic(likeImageView);
            } else {
                System.out.println("File not found: " + likeImagePath);
            }

            // Set the absolute path of the dislike image
            String dislikeImagePath = "C:\\Users\\marie\\IdeaProjects\\AffarietyGUI\\src\\main\\resources\\tn\\esprit\\affarietygui\\dislike.png";
            File dislikeImageFile = new File(dislikeImagePath);
            if (dislikeImageFile.exists()) {
                Image dislikeImage = new Image(dislikeImageFile.toURI().toString());
                ImageView dislikeImageView = new ImageView(dislikeImage);
                dislikeImageView.setFitWidth(22.0);
                dislikeImageView.setFitHeight(22.0);
                dislikeButton.setGraphic(dislikeImageView);
            } else {
                System.out.println("File not found: " + dislikeImagePath);
            }

            // Set the absolute path of the comment image
            String commentImagePath = "C:\\Users\\marie\\IdeaProjects\\AffarietyGUI\\src\\main\\resources\\tn\\esprit\\affarietygui\\commenter.png";
            File commentImageFile = new File(commentImagePath);
            if (commentImageFile.exists()) {
                Image commentImage = new Image(commentImageFile.toURI().toString());
                ImageView commentImageView = new ImageView(commentImage);
                commentImageView.setFitWidth(22.0);
                commentImageView.setFitHeight(22.0);
                commentButton.setGraphic(commentImageView);
            } else {
                System.out.println("File not found: " + commentImagePath);
            }

// Set the absolute path of the option image
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


            publicationContainer.getChildren().add(publicationBox);
        }

    }

    @FXML
    public void Traduire(ActionEvent actionEvent) {
        // Vérifier si la source de l'événement est bien un bouton
        if (!(actionEvent.getSource() instanceof Button)) {
            return;
        }

        Button traduireBtn = (Button) actionEvent.getSource();
        // Vérifier si le parent du bouton est bien un HBox
        if (!(traduireBtn.getParent() instanceof HBox)) {
            return;
        }

        HBox hbox = (HBox) traduireBtn.getParent();
        // Vérifier si le parent du HBox est bien un VBox
        if (!(hbox.getParent() instanceof VBox)) {
            return;
        }

        VBox vbox = (VBox) hbox.getParent(); // Get the parent VBox

        // Get the index of the VBox in the container
        int index = publicationContainer.getChildren().indexOf(vbox);
        Publication publicationSelectionnee = null;

        if (index >= 0 && index < publications.size()) {
            publicationSelectionnee = publications.get(index);
        }

        // Vérifier si la publication sélectionnée n'est pas null
        if (publicationSelectionnee != null) {
            // Utiliser le service Chat pour traduire le contenu de la publication
            Chat chat = new Chat();
            String contenuTraduit = chat.traduire(publicationSelectionnee.getContenu());

            // Créer une boîte de dialogue personnalisée pour afficher le contenu traduit
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Contenu traduit");
            dialog.setHeaderText(null);

            // Bouton pour fermer la boîte de dialogue
            ButtonType closeButton = new ButtonType("Fermer", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().add(closeButton);

            // Afficher le contenu traduit dans une étiquette dans la boîte de dialogue
            Label label = new Label("Le contenu traduit de la publication est :\n" + contenuTraduit);
            dialog.getDialogPane().setContent(label);

            // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
            dialog.showAndWait();
        } else {
            // Si la publication sélectionnée est null, afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Publication introuvable.");
            alert.showAndWait();
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

        }
        afficherNotification("Vous avez reçu un like sur votre publication");
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
        }
        afficherNotification("Vous avez reçu un dislike sur votre publication");
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

    public void filtrerParPlusRecentes(ActionEvent actionEvent) {
        // Sort the publications by date in descending order (most recent first)
        List<Publication> filteredPublications = publications.stream()
                .sorted(Comparator.comparing(Publication::getDate_pub).reversed())
                .collect(Collectors.toList());

        // Display the filtered publications
        afficherPublications(filteredPublications);
    }

    public void filtrerParPlusAnciennes(ActionEvent actionEvent) {
        // Sort the publications by date in ascending order (oldest first)
        List<Publication> filteredPublications = publications.stream()
                .sorted(Comparator.comparing(Publication::getDate_pub))
                .collect(Collectors.toList());

        // Display the filtered publications
        afficherPublications(filteredPublications);
    }

    public void clearFilter(ActionEvent actionEvent) {
        afficherPublications(publications);
    }

    public void jouer(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Jouer à X/O");
        alert.setHeaderText("Voulez-vous jouer à un jeu X/O avec nous ?");
        alert.setContentText("Cliquez sur OK pour jouer à X/O ou Annuler pour ignorer.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                TicTacToeGame snakeGame = new TicTacToeGame();
                Stage gameStage = new Stage();
                snakeGame.start(gameStage);
            }
        });
    }

    public void accueil(ActionEvent actionEvent) {

            System.out.println("presssed");

    }
}
