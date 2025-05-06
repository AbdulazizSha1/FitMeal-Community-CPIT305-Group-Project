package org.example.io;

import org.example.db.Database;
import org.example.db.Meal;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MealAdderTest {

    @Test
    public void testAddMealToDatabaseUsingInsertAll() {
        Database.initializeDatabase();
        Meal meal = new Meal("Salad", "Appetizer", "Lettuce, Tomato", "Mix all", 5, "Sara", 3.8, "no");

        List<Meal> list = new ArrayList<>();
        list.add(meal);
        Database.insertAllMeals(list);

        List<Meal> allMeals = Database.getAllMeals();
        boolean found = allMeals.stream().anyMatch(m -> m.getDishName().equals("Salad"));
        assertTrue(found);
    }
}
