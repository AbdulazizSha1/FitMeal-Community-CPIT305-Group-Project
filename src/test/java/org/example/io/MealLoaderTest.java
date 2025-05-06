package org.example.io;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MealLoaderTest {

    @Test
    public void testLoadMealsFromCSV() {
        assertDoesNotThrow(MealLoader::loadMealsFromCSV);
    }
}
