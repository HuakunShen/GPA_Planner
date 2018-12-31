package project.gpa_calculator.activities.year;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import project.gpa_calculator.R;
import project.gpa_calculator.Util.ActivityController;
import project.gpa_calculator.Util.SwipeToDeleteCallback;
import project.gpa_calculator.activities.main.MainActivity;
import project.gpa_calculator.models.ListItem;
import project.gpa_calculator.models.Semester;
import project.gpa_calculator.models.User;
import project.gpa_calculator.models.Year;
import project.gpa_calculator.models.YearListItem;

import static android.content.Context.MODE_PRIVATE;

public class YearActivityController extends ActivityController {
    private List<ListItem> listItems = new ArrayList<>();

    private static final String TAG = "YearActivityController";

    private RecyclerView recyclerView;

    private RecyclerView.Adapter adapter;
    private Context context;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    DocumentReference userRef = db.collection("Users").document(mAuth.getUid());

    private List<Year> year_list;


    YearActivityController(Context context) {
        this.context = context;
        this.year_list = new ArrayList<>();
    }


    RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    private void setupListItems() {

        for (Year year : this.year_list) {
            ListItem item = new YearListItem(year.getYear_name(), "Description", "GPA: ");
            this.listItems.add(item);
        }
    }

    void setupRecyclerView() {
        recyclerView = ((Activity) context).findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

    }

    @Override
    public void deleteItem(final int position) {
        db.collection("Users").document(mAuth.getUid())
                .collection("Years").document(year_list.get(position).getDocID())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                        Toast.makeText(context, year_list.get(position).getYear_name() + " Deleted", Toast.LENGTH_SHORT).show();
                        year_list.remove(position);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                        Toast.makeText(context, "Failed To Delete", Toast.LENGTH_SHORT).show();
                    }
                });

    }


    public List<ListItem> getListItems() {
        return listItems;
    }


    boolean addYear(String year_name, String description) {
        Year year = new Year(year_name);
        userRef.collection("Years").add(year);
        this.listItems.add(new YearListItem(year_name, description, "GPA"));
        return true;
    }

    YearActivityController getInstance() {
        return this;
    }

    void setupRecyclerViewContent() {
        db.collection("Users").document(mAuth.getUid()).collection("Years")
                .orderBy("year_name")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Year year = document.toObject(Year.class);
                                year.setDocID(document.getId());
                                year_list.add(year);
                            }
                            setupListItems();
                            adapter = new RecyclerViewAdapter(context, listItems, getInstance());
                            recyclerView.setAdapter(adapter);
                            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback((RecyclerViewAdapter) adapter));
                            itemTouchHelper.attachToRecyclerView(recyclerView);

//                            adapter = new RecyclerViewAdapter(context, listItems, );
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.getMessage());
                        Toast.makeText(context, "Unable to load Data From Years", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
