package org.example.io;

import org.example.db.Database;
import org.example.db.Meal;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MealFavoritesTest {

    @Test
    public void testToggleFavoriteUsingReflection() throws Exception {
        Database.initializeDatabase();
        Meal meal = new Meal("Soup", "Starter", "Water, Salt", "Boil", 20, "Lina", 4.0, "no");


        List<Meal> list = new ArrayList<>();
        list.add(meal);
        Database.insertAllMeals(list);


        Field field = Meal.class.getDeclaredField("isFavorite");
        field.setAccessible(true);
        field.setBoolean(meal, true);

        assertTrue(field.getBoolean(meal));
    }
}
