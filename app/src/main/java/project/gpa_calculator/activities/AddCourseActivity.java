package project.gpa_calculator.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import project.gpa_calculator.R;

public class AddCourseActivity extends AppCompatActivity {
    private EditText course_name_EditText;
    private EditText department_EditText;
    private EditText course_number_EditText;
    private EditText term_code_EditText;
    private EditText target_score_EditText;
    private EditText credit_weight_EditText;
    private Button add_course_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        setupEditText();
        setupButton();
    }

    private void setupButton() {
        add_course_button = findViewById(R.id.add_course_button);
        add_course_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setupEditText() {
        course_name_EditText = findViewById(R.id.course_name_ET);
        department_EditText = findViewById(R.id.dept_ET);
        course_number_EditText = findViewById(R.id.course_num_ET);
        term_code_EditText = findViewById(R.id.term_code_ET);
        target_score_EditText = findViewById(R.id.target_score_ET);
        credit_weight_EditText = findViewById(R.id.credit_ET);
    }
}
