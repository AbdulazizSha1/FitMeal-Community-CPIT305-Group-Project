package org.example.io;

import org.example.db.Database;
import org.example.db.Meal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MealLoader {


    private static final String CSV_FILE_PATH = "meals.csv";

    public static void loadMealsFromCSV() {
        if (!Database.isMealTableEmpty()) {
            return;
        }

        List<Meal> meals = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] values = line.split(",");
                if (values.length < 8) continue;

                String dishName = values[0].trim();
                String typeOfDish = values[1].trim();
                String ingredients = values[2].trim();
                String preparationSteps = values[3].trim();
                int timeToPrepare = Integer.parseInt(values[4].trim());
                String uploadedBy = values[5].trim();
                double rating = Double.parseDouble(values[6].trim());
                String hasVedio = values[7].trim();

                Meal meal = new Meal(dishName, typeOfDish, ingredients, preparationSteps,
                        timeToPrepare, uploadedBy, rating, hasVedio);

                meals.add(meal);
            }

            Database.insertAllMeals(meals);
            System.out.println("Meals loaded successfully from CSV.");

        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format in CSV: " + e.getMessage());
        }
    }
}

