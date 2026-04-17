package model;

public abstract class Goal {

    public abstract double calculateTarget(double tdee);

    public String evaluate(double intake, double target) {

        double diff = intake - target;

        if (Math.abs(diff) <= 50) {
            return "✅ Good job! You are on track.";
        } 
        else if (diff > 0) {
            return "🔺 You consumed too many calories today (+ " + diff + " kcal)";
        } 
        else {
            return "🔻 You consumed too few calories today (" + diff + " kcal)";
        }
    }
}