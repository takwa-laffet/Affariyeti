package com.example.test.gui;

import com.example.test.models.CategorieCodePromo;
import com.example.test.services.GestionCategorieCodePromo;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddCategorieCodePromo {
    @javafx.fxml.FXML
    private TextField inputCode;
    @javafx.fxml.FXML
    private TextField inputLimit;
    @javafx.fxml.FXML
    private TextField inputValue;
    @javafx.fxml.FXML
    private Label error;
    @javafx.fxml.FXML
    private Label welcome;

    @javafx.fxml.FXML
    public void add(ActionEvent actionEvent) {
        GestionCategorieCodePromo gccp = new GestionCategorieCodePromo();
        CategorieCodePromo ccp = new CategorieCodePromo();

        String code = inputCode.getText();
        String valueText = inputValue.getText();
        String limitText = inputLimit.getText();

        if (code.isEmpty() || valueText.isEmpty() || limitText.isEmpty()) {
            error.setText("Les champs sont obligatoires");
        } else {
            try {
                int value = Integer.parseInt(valueText);
                int limit = Integer.parseInt(limitText);

                ccp.setCode(code);
                ccp.setValeur(value);
                ccp.setLimite(limit);

                gccp.Create(ccp);
            } catch (NumberFormatException e) {
                error.setText("Veuillez entrer des nombres valides pour 'Valeur' et 'Limite'");
            }
        }
    }


    @javafx.fxml.FXML
    public void backAction(ActionEvent actionEvent) {
    }
}
