package project.gpa_calculator.activities.semester;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import project.gpa_calculator.R;

public class SemesterDialog extends AppCompatDialogFragment {
    private EditText semester_name_ET;
    private EditText semester_description_ET;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (SemesterDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement SemesterDialogListener");
        }
    }

    private SemesterDialogListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_semester, null);
        semester_name_ET = view.findViewById(R.id.semester_dialog_name_ET);
        semester_description_ET = view.findViewById(R.id.semester_dialog_description_ET);
        builder.setView(view)
                .setTitle("Add Semester")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String semester_name = semester_name_ET.getText().toString();
                        String semester_description = semester_description_ET.getText().toString();
                        if (!semester_name.isEmpty())
                            listener.applyDialog(semester_name, semester_description);
                    }
                });

        return builder.create();
    }

    public interface SemesterDialogListener {
        void applyDialog(String name, String description);
    }
}
