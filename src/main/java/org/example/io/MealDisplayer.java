package org.example.io;

import org.example.db.Database;
import org.example.db.Meal;

import java.util.List;
import java.util.Scanner;

public class MealDisplayer {
    public static void displayAllMeals() {
        List<Meal> meals = Database.getAllMeals();
        Scanner scanner = new Scanner(System.in);

        if (meals.isEmpty()) {
            System.out.println("No meals available to display.");
            return;
        }

        System.out.println("=== All Available Meals ===");
        for (Meal meal : meals) {
            System.out.println("-------------------------------");
            System.out.println("Dish ID: " + meal.getId());
            System.out.println("Dish Name: " + meal.getDishName());
            System.out.println("Type: " + meal.getTypeOfDish());
            System.out.println("Ingredients: " + meal.getIngredients());
            System.out.println("Preparation Steps: " + meal.getPreparationSteps());
            System.out.println("Time to Prepare: " + meal.getTimeToPrepare() + " minutes");
            System.out.println("Uploaded By: " + meal.getUploadedBy());
            System.out.println("Rating: " + meal.getRating());
            System.out.println("Has Video: " + meal.getHasVedio());
        }

        while (true) {
            System.out.print("\nWould you like to add a meal to favorites? (yes/no): ");
            String answer = scanner.nextLine().trim().toLowerCase();

            if (answer.equals("no")) {
                break;
            } else if (answer.equals("yes")) {
                System.out.print("Enter the Dish ID to add to favorites: ");
                try {
                    int id = Integer.parseInt(scanner.nextLine());
                    Meal selectedMeal = null;
                    for (Meal meal : meals) {
                        if (meal.getId() == id) {
                            selectedMeal = meal;
                            break;
                        }
                    }
                    if (selectedMeal != null) {
                        MealFavorites.addToFavorites(selectedMeal);
                    } else {
                        System.out.println("Invalid Dish ID.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number.");
                }
            } else {
                System.out.println("Please answer with 'yes' or 'no'.");
            }
        }
    }
}
