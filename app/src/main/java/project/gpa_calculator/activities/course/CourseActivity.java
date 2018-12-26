package project.gpa_calculator.activities.course;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import project.gpa_calculator.Adapter.RecyclerViewAdapter;
import project.gpa_calculator.R;
import project.gpa_calculator.Util.SwipeToDeleteCallback;
import project.gpa_calculator.models.User;
import project.gpa_calculator.models.Year;

public class CourseActivity extends AppCompatActivity {
    private CourseActivityController controller;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        setupController();
        setupToolBar();
        setupAddButton();
        setupRecyclerView();
    }

    private void setupController() {
        controller = new CourseActivityController();
        controller.setupUser((User) getIntent().getSerializableExtra("user_object"),
                (Year) getIntent().getSerializableExtra("year_object"),
                getIntent().getStringExtra("semester_name"));
        controller.setContext(this);
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        controller.setupListItems();

        adapter = new RecyclerViewAdapter(this, controller.getListItems(), controller);
        recyclerView.setAdapter(adapter);


//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback((RecyclerViewAdapter) adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void setupToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

//    private void setupFAB() {
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//    }

    private void setupAddButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_course_button
        );
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
//                openDialog();
            }

//            private void openDialog() {
//                AddDialog dialog = new AddDialog();
//                dialog.show(getSupportFragmentManager(), "Add Semester Dialog");
//            }

        });
    }

}
