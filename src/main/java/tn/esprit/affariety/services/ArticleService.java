package tn.esprit.affariety.services;

import tn.esprit.affariety.models.Article;
import tn.esprit.affariety.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleService {
    private Connection connection ;
    public ArticleService(){
        connection= tn.esprit.affariety.utils.MyDataBase.getInstance().getConnection();
    }

    public void ajouterArticle(Article article) {
        String query = "INSERT INTO article (nom, categorie, prix) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, article.getNomArticle());
            preparedStatement.setString(2, article.getCategorie());
            preparedStatement.setFloat(3, article.getPrix());

            preparedStatement.executeUpdate();
            System.out.println("Article ajouté avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'article : " + e.getMessage());
        }
    }

    public List<Article> getAllArticles() {
        List<Article> articles = new ArrayList<>();
        String query = "SELECT * FROM article";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nomArticle = resultSet.getString("nom");
                String categorie = resultSet.getString("categorie");
                float prix = resultSet.getFloat("prix");
                articles.add(new Article(id, nomArticle, categorie, prix));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des articles : " + e.getMessage());
        }
        return articles;
    }
}

