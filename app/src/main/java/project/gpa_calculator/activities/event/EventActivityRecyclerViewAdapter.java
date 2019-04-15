package project.gpa_calculator.activities.event;

import android.content.Context;
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
import project.gpa_calculator.models.Event;

public class EventActivityRecyclerViewAdapter extends RecyclerView.Adapter<EventActivityRecyclerViewAdapter.ViewHolder> {

    private List<Event> list_items;
    private Context context;

    EventActivityRecyclerViewAdapter(Context context, List<Event> list_items) {
        this.list_items = list_items;
        this.context = context;
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
        final Event event = this.list_items.get(position);
        viewHolder.name.setText(event.getEvent_name());
        viewHolder.weight.setText(("Weight: " + String.valueOf(event.getEvent_weight())));
        viewHolder.score.setText(("Score: " + String.valueOf(event.getEvent_score())));
        viewHolder.status.setText(("Status: " + (event.isDone() ? "Done" : "Not Done")));
        viewHolder.target.setText(("Target: " + String.valueOf(event.getTarget())));


        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        //dari kiri
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper1));

        //dari kanan
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.bottom_wraper));

//        viewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
//            @Override
//            public void onStartOpen(SwipeLayout layout) {
//
//            }
//
//            @Override
//            public void onOpen(SwipeLayout layout) {
//
//            }
//
//            @Override
//            public void onStartClose(SwipeLayout layout) {
//
//            }
//
//            @Override
//            public void onClose(SwipeLayout layout) {
//
//            }
//
//            @Override
//            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
//
//            }
//
//            @Override
//            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
//
//            }
//        });


        viewHolder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, " Click : " + event.getEvent_name(), Toast.LENGTH_SHORT).show();
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

//        viewHolder.Delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mItemManger.removeShownLayouts(viewHolder.swipeLayout);
//                studentList.remove(position);
//                notifyItemRemoved(position);
//                notifyItemRangeChanged(position, studentList.size());
//                mItemManger.closeAllItems();
//                Toast.makeText(v.getContext(), "Deleted " + viewHolder.Name.getText().toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        mItemManger.bindView(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return this.list_items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView name, score, weight, status, target;
        private SwipeLayout swipeLayout;
        private ImageButton btnLocation;
        private TextView Delete;
        private TextView Edit;
        private TextView Share;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            swipeLayout = itemView.findViewById(R.id.list_row);
            btnLocation = itemView.findViewById(R.id.btnLocation);
            Delete = itemView.findViewById(R.id.Delete);
            Edit = itemView.findViewById(R.id.Edit);
            Share = itemView.findViewById(R.id.Share);


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
