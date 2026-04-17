package model;

public class GainWeightGoal extends Goal {
    public double calculateTarget(double tdee) {
        return tdee + 300;
    }
}