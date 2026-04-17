package service;

import java.util.ArrayList;
import model.*;

// Handles food tracking
public class Tracker {

    private ArrayList<Food> foods = new ArrayList<>();

    // Getter (ใช้กับ File I/O)
    public ArrayList<Food> getFoods() {
        return foods;
    }

    public void addFood(Food f) {
        foods.add(f);
    }

    public double totalCaloriesIn() {
        double sum = 0;

        for (Food f : foods) {
            sum += f.getCalories();
        }

        return sum;
    }
} 