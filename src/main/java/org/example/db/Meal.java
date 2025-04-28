package org.example.db;

public class Meal {
    private final String dishName;
    private final String ingredients;
    private final String preparationSteps;
    private final int timeToPrepare;
    private final String uploadedBy;
    private final double rating;
    private final String hasVedio;

    public Meal(String dishName, String ingredients, String preparationSteps, int timeToPrepare, String uploadedBy, double rating, String hasVedio) {
        this.dishName = dishName;
        this.ingredients = ingredients;
        this.preparationSteps = preparationSteps;
        this.timeToPrepare = timeToPrepare;
        this.uploadedBy = uploadedBy;
        this.rating = rating;
        this.hasVedio = hasVedio;
    }

    public String getDishName() {
        return dishName;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getPreparationSteps() {
        return preparationSteps;
    }

    public int getTimeToPrepare() {
        return timeToPrepare;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public double getRating() {
        return rating;
    }

    public String getHasVedio() {
        return hasVedio;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "dishName='" + dishName + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", preparationSteps='" + preparationSteps + '\'' +
                ", timeToPrepare=" + timeToPrepare +
                ", uploadedBy='" + uploadedBy + '\'' +
                ", rating=" + rating +
                ", hasVedio='" + hasVedio + '\'' +
                '}';
    }
}
