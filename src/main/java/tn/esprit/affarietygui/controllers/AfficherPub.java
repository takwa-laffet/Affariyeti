package tn.esprit.affarietygui.controllers;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.affarietygui.models.Commentaire;
import tn.esprit.affarietygui.models.Publication;
import tn.esprit.affarietygui.services.CommentaireService;
import tn.esprit.affarietygui.services.PublicationService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AfficherPub {
    @FXML
    private ListView<Publication> lvpub;
    @FXML
    private TextField searchTextField;

    private final PublicationService ps = new PublicationService();
    private ObservableList<Publication> allPublications;
    private ObservableList<Publication> filteredPublications;
    @FXML
    void initialize() {
        PublicationService publicationService = new PublicationService();
        try {
            List<Publication> publications = publicationService.recuperer();
            allPublications = FXCollections.observableArrayList(publications);
            filteredPublications = FXCollections.observableArrayList(publications);
            lvpub.setItems(filteredPublications);
            lvpub.setCellFactory(new PublicationCellFactory());

            // Ajouter un écouteur pour le champ de recherche
            searchTextField.textProperty().addListener((observable, oldValue, newValue) -> searchPublication());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void modifierPub(ActionEvent event) {
        Publication selectedPublication = lvpub.getSelectionModel().getSelectedItem();
        if (selectedPublication == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune publication sélectionnée");
            alert.setContentText("Veuillez sélectionner une publication à modifier.");
            alert.showAndWait();
            return;
        }
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Modifier la publication");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir modifier cette publication ?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Modifier la publication");
            dialog.setHeaderText(null);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/affarietygui/modifierPub.fxml"));
            try {
                Parent root = loader.load();
                dialog.getDialogPane().setContent(root);
                ModifierPub modifierPubController = loader.getController();
                modifierPubController.initData(selectedPublication.getId_pub());

                // Set the userData property
                Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                stage.setUserData(this); // Assuming "this" refers to AfficherPubController

            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> dialogResult = dialog.showAndWait();
            if (dialogResult.isPresent() && dialogResult.get() == ButtonType.OK) {
                initialize();
            }
        }
    }

    @FXML
    void supprimerPub(ActionEvent event) {
        Publication selectedPublication = lvpub.getSelectionModel().getSelectedItem();
        if (selectedPublication != null) {
            PublicationService publicationService = new PublicationService();
            try {
                publicationService.supprimer(selectedPublication.getId_pub());
                initialize();
            } catch (SQLException e) {
                System.err.println("Error while deleting publication: " + e.getMessage());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune publication sélectionnée");
            alert.setContentText("Veuillez sélectionner une publication à supprimer.");
            alert.showAndWait();
        }
    }

    public void afficherCommentaire(ActionEvent actionEvent) {
        Publication selectedPublication = lvpub.getSelectionModel().getSelectedItem();

        // Vérifier si une publication est sélectionnée
        if (selectedPublication != null) {
            try {
                CommentaireService commentaireService = new CommentaireService();
                List<Commentaire> commentaires = commentaireService.recuperer(selectedPublication.getId_pub());

                // Load the UserComments view
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/affarietygui/GestionCom.fxml"));
                Parent root = loader.load();
                GestionCom gestionComController = loader.getController();

                // Call the method to display the comments
                gestionComController.afficherCommentaires(commentaires, selectedPublication);

                // Display the UserComments interface in a new window
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }

        }else {
            // If the selected publication is null, display an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No publication selected.");
            alert.showAndWait();
        }
    }
    @FXML
    public void searchPublication() {
        String searchText = searchTextField.getText().toLowerCase().trim();
        filteredPublications.clear();
        for (Publication publication : allPublications) {
            if (publication.getContenu().toLowerCase().contains(searchText)) {
                filteredPublications.add(publication);
            }
        }
    }

    public void StatistiqueBtn(ActionEvent actionEvent) {
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/affarietygui/Statistique.fxml"));
            Parent root = loader.load();

            // Obtenir la scène depuis le bouton source
            Scene scene = ((Node) actionEvent.getSource()).getScene();

            // Obtenir la fenêtre actuelle et la cacher
            Stage currentStage = (Stage) scene.getWindow();
            currentStage.hide();

            // Afficher la nouvelle interface utilisateur dans une nouvelle fenêtre
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'exception en affichant la trace
        }
    }

    public void AjouterPub(ActionEvent actionEvent) {
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/affarietygui/UserAffichePub.fxml"));
            Parent root = loader.load();

            // Obtenir la scène depuis le bouton source
            Scene scene = ((Node) actionEvent.getSource()).getScene();

            // Obtenir la fenêtre actuelle et la cacher
            Stage currentStage = (Stage) scene.getWindow();
            currentStage.hide();

            // Afficher la nouvelle interface utilisateur dans une nouvelle fenêtre
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'exception en affichant la trace
        }
    }

    private static class PublicationCellFactory implements Callback<ListView<Publication>, ListCell<Publication>> {
        @Override
        public ListCell<Publication> call(ListView<Publication> param) {
            return new ListCell<Publication>() {
                @Override
                protected void updateItem(Publication publication, boolean empty) {
                    super.updateItem(publication, empty);
                    if (empty || publication == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        VBox vBox = new VBox();
                        vBox.setAlignment(Pos.CENTER_LEFT);

                        Separator separator = new Separator();
                        separator.setMaxWidth(Double.MAX_VALUE);
                        ImageView imageView = new ImageView(publication.getPhoto());
                        imageView.setFitWidth(100);
                        imageView.setFitHeight(100);

                        TextFlow textFlow = new TextFlow();

                        // User
                        Text userText = new Text("User: ");
                        userText.setStyle("-fx-font-weight: bold;");
                        Text userNameText = new Text(publication.getUser().getPrenom() + " " + publication.getUser().getNom() + "\n");
                        userNameText.setStyle("-fx-font-weight: normal;");
                        textFlow.getChildren().addAll(userText, userNameText);

                        // Contenu
                        Text contenuText = new Text("Contenu: ");
                        contenuText.setStyle("-fx-font-weight: bold;");
                        Text contenuValueText = new Text(publication.getContenu() + "\n");
                        contenuValueText.setStyle("-fx-font-weight: normal;");
                        textFlow.getChildren().addAll(contenuText, contenuValueText);

                        // Likes
                        Text likesText = new Text("Likes: ");
                        likesText.setStyle("-fx-font-weight: bold;");
                        Text likesValueText = new Text(String.valueOf(publication.getNb_likes()) + "\n");
                        likesValueText.setStyle("-fx-font-weight: normal;");
                        textFlow.getChildren().addAll(likesText, likesValueText);

                        // Dislikes
                        Text dislikesText = new Text("Dislikes: ");
                        dislikesText.setStyle("-fx-font-weight: bold;");
                        Text dislikesValueText = new Text(String.valueOf(publication.getNb_dislike()) + "\n");
                        dislikesValueText.setStyle("-fx-font-weight: normal;");
                        textFlow.getChildren().addAll(dislikesText, dislikesValueText);

                        // Date
                        Text dateText = new Text("Date: ");
                        dateText.setStyle("-fx-font-weight: bold;");
                        Text dateValueText = new Text(publication.getDate_pub() + "\n");
                        dateValueText.setStyle("-fx-font-weight: normal;");
                        textFlow.getChildren().addAll(dateText, dateValueText);

                        vBox.getChildren().addAll(textFlow,imageView, separator);
                        setGraphic(vBox);
                    }
                }
            };
        }

    }
}
