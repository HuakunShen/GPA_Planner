package project.gpa_calculator.activities.GPA_setter;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import project.gpa_calculator.Adapter.RecyclerViewAdapter;
import project.gpa_calculator.R;
import project.gpa_calculator.Util.SwipeToDeleteCallback;
import project.gpa_calculator.Util.AddDialog;
import project.gpa_calculator.activities.main.MainActivity;
import project.gpa_calculator.models.User;

public class GPA_setter_Activity extends AppCompatActivity implements AddDialog.GPADialogListener {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private GPA_setter_Controller controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa);
        setupController();
        setupToolBar();
        setupAddButton();
        setupRecyclerView();
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

    @Override
    protected void onResume() {
        super.onResume();
        controller.loadFromFile(MainActivity.userFile);
    }

    private void setupController() {
        controller = new GPA_setter_Controller();
//        controller.setupCurrentYear((User) getIntent().getSerializableExtra("user_object"));
        controller.setContext(this);
        controller.loadFromFile(MainActivity.userFile);
    }

    private void setupToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupAddButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_gpa_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG);
//                        .setAction("Action", null).show();
                openDialog();
            }

            private void openDialog() {
                AddDialog dialog = new AddDialog();
                dialog.show(getSupportFragmentManager(), "Add GPA Dialog");
            }
        });
    }

    @Override
    public void applyDialog(int low, int high,double gpa,String mark) {
        if (controller.addGPA(low,high,gpa,mark)) {
            adapter.notifyItemInserted(controller.getListItems().size() - 1);
        }
    }

}
