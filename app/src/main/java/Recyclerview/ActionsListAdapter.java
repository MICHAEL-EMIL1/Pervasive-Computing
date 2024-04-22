package Recyclerview;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pervasivecomputingproject.Actions;
import com.example.pervasivecomputingproject.R;

import java.util.ArrayList;

public class ActionsListAdapter extends RecyclerView.Adapter<ActionsListAdapter.RecyclerViewHolder>{
    private ArrayList<Actions> actionsList = new ArrayList<>();
    private ArrayList<Actions> actionsListFull = new ArrayList<>();
    private OnItemClickListener listener;
    public ActionsListAdapter(ArrayList<Actions> actionsList, OnItemClickListener listener) {
        this.actionsList = actionsList; // Assign parameter to instance variable
        this.actionsListFull = new ArrayList<>(this.actionsList);
        this.listener = listener;
    }



    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    results.values = actionsListFull;
                    results.count = actionsListFull.size();
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    ArrayList<Actions> filteredList = new ArrayList<>();
                    for (Actions action : actionsListFull) {
                        if (action.getTitle().toLowerCase().contains(filterPattern)) {
                            filteredList.add(action);
                        }
                    }
                    results.values = filteredList;
                    results.count = filteredList.size();
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                actionsList.clear();
                actionsList.addAll((ArrayList) results.values);
                notifyDataSetChanged();
            }
        };
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }



    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new RecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.actions_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder,int position) {
        Actions currentAction = actionsList.get(position);
        holder.img.setImageResource(currentAction.getImageResId());
        holder.actions.setText(currentAction.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the activity class for the current item
                Class<?> activityClass = currentAction.getActivityClass();
                // Start  activity
                Intent intent = new Intent(v.getContext(), activityClass);
                v.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return actionsList.size();
    }

    public void setList(ArrayList<Actions>actionslist){

        this.actionsList= actionslist;
        notifyDataSetChanged();
    }
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView actions ;
        ImageView img;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            actions = itemView.findViewById(R.id.recyclerviewitem_title);
            img=itemView.findViewById(R.id.recyclerviewitem_imageView);
        }

    }
}