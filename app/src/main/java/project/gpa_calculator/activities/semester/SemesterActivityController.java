package project.gpa_calculator.activities.semester;

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

public class SemesterActivityController extends ActivityController {

    private List<ListItem> listItems;

    private User user;


    private Year current_year;

    private Context context;

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

    public void setupUser(User user, String year_name) {
        this.user = user;
        this.current_year = user.getYear(year_name);
    }


    public boolean addSemester(String name, String description) {
        Semester semester = new Semester(name);
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
