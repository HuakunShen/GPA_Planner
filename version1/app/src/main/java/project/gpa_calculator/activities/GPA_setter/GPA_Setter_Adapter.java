package project.gpa_calculator.activities.GPA_setter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import project.gpa_calculator.R;
import project.gpa_calculator.models.GPA;

public class GPA_Setter_Adapter extends RecyclerView.Adapter<GPA_Setter_Adapter.ViewHolder> {
    private List<GPA> list_items;
    private Context context;
    private GPA_Setter_Controller controller;
    public GPA_Setter_Adapter(Context context, List<GPA> list_items, GPA_Setter_Controller controller) {
        this.list_items = list_items;
        this.context = context;
        this.controller = controller;
    }

    @NonNull
    @Override
    public GPA_Setter_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.gpa_row, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        GPA cur_item = this.list_items.get(position);
        viewHolder.low.setHint(Integer.toString(cur_item.getLower()));
        viewHolder.high.setHint(Integer.toString(cur_item.getUpper()));
        viewHolder.gpa_point.setHint(Double.toString(cur_item.getGrade_point()));
        viewHolder.gpa_grade.setHint(cur_item.getGrade());
    }


    @Override
    public int getItemCount() {
        return this.list_items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private TextView low,high,gpa_grade, gpa_point;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            low = itemView.findViewById(R.id.low);
            high = itemView.findViewById(R.id.high);
            gpa_grade = itemView.findViewById(R.id.GPA_grade);
            gpa_point = itemView.findViewById(R.id.GPA_point);

            gpa_point.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            low.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            high.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            itemView.setOnLongClickListener(this);
        }
        public int get_low_hint(){
            return Integer.valueOf(low.getHint().toString());
        }

        public int get_high_hint(){
            return Integer.valueOf(high.getHint().toString());
        }

        public double get_gpapoint_hint(){
            return Double.valueOf(gpa_point.getHint().toString());
        }

        public String get_gpagrade_hint(){
            return gpa_grade.getHint().toString();
        }


        public String getGpa_grade() {
            return gpa_grade.getText().toString();
        }

        public String getHigh() {
            return high.getText().toString();
        }

        public String getLow() {
            return low.getText().toString();
        }

        public String getGpa_point() {
            return gpa_point.getText().toString();
        }

        @Override
        public void onClick(View v) {
//            int position = getAdapterPosition();
//            Intent intent = new Intent(context, SemesterActivity.class);
//            Year year = list_items.get(position);
//            intent.putExtra("year_doc_path", controller.getYearPath() + year.getDocID());
//            context.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            //name.setText(("LongClicked"));
            return true;
        }
    }

    public Context getContext() {
        return context;
    }

    public void deleteItem(final int position){
        new AlertDialog.Builder(context)
                .setTitle("Deletion Warning!")
                .setMessage("Do You Want To Delete?\nIt Is Unrecoverable!")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Deletion Cancelled", Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        controller.deleteItem(position);
                    }
                }).show();
    }
}
