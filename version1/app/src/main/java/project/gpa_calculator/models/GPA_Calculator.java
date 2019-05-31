package project.gpa_calculator.models;

import java.util.HashMap;
import java.util.Map;

public class GPA_Calculator {

    private static Map<String, Double> letter_gpa_map = new HashMap<String, Double>() {
        {
            put("A+", 4.0d);
            put("A", 4.0d);
            put("A-", 3.7d);
            put("B+", 3.3d);
            put("B", 3.0d);
            put("B-", 2.7d);
            put("C+", 2.3d);
            put("C", 2.0d);
            put("C-", 1.7d);
            put("D+", 1.3d);
            put("D", 1.0d);
            put("D-", 1.0d);
            put("F", 0.0d);
        }
    };



    public static double calculate_gpa(Year year) {
        double gpa_sum = 0d;
        double weight_sum = 0d;
        for (Semester semester : year) {
            for (Course course : semester) {
                if (!course.isDone()) {
                    return -1d;
                } else {
                    gpa_sum += calculate_gpa(course) * course.getCredit();
                    weight_sum += course.getCredit();
                }
            }
        }
        for (Course course : year.getYear_course_list()) {
            gpa_sum += calculate_gpa(course) * course.getCredit();
            weight_sum += course.getCredit();
        }
        return gpa_sum / weight_sum;
    }

    public static double calculate_gpa(Semester semester) {
        double gpa_sum = 0d;
        double weight_sum = 0d;
        for (Course course : semester) {
            if (!course.isDone()) {
                return -1d;
            } else {
                gpa_sum += calculate_gpa(course) * course.getCredit();
                weight_sum += course.getCredit();
            }
        }
        return gpa_sum / weight_sum;
    }

    public static double calculate_gpa(Course course) {
        double percentage_sum = 0d;
        for (Event event : course) {
            if (event.isDone()) {
                percentage_sum += event.getEvent_score() * event.getEvent_weight() / 100d;
            } else {
                return -1d;
            }
        }
        return calculate_gpa(calculate_letter_grade(percentage_sum));
    }

    public static double calculate_gpa(String letter_grade) {
        Double gpa = letter_gpa_map.get(letter_grade);
        return gpa != null ? gpa : -1d;
    }

    public static String calculate_letter_grade(double percentage) {
        if (90 <= percentage && percentage <= 100) {
            return "A+";
        } else if (85 <= percentage && percentage <= 89) {
            return "A";
        } else if (80 <= percentage && percentage <= 84) {
            return "A-";
        } else if (77 <= percentage && percentage <= 79) {
            return "B+";
        } else if (73 <= percentage && percentage <= 76) {
            return "B";
        } else if (70 <= percentage && percentage <= 72) {
            return "B-";
        } else if (67 <= percentage && percentage <= 69) {
            return "C+";
        } else if (63 <= percentage && percentage <= 66) {
            return "C";
        } else if (60 <= percentage && percentage <= 62) {
            return "C-";
        } else if (57 <= percentage && percentage <= 59) {
            return "D+";
        } else if (53 <= percentage && percentage <= 56) {
            return "D";
        } else if (50 <= percentage && percentage <= 52) {
            return "D-";
        } else {
            return "F";
        }
    }

}
