package model;

public class ResultRecord {
    private double bmr;
    private double tdee;
    private double intake;
    private double target;
    private String status;

    public ResultRecord(double bmr, double tdee, double intake, double target, String status) {
        this.bmr = bmr;
        this.tdee = tdee;
        this.intake = intake;
        this.target = target;
        this.status = status;
    }

    public String toFileString() {
        return bmr + "," + tdee + "," + intake + "," + target + "," + status;
    }

    public static ResultRecord fromString(String line) {
        String[] p = line.split(",");
        return new ResultRecord(
                Double.parseDouble(p[0]),
                Double.parseDouble(p[1]),
                Double.parseDouble(p[2]),
                Double.parseDouble(p[3]),
                p[4]
        );
    }

    public String toString() {
        return "BMR=" + bmr +
               ", TDEE=" + tdee +
               ", Intake=" + intake +
               ", Target=" + target +
               ", Status=" + status;
    }
}