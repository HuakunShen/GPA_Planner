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

import project.gpa_calculator.R;

public class YearActivity extends AppCompatActivity {
    private Button add_course;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year);

        add_course = (Button)findViewById(R.id.add_year_button);
        add_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Add_pop.class);
                startActivity(i);
                add_course.setTextColor(Color.RED);

            }
        });
    }



}
