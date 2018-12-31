package project.gpa_calculator.activities.course;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import project.gpa_calculator.Adapter.RecyclerViewAdapter;
import project.gpa_calculator.R;
import project.gpa_calculator.Util.AddDialog;
import project.gpa_calculator.Util.SwipeToDeleteCallback;
import project.gpa_calculator.activities.main.MainActivity;
import project.gpa_calculator.activities.semester.SemesterActivityController;
import project.gpa_calculator.models.Year;

public class CourseActivity extends AppCompatActivity implements AddDialog.CourseDialogListener  {
    private CourseActivityController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        setupController();
        controller.setupRecyclerView();
        setupToolBar();
        setupAddButton();
        controller.setupRecyclerViewContent();
    }

    private void setupController() {
        controller = new CourseActivityController(this, getIntent().getStringExtra("semester_doc_path"));
    }

    private void setupToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupAddButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_course_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }

            private void openDialog() {
                AddDialog dialog = new AddDialog();
                dialog.show(getSupportFragmentManager(), "Add Course Dialog");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void applyDialog(String course_name, String course_code, double target, double credit_weight) {
        if (controller.addCourse(course_name, course_code, target, credit_weight)) {
            controller.getAdapter().notifyItemInserted(controller.getListItems().size() - 1);
        } else {
            Toast.makeText(getApplication(), "Course Exists or Input Not Valid", Toast.LENGTH_LONG).show();
        }
    }
}
