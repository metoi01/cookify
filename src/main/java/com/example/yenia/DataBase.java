package com.example.yenia;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class DataBase {

    private static final String DATABASE_URL = "jdbc:sqlite:/Users/mertokhan/Desktop/github/cookify/src/main/resources/SQLite/a/usersdb.db";

    public DataBase() {
        // Veritabanı bağlantısını başlat
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL);
            createTables(connection);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //start
    //TODO This method will get all recipes from the database.
    public ArrayList<Integer> getAllRecipes() {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM recipetable")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Integer> recipeList = new ArrayList<>();
            while (resultSet.next()) {
                recipeList.add(resultSet.getInt("id"));
            }
            return recipeList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //TODO This method will get all users from the database.
    public ArrayList<String> getAllUsers() {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT username FROM users")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<String> userList = new ArrayList<>();
            while (resultSet.next()) {
                userList.add(resultSet.getString("username"));
            }
            return userList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //TODO This method will change the username of the given user.
    public void changeUsernameTo(String oldUsername, String newUsername) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET username = ? WHERE username = ?")) {
            preparedStatement.setString(1, newUsername);
            preparedStatement.setString(2, oldUsername);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //TODO This method will delete the user from the database.
    public void deleteUser(String username) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE username = ?")) {
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void likeTo(int ID){
    }

    //TODO This method will get the name of the given ingredient.
    public String getIngredientName(int ingredientID) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM ingredients WHERE id = ?")) {
            preparedStatement.setInt(1, ingredientID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //TODO This method will get the name of the given entity (user or recipe).
    public String getNameOf(int entityID) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM recipetable WHERE id = ?")) {
            preparedStatement.setInt(1, entityID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //TODO This method will get all ingredients from the database.
    public ArrayList<Integer> getAllIngredients() {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM ingredients")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Integer> ingredientList = new ArrayList<>();
            while (resultSet.next()) {
                ingredientList.add(resultSet.getInt("id"));
            }
            return ingredientList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //end

    private void createTables(Connection connection) throws SQLException {
        // Kullanıcılar tablosu
        String createUserTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT NOT NULL UNIQUE," +
                "password TEXT NOT NULL," +
                "personal_info TEXT," +
                "profile_photo BLOB)";

        // Tarifler tablosu
        String createRecipeTableSQL = "CREATE TABLE IF NOT EXISTS recipetable (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "chef TEXT NOT NULL," +
                "vote_rate REAL," +
                "like_count INTEGER," +
                "cook_count INTEGER," +
                "comment_count INTEGER," +
                "share_count INTEGER," +
                "recipe_explanation TEXT," +
                "image BLOB," +
                "sections TEXT," +
                "ingredient_list TEXT," +
                "FOREIGN KEY (chef) REFERENCES users(username))";

        // Takip tablosu
        String createFollowTableSQL = "CREATE TABLE IF NOT EXISTS follows (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "follower TEXT NOT NULL," +
                "followed TEXT NOT NULL," +
                "FOREIGN KEY (follower) REFERENCES users(username)," +
                "FOREIGN KEY (followed) REFERENCES users(username))";

        String createIngredientsTableSQL = "CREATE TABLE IF NOT EXISTS ingredients (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "amount INTEGER," +
                "amount_kind TEXT," +
                "recipe_id INTEGER," +
                "FOREIGN KEY (recipe_id) REFERENCES recipetable(id)" +
                ")";



        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createUserTableSQL);
            statement.executeUpdate(createRecipeTableSQL);
            statement.executeUpdate(createFollowTableSQL);
            statement.executeUpdate(createIngredientsTableSQL);
        }
    }

    public String getPasswordOf(String username) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT password FROM users WHERE username = ?")) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveNewUser(String username, String password) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)")) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean doesUserExists(String username) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS count FROM users WHERE username = ?")) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("count") > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void changePasswordTo(String username, String newPassword) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET password = ? WHERE username = ?")) {
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changePersonalInfoTo(String username, String newPersonalInfo) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET personal_info = ? WHERE username = ?")) {
            preparedStatement.setString(1, newPersonalInfo);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeProfilePhotoTo(String username, BufferedImage image) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET profile_photo = ? WHERE username = ?")) {
            // BufferedImage'i byte dizisine çevir
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            byte[] imageInByte = baos.toByteArray();

            preparedStatement.setBytes(1, imageInByte);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void addRecipe(String username, int recipeID) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO recipetable (name, chef) VALUES (?, ?)")) {
            // Tarif bilgilerini ekleyin
            preparedStatement.setString(1, "Tarif Adı");
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeRecipe(String username, int recipeID) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM recipetable WHERE id = ? AND chef = ?")) {
            preparedStatement.setInt(1, recipeID);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void voteTo(String votedBy, String votedTo, int voteRate) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO votes (voted_by, voted_to, vote_rate) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE vote_rate = ?")) {
            preparedStatement.setString(1, votedBy);
            preparedStatement.setString(2, votedTo);
            preparedStatement.setInt(3, voteRate);
            preparedStatement.setInt(4, voteRate);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void followTo(String follower, String followed) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO follows (follower, followed) VALUES (?, ?)")) {
            preparedStatement.setString(1, follower);
            preparedStatement.setString(2, followed);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public float getVoteRateOf(String username) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT AVG(vote_rate) AS avg_vote FROM votes WHERE voted_to = ?")) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getFloat("avg_vote");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getFollowerCountOf(String username) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS follower_count FROM follows WHERE followed = ?")) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("follower_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getPersonalInfoOf(String username) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT personal_info FROM users WHERE username = ?")) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("personal_info");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Integer> getRecipeListOf(String username) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM recipetable WHERE chef = ?")) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Integer> recipeList = new ArrayList<>();
            while (resultSet.next()) {
                recipeList.add(resultSet.getInt("id"));
            }
            return recipeList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BufferedImage getProfilePhotoOf(String username) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT profile_photo FROM users WHERE username = ?")) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                byte[] imageBytes = resultSet.getBytes("profile_photo");
                try {
                    return ImageIO.read(new ByteArrayInputStream(imageBytes));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String createNewRecipe() {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO recipetable (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, "New Recipe");
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return String.valueOf(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getRecipeNameOf(int ID) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM recipetable WHERE id = ?")) {
            preparedStatement.setInt(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getChefOf(int ID) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT chef FROM recipetable WHERE id = ?")) {
            preparedStatement.setInt(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("chef");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public float getVoteRateOf(int ID) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT vote_rate FROM recipetable WHERE id = ?")) {
            preparedStatement.setInt(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getFloat("vote_rate");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getLikeCountOf(int ID) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT like_count FROM recipetable WHERE id = ?")) {
            preparedStatement.setInt(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("like_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getCookCountOf(int ID) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT cook_count FROM recipetable WHERE id = ?")) {
            preparedStatement.setInt(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("cook_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getCommentCountOf(int ID) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT comment_count FROM recipetable WHERE id = ?")) {
            preparedStatement.setInt(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("comment_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getShareCountOf(int ID) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT share_count FROM recipetable WHERE id = ?")) {
            preparedStatement.setInt(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("share_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<String> getCommentsOf(int ID) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT comment FROM comments WHERE recipe_id = ?")) {
            preparedStatement.setInt(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<String> comments = new ArrayList<>();
            while (resultSet.next()) {
                comments.add(resultSet.getString("comment"));
            }
            return comments;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getRecipeExplanation(int ID) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT recipe_explanation FROM recipetable WHERE id = ?")) {
            preparedStatement.setInt(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("recipe_explanation");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
     public BufferedImage getImageOf(int ID) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT image FROM recipetable WHERE id = ?")) {
            preparedStatement.setInt(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                byte[] imageBytes = resultSet.getBytes("image");
                try {
                    return ImageIO.read(new ByteArrayInputStream(imageBytes));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean[] getSectionsOf(int ID) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT sections FROM recipetable WHERE id = ?")) {
            preparedStatement.setInt(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String sectionsString = resultSet.getString("sections");
                // sectionsString'i boolean dizisine dönüştür
                boolean[] sections = new boolean[sectionsString.length()];
                for (int i = 0; i < sectionsString.length(); i++) {
                    sections[i] = sectionsString.charAt(i) == '1';
                }
                return sections;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<int[]> getIngridientListOf(int ID) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, amount, amount_kind FROM ingredients WHERE recipe_id = ?")) {
            preparedStatement.setInt(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<int[]> ingredientList = new ArrayList<>();
            while (resultSet.next()) {
                int ingridientID = resultSet.getInt("id");
                int amount = resultSet.getInt("amount");
                int amountKind = resultSet.getInt("amount_kind");
                ingredientList.add(new int[]{ingridientID, amount, amountKind});
            }
            return ingredientList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getIngridient(int index) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM ingredients ORDER BY id LIMIT 1 OFFSET ?")) {
            preparedStatement.setInt(1, index - 1);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
