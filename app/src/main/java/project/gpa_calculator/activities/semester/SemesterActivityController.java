package project.gpa_calculator.activities.semester;

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

import static android.content.Context.MODE_PRIVATE;

public class SemesterActivityController extends ActivityController {

    private List<ListItem> listItems;

    private User user;


    private Year current_year;

    private Context context;

    private String year_name;

    SemesterActivityController() {
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<ListItem> getListItems() {
        return listItems;
    }

    public void setupListItems() {
        listItems = new ArrayList<>();
        for (Semester semester : current_year) {
            ListItem item = new ListItem(semester.getSemester_name(), "Description", "GPA: ");
            listItems.add(item);
        }
    }

    public Year getCurrent_year() {
        return current_year;
    }

    public User getUser() {
        return user;
    }

    public void setupCurrentYear(String year_name) {
        this.current_year = user.getYear(year_name);
//        this.year_name = year_name;
    }


    public boolean addSemester(String name, String description) {
        Semester semester = new Semester(name);
//        Course course = new Course(course_name);
        boolean result = current_year.addSemester(semester);
        if (result) {
            this.listItems.add(new ListItem(name, description, "GPA"));
            saveToFile(MainActivity.userFile);
        }
        return result;
    }

    public void deleteItem(int position) {
        current_year.removeFromSemesterList(position);
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
