package project.gpa_calculator.Util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import project.gpa_calculator.R;
import project.gpa_calculator.activities.GPA_setter.GPA_Setter_Activity;
import project.gpa_calculator.activities.course.CourseActivity;
import project.gpa_calculator.activities.event.EventActivity;
import project.gpa_calculator.activities.semester.SemesterActivity;
import project.gpa_calculator.activities.year.YearActivity;

public class AddDialog extends AppCompatDialogFragment {
    private EditText first_ET;
    private EditText second_ET;

    private EditText third_ET;
    private EditText fourth_ET;
    private String type;
    private DialogListener listener;

    /**
     * checkValidity which Activity is Attach to
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof YearActivity) {
            type = "Year";
        } else if (context instanceof SemesterActivity) {
            type = "Semester";
        } else if (context instanceof CourseActivity) {
            type = "Course";
        } else if (context instanceof EventActivity) {
            type = "Event";
        } else {
            type = "GPA";
        }


        setupExtraInput(context);
        try {
            if (context instanceof YearActivity || context instanceof SemesterActivity) {
                listener = (YearSemesterDialogListener) context;
            } else if (context instanceof CourseActivity) {
                listener = (CourseDialogListener) context;
            } else if (context instanceof EventActivity) {
                listener = (EventDialogListener) context;
            } else if (context instanceof GPA_Setter_Activity) {
                listener = (GPADialogListener) context;
            }
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement YearSemesterDialogListener");
        }

    }

    private void setupExtraInput(Context context) {
        third_ET = new EditText(context);
        fourth_ET = new EditText(context);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
//        if (!this.type.equalsIgnoreCase("Course")) {

        View view = inflater.inflate(R.layout.dialog_year_and_semester, null);
        LinearLayout dialog_linearLayout = view.findViewById(R.id.dialog_id);

        setupInputProperties(view, dialog_linearLayout);
        builder.setView(view)
                .setTitle("Add " + type)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setupConfirmAction();
                    }
                });

        return builder.create();
    }

    /**
     * get information from the view
     */
    private void setupConfirmAction() {
        if (type.equalsIgnoreCase("Year") || type.equalsIgnoreCase("Semester")) {
            String semester_name = first_ET.getText().toString();
            String semester_description = second_ET.getText().toString();
            if (!semester_name.isEmpty())
                ((YearSemesterDialogListener) listener).applyDialog(semester_name, semester_description);
        } else if (type.equalsIgnoreCase("Course")) {
            String course_name = first_ET.getText().toString();
            String course_code = second_ET.getText().toString();
            double target = third_ET.getText().toString().equals("") ? 0d : Double.valueOf(third_ET.getText().toString());
            double credit_weight = fourth_ET.getText().toString().equals("") ? 0d : Double.valueOf(fourth_ET.getText().toString());
            ((CourseDialogListener) listener).applyDialog(course_name, course_code, target, credit_weight);
        } else if (type.equalsIgnoreCase("Event")) {
            String event_name = first_ET.getText().toString();
            double event_weight = second_ET.getText().toString().equals("") ? 0d : Double.valueOf(second_ET.getText().toString());
            if (!event_name.isEmpty())
                ((EventDialogListener) listener).applyDialog(event_name, event_weight);
        } else if (type.equalsIgnoreCase("GPA")) {
            String mark = first_ET.getText().toString();
            double gpa = second_ET.getText().toString().equals("") ? 0d : Double.valueOf(second_ET.getText().toString());
            int low = third_ET.getText().toString().equals("") ? 0 : Integer.valueOf(third_ET.getText().toString());
            int high = fourth_ET.getText().toString().equals("") ? 0 : Integer.valueOf(fourth_ET.getText().toString());

            ((GPADialogListener) listener).applyDialog(low, high, gpa, mark);
        }
    }

    /**
     * set up the view
     * @param view
     * @param dialog_linearLayout
     */
    private void setupInputProperties(View view, LinearLayout dialog_linearLayout) {
        first_ET = view.findViewById(R.id.dialog_name_ET);
        first_ET.setHint(this.type + " Name");
        second_ET = view.findViewById(R.id.dialog_description_ET);
        if (this.type.equalsIgnoreCase("Course")) {
            third_ET.setHint("Target Score (Percentage)");
            third_ET.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            dialog_linearLayout.addView(third_ET);
            fourth_ET.setHint("Credit");
            fourth_ET.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            dialog_linearLayout.addView(fourth_ET);
            second_ET.setHint("Course Code");
        } else if (this.type.equalsIgnoreCase("Event")) {
            second_ET.setHint("Weight (Percentage)");
            second_ET.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        } else if (this.type.equalsIgnoreCase("GPA")) {
            first_ET.setHint("Letter Grade");
            second_ET.setHint("GPA Points");
            second_ET.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);


            third_ET.setHint("lower bound");
            third_ET.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

            fourth_ET.setHint("upper bound");
            fourth_ET.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            dialog_linearLayout.addView(third_ET);
            dialog_linearLayout.addView(fourth_ET);
        }
    }


    public interface DialogListener {
    }

    public interface EventDialogListener extends DialogListener {
        void applyDialog(String name, double weight);
    }

    public interface YearSemesterDialogListener extends DialogListener {
        void applyDialog(String name, String description);
    }

    public interface CourseDialogListener extends DialogListener {
        void applyDialog(String course_name, String course_code, double target, double credit_weight);
    }

    public interface GPADialogListener extends DialogListener {
        void applyDialog(int low, int high, double gpa, String mark);
    }
}
