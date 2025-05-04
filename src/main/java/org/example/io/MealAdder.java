package org.example.io;

import org.example.db.Database;
import org.example.db.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MealAdder {
    public static void addNewMealFromUser() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Enter dish name:");
            String dishName = scanner.nextLine();

            System.out.println("Select type of dish:");
            System.out.println("1 - Breakfast");
            System.out.println("2 - Lunch");
            System.out.println("3 - Dinner");
            String typeOfDish = "";
            int typeChoice = Integer.parseInt(scanner.nextLine());
            switch (typeChoice) {
                case 1:
                    typeOfDish = "Breakfast";
                    break;
                case 2:
                    typeOfDish = "Lunch";
                    break;
                case 3:
                    typeOfDish = "Dinner";
                    break;
                default:
                    System.out.println("Invalid choice. Defaulting to Lunch.");
                    typeOfDish = "Lunch";
                    break;
            }

            System.out.println("Enter ingredients (separated by commas):");
            String ingredients = scanner.nextLine();

            System.out.println("Enter preparation steps:");
            String preparationSteps = scanner.nextLine();

            System.out.println("Enter time to prepare (minutes):");
            int timeToPrepare = Integer.parseInt(scanner.nextLine());

            System.out.println("Enter your name:");
            String uploadedBy = scanner.nextLine();


            double rating = 0.0;


            System.out.println("Does it have a video? (yes/no):");
            String hasVedio = scanner.nextLine().trim().toLowerCase();
            if (!hasVedio.equals("yes") && !hasVedio.equals("no")) {
                hasVedio = "no";
            }

            Meal newMeal = new Meal(
                    dishName,
                    typeOfDish,
                    ingredients,
                    preparationSteps,
                    timeToPrepare,
                    uploadedBy,
                    rating,
                    hasVedio
            );

            List<Meal> tempList = new ArrayList<>();
            tempList.add(newMeal);
            Database.insertAllMeals(tempList);

            System.out.println("Meal added successfully!");

        } catch (NumberFormatException e) {
            System.err.println("Invalid number entered. Meal not added.");
        }
    }
}

