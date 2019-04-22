package project.gpa_calculator.activities.course;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import project.gpa_calculator.R;
import project.gpa_calculator.activities.event.EventActivity;
import project.gpa_calculator.models.Course;

public class CourseActivityRecyclerViewAdapter extends RecyclerView.Adapter<CourseActivityRecyclerViewAdapter.ViewHolder> {
    private List<Course> list_items;
    private Context context;
    private CourseActivityController controller;

    public CourseActivityRecyclerViewAdapter(Context context, List<Course> list_items, CourseActivityController controller) {
        this.list_items = list_items;
        this.context = context;
        this.controller = controller;
    }

    @NonNull
    @Override
    public CourseActivityRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_row, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Course course = this.list_items.get(position);
        viewHolder.name.setText(course.getCourse_code());
        viewHolder.course_code.setText((String.valueOf(course.getCourse_code())));
        String current_finished = Integer.toString(course.finishSize()) + "/" + Integer.toString(course.size());
        // TODO: status isDone method is not complete since we use firestore instead of pure object
        viewHolder.status.setText(current_finished);
//        viewHolder.status.setText(("Status: " + (course.isDone() ? "Done" : "Not Done")));
        // TODO: score_target should display either Score or Target depends on whether it's done, target is given but score is not Calculated
        String target = Double.toString(course.getTarget());
        viewHolder.score_target.setText(target);
//        viewHolder.score_target.setText((course.isDone() ? "Score: " : "Target: " + String.valueOf(course.getTarget())));
        viewHolder.credit.setText(("Credit: " + String.valueOf(course.getCredit())));
        viewHolder.neededscore.setText("0");
    }

    @Override
    public int getItemCount() {
        return this.list_items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private TextView name, course_code, status, score_target, credit,neededscore;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            course_code = itemView.findViewById(R.id.title);
            name= itemView.findViewById(R.id.description1);
            score_target = itemView.findViewById(R.id.description2);
            status = itemView.findViewById(R.id.description3);
            credit = itemView.findViewById(R.id.description4);
            neededscore = itemView.findViewById(R.id.bigBox);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Intent intent = new Intent(context, EventActivity.class);
            Course course = list_items.get(position);
            intent.putExtra("course_doc_path", controller.getCoursePath() + course.getDocID());
            context.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            name.setText("LongClicked");
            return true;
        }
    }
}
