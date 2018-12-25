package project.gpa_calculator.activities.semester;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import project.gpa_calculator.activities.main.MainActivity;
import project.gpa_calculator.models.Course;
import project.gpa_calculator.models.ListItem;
import project.gpa_calculator.models.Semester;
import project.gpa_calculator.models.User;
import project.gpa_calculator.models.Year;

import static android.content.Context.MODE_PRIVATE;

public class SemesterActivityController {

    private List<ListItem> listItems;

    private User user;

    private Year current_year;

    private Context context;

    SemesterActivityController() {}

    public void setContext(Context context) {
        this.context = context;
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

    public void setupUser(User user, String year_name) {
        this.user = user;
        this.current_year = user.getYear(year_name);
    }


    /**
     * just for testing, initialize a semester, comment out later
     */
//    public void setupUserForTesting() {
//
//        Year year = user.getYear_list().get(0);
//        Semester semester = new Semester("2018fall");
//        year.addSemester(semester);
//        Course course = new Course("CSC207", "Software Design", 85d, 0.5d);
//        semester.addCourse(course);
//        user.addYear(year);
//        current_year = year;
//    }

    public boolean addSemester(String name, String description) {
        Semester semester = new Semester(name);
        boolean result = current_year.addSemester(semester);
        if (result) {
            this.listItems.add(new ListItem(name, description, "GPA"));
            saveToFile(MainActivity.userFile);
        }
        return result;
    }

    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    context.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(this.user);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public void deleteItem(int position) {
        current_year.getSemester_list().remove(position);
        saveToFile(MainActivity.userFile);
    }
}
