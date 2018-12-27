package project.gpa_calculator.activities.GPA_setter;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import project.gpa_calculator.Util.ActivityController;
import project.gpa_calculator.activities.main.MainActivity;
import project.gpa_calculator.models.GPA;
import project.gpa_calculator.models.GPAListItem;
import project.gpa_calculator.models.GPA_setting;
import project.gpa_calculator.models.ListItem;
import project.gpa_calculator.models.User;
import project.gpa_calculator.models.Year;

import static android.content.Context.MODE_PRIVATE;

public class GPA_setter_Controller extends ActivityController {
    private List<ListItem> listItems;

    private User user;

    GPA_setting gpa_setting;
    private Context context;

    public GPA_setter_Controller() {
    }

    public void setupListItems() {
        this.listItems = new ArrayList<>();

//        ListItem listItem1 = new GPAListItem(85,100,4.0,"A");
//        ListItem listItem2 = new GPAListItem(80,84,3.7,"A-");
//        ListItem listItem3 = new GPAListItem(77,79,3.3,"B+");
//        ListItem listItem4 = new GPAListItem(73,76,3.0,"B");
//        ListItem listItem5 = new GPAListItem(70,72,2.7,"B-");
//        ListItem listItem6 = new GPAListItem(67,69,2.3,"C+");
//        ListItem listItem7 = new GPAListItem(63,66,2.0,"C");
//        ListItem listItem8 = new GPAListItem(60,62,1.7,"C-");
//
//        listItems.addAll(Arrays.asList(listItem1,listItem2,listItem3,listItem4,listItem5,listItem6,listItem7,listItem8))
        gpa_setting = user.getGpa_setting();
        for(GPA g:gpa_setting){
            listItems.add(new GPAListItem(g.getLower(),g.getUpper(),g.getGrade_point(),g.getGrade()));
        }


    }

    @Override
    public void deleteItem(int position) {
//        user.getYear_list().remove(position);
        user.removeFromGPA(position);
        saveToFile(MainActivity.userFile);
    }


    public List<ListItem> getListItems() {
        return listItems;
    }

//    public void setupCurrentSemester(User user) {
//        this.user = user;
//        saveToFile(MainActivity.userFile);
//    }
    //TODO later
    public boolean addGPA(int low, int high,double gpa,String mark) {
        GPAListItem new_gpa= new GPAListItem(low,high,gpa,mark);
        user.addGPA(low,high,gpa,mark);

        this.listItems.add(new_gpa);
        saveToFile(MainActivity.userFile);

        return true;
    }

    public void setContext(Context context) {
        this.context = context;
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

