package project.gpa_calculator.activities.main;

import project.gpa_calculator.models.Course;
import project.gpa_calculator.models.Event;
import project.gpa_calculator.models.GPA_Calculator;
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


    public void initializeUserForTesting() {
        Year year2018ForTesting = new Year("year2018ForTesting");
        Semester fall = new Semester("Fall");
        year2018ForTesting.addSemester(fall);
        Course course1 = new Course("CSC207", "Software Design", 85d, 0.5);
        fall.addCourse(course1);
        Event event = new Event("midterm", 50d);
        Event event1 = new Event("final", 50d);
        course1.addEvent(event);
        course1.addEvent(event1);
        Course course2 = new Course("CSC343", "Intro To Database", 80d, 0.5d);
        fall.addCourse(course2);
        Event event3 = new Event("final", 100d);
        course2.addEvent(event3);
        event3.setEvent_score(70);
        this.user.addYear(year2018ForTesting);

    }
}
