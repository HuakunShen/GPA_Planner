package project.gpa_calculator.activities.year;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import project.gpa_calculator.Adapter.RecyclerViewAdapter;
import project.gpa_calculator.R;
import project.gpa_calculator.Util.SwipeToDeleteCallback;
import project.gpa_calculator.Util.AddDialog;
import project.gpa_calculator.models.Year;

public class YearActivity extends AppCompatActivity implements AddDialog.YearSemesterDialogListener {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private YearActivityController controller;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static final String TAG = "YearActivity";
    private List<Year> year_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year);
        setupController();
        setupRecyclerView();
        setupToolBar();
        setupAddButton();
        setupRecyclerViewContent();
    }

    private void setupRecyclerViewContent() {
        year_list = new ArrayList<>();
        db.collection("Users").document(mAuth.getUid()).collection("Years")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                year_list.add(document.toObject(Year.class));
                            }
                            controller.setYear_list(year_list);
                            controller.setupListItems();
                            adapter = new RecyclerViewAdapter(YearActivity.this, controller.getListItems(), controller);
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
                Toast.makeText(YearActivity.this, "Unable to load Data From Years", Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void setupController() {
        controller = new YearActivityController();
        controller.setContext(this);

    }

    private void setupToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupAddButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_year_Btn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }

            private void openDialog() {
                AddDialog dialog = new AddDialog();
                dialog.show(getSupportFragmentManager(), "Add Year Dialog");
            }
        });
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onResume() {
        super.onResume();
    }




    @Override
    public void applyDialog(String name, String description) {
        if (controller.addYear(name, description)) {
            adapter.notifyItemInserted(controller.getListItems().size() - 1);
        }
    }

}
