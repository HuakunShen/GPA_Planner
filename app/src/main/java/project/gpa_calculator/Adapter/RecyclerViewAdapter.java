package project.gpa_calculator.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import project.gpa_calculator.R;
import project.gpa_calculator.activities.course.CourseActivity;
import project.gpa_calculator.activities.semester.SemesterActivityController;
import project.gpa_calculator.models.ListItem;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<ListItem> list_items;
    private SemesterActivityController controller;


    public RecyclerViewAdapter(Context context, List<ListItem> list_items, SemesterActivityController controller) {
        this.context = context;
        this.list_items = list_items;
        this.controller = controller;
    }


    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder, int position) {
        ListItem item = list_items.get(position);
        viewHolder.name.setText(item.getName());
        viewHolder.description.setText(item.getDescription());
        viewHolder.gpa.setText(item.getGpa());

    }

    @Override
    public int getItemCount() {
        return list_items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name;
        private TextView description;
        private TextView gpa;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            gpa = itemView.findViewById(R.id.gpa);

        }

        @Override
        public void onClick(View v) {
            // Get Position of row clicked
            int position = getAdapterPosition();
            ListItem item = list_items.get(position);
            Intent intent = new Intent(context, CourseActivity.class);

            intent.putExtra("semester_name", item.getName());
            intent.putExtra("user_object", controller.getUser());
            context.startActivity(intent);

            Toast.makeText(context, item.getName(), Toast.LENGTH_LONG).show();
        }
    }
}
