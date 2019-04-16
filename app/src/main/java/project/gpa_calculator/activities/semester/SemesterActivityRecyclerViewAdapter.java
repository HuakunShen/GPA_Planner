package project.gpa_calculator.activities.semester;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;

import java.util.List;

import project.gpa_calculator.R;
import project.gpa_calculator.activities.course.CourseActivity;
import project.gpa_calculator.activities.course.CourseActivityController;
import project.gpa_calculator.activities.course.CourseActivityRecyclerViewAdapter;
import project.gpa_calculator.activities.event.EventActivity;
import project.gpa_calculator.models.Course;
import project.gpa_calculator.models.Semester;

public class SemesterActivityRecyclerViewAdapter extends RecyclerView.Adapter<SemesterActivityRecyclerViewAdapter.ViewHolder> {
    private List<Semester> list_items;
    private Context context;
    private SemesterActivityController controller;

    public SemesterActivityRecyclerViewAdapter(Context context, List<Semester> list_items, SemesterActivityController controller) {
        this.list_items = list_items;
        this.context = context;
        this.controller = controller;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_row, viewGroup, false);

        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        Semester semester = this.list_items.get(position);
        viewHolder.name.setText(semester.getSemester_name());
        // TODO: status isDone method is not complete since we use firestore instead of pure object
        viewHolder.status.setText(("Status: Not Done"));
        viewHolder.gpa.setText(("GPA: " + 0.0d));
        // TODO: score_target should display either Score or Target depends on whether it's done, target is given but score is not Calculated
        viewHolder.target.setText(("Target: " + 0.0d));
//        viewHolder.score_target.setText((course.isDone() ? "Score: " : "Target: " + String.valueOf(course.getTarget())));
        viewHolder.credit.setText(("Credit: " + 0d));

        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        //dari kiri
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper1));

        //dari kanan
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.bottom_wraper));

        viewHolder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CourseActivity.class);
                Semester semester = list_items.get(position);
                intent.putExtra("semester_doc_path", controller.getSemesterPath() + semester.getDocID());
                context.startActivity(intent);
            }
        });

        viewHolder.swipeLayout.getSurfaceView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(context, "long clicked", Toast.LENGTH_SHORT).show();
                return true;
            }
        });


        viewHolder.btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Clicked on Information ", Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(view.getContext(), "Clicked on Share ", Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(view.getContext(), "Clicked on Edit  ", Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Delete Clicked", Toast.LENGTH_SHORT).show();
                controller.deleteItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.list_items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, status, gpa, target, credit;
        private SwipeLayout swipeLayout;
        private ImageButton btnLocation;
        private TextView Delete;
        private TextView Edit;
        private TextView Share;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            swipeLayout = itemView.findViewById(R.id.list_row);
            btnLocation = itemView.findViewById(R.id.btnLocation);
            Delete = itemView.findViewById(R.id.Delete);
            Edit = itemView.findViewById(R.id.Edit);
            Share = itemView.findViewById(R.id.Share);


            name = itemView.findViewById(R.id.title);
            status = itemView.findViewById(R.id.description1);
            gpa = itemView.findViewById(R.id.description2);
            target = itemView.findViewById(R.id.description3);
            credit = itemView.findViewById(R.id.description4);
//            itemView.setOnClickListener(this);
//            itemView.setOnLongClickListener(this);
        }

//        @Override
//        public void onClick(View v) {
//            int position = getAdapterPosition();
//            Intent intent = new Intent(context, CourseActivity.class);
//            Semester semester = list_items.get(position);
//            intent.putExtra("semester_doc_path", controller.getSemesterPath() + semester.getDocID());
//            context.startActivity(intent);
//        }
//
//        @Override
//        public boolean onLongClick(View v) {
//            name.setText("LongClicked");
//            return true;
//        }
    }
}
