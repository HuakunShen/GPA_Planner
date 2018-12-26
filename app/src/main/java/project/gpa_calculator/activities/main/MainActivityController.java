package project.gpa_calculator.activities.main;

import project.gpa_calculator.models.Course;
import project.gpa_calculator.models.Semester;
import project.gpa_calculator.models.User;
import project.gpa_calculator.models.Year;

public class MainActivityController {

    private User user;


    public MainActivityController() {
        user = new User("admin", "admin", "admin");
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
