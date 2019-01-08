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
import project.gpa_calculator.models.GPA;

public class GPA_setter_Activity extends AppCompatActivity implements AddDialog.GPADialogListener {
    private GPA_setter_Controller controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa);
        setupController();
        setupToolBar();
        setupAddButton();
//        setupRecyclerView();
        controller.setupRecyclerViewContent();

        controller.setupListItems();
        controller.setupRecyclerView();

    }

//    private void setupRecyclerView() {
//        recyclerView = findViewById(R.id.recycler_view);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//
//
//        adapter = new RecyclerViewAdapter(this, controller.getListItems(), controller);
//        recyclerView.setAdapter(adapter);
//
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback((RecyclerViewAdapter) adapter));
//        itemTouchHelper.attachToRecyclerView(recyclerView);
//    }

    @Override
    protected void onResume() {
        super.onResume();
        controller.loadFromFile(MainActivity.userFile);
    }

    @Override
    public void onBackPressed() {
        //should put in controller but idk how
       controller.save_update();

        super.onBackPressed();

    }

    private void setupController() {
        controller = new GPA_setter_Controller(this);
//        controller.loadFromFile(MainActivity.userFile);
    }

    /**
     * set up the toolbar on the top of the screen
     */
    private void setupToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * set up add button for add new GPA
     */
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

    /**
     * apply change on GPA recycler view
     * @param low
     * @param high
     * @param gpa
     * @param mark
     */
    @Override
    public void applyDialog(int low, int high,double gpa,String mark) {
        controller.addGPA(low,high,gpa,mark);
    }

}
