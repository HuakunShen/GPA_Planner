package project.gpa_calculator.activities.year;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import project.gpa_calculator.Adapter.RecyclerViewAdapter;
import project.gpa_calculator.Util.ActivityController;
import project.gpa_calculator.activities.main.MainActivity;
import project.gpa_calculator.models.ListItem;
import project.gpa_calculator.models.Semester;
import project.gpa_calculator.models.User;
import project.gpa_calculator.models.Year;
import project.gpa_calculator.models.YearListItem;

import static android.content.Context.MODE_PRIVATE;

public class YearActivityController extends ActivityController {
    private List<ListItem> listItems = new ArrayList<>();

//    private YearActivity yearActivity;

    private Context context;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    DocumentReference userRef = db.collection("Users").document(mAuth.getUid());

    private List<Year> year_list;


    public List<Year> getYear_list() {
        return year_list;
    }

    public void setYear_list(List<Year> year_list) {
        this.year_list = year_list;
    }


    private static final String TAG = "YearActivityController";


    public YearActivityController() {
//        this.yearActivity = yearActivity;
        this.year_list = new ArrayList<>();

    }


    public void setupListItems() {
//        this.listItems = new ArrayList<>();
//        List<Semester> semester_list = year_list.get(0).getSemester_list();
//        for (Year year : user.getYear_list()) {
//            ListItem item = new YearListItem(year.getYear_name(), "Description", "GPA: ");
//            listItems.add(item);
//        }
        for (Year year : this.year_list) {
            ListItem item = new YearListItem(year.getYear_name(), "Description", "GPA: ");
            this.listItems.add(item);
        }
    }

    public void setupYearList() {
        this.year_list = new ArrayList<>();

        userRef.collection("Years")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                year_list.add(document.toObject(Year.class));
                            }

//                            adapter = new RecyclerViewAdapter(context, listItems, );
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        setupListItems();
    }

    @Override
    public void deleteItem(int position) {
//        user.getYear_list().remove(position);
//        user.removeFromYearList(position);
//        saveToFile(MainActivity.userFile);
    }


    public List<ListItem> getListItems() {
        return listItems;
    }

//    public void setupCurrentSemester(User user) {
//        this.user = user;
//        saveToFile(MainActivity.userFile);
//    }

    public boolean addYear(String year_name, String description) {
        Year year= new Year(year_name);
        userRef.collection("Years").add(year);
//        boolean result = user.addYear(year);
//        if (result) {
            this.listItems.add(new YearListItem(year_name, description, "GPA"));
//            saveToFile(MainActivity.userFile);
//        }
        return true;
    }




    public void setContext(Context context) {
        this.context = context;
    }

//    public void loadFromFile(String fileName) {
//        try {
//            InputStream inputStream = this.context.openFileInput(fileName);
//            if (inputStream != null) {
//                ObjectInputStream input = new ObjectInputStream(inputStream);
//                this.user = (User) input.readObject();
////                user = (User) input.readObject();
//                inputStream.close();
//            }
//        } catch (FileNotFoundException e) {
//            Log.e("login activity", "File not found: " + e.toString());
//        } catch (IOException e) {
//            Log.e("login activity", "Can not read file: " + e.toString());
//        } catch (ClassNotFoundException e) {
//            Log.e("login activity", "File contained unexpected data type: " + e.toString());
//        }
//    }
//
//    public void saveToFile(String fileName) {
//        try {
//            ObjectOutputStream outputStream = new ObjectOutputStream(
//                    context.openFileOutput(fileName, MODE_PRIVATE));
//            outputStream.writeObject(this.user);
//            outputStream.close();
//        } catch (IOException e) {
//            Log.e("Exception", "File write failed: " + e.toString());
//        }
//    }
}
