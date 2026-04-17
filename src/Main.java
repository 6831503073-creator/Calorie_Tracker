import java.util.Scanner;
import model.*;
import service.*;
import exception.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Tracker tracker = new Tracker();
        FileManager fileManager = new FileManager();

        System.out.println("=== Calorie Tracker System ===");
        // ===== Name =====
        System.out.print("Enter name: ");
        String name = sc.nextLine();

        // โหลดข้อมูลของ user คนนี้
        java.util.HashMap<Integer, ResultRecord> history = fileManager.loadResults(name);

        // ถ้ามีข้อมูลเก่า → แสดง
        if (!history.isEmpty()) {
            System.out.println("\nPrevious records:");
            for (int key : history.keySet()) {
                System.out.println("Slot " + key + ": " + history.get(key));
            }
        }

        // ===== Weight =====
        double weight = 0;
        while (true) {
            try {
                System.out.print("Enter weight (kg): ");
                weight = sc.nextDouble();

                if (weight <= 0)
                    throw new InvalidInputException("Weight must be positive.");

                break;
            } catch (Exception e) {
                System.out.println("X Invalid weight. Try again.");
                sc.nextLine();
            }
        }

        // ===== Height =====
        double height = 0;
        while (true) {
            try {
                System.out.print("Enter height (cm): ");
                height = sc.nextDouble();

                if (height <= 0)
                    throw new InvalidInputException("Height must be positive.");

                break;
            } catch (Exception e) {
                System.out.println("X Invalid height. Try again.");
                sc.nextLine();
            }
        }

        // ===== Age =====
        int age = 0;
        while (true) {
            try {
                System.out.print("Enter age: ");
                age = sc.nextInt();

                if (age <= 0)
                    throw new InvalidInputException("Age must be positive.");

                break;
            } catch (Exception e) {
                System.out.println("X Invalid age. Try again.");
                sc.nextLine();
            }
        }

        // ===== Create User =====
        User user = null;
        try {
            user = new User(name, weight, height, age);
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // ===== Activity Level =====
        ActivityLevel activity = null;

        while (activity == null) {
            try {
                System.out.println("\nSelect Activity Level:");
                System.out.println("1. No exercise");
                System.out.println("2. Light (1-3 days/week)");
                System.out.println("3. Moderate (3-5 days/week)");
                System.out.println("4. Heavy (6-7 days/week)");
                System.out.println("5. Athlete");

                System.out.print("Choose (1-5): ");
                int actChoice = sc.nextInt();

                switch (actChoice) {
                    case 1:
                        activity = ActivityLevel.SEDENTARY;
                        break;
                    case 2:
                        activity = ActivityLevel.LIGHT;
                        break;
                    case 3:
                        activity = ActivityLevel.MODERATE;
                        break;
                    case 4:
                        activity = ActivityLevel.HEAVY;
                        break;
                    case 5:
                        activity = ActivityLevel.ATHLETE;
                        break;
                    default:
                        throw new InvalidInputException("Please enter number between 1-5.");
                }

            } catch (Exception e) {
                System.out.println("X Invalid choice. Try again.");
                sc.nextLine(); // clear buffer
            }
        }

        // ===== Goal =====
        Goal goal = null;

        while (goal == null) {
            try {
                System.out.println("\nSelect Goal:");
                System.out.println("1. Lose Weight");
                System.out.println("2. Gain Weight");
                System.out.println("3. Maintain");

                System.out.print("Choose (1-3): ");
                int goalChoice = sc.nextInt();

                switch (goalChoice) {
                    case 1:
                        goal = new LoseWeightGoal();
                        break;
                    case 2:
                        goal = new GainWeightGoal();
                        break;
                    case 3:
                        goal = new MaintainGoal();
                        break;
                    default:
                        throw new InvalidInputException("Please enter number between 1-3.");
                }

            } catch (Exception e) {
                System.out.println("X Invalid choice. Try again.");
                sc.nextLine();
            }
        }

        // ===== Menu =====
        while (true) {
            try {
                System.out.println("\n1. Add Food");
                System.out.println("2. Show Result");
                System.out.println("3. Exit");
                System.out.print("Choose (1-3): ");

                int choice = sc.nextInt();

                // validate ก่อน
                if (choice < 1 || choice > 3) {
                    throw new InvalidInputException("Please choose between 1-3.");
                }

                // ===== Logic =====
                if (choice == 1) {
                    sc.nextLine();

                    System.out.print("Food name: ");
                    String fname = sc.nextLine();

                    double cal = 0;
                    while (true) {
                        try {
                            System.out.print("Calories: ");
                            cal = sc.nextDouble();

                            if (cal <= 0)
                                throw new InvalidInputException("Calories must be positive.");

                            break;
                        } catch (Exception e) {
                            System.out.println("X Invalid calories. Try again.");
                            sc.nextLine();
                        }
                    }

                    tracker.addFood(new Food(fname, cal));
                    System.out.println(" Food added!");

                } else if (choice == 2) {

                    // Calculate core values
                    double bmr = user.calculateBMR();
                    double tdee = bmr * activity.getMultiplier();
                    double intake = tracker.totalCaloriesIn();
                    double target = goal.calculateTarget(tdee);

                    // Display result to user
                    System.out.println("\n===== Result =====");
                    System.out.println("BMR: " + bmr);
                    System.out.println("TDEE: " + tdee);
                    System.out.println("Calories Intake: " + intake);
                    System.out.println("Target Calories: " + target);
                    System.out.println("Difference: " + (intake - target));
                    System.out.println("Status: " + goal.evaluate(intake, target));

                    // Create result object for saving (encapsulation of result data)
                    ResultRecord record = new ResultRecord(
                            bmr,
                            tdee,
                            intake,
                            target,
                            goal.evaluate(intake, target));

                    sc.nextLine();

                    String ans = "";

                    // Validate input (only y or n allowed)
                    while (true) {
                        System.out.print("\nDo you want to save this result? (y/n): ");
                        ans = sc.nextLine().trim();

                        if (ans.equalsIgnoreCase("y") || ans.equalsIgnoreCase("n")) {
                            break;
                        } else {
                            System.out.println("Invalid input. Please enter only 'y' or 'n'.");
                        }
                    }

                    // Process answer
                    if (ans.equalsIgnoreCase("y")) {
                        int slot = 0;

                        while (true) {
                            try {
                                System.out.print("Choose slot (1-7): ");
                                slot = sc.nextInt();

                                if (slot < 1 || slot > 7) {
                                    throw new InvalidInputException("Slot must be 1-7");
                                }

                                break;

                            } catch (Exception e) {
                                System.out.println("Invalid slot. Try again.");
                                sc.nextLine();
                            }
                        }

                        fileManager.saveResult(name, slot, record);
                    }

                } else if (choice == 3) {

                    System.out.println("Goodbye!");
                    break;
                }

            } catch (InvalidInputException e) {
                System.out.println("X " + e.getMessage());
            } catch (Exception e) {
                System.out.println("X Invalid input type.");
                sc.nextLine(); // clear buffer
            }
        }
    }
}