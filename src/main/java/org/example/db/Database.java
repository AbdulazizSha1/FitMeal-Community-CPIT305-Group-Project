package org.example.db;

import java.sql.*;
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

            stmt.execute("DROP TABLE IF NOT EXISTS "+TABLE_NAME);

            String createTableSQL = "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME + " (" +
                    "id SERIAL PRIMARY KEY, " +
                    "dishName VARCHAR(255) NOT NULL," +
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

    public static void insertAllDishes(List<Meal> dishesList){
        lock.lock();
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO "+ TABLE_NAME + "(dishName, ingredients, preparationSteps, timeToPrepare, uploadedBy, rating, hasVedio) VALUES (?, ?, ?, ?, ?, ?, ?)")){
            conn.setAutoCommit(false);
            for (Meal item :dishesList){
                pstmt.setString(1, item.getDishName());
                pstmt.setString(2, item.getIngredients());
                pstmt.setString(3, item.getPreparationSteps());
                pstmt.setInt(4,item.getTimeToPrepare());
                pstmt.setString(5, item.getUploadedBy());
                pstmt.setDouble(6,item.getRating());
                pstmt.setString(7, item.getHasVedio());
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

    // implement the search (SELECT)

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
    }

}
