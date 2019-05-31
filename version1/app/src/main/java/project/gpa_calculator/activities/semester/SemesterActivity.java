package project.gpa_calculator.activities.semester;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import project.gpa_calculator.R;
import project.gpa_calculator.Util.AddDialog;

public class SemesterActivity extends AppCompatActivity implements AddDialog.YearSemesterDialogListener {

    private SemesterActivityController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester);
        setupController();
        controller.setupRecyclerView();
        setupToolBar();
        setupAddButton();
        controller.setupRecyclerViewContent();
    }

    private void setupController() {
        controller = new SemesterActivityController(this, getIntent().getStringExtra("year_doc_path"));
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
    }

    @Override
    public void applyDialog(String name, String description) {
        controller.addSemester(name, description);
//        if (controller.addSemester(name, description)) {
//            controller.getAdapter().notifyItemInserted(controller.getListItems().size() - 1);
//        }
    }
}
