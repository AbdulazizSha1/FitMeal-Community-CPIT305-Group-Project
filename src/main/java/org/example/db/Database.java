package org.example.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Database {

    private static String DB_USER ="postgres";

    private static String DB_PASSWORD= "postgres";

    private static String TABLE_NAME = "fitmeal_community";

    private static String DB_URL ="jdbc:postgresql://localhost:5432/" + TABLE_NAME;

    private static final ReentrantLock lock = new ReentrantLock();

    public static void initializeDatabase(){
        try(Connection conn = getConnection();
            Statement stmt = conn.createStatement()){

           // stmt.execute("DROP TABLE IF NOT EXISTS "+TABLE_NAME);

            String createTableSQL = "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME + " (" +
                    "id SERIAL PRIMARY KEY, " +
                    "dishName VARCHAR(255) NOT NULL," +
                    "typeOfDish VARCHAR(255) NOT NULL, " +
                    "ingredients VARCHAR(255) NOT NULL, " +
                    "preparationSteps VARCHAR(255) NOT NULL, " +
                    "timeToPrepare INT NOT NULL, " +
                    "uploadedBy VARCHAR(255) NOT NULL, " +
                    "rating DOUBLE PRECISION NOT NULL, " +
                    "hasVedio VARCHAR(255) NOT NULL) ";
            stmt.execute(createTableSQL);
            System.out.println("Database has been initialized");
        }
        catch (SQLException e){
            System.err.println("Unable to initialize the database " +e.getMessage());
            System.exit(2);
        }

    }
    //====================================================================================================
    // Here CRUD Operations:
    // Create - Read - Update - Delete

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // We may change the logic of method in the future, based on our system requirements.
    // We will delete this note after complete the whole project.
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    // Insert Operation:
    public static void insertAllMeals(List<Meal> dishesList){
        lock.lock();
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO "+ TABLE_NAME + "(dishName, typeOfDish, ingredients, preparationSteps, timeToPrepare, uploadedBy, rating, hasVedio) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")){
            conn.setAutoCommit(false);
            for (Meal item :dishesList){
                pstmt.setString(1, item.getDishName());
                pstmt.setString(2,item.getTypeOfDish());
                pstmt.setString(3, item.getIngredients());
                pstmt.setString(4, item.getPreparationSteps());
                pstmt.setInt(5,item.getTimeToPrepare());
                pstmt.setString(6, item.getUploadedBy());
                pstmt.setDouble(7,item.getRating());
                pstmt.setString(8, item.getHasVedio());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            conn.commit();
        }
        catch (SQLException e){
            System.err.println("Failed to insert meal data");
        }
        finally{
            lock.unlock();
        }
    }





    // Read Operation:
     public static List<Meal> searchDish(String searchName) {
        List<Meal> results = new ArrayList<>();
        lock.lock();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT * FROM " + TABLE_NAME + " WHERE dishName ILIKE ?")) {
            pstmt.setString(1, "%" + searchName + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Meal meal = new Meal(
                            rs.getString("dishName"),
                            rs.getString("typeOfDish"),
                            rs.getString("ingredients"),
                            rs.getString("preparationSteps"),
                            rs.getInt("timeToPrepare"),
                            rs.getString("uploadedBy"),
                            rs.getDouble("rating"),
                            rs.getString("hasVedio")
                    );
                    results.add(meal);
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to search meals: " + e.getMessage());
        } finally {
            lock.unlock();
        }
        return results;
    }

    // Update Operation:
    public static boolean updateMeal(int id, Meal updatedMeal) {
        lock.lock();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "UPDATE " + TABLE_NAME + " SET " +
                             "dishName = ?, " +
                             "typeOfDish = ?, "+
                             "ingredients = ?, " +
                             "preparationSteps = ?, " +
                             "timeToPrepare = ?, " +
                             "uploadedBy = ?, " +
                             "rating = ?, " +
                             "hasVedio = ? " +
                             "WHERE id = ?")) {

            pstmt.setString(1, updatedMeal.getDishName());
            pstmt.setString(2, updatedMeal.getIngredients());
            pstmt.setString(3, updatedMeal.getPreparationSteps());
            pstmt.setInt(4, updatedMeal.getTimeToPrepare());
            pstmt.setString(5, updatedMeal.getUploadedBy());
            pstmt.setDouble(6, updatedMeal.getRating());
            pstmt.setString(7, updatedMeal.getHasVedio());
            pstmt.setInt(8, id);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Failed to update meal: " + e.getMessage());
            return false;
        } finally {
            lock.unlock();
        }
    }


    // Delete Operation:
    public static boolean deleteMeal(int id) {
        lock.lock();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "DELETE FROM " + TABLE_NAME + " WHERE id = ?")) {

            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Failed to delete meal: " + e.getMessage());
            return false;
        } finally {
            lock.unlock();
        }
    }

    //====================================================================================================

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
    }

}
