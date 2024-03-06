package tn.esprit.gestion.controllers;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.itextpdf.text.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.mysql.cj.result.Row;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import tn.esprit.gestion.models.Depot;
import tn.esprit.gestion.models.Livraison;
import tn.esprit.gestion.services.DepotService;
import tn.esprit.gestion.services.LivraisonService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.*;


public class Afficherlivraison {
    private Callback<ListView<Livraison>, ListCell<Livraison>> livraisonCellFactory;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField searchtext;

    @FXML
    private Button AVIS;

    @FXML
    private AnchorPane AnchorPaneaff;

    @FXML
    private Button COMMANDES;

    @FXML
    private Button ENCHERE;

    @FXML
    private Button LIVRAISON;

    @FXML
    private ListView<Livraison> LVcmd;

    @FXML
    private Button PRODUITS;

    @FXML
    private Button USER;

    @FXML
    private Button versModifier;

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    private DepotService depotService = new DepotService();

    @FXML
    void initialize() {
        LivraisonService livraisonService = new LivraisonService();
        try {
            List<Livraison> livraisons = livraisonService.recuperer();
            ObservableList<Livraison> livraisonDataList = FXCollections.observableArrayList(livraisons);

            LVcmd.setItems(livraisonDataList);

            // Add listener to search text field
            searchtext.textProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    // Filter livraisons based on addresselivraison using streams
                    List<Livraison> result = livraisonService.recuperer().stream()
                            .filter(livraison -> livraison.getAdresselivraison().toLowerCase().contains(newValue.toLowerCase()))
                            .collect(Collectors.toList());

                    // Clear the ListView before adding new items
                    LVcmd.getItems().clear();
                    LVcmd.getItems().addAll(result);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

            // Define the updateItem method for the ListView
            // Modify the updateItem method for Livraison in your Afficherlivraison class

            LivraisonListCellFactory livraisonCellFactory = new LivraisonListCellFactory();

            LVcmd.setCellFactory(param -> new ListCell<Livraison>() {
                @Override
                protected void updateItem(Livraison item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        try {
                            // Retrieve Depot information
                            DepotService depotService = new DepotService();
                            Depot depot = depotService.getById(item.getIddepot());

                            // Create a custom cell with a VBox and styling
                            VBox vBox = new VBox(10);
                            vBox.setPadding(new Insets(10));
                            vBox.setStyle("-fx-border-radius: 10px; -fx-background-radius: 10px;");

                            // Add labels for each piece of information with styling
                            Label addressLabel = createLabel("Adresse Livraison: " + item.getAdresselivraison(), 14);
                            Label dateCommandeLabel = createLabel("Date Commande: " + dateFormatter.format(item.getDatecommande().toLocalDateTime()), 12);
                            Label dateLivraisonLabel = createLabel("Date Livraison: " + dateFormatter.format(item.getDatelivraison().toLocalDate()), 12);
                            Label statusLabel = createLabel("Status Livraison: " + item.getStatuslivraison(), 12);
                            Label depotLabel = createLabel("Depot: " + (depot != null ? depot.getNomdepot() : ""), 12);

                            // Generate QR code image from Livraison's information
                            Image qrCodeImage = generateQRCode(item);

                            // Add labels and QR code to the VBox
                            vBox.getChildren().addAll(addressLabel, dateCommandeLabel, dateLivraisonLabel, statusLabel, depotLabel, new ImageView(qrCodeImage));

                            // Set the custom cell as the graphic for the cell
                            setGraphic(vBox);
                        } catch (SQLException | WriterException e) {
                            e.printStackTrace();
                        }
                    }
                }

                private Label createLabel(String text, int fontSize) {
                    Label label = new Label(text);
                    label.setStyle("-fx-font-size: " + fontSize + "px;");
                    return label;
                }

                private Image generateQRCode(Livraison livraison) throws WriterException {
                    // Create a string with Livraison information
                    String content = "Adresse Livraison: " + livraison.getAdresselivraison() + "\n" +
                            "Date Commande: " + dateFormatter.format(livraison.getDatecommande().toLocalDateTime()) + "\n" +
                            "Date Livraison: " + dateFormatter.format(livraison.getDatelivraison().toLocalDate()) + "\n" +
                            "Status Livraison: " + livraison.getStatuslivraison();

                    // Create a QR code
                    BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 150, 200);

                    // Convert the BitMatrix to a byte array
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    try {
                        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    // Convert the byte array to an Image
                    return new Image(new ByteArrayInputStream(outputStream.toByteArray()));
                }

            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void trier(ActionEvent event) throws SQLException {
        try {
            LivraisonService livraisonService = new LivraisonService();
            List<Livraison> livraisons = livraisonService.tri();

            ObservableList<Livraison> livraisonsObservable = livraisons.stream()
                    .sorted(Comparator.comparing(Livraison::getDatelivraison))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));

            LVcmd.setItems(livraisonsObservable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void back(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/gestion/Ajouterlivraison.fxml"));
            Parent root = fxmlLoader.load();
            AnchorPaneaff.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void supprimer(ActionEvent event) {
        LivraisonService livraisonService = new LivraisonService();

        try {
            Livraison selectedLivraison = LVcmd.getSelectionModel().getSelectedItem();

            if (selectedLivraison == null) {
                showAlert("Avertissement", "Veuillez sélectionner une livraison à supprimer.");
                return;
            }

            int id = selectedLivraison.getId();

            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation de suppression");
            confirmationAlert.setHeaderText("Êtes-vous sûr de vouloir supprimer cette livraison ?");
            confirmationAlert.setContentText("Cette action est irréversible.");

            if (confirmationAlert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                if (livraisonService.idExists(id)) {
                    livraisonService.supprimer(id);
                    LVcmd.getItems().remove(selectedLivraison);
                    showAlert("Suppression", "Livraison a été supprimée avec succès.");
                } else {
                    showAlert("Avertissement", "L'ID spécifié n'existe pas dans la base de données.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors de la suppression de la livraison : " + e.getMessage());
        }
    }


    @FXML
    void ondepot(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/gestion/Ajouterdepot.fxml"));
            Parent root = fxmlLoader.load();
            AnchorPaneaff.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors du chargement de l'interface Ajouterlivraison : " + e.getMessage());
        }
    }

    @FXML
    void searchLivraison(ActionEvent event) {
        // This method is no longer used as we update the search results dynamically
    }

    @FXML
    void VersModifier(ActionEvent event) {
        Livraison selectedLivraison = LVcmd.getSelectionModel().getSelectedItem();

        if (selectedLivraison != null) {
            try {

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Editlivraison.fxml"));
                Parent root = fxmlLoader.load();
                Editlivraison editController = fxmlLoader.getController();
                editController.initData(selectedLivraison);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Erreur", "Erreur lors du chargement de l'interface Edit : " + e.getMessage());
            }
        } else {
            showAlert("Avertissement", "Veuillez sélectionner une livraison à modifier.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void toStat(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/gestion/statistique.fxml"));
            Parent root = fxmlLoader.load();
            AnchorPaneaff.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors du chargement de l'interface Ajouterlivraison : " + e.getMessage());
        }


    }

    @FXML
    void ToMailing(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/gestion/mailing.fxml"));
            Parent root = fxmlLoader.load();
            AnchorPaneaff.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors du chargement de l'interface Ajouterlivraison : " + e.getMessage());
        }
    }

    @FXML
    public void gotolivraison(ActionEvent event) {
        System.out.println("test");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Ajouterlivraison.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void pdf(javafx.event.ActionEvent actionEvent) throws IOException {
        Livraison selectedLivraison = LVcmd.getSelectionModel().getSelectedItem();
        if (selectedLivraison != null) {
            try {
                // Créer un nouveau document PDF
                PDDocument document = new PDDocument();

                // Créer une page dans le document
                PDPage page = new PDPage();
                document.addPage(page);

                // Obtenez le contenu de la page
                PDPageContentStream contentStream = new PDPageContentStream(document, page);

                // Définissez la police de caractères
                contentStream.setFont(PDType1Font.HELVETICA, 12);

                // Afficher le titre en haut de la page
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.newLineAtOffset(400, 750);
                contentStream.showText("AFFARIYATY");
                contentStream.endText();

                // Afficher les détails de la livraison dans le PDF avec une meilleure mise en forme
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.newLineAtOffset(100, 750);
                contentStream.showText("Détails de la livraison");
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(0, -20);

                // ID de la livraison
                contentStream.showText("ID de la livraison: " + selectedLivraison.getId());
                contentStream.newLineAtOffset(0, -20);

                // Autres détails de la livraison
                contentStream.showText("ID: " + selectedLivraison.getId());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Adresse: " + selectedLivraison.getAdresselivraison());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("DATE COMMANDE: " + selectedLivraison.getDatecommande());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("DATE LIVRAISON: " + selectedLivraison.getDatelivraison());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("STATUS LIVRAISON: " + selectedLivraison.getStatuslivraison());


                // Ajouter un message de remerciement
                contentStream.newLineAtOffset(0, -40);
                contentStream.showText("Merci pour votre livraison!");

                contentStream.endText();

                // Fermez le contenu de la page
                contentStream.close();

                String outputPath = "C:/Users/njahi/OneDrive/Desktop/pidev/pidev.pdf";
                File file = new File(outputPath);
                document.save(file);

                // Fermez le document
                document.close();

                System.out.println("Le PDF a été généré avec succès.");
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Afficher un message d'erreur si aucune livraison n'est sélectionnée
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Aucune livraison sélectionnée");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une livraison avant de générer le PDF.");
            alert.showAndWait();
        }
    }}





