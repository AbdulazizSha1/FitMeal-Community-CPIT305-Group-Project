package org.example.io;

import org.example.db.Database;
import org.example.db.Meal;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MealDisplayerTest {

    @Test
    public void testDisplayAllMealsWithoutCrash() {
        Database.initializeDatabase();

        Meal meal = new Meal("Koshari", "Main", "Rice, Lentils", "Boil and mix", 30, "Ahmed", 5.0, "yes");
        List<Meal> list = new ArrayList<>();
        list.add(meal);
        Database.insertAllMeals(list);

        String simulatedUserInput = "no\n";
        System.setIn(new java.io.ByteArrayInputStream(simulatedUserInput.getBytes()));

        assertDoesNotThrow(MealDisplayer::displayAllMeals);
    }
}
