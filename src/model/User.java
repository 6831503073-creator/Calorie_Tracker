package model;

import exception.InvalidInputException;

public class User {
    private String name;
    private double weight, height;
    private int age;

    public User(String name, double weight, double height, int age) throws InvalidInputException {

        if (weight <= 0) throw new InvalidInputException("Weight must be positive.");
        if (height <= 0) throw new InvalidInputException("Height must be positive.");
        if (age <= 0) throw new InvalidInputException("Age must be positive.");

        this.name = name;
        this.weight = weight;
        this.height = height;
        this.age = age;
    }

    public double calculateBMR() {
        return 10 * weight + 6.25 * height - 5 * age + 5;
    }
}