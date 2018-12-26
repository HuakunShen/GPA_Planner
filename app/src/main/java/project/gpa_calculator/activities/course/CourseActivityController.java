package project.gpa_calculator.activities.course;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import project.gpa_calculator.Util.ActivityController;
import project.gpa_calculator.activities.main.MainActivity;
import project.gpa_calculator.models.Course;
import project.gpa_calculator.models.ListItem;
import project.gpa_calculator.models.Semester;
import project.gpa_calculator.models.User;
import project.gpa_calculator.models.Year;

import static android.content.Context.MODE_PRIVATE;

public class CourseActivityController extends ActivityController {
    private List<ListItem> listItems;

    private User user;

    private Semester current_semester;

    private Context context;

    CourseActivityController() {
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<ListItem> getListItems() {
        return listItems;
    }


    public void setupListItems() {
        listItems = new ArrayList<>();
        for (Course course : this.current_semester) {
            ListItem item = new ListItem(course.getCourse_name(), "Description", "GPA: ");
            listItems.add(item);
        }
    }


    public User getUser() {
        return user;
    }

    public void setupUser(User user, Year year, String semester_name) {
        this.user = user;
        this.current_semester = year.getSemester(semester_name);
    }


    public boolean addCourse(String course_name, String description) {
//        Course course = new Course(course_name);
//        boolean result = current_year.addSemester(semester);
//        if (result) {
//            this.listItems.add(new ListItem(name, description, "GPA"));
//            saveToFile(MainActivity.userFile);
//        }
//        return result;
        return false;
    }

    public void deleteItem(int position) {
//        current_year.getSemester_list().remove(position);
//        saveToFile(MainActivity.userFile);
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
}
