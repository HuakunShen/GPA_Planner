package project.gpa_calculator.activities.semester;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
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
import java.util.Objects;

import project.gpa_calculator.Adapter.RecyclerViewAdapter;
import project.gpa_calculator.R;
import project.gpa_calculator.Util.ActivityController;
import project.gpa_calculator.Util.SwipeController;
import project.gpa_calculator.Util.SwipeControllerActions;
import project.gpa_calculator.Util.SwipeToDeleteCallback;
import project.gpa_calculator.activities.course.CourseActivityRecyclerViewAdapter;
import project.gpa_calculator.models.ListItem;
import project.gpa_calculator.models.Semester;
import project.gpa_calculator.models.YearListItem;

public class SemesterActivityController extends ActivityController {

//    private List<ListItem> listItems = new ArrayList<>();

    private static final String TAG = "SemesterActivityControl";
    private RecyclerView recyclerView;
    private static final String SEMESTER_COLLECTION= "Semesters";
    private RecyclerView.Adapter adapter;
    private Context context;
    private String year_path;
    private DocumentReference yearRef;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private DocumentReference userRef = db.collection("Users").document(Objects.requireNonNull(mAuth.getUid()));

    private List<Semester> semester_list;

    private SwipeController swipeController;



    SemesterActivityController(Context context, String year_path) {
        this.context = context;
        this.semester_list = new ArrayList<>();
//        this.yearRef = db.collection("Users").document(mAuth.getUid()).collection("Years").document(year_docID);
        this.year_path = year_path;
        this.yearRef = db.document(year_path);
    }


    public String getSemesterPath() {
        return this.year_path + "/Semesters/";
    }

    RecyclerView.Adapter getAdapter() {
        return adapter;
    }

//    private void setupListItems() {
//
//        for (Semester semester : this.semester_list) {
//            ListItem item = new YearListItem(semester.getSemester_name(), "Description", "GPA: ", semester);
//            this.listItems.add(item);
//        }
//    }

    void setupRecyclerView() {
        recyclerView = ((Activity) context).findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

    }

    @Override
    public void deleteItem(final int position) {
        yearRef.collection(SEMESTER_COLLECTION).document(semester_list.get(position).getDocID())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                        Toast.makeText(context, semester_list.get(position).getSemester_name() + " Deleted", Toast.LENGTH_SHORT).show();
                        semester_list.remove(position);
                        adapter.notifyItemRemoved(position);
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


//    public List<ListItem> getListItems() {
//        return listItems;
//    }


    boolean addSemester(String semester_name, String description) {
        final Semester semester = new Semester(semester_name);
        yearRef.collection(SEMESTER_COLLECTION)
                .add(semester)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                semester.setDocID(documentReference.getId());
                semester_list.add(semester);
                adapter.notifyItemInserted(semester_list.size() - 1);
            }
        });
//        this.listItems.add(new YearListItem(semester_name, description, "GPA", semester));
//        this.semester_list.add(semester);
        return true;
    }

    SemesterActivityController getInstance() {
        return this;
    }

    void setupRecyclerViewContent() {
        yearRef.collection(SEMESTER_COLLECTION)
                .orderBy("semester_name")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Semester semester = document.toObject(Semester.class);
                                semester.setDocID(document.getId());
                                semester_list.add(semester);
                            }
                            adapter = new SemesterActivityRecyclerViewAdapter(context, semester_list, getInstance());
                            recyclerView.setAdapter(adapter);
                            swipeController = new SwipeController(new SwipeControllerActions() {
                                @Override
                                public void onRightClicked(final int position) {
                                    new AlertDialog.Builder(context)
                                            .setTitle("Deletion Warning!")
                                            .setMessage("Do You Want To Delete?\nIt Is Unrecoverable!")
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Toast.makeText(context, "Deletion Cancelled", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    deleteItem(position);
                                                }
                                            }).show();
                                }

                                @Override
                                public void onLeftClicked(int position) {
                                    Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show();
                                }
                            });
                            ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
                            itemTouchhelper.attachToRecyclerView(recyclerView);
                            recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                                @Override
                                public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                                    swipeController.onDraw(c);
                                }
                            });
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.getMessage());
                        Toast.makeText(context, "Unable to load Data From Semester", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
