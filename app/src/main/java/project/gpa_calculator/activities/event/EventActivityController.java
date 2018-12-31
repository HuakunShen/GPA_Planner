package project.gpa_calculator.activities.event;

import android.app.Activity;
import android.content.ContentUris;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import project.gpa_calculator.Adapter.RecyclerViewAdapter;
import project.gpa_calculator.R;
import project.gpa_calculator.Util.ActivityController;
import project.gpa_calculator.Util.SwipeToDeleteCallback;
import project.gpa_calculator.activities.semester.SemesterActivityController;
import project.gpa_calculator.models.Event;
import project.gpa_calculator.models.ListItem;
import project.gpa_calculator.models.Semester;
import project.gpa_calculator.models.YearListItem;

public class EventActivityController extends ActivityController {
    private List<ListItem> listItems = new ArrayList<>();
    private static final String TAG = "EventActivityController";
    private RecyclerView recyclerView;
    private static final String EVENT_COLLECTION= "Events";
    private RecyclerView.Adapter adapter;
    private Context context;
    private String course_path;
    private DocumentReference courseRef;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private DocumentReference userRef = db.collection("Users").document(mAuth.getUid());

    private List<Event> event_list;


    EventActivityController(Context context, String course_path) {
        this.context = context;
        this.event_list = new ArrayList<>();
        this.course_path = course_path;
        this.courseRef = db.document(course_path);
    }


    public String getSemesterPath() {
        return this.course_path + "/Event/";
    }

    RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    void setupListItems() {

        for (Event event: this.event_list) {
            ListItem item = new YearListItem(event.getEvent_name(), "Weight: " + event.getEvent_weight(), "GPA: ", event);
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
        courseRef.collection(EVENT_COLLECTION).document(event_list.get(position).getDocID())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                        Toast.makeText(context, event_list.get(position).getEvent_name() + " Deleted", Toast.LENGTH_SHORT).show();
                        event_list.remove(position);
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


    boolean addEvent(String event_name, double weight) {
        final Event event = new Event(event_name, weight);
        courseRef.collection(EVENT_COLLECTION)
                .add(event)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        event.setDocID(documentReference.getId());
                    }
                });
        this.listItems.add(new YearListItem(event_name, "Weight: " + weight, "GPA", event));
        return true;
    }

    EventActivityController getInstance() {
        return this;
    }

    void setupRecyclerViewContent() {
        courseRef.collection(EVENT_COLLECTION)
                .orderBy("semester_name")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Event event = document.toObject(Event.class);
                                event.setDocID(document.getId());
                                event_list.add(event);
                            }
                            setupListItems();
                            adapter = new RecyclerViewAdapter(context, listItems, getInstance());
                            recyclerView.setAdapter(adapter);
                            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback((RecyclerViewAdapter) adapter));
                            itemTouchHelper.attachToRecyclerView(recyclerView);

//                            adapter = new RecyclerViewAdapter(context, listItems, );
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            Toast.makeText(context, "Error getting documents", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.getMessage());
                        Toast.makeText(context, "Unable to load Data From Event", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
