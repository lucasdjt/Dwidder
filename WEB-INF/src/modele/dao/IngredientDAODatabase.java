package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dto.Ingredient;

public class IngredientDAODatabase implements DAOIngredient {

    @Override
    public List<Ingredient> findAll() {
        List<Ingredient> ingredients = new ArrayList<>();
        try (Connection con = psqlSetupConnection()) {
            String sql = "SELECT * FROM ingredients";
            try (Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("nom");
                    int price = rs.getInt("prix");
                    ingredients.add(new Ingredient(id, name, price));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ingredients;
    }

    @Override
    public Ingredient findById(int id) {
        Ingredient ingredient = null;
        try (Connection conn = psqlSetupConnection()) {
            String sql = "SELECT * FROM ingredients WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String name = rs.getString("nom");
                        int price = rs.getInt("prix");
                        ingredient = new Ingredient(id, name, price);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ingredient;
    }

    @Override
    public void save(Ingredient ingredient) {
        try (Connection conn = psqlSetupConnection()) {
            String sql = "INSERT INTO ingredients (id, nom, prix) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, 1);
                pstmt.setString(2, ingredient.getName());
                pstmt.setInt(3, ingredient.getPrice());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection psqlSetupConnection() throws Exception{
        String driver = "org.postgresql.Driver";
        String url = "jdbc:postgresql://psqlserv/but2";
        String nom = "lucasdejesusteixeiraetu";
        String mdp = "moi";
        Class.forName(driver);
        return DriverManager.getConnection(url,nom,mdp);
    }
}