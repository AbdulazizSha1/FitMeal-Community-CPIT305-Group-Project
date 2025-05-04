package org.example.db;

public class Meal {
    private int id;
    private final String dishName;
    private final String typeOfDish;
    private final String ingredients;
    private final String preparationSteps;
    private final int timeToPrepare;
    private final String uploadedBy;
    private double rating;
    private final String hasVedio;
    private boolean isFavorite;

    public Meal(String dishName, String typeOfDish, String ingredients, String preparationSteps, int timeToPrepare, String uploadedBy, double rating, String hasVedio) {
        this.dishName = dishName;
        this.typeOfDish = typeOfDish;
        this.ingredients = ingredients;
        this.preparationSteps = preparationSteps;
        this.timeToPrepare = timeToPrepare;
        this.uploadedBy = uploadedBy;
        this.rating = rating;
        this.hasVedio = hasVedio;
        this.isFavorite = false;
    }

    public Meal(int id, String dishName, String typeOfDish, String ingredients, String preparationSteps, int timeToPrepare, String uploadedBy, double rating, String hasVedio) {
        this.id = id;
        this.dishName = dishName;
        this.typeOfDish = typeOfDish;
        this.ingredients = ingredients;
        this.preparationSteps = preparationSteps;
        this.timeToPrepare = timeToPrepare;
        this.uploadedBy = uploadedBy;
        this.rating = rating;
        this.hasVedio = hasVedio;
    }

    public int getId() {
        return id;
    }

    public String getDishName() {
        return dishName;
    }

    public String getTypeOfDish() {
        return typeOfDish;
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
                ", typeOfDish='" + typeOfDish + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", preparationSteps='" + preparationSteps + '\'' +
                ", timeToPrepare=" + timeToPrepare +
                ", uploadedBy='" + uploadedBy + '\'' +
                ", rating=" + rating +
                ", hasVedio='" + hasVedio + '\'' +
                '}';
    }
}
