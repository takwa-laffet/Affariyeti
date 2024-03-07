package tn.esprit.affariety.services;

import tn.esprit.affariety.models.Raiting;
import tn.esprit.affariety.utils.MyDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RaitingService {
    private Connection connection;

    public RaitingService(){
        connection = MyDataBase.getInstance().getConnection();
    }

    public void addRating(Raiting rating) throws SQLException {
        String query = "INSERT INTO rating (user_id, product_id, rating_value) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, rating.getUser_id());
            preparedStatement.setInt(2, rating.getProduct_id());
            preparedStatement.setInt(3, rating.getValue());
            preparedStatement.executeUpdate();
        }
    }



    public void updateRating(Raiting updatedRating) throws SQLException {
        String query = "UPDATE ratings SET user_id=?, product_id=?, value=? WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, updatedRating.getUser_id());
            preparedStatement.setInt(2, updatedRating.getProduct_id());
            preparedStatement.setInt(3, updatedRating.getValue());
            preparedStatement.setInt(4, updatedRating.getId());
            preparedStatement.executeUpdate();
        }
    }

    public void deleteRating(int ratingId) throws SQLException {
        String query = "DELETE FROM ratings WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, ratingId);
            preparedStatement.executeUpdate();
        }
    }

    public double getmoyenneraiting(int productId) throws SQLException {
        String query = "SELECT AVG(rating_value) AS average FROM rating WHERE product_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, productId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("average");
                }
            }
        }
        return 0;
    }
}
