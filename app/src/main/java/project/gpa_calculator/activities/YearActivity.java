package project.gpa_calculator.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import project.gpa_calculator.R;

public class YearActivity extends AppCompatActivity {
    private Button add_course;
    private ScrollView scrollView;
    private LinearLayout linearLayout;
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
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String strEditText = data.getStringExtra("editTextValue");
                Button new_year = new Button(this);
                scrollView = (ScrollView) findViewById(R.id.scroll);
                new_year.setText(strEditText);

                linearLayout = (LinearLayout)findViewById(R.id.scrollLayout);
                //add_course.setText(strEditText);
                // Create a LinearLayout element
                linearLayout.addView(new_year);


            }
        }
    }



}
