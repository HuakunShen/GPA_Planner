package project.gpa_calculator.activities.GPA_setter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import project.gpa_calculator.Adapter.RecyclerViewAdapter;
import project.gpa_calculator.R;
import project.gpa_calculator.Util.SwipeToDeleteCallback;
import project.gpa_calculator.Util.AddDialog;
import project.gpa_calculator.activities.main.MainActivity;
import project.gpa_calculator.models.GPA;

public class GPA_setter_Activity extends AppCompatActivity implements AddDialog.GPADialogListener {
    private GPA_setter_Controller controller;
    private Boolean save = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa);
        setupController();
        setupToolBar();
        setupAddButton();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        controller.setupRecyclerViewContent();

        controller.setupListItems();
        controller.setupRecyclerView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.save, menu);

        MenuItem getItem = menu.findItem(R.id.get_item);
        if (getItem != null) {
            AppCompatButton button = (AppCompatButton) getItem.getActionView();
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   save = controller.save_update();
                    if(save){
                        onBackPressed();
                    }
                }
            //the background color or something like that
            });
            String word = "SAVE";
            button.setText(word);
            button.setBackgroundColor(12);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        //should put in controller but idk how
        if(save){
            super.onBackPressed();
        }else{
            new AlertDialog.Builder(controller.getContext())
                    .setTitle("new setting not saved")
                    .setMessage("Do you want to ues the original setting?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            save = true;
                            onBackPressed();
                        }
                    }).show();
        }

    }

    private void setupController() {
        controller = new GPA_setter_Controller(this);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(GPA_setter_Activity.this, "Action clicked", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
