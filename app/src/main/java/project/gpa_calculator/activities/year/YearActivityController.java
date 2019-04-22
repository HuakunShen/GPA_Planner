package project.gpa_calculator.activities.year;

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
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
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
import project.gpa_calculator.models.Year;
import project.gpa_calculator.models.YearListItem;

public class YearActivityController extends ActivityController {
//    private List<ListItem> listItems = new ArrayList<>();

    private static final String TAG = "YearActivityController";

    private RecyclerView recyclerView;

    private RecyclerView.Adapter adapter;
    private Context context;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private DocumentReference userRef = db.collection("Users").document(Objects.requireNonNull(mAuth.getUid()));

    private List<Year> year_list;

    private SwipeController swipeController;


    YearActivityController(final Context context) {
        this.context = context;
        this.year_list = new ArrayList<>();
    }


    RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    public String getYearPath() {
        return "/Users/" + mAuth.getUid() + "/Years/";
    }

    void setupRecyclerView() {
        recyclerView = ((Activity) context).findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

    }

    @Override
    public void deleteItem(final int position) {
        db.collection("Users").document(Objects.requireNonNull(mAuth.getUid()))
                .collection("Years").document(year_list.get(position).getDocID())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                        Toast.makeText(context, year_list.get(position).getYear_name() + " Deleted", Toast.LENGTH_SHORT).show();
                        year_list.remove(position);
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

    boolean addYear(String year_name, String description) {
        final Year year = new Year(year_name);
        userRef.collection("Years").add(year).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                year.setDocID(documentReference.getId());
                year_list.add(year);
                adapter.notifyItemInserted(year_list.size() - 1);
            }
        });
        return true;
    }

    YearActivityController getInstance() {
        return this;
    }

    void setupRecyclerViewContent() {
        db.collection("Users").document(Objects.requireNonNull(mAuth.getUid())).collection("Years")
                .orderBy("year_name")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Year year = document.toObject(Year.class);
                                year.setDocID(document.getId());
                                year_list.add(year);
                            }
                            adapter = new YearActivityRecyclerViewAdapter(context, year_list, getInstance());
                            recyclerView.setAdapter(adapter);

                            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                    super.onScrollStateChanged(recyclerView, newState);
                                    Log.e("RecyclerView", "onScrollStateChanged");
                                }
                                @Override
                                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                    super.onScrolled(recyclerView, dx, dy);
                                }
                            });
//                            swipeController = new SwipeController(new SwipeControllerActions() {
//                                @Override
//                                public void onRightClicked(final int position) {
//                                    new AlertDialog.Builder(context)
//                                            .setTitle("Deletion Warning!")
//                                            .setMessage("Do You Want To Delete?\nIt Is Unrecoverable!")
//                                            .setIcon(android.R.drawable.ic_dialog_alert)
//                                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialog, int which) {
//                                                    Toast.makeText(context, "Deletion Cancelled", Toast.LENGTH_SHORT).show();
//                                                }
//                                            })
//                                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialog, int which) {
//                                                    deleteItem(position);
//                                                }
//                                            }).show();
//                                }
//
//                                @Override
//                                public void onLeftClicked(int position) {
//                                    Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                            ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
//                            itemTouchhelper.attachToRecyclerView(recyclerView);
//                            recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
//                                @Override
//                                public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//                                    swipeController.onDraw(c);
//                                }
//                            });
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
