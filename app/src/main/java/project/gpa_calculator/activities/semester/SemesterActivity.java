package project.gpa_calculator.activities.semester;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import project.gpa_calculator.Adapter.RecyclerViewAdapter;
import project.gpa_calculator.R;
import project.gpa_calculator.activities.Recycler_Adapter;
import project.gpa_calculator.models.ListItem;
import project.gpa_calculator.models.Semester;

public class SemesterActivity extends AppCompatActivity implements SemesterDialog.SemesterDialogListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItem> listItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester);
        setupToolBar();
        setupAddButton();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listItems = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ListItem item = new ListItem("Item "+ (i + 1), "Description", "GPA: ");
            listItems.add(item);
        }
        adapter = new RecyclerViewAdapter(this, listItems);
        recyclerView.setAdapter(adapter);
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
//                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getApplication());
//                View mView = getLayoutInflater().inflate(R.layout.dialog_semester, null);
//                EditText semester_dialog_name_ET = mView.findViewById(R.id.semester_dialog_name_ET);
//                EditText semester_dialog_description_ET = mView.findViewById(R.id.semester_dialog_description_ET);
//                Button confirm_Btn = mView.findViewById(R.id.semester_dialog_confirm_Btn);
//                Button cancel_Btn = mView.findViewById(R.id.semester_dialog_cancel_Btn);
//
//                mBuilder.setView(mView);
//                AlertDialog dialog = mBuilder.create();
//                dialog.show();
                openDialog();
            }

            private void openDialog() {
                SemesterDialog dialog = new SemesterDialog();
                dialog.show(getSupportFragmentManager(), "Add Semester Dialog");
            }

        });
    }

    @Override
    public void applyDialog(String name, String description) {

    }
}
