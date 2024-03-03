package tn.esprit.affarietygui.services;

import tn.esprit.affarietygui.models.GrosMots;
import tn.esprit.affarietygui.utils.Mydb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GrosMotsService {
    private Connection connection;

    public GrosMotsService() {
        connection = Mydb.getInstance().getConnection();
    }

    public List<String> getGrosMots() {
        List<String> grosMots = new ArrayList<>();

        try {
            // Requête SQL pour récupérer les gros mots
            String sql = "SELECT GrosMots FROM grosmots";

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                // Parcourir les résultats de la requête
                while (resultSet.next()) {
                    // Ajouter le gros mot à la liste
                    grosMots.add(resultSet.getString("GrosMots"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de connexion à la base de données
        }

        return grosMots;
    }
}