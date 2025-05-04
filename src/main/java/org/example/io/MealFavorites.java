package org.example.io;

import org.example.db.Meal;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MealFavorites {
    private static final String FAVORITES_FILE = "favorites.csv";

    public static void addToFavorites(Meal meal) {
        if (isMealAlreadyFavorited(meal.getId())) {
            System.out.println("This meal is already in favorites.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FAVORITES_FILE, true))) {
            writer.write(
                    meal.getId() + "," +
                            meal.getDishName() + "," +
                            meal.getTypeOfDish() + "," +
                            meal.getIngredients() + "," +
                            meal.getPreparationSteps() + "," +
                            meal.getTimeToPrepare() + "," +
                            meal.getUploadedBy() + "," +
                            meal.getRating() + "," +
                            meal.getHasVedio()
            );
            writer.newLine();
            System.out.println("Meal added to favorites.");

        } catch (IOException e) {
            System.err.println("Failed to add meal to favorites: " + e.getMessage());
        }
    }

    private static boolean isMealAlreadyFavorited(int id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FAVORITES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > 0) {
                    try {
                        int existingId = Integer.parseInt(values[0].trim());
                        if (existingId == id) {
                            return true;
                        }
                    } catch (NumberFormatException e) {
                    }
                }
            }
        } catch (IOException e) {
        }
        return false;
    }

    public static void displayFavorites() {
        List<Meal> favorites = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FAVORITES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length < 8) continue;

                Meal meal = new Meal(
                        Integer.parseInt(values[0].trim()),
                        values[1].trim(),
                        values[2].trim(),
                        values[3].trim(),
                        values[4].trim(),
                        Integer.parseInt(values[5].trim()),
                        values[6].trim(),
                        Double.parseDouble(values[7].trim()),
                        values[8].trim()
                );
                favorites.add(meal);
            }
        } catch (IOException e) {
            System.err.println("Failed to read favorites: " + e.getMessage());
        }

        if (favorites.isEmpty()) {
            System.out.println("No favorites found.");
            return;
        }

        System.out.println("=== Favorite Meals ===");
        for (Meal meal : favorites) {
            System.out.println("-------------------------------");
            System.out.println("Dish Name: " + meal.getDishName());
            System.out.println("Type: " + meal.getTypeOfDish());
            System.out.println("Ingredients: " + meal.getIngredients());
            System.out.println("Preparation Steps: " + meal.getPreparationSteps());
            System.out.println("Time to Prepare: " + meal.getTimeToPrepare() + " minutes");
            System.out.println("Uploaded By: " + meal.getUploadedBy());
            System.out.println("Rating: " + meal.getRating());
            System.out.println("Has Video: " + meal.getHasVedio());
        }
        System.out.println("=======================");
    }
}


