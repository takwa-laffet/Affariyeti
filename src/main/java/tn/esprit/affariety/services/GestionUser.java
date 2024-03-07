package tn.esprit.affariety.services;




import tn.esprit.affariety.models.CodePromo;
import tn.esprit.affariety.models.User;
import tn.esprit.affariety.utils.MyDataBase;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class GestionUser implements Fonctions<User> {
    Connection cnx;
    private GestionCodePromo gestionCodePromo;

    public GestionUser() {
        this.cnx = MyDataBase.getInstance().getConnection();
        this.gestionCodePromo = new GestionCodePromo();
    }
    public void updateImage(User user, String image) {
        String query = "UPDATE user SET image = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setString(1, image);
            preparedStatement.setInt(2, user.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {

            System.out.println(ex.getMessage());
        }
    }
    public void Create(User user) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)//generate code de verif yaatik les alphabEts mn a l z
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        try {
            String sql = "INSERT INTO user(email, mdp, status, nom, prenom,verificationCode,role) VALUES (?, ?, ?, ?, ?,?,?)";
            try (PreparedStatement st = this.cnx.prepareStatement(sql)) {
                st.setString(1, user.getEmail());
                st.setString(2, user.getMdp());
                st.setBoolean(3, true);
                st.setString(4, user.getNom());
                st.setString(5, user.getPrenom());
                st.setString(6, generatedString);
                st.setString(7, "client");

                st.executeUpdate();
                System.out.println("User created successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error creating user: " + e.getMessage());
        }
    }

    public boolean UserExistsByEmail(String email) {
        String query = "SELECT COUNT(*) FROM user WHERE Email = ?";
        try {
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException ex) {

        }
        return false;
    }

    public String getVerificationCodeByEmail(String email) {
        String query = "SELECT verificationCode FROM user WHERE Email = ?";
        try {
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String verificationCode = resultSet.getString("verificationCode");
                return verificationCode;
            }
        } catch (SQLException ex) {
        }
        return null;
    }

    public void changePasswordByEmail(String text, String hashedPassword) {
        String query = "UPDATE user SET mdp = ? WHERE email = ?";
        try {
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setString(1, hashedPassword);
            preparedStatement.setString(2, text);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
        }
    }

    public boolean userExists(String nom, String prenom, String mdp, String email) {
        try {
            String sql = "SELECT COUNT(*) FROM user WHERE nom = ? AND prenom = ? AND mdp = ? AND email = ?";
            try (PreparedStatement st = this.cnx.prepareStatement(sql)) {
                st.setString(1, nom);
                st.setString(2, prenom);
                st.setString(3, mdp);
                st.setString(4, email);

                try (ResultSet rs = st.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1) > 0;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking user existence: " + e.getMessage());
        }
        return false;
    }

    // Méthode pour créer un nouvel utilisateur


    public void delete(User user) {
        PreparedStatement st = null;

        try {
            String sql = "DELETE FROM user WHERE id=?";
            st = this.cnx.prepareStatement(sql);

            st.setInt(1, user.getId());
            st.executeUpdate();

            System.out.println("User deleted successfully.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            // Fermer la déclaration préparée dans le bloc finally
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing prepared statement: " + e.getMessage());
            }
        }
    }

    public void update(User user) {
        try {
            String sql = "UPDATE user SET email=?, mdp=?, status=?, nom=?, prenom=? WHERE id=?";
            System.out.println(user.getId());
            PreparedStatement st = this.cnx.prepareStatement(sql);

            st.setString(1, user.getEmail());
            st.setString(2, user.getMdp());
            st.setBoolean(3, user.getStatus());


            st.setString(4, user.getNom());
            st.setString(5, user.getPrenom());

            st.setInt(6, user.getId()); //

            st.executeUpdate();

            System.out.println("User updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating user: " + e.getMessage());
        }
    }


    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM user";
            try (PreparedStatement st = this.cnx.prepareStatement(sql)) {
                // ResultSet est utilise pour récupérer les résultats d'une requête SQL
                try (ResultSet rs = st.executeQuery()) {
                    //a méthode next() de l'objet ResultSet est utilisée pour déplacer le curseur vers la prochaine ligne
                    // des résultats. Elle renvoie true s'il y a une ligne suivante, et false s'il n'y en a plus
                    while (rs.next()) {

                        User user = new User(

                                rs.getString("email"),
                                rs.getString("mdp"),
                                rs.getBoolean("status"),
                                rs.getString("nom"),
                                rs.getString("prenom"),
                                rs.getString("role"),
                                rs.getString("image")

                        );
                        user.setId(rs.getInt("id"));
                        userList.add(user);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading users: " + e.getMessage());
        }
        return userList;
    }

    public User findById(int userId) {
        User user = null;//t7otha null bch baad kif tlawj kn mawjoud trajaa user sinon matnajmch taamk instance user mch mawjoud
        try {
            String sql = "SELECT * FROM user WHERE id = ?";
            try (PreparedStatement st = this.cnx.prepareStatement(sql)) {
                st.setInt(1, userId);
                try (ResultSet rs = st.executeQuery()) {
                    if (rs.next()) {
                        // Construire un objet User à partir des données de la base de données
                        user = new User(
                                rs.getString("email"),
                                rs.getString("mdp"),
                                rs.getBoolean("status"),
                                rs.getString("nom"),
                                rs.getString("prenom"),
                                rs.getString("role"),
                                rs.getString("image")

                                // Ajoutez d'autres attributs si nécessaire
                        );
                        user.setId(rs.getInt("id")); // Assurez-vous d'avoir une méthode setId dans la classe User
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error finding user by id: " + e.getMessage());
        }
        return user;

    }

    private List<User> users;

    public GestionUser(List<User> users) {
        this.users = users;
    }

    public User getUserByEmail(String email) {
        String req = "SELECT * FROM user WHERE email=?";
        try {
            PreparedStatement st = cnx.prepareStatement(req);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("mdp"),
                        rs.getBoolean("status"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("role")

                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<User> join() throws SQLException {
        List<User> user = new ArrayList<>();
        String req = "SELECT  user.id,user.nom ,user.prenom,user.email,user.role,user.mdp ,codepromo.idUser from user inner join codePromo  on user.id=codepromo.idUser";
        Statement st = this.cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {

            User us = new User();
            us.setId(rs.getInt("id"));
            us.setNom(rs.getString("nom"));
            us.setPrenom(rs.getString("prenom"));
            us.setEmail(rs.getString("email"));
            us.setRole(rs.getString("role"));
            us.setMdp(rs.getString("motDePasse"));
            user.add(us);
        }
        return user;
    }

    public Map<Date, Integer> getUserDataByDate() {
        Map<Date, Integer> userDataByDate = new HashMap<>();
        String query = "SELECT date, COUNT(*) AS user_count FROM user where role='client' GROUP BY date ";

        try (
                PreparedStatement statement = cnx.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Date dateRegistered = resultSet.getDate("date");
                int userCount = resultSet.getInt("user_count");
                userDataByDate.put(dateRegistered, userCount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException appropriately
        }

        return userDataByDate;
    }



    public Map<String, Integer> getUserDataByStatus() {
        Map<String, Integer> userDataByStatus = new HashMap<>();
        String query = "SELECT status, COUNT(*) AS status_count FROM user GROUP BY status";

        try (
                PreparedStatement statement = cnx.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int isActive = resultSet.getInt("status");
                String status = (isActive == 1) ? "Active" : "Inactive";
                int statusCount = resultSet.getInt("status_count");
                userDataByStatus.put(status, statusCount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException appropriately
        }

        return userDataByStatus;
    }
}










