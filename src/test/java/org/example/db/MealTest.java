package org.example.db;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class MealTest {

    @Test
    public void testMealConstructorAndGetters() throws Exception {
        Meal meal = new Meal("Pizza", "Main Course", "Dough, Cheese", "Bake for 10 minutes", 15, "Ahmed", 4.5, "yes");

        assertEquals("Pizza", meal.getDishName());
        assertEquals("Main Course", meal.getTypeOfDish());
        assertEquals("Dough, Cheese", meal.getIngredients());
        assertEquals("Bake for 10 minutes", meal.getPreparationSteps());
        assertEquals(15, meal.getTimeToPrepare());
        assertEquals("Ahmed", meal.getUploadedBy());
        assertEquals(4.5, meal.getRating(), 0.01);
        assertEquals("yes", meal.getHasVedio());

        // Access private field isFavorite using reflection
        Field field = Meal.class.getDeclaredField("isFavorite");
        field.setAccessible(true);
        boolean isFavoriteValue = field.getBoolean(meal);
        assertFalse(isFavoriteValue);
    }
}
