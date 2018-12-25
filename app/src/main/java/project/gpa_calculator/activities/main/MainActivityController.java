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

    public void setupUserForTesting() {
        if (this.user.getYear_list().isEmpty()){
            user.addYear(new Year("2018"));
        }
//        Year year = user.getYear_list().get(0);
//        Semester semester = new Semester("2018fall");
//        year.addSemester(semester);
//        Course course = new Course("CSC207", "Software Design", 85d, 0.5d);
//        semester.addCourse(course);
//        user.addYear(year);
    }
}
