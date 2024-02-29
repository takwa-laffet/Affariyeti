package com.example.affariyetii;

import com.example.affariyetii.services.TicketPaimentService;

 public class AfficherTicketscontroller {

    private TicketPaimentService ticketPaimentService;
/*
    public AfficherTicketsController(TicketPaimentService ticketPaimentService) {
        this.ticketPaimentService = ticketPaimentService;
    }

    @FXML
    private TextField clientPrenomTextField;

    @FXML
    private TextField clientNomTextField;

    @FXML
    private Label resultLabel;

    @FXML
    private HBox productBox;

    @FXML
    private TextField chercher;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        afficherTousLesAuctions();
    }

    private void afficherTousLesAuctions() {
        List<Enchere> topSaleAuctions = ticketPaimentService.getEncheresParticipatedByUser(clientNomTextField.getText(), clientPrenomTextField.getText());
        displayAuctions(topSaleAuctions);
    }

    void displayAuctions(List<Enchere> auctions) {
        productBox.getChildren().clear(); // Clear the existing items

        for (Enchere enchere : auctions) {
            ImageView imageView = new ImageView(enchere.getImage());
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);

            Text itemNameText = new Text(enchere.getNom_enchere());
            itemNameText.setFont(Font.font("Arial", FontWeight.BOLD, 12));

            Text itemPriceText = new Text("Montant initial: " + enchere.getMontantInitial() + " dt");

            VBox textContainer = new VBox(itemNameText, itemPriceText);

            VBox contentContainer = new VBox(imageView, textContainer);

            // Add zoom effect on mouse hover
            contentContainer.setOnMouseEntered(event -> {
                ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), contentContainer);
                scaleTransition.setToX(1.2);
                scaleTransition.setToY(1.2);
                scaleTransition.play();
            });

            // Remove zoom effect on mouse exit
            contentContainer.setOnMouseExited(event -> {
                ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), contentContainer);
                scaleTransition.setToX(1.0);
                scaleTransition.setToY(1.0);
                scaleTransition.play();
            });

            productBox.getChildren().add(contentContainer);
        }
    }


    @FXML
    void openAjouterEnchere(ActionEvent event) {
        // Your implementation for opening the "AjouterEnchere" view
    }

    @FXML
    void openModifierEnchere(ActionEvent event) {
        // Your implementation for opening the "ModifierEnchere" view
    }

    @FXML
    void openAjouterTickect(ActionEvent event) {
        // Your implementation for opening the "AjouterTickect" view
    }

    @FXML
    void openTicket(ActionEvent event) {
        // Your implementation for opening the "AfficherTicketclient" view
    }

    @FXML
    void openAcher(ActionEvent event) {
        // Your implementation for opening the "AcheterTickect" view
    }

    private void showAlert(String error, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
*/}
