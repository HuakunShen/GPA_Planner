package project.gpa_calculator.activities.event;

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
import project.gpa_calculator.models.Event;
import project.gpa_calculator.models.ListItem;
import project.gpa_calculator.models.Semester;
import project.gpa_calculator.models.User;
import project.gpa_calculator.models.Year;
import project.gpa_calculator.models.YearListItem;

import static android.content.Context.MODE_PRIVATE;

public class EventActivityController extends ActivityController {
    private Context context;
    private User user;
    private Course current_course;
    private List<ListItem> listItems;

    public EventActivityController(Context context) {
        this.context = context;
    }

    @Override
    public void deleteItem(int position) {
        this.current_course.removeFromEventList(position);
        saveToFile(MainActivity.userFile);
    }

    public void setupCurrentCourse(Year year, Semester semester, String course_code) {
        this.current_course = this.user.getYear(year.getYear_name()).
                getSemester(semester.getSemester_name()).getCourse(course_code);
    }

    public void setupListItems() {
        this.listItems = new ArrayList<>();
        for (Event event : this.current_course) {
            ListItem item = new YearListItem(event.getEvent_name(), "Score: " +
                    event.getEvent_score(), "Weight: " + event.getEvent_weight(), null);
            listItems.add(item);
        }
    }

    public List<ListItem> getListItems() {
        return listItems;
    }

    public boolean addEvent(String name, double weight) {
        if (name.equals(""))
            return false;
        Event event = new Event(name, weight);
        boolean result = this.current_course.addEvent(event);
        if (result) {
            this.listItems.add(new YearListItem(name, "Weight: " + weight + "%", "Score: " + event.getEvent_score(), null));
            saveToFile(MainActivity.userFile);
        }
        return result;
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
