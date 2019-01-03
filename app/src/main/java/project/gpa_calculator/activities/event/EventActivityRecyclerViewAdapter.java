package project.gpa_calculator.activities.event;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import project.gpa_calculator.R;
import project.gpa_calculator.models.Event;

public class EventActivityRecyclerViewAdapter extends RecyclerView.Adapter<EventActivityRecyclerViewAdapter.ViewHolder> {

    private List<Event> list_items;
    private Context context;

    public EventActivityRecyclerViewAdapter(Context context, List<Event> list_items) {
        this.list_items = list_items;
        this.context = context;
    }


    public List<Event> getList_items() {
        return list_items;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_row, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Event event = this.list_items.get(position);
        viewHolder.name.setText(event.getEvent_name());
        viewHolder.weight.setText(("Weight: " + String.valueOf(event.getEvent_weight())));
        viewHolder.score.setText(("Score: " + String.valueOf(event.getEvent_score())));
        viewHolder.status.setText(("Status: " + (event.isDone() ? "Done" : "Not Done")));
        viewHolder.target.setText(("Target: " + String.valueOf(event.getTarget())));
    }

    @Override
    public int getItemCount() {
        return this.list_items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private TextView name, score, weight, status, target;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.title);
            weight = itemView.findViewById(R.id.description1);
            score = itemView.findViewById(R.id.description2);
            status = itemView.findViewById(R.id.description3);
            target = itemView.findViewById(R.id.description4);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context, name.getText().toString(), Toast.LENGTH_SHORT).show();
        }


        @Override
        public boolean onLongClick(View v) {
            name.setText("LongClicked");
            return true;
        }
    }
}
