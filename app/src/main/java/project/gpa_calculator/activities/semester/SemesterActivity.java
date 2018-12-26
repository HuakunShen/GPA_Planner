package project.gpa_calculator.activities.semester;

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
import project.gpa_calculator.Util.AddDialog;
import project.gpa_calculator.Util.SwipeToDeleteCallback;
import project.gpa_calculator.activities.main.MainActivity;

public class SemesterActivity extends AppCompatActivity implements AddDialog.YearSemesterDialogListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
//    private List<ListItem> listItems;

    private SemesterActivityController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester);
        setupController();
        setupToolBar();
        setupAddButton();
        setupRecyclerView();
    }

    private void setupController() {
        controller = new SemesterActivityController();
        controller.setContext(this);
        controller.loadFromFile(MainActivity.userFile);
        controller.setupCurrentYear(getIntent().getStringExtra("year_name"));
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

    private void setupAddButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_semester_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                openDialog();
            }

            private void openDialog() {
                AddDialog dialog = new AddDialog();
                dialog.show(getSupportFragmentManager(), "Add Semester Dialog");
            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        controller.loadFromFile(MainActivity.userFile);
    }

    @Override
    public void applyDialog(String name, String description) {
        if (controller.addSemester(name, description)) {
            adapter.notifyItemInserted(controller.getListItems().size() - 1);
        }
    }


//    public void saveToFile(String fileName) {
//        try {
//            ObjectOutputStream outputStream = new ObjectOutputStream(
//                    this.openFileOutput(fileName, MODE_PRIVATE));
//            outputStream.writeObject(controller.getUser());
//            outputStream.close();
//        } catch (IOException e) {
//            Log.e("Exception", "File write failed: " + e.toString());
//        }
//    }
}
