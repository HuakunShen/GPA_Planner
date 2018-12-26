package project.gpa_calculator.activities.course;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
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
import project.gpa_calculator.models.YearListItem;

import static android.content.Context.MODE_PRIVATE;

public class CourseActivityController extends ActivityController {
    private List<ListItem> listItems;

    private User user;


    private Semester current_semester;

    private Context context;


    private Year current_year;

    CourseActivityController() {
    }

    public Year getCurrent_year() {
        return current_year;
    }

    public Semester getCurrent_semester() {
        return current_semester;
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
            ListItem item = new YearListItem(course.getCourse_code(), course.getCourse_name(), "Target: " + course.getTarget());
            listItems.add(item);
        }
    }



    public void setupCurrentSemester(Year year, String semester_name) {
        this.current_semester = user.getYear(year.getYear_name()).getSemester(semester_name);
        this.current_year = year;
    }


    public boolean addCourse(String course_name, String course_code, double target, double credit_weight) {
        if (course_code.equals("") && course_name.equals(""))
            return false;
        Course course = new Course(course_code, course_name, target, credit_weight);
        boolean result = current_semester.addCourse(course);
        if (result) {
            this.listItems.add(new YearListItem(course_code, course_name, "Target: " + target));
            saveToFile(MainActivity.userFile);
        }
        return result;
    }

    public void deleteItem(int position) {
        current_semester.removeFromCourseList(position);
        saveToFile(MainActivity.userFile);
    }


    public void loadFromFile(String fileName) {
        try {
            InputStream inputStream = this.context.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                this.user = (User) input.readObject();
//                user = (User) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
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
