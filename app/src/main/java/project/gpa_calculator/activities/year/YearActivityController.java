package project.gpa_calculator.activities.year;

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

public class YearActivityController extends ActivityController {
    private List<ListItem> listItems;

    private User user;


    private Context context;

    public YearActivityController() {
    }

    public void setupListItems() {
        this.listItems = new ArrayList<>();
//        List<Semester> semester_list = year_list.get(0).getSemester_list();
        for (Year year : user.getYear_list()) {
            ListItem item = new ListItem(year.getYear_name(), "Description", "GPA: ");
            listItems.add(item);
        }
    }

    @Override
    public void deleteItem(int position) {

    }

    @Override
    public User getUser() {
        return this.user;
    }

    public List<ListItem> getListItems() {
        return listItems;
    }

    public void setupUser(User user) {
        this.user = user;
    }

    public boolean addYear(String year_name, String description) {
        Year year= new Year(year_name);
        boolean result = user.addYear(year);
        if (result) {
            this.listItems.add(new ListItem(year_name, description, "GPA"));
            saveToFile(MainActivity.userFile);
        }
        return result;
    }

    public void setContext(Context context) {
        this.context = context;
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
