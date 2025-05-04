package org.example;

import org.example.db.Database;
import org.example.io.MealAdder;
import org.example.io.MealDisplayer;
import org.example.io.MealLoader;
import org.example.io.MealFavorites;
import org.example.networking.ChatHandler;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize the database
        Database.initializeDatabase();

        // Load meals from CSV if not already loaded
        MealLoader.loadMealsFromCSV();

        while (true) {
            System.out.println("\n=== FitMeal Menu ===");
            System.out.println("1. Display All Meals");
            System.out.println("2. Add New Meal");
            System.out.println("3. Show Favorites");
            System.out.println("4. Start Chat");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    MealDisplayer.displayAllMeals();
                    break;
                case "2":
                    MealAdder.addNewMealFromUser();
                    break;
                case "3":
                    MealFavorites.displayFavorites();
                    break;
                case "4":
                    org.example.networking.ChatClient.main(new String[]{});
                    break;

                    /*Very important note in case 4:
                    What if we wrote:
                    org.example.networking.ChatServer.main(new String[]{});
                    org.example.networking.ChatClient.main(new String[]{});
                    break;
                    Is it better than run ChatServer in independent winodw?
                    Of course no!
                    // ChatServer must run in the background to accept incoming connections.
                    // If you start it from MainApp, it will block the main thread,
                    // which prevents the rest of the program (like the menu or ChatClient) from continuing.
                    */

                case "5":
                    System.out.println("Exiting FitMeal. Goodbye.");
                    return;
                default:
                    System.out.println("Invalid option. Please choose 1-5.");
            }
        }
    }
}
