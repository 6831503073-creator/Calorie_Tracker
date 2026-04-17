package model;

public class LoseWeightGoal extends Goal {
    public double calculateTarget(double tdee) {
        return tdee - 500;
    }
}