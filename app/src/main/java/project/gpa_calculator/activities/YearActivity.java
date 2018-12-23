package project.gpa_calculator.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import project.gpa_calculator.R;

public class year_activity extends AppCompatActivity {
    private Button add_course;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year);

        add_course = (Button)findViewById(R.id.add_course_button);
    }
}
