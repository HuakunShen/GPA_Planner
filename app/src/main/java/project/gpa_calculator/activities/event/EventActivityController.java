package project.gpa_calculator.activities.event;

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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import project.gpa_calculator.R;
import project.gpa_calculator.Util.ActivityController;
import project.gpa_calculator.Util.SwipeController;
import project.gpa_calculator.Util.SwipeControllerActions;
import project.gpa_calculator.models.Event;

public class EventActivityController extends ActivityController {
    private static final String TAG = "EventActivityController";
    private RecyclerView recyclerView;
    private static final String EVENT_COLLECTION = "Events";
    private EventActivityRecyclerViewAdapter adapter;
    private Context context;
    private DocumentReference courseRef;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Event> event_list;
    private SwipeController swipeController;


    EventActivityController(Context context, String course_path) {
        this.context = context;
        this.event_list = new ArrayList<>();
        this.courseRef = db.document(course_path);
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


    void addEvent(String event_name, double weight) {
        final Event event = new Event(event_name, weight);
        courseRef.collection(EVENT_COLLECTION)
                .add(event)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        event.setDocID(documentReference.getId());
                        event_list.add(event);
                        adapter.notifyItemInserted(event_list.size() - 1);
                    }
                });
    }


    void setupRecyclerViewContent() {
        courseRef.collection(EVENT_COLLECTION)
                .orderBy("event_name")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Event event = document.toObject(Event.class);
                                event.setDocID(document.getId());
                                event_list.add(event);
                            }
                            adapter = new EventActivityRecyclerViewAdapter(context, event_list);
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





