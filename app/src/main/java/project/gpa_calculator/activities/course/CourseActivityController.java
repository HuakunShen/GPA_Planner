package project.gpa_calculator.activities.course;

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
import project.gpa_calculator.models.Course;
import project.gpa_calculator.models.ListItem;
import project.gpa_calculator.models.Semester;
import project.gpa_calculator.models.YearListItem;

public class CourseActivityController extends ActivityController {
    private List<ListItem> listItems = new ArrayList<>();

    private static final String TAG = "CourseActivityControlle";
    private RecyclerView recyclerView;
    private static final String COURSE_COLLECTION = "Courses";
    private RecyclerView.Adapter adapter;
    private Context context;
    private String semester_path;
    private DocumentReference semesterRef;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private DocumentReference userRef = db.collection("Users").document(mAuth.getUid());

    private List<Course> course_list;


    CourseActivityController(Context context, String semester_path) {
        this.context = context;
        this.course_list = new ArrayList<>();
//        this.semesterRef = db.collection("Users").document(mAuth.getUid()).collection("Years").document(year_docID);
        this.semester_path = semester_path;
        this.semesterRef = db.document(semester_path);
    }


    public String getCoursePath() {
        return this.semester_path + "/Courses/";
    }

    RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    private void setupListItems() {

        for (Course course: this.course_list) {
            ListItem item = new YearListItem(course.getCourse_code(), course.getCourse_name(), "Target: " + course.getTarget(), course);
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
        semesterRef.collection(COURSE_COLLECTION).document(course_list.get(position).getDocID())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                        Toast.makeText(context, course_list.get(position).getCourse_name() + " Deleted", Toast.LENGTH_SHORT).show();
                        course_list.remove(position);
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


    boolean addCourse(String course_name, String course_code, double target, double credit_weight) {
        final Course course = new Course(course_name, course_code, target, credit_weight);
        semesterRef.collection(COURSE_COLLECTION)
                .add(course)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        course.setDocID(documentReference.getId());
                    }
                });
        this.listItems.add(new YearListItem(course_code, course_name, "Target: " + target, course));
        return true;
    }

    CourseActivityController getInstance() {
        return this;
    }

    void setupRecyclerViewContent() {
        semesterRef.collection(COURSE_COLLECTION)
                .orderBy("course_code")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Course course = document.toObject(Course.class);
                                course.setDocID(document.getId());
                                course_list.add(course);
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
                        Toast.makeText(context, "Unable to load Data From Course", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
