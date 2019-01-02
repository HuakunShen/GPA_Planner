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

//    private User user;

    GPA_setting gpa_setting;
    private Context context;

    public GPA_setter_Controller(Context context) {
        this.context = context;
    }

    /**
     * set up recycler view base on information in user
     */
    public void setupListItems() {
        this.listItems = new ArrayList<>();
//        gpa_setting = user.getGpa_setting();
        for(GPA g:gpa_setting){
            listItems.add(new GPAListItem(g.getLower(),g.getUpper(),g.getGrade_point(),g.getGrade()));
        }
    }


    @Override
    public void deleteItem(int position) {
//        user.getYear_list().remove(position);
//        user.removeFromGPA(position);
        saveToFile(MainActivity.userFile);
    }


    public List<ListItem> getListItems() {
        return listItems;
    }


    /**
     * add a new GPA to recycler voew
     * @param low
     * @param high
     * @param gpa
     * @param mark
     * @return return true if success, in this case, alway true
     */
    public boolean addGPA(int low, int high,double gpa,String mark) {
        GPAListItem new_gpa= new GPAListItem(low,high,gpa,mark);
        gpa_setting.add(new GPA(low,high,gpa,mark));
        this.listItems.add(new_gpa);
        return true;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * save the update on gpa_setting to user
     */
    public void save_update(){
//        user.setGpa_setting(gpa_setting);
        saveToFile(MainActivity.userFile);
    }


    public void loadFromFile(String fileName) {
        try {
            InputStream inputStream = this.context.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
//                this.user = (User) input.readObject();
//                user = (User) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
    }

    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    context.openFileOutput(fileName, MODE_PRIVATE));
//            outputStream.writeObject(this.user);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}

