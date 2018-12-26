package project.gpa_calculator.activities.course;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import project.gpa_calculator.Util.ActivityController;
import project.gpa_calculator.activities.main.MainActivity;
import project.gpa_calculator.models.ListItem;
import project.gpa_calculator.models.Semester;
import project.gpa_calculator.models.User;
import project.gpa_calculator.models.Year;

import static android.content.Context.MODE_PRIVATE;

public class CourseActivityController extends ActivityController {
    private List<ListItem> listItems;

    private User user;

    private Year current_year;

    private Context context;

    CourseActivityController() {
    }

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

    public void setupUser(User user, String semester_name) {
        this.user = user;
//        this.current_year = user.getYe;
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
        current_year.getSemester_list().remove(position);
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
