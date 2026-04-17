package model;

import exception.InvalidInputException;

public class Food {
    private String name;
    private double calories;

    public Food(String name, double calories) throws InvalidInputException {
        if (calories <= 0)
            throw new InvalidInputException("Calories must be positive.");

        this.name = name;
        this.calories = calories;
    }

    public double getCalories() {
        return calories;
    }

    public String toString() {
        return name + "," + calories;
    }
}