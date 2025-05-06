package org.example.db;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {

    @Test
    public void testDatabaseInitialization() {
        Database.initializeDatabase();
        assertNotNull(Database.getAllMeals());
    }

    @Test
    public void testInsertAndRetrieveMeal() {
        Database.initializeDatabase();
        Meal meal = new Meal("Burger", "Fast Food", "Bun, Meat", "Grill it", 10, "Ali", 4.2, "no");
        List<Meal> meals = new ArrayList<>();
        meals.add(meal);
        Database.insertAllMeals(meals);

        List<Meal> allMeals = Database.getAllMeals();


        boolean found = allMeals.stream().anyMatch(m -> m.getDishName().equals("Burger"));
        assertTrue(found);
    }
}
