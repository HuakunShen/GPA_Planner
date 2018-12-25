package project.gpa_calculator.activities;

import android.content.Intent;
import android.graphics.Color;
import android.provider.CalendarContract;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import project.gpa_calculator.R;


public class YearActivity extends AppCompatActivity implements Recycler_Adapter.ItemClickListener, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{
    private Button add_course;
    private Recycler_Adapter adapter;
    List<String> item  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year);


        add_course = (Button)findViewById(R.id.add_year_button);
        add_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Add_pop.class);
                startActivityForResult(i,1);
                add_course.setTextColor(Color.RED);

            }
        });


        RecyclerView recycler = findViewById(R.id.test);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Recycler_Adapter(this, item);
        adapter.setClickListener(this);
        recycler.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recycler);


        final TextView all_year = (TextView)findViewById(R.id.all_year);
        all_year.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View l){
                all_year.setText("every year is great");
            }
        });
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof Recycler_Adapter.ViewHolder) {
            // get the removed item name to display it in snack bar
            //String name = cartList.get(viewHolder.getAdapterPosition()).getName();

            // backup of removed item for undo purpose
            //final Item deletedItem = cartList.get(viewHolder.getAdapterPosition());
            //final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            int removeIndex = 0;
            item.remove(removeIndex);
            adapter.notifyItemRemoved(removeIndex);

            // showing snack bar with Undo option
//            Snackbar snackbar = Snackbar
//                    .make(coordinatorLayout, name + " removed from cart!", Snackbar.LENGTH_LONG);
//            snackbar.setAction("UNDO", new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    // undo is selected, restore the deleted item
//                    adapter.restoreItem(deletedItem, deletedIndex);
//                }
//            });
//            snackbar.setActionTextColor(Color.YELLOW);
//            snackbar.show();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String strEditText = data.getStringExtra("editTextValue");
                Button new_year = new Button(this);
                new_year.setText(strEditText);
                //add_course.setText(strEditText);
                // Create a LinearLayout element
                //linearLayout.addView(new_year);
                String new_item = strEditText;
                int insertIndex = 0;
                item.add(insertIndex, new_item);
                adapter.notifyItemInserted(insertIndex);


            }
        }
    }



}
