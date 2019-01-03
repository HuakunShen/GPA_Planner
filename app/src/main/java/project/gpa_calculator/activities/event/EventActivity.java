package project.gpa_calculator.activities.event;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import project.gpa_calculator.R;
import project.gpa_calculator.Util.AddDialog;

public class EventActivity extends AppCompatActivity implements AddDialog.EventDialogListener {
    private EventActivityController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        setupController();
        controller.setupRecyclerView();
        setupToolBar();
        setupAddButton();
        controller.setupRecyclerViewContent();
    }

    private void setupController() {
        controller = new EventActivityController(this, getIntent().getStringExtra("course_doc_path"));
    }


    private void setupToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupAddButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_event_btn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }

            private void openDialog() {
                AddDialog dialog = new AddDialog();
                dialog.show(getSupportFragmentManager(), "Add Event Dialog");
            }
        });
    }

    @Override
    public void applyDialog(String name, double weight) {
        if (controller.addEvent(name, weight)) {
            controller.getAdapter().notifyItemInserted(controller.getListItems().size() - 1);
        }
    }
}
