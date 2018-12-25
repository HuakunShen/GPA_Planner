package project.gpa_calculator.activities.semester;

import java.util.ArrayList;
import java.util.List;

import project.gpa_calculator.models.Course;
import project.gpa_calculator.models.ListItem;
import project.gpa_calculator.models.Semester;
import project.gpa_calculator.models.User;
import project.gpa_calculator.models.Year;

public class SemesterActivityController {

    private List<ListItem> listItems;

    private User user;

    private Year current_year;

    SemesterActivityController() {
//        current_year = user.getYear("2018");
    }

    public List<ListItem> getListItems() {
        return listItems;
    }

    public void setListItems(List<ListItem> listItems) {
        this.listItems = listItems;
    }

    public void setupListItems() {
        listItems = new ArrayList<>();
        List<Year> year_list = user.getYear_list();
        List<Semester> semester_list = year_list.get(0).getSemester_list();
        for (Semester semester : semester_list) {
            ListItem item = new ListItem(semester.getSemester_name(), "Description", "GPA: ");
            listItems.add(item);
        }
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    /**
     * just for testing, initialize a semester, comment out later
     */
    public void setupUserForTesting() {
        Year year = new Year("2018");
        Semester semester = new Semester("2018fall");
        year.addSemester(semester);
        Course course = new Course("CSC207", "Software Design", 85d, 0.5d);
        semester.addCourse(course);
        user.addYear(year);
        current_year = year;
    }

    public boolean addSemester(String name, String description) {
        Semester semester = new Semester(name);
        boolean result = current_year.addSemester(semester);
        if (result) {
            this.listItems.add(new ListItem(name, description, "GPA"));
        }
        return result;
    }
}
