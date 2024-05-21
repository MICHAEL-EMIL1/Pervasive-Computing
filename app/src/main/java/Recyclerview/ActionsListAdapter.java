package Recyclerview;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pervasivecomputingproject.Actions;
import com.example.pervasivecomputingproject.R;

import java.util.ArrayList;

public class ActionsListAdapter extends RecyclerView.Adapter<ActionsListAdapter.RecyclerViewHolder> {
    private ArrayList<Actions> actionsList;
    private ArrayList<Actions> actionsListFull;
    private OnItemClickListener listener;
    private ArrayList<Actions> filteredList;

    public ActionsListAdapter(ArrayList<Actions> actionsList, OnItemClickListener listener) {
        this.actionsList = new ArrayList<>(actionsList);
        this.actionsListFull = new ArrayList<>(actionsList); // Deep copy of actionsList
        this.listener = listener;
    }

    public void setList(ArrayList<Actions> actionsList) {
        this.actionsList = new ArrayList<>(actionsList);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.actions_list_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Actions currentAction = actionsList.get(position);
        holder.img.setImageResource(currentAction.getImageResId());
        holder.actions.setText(currentAction.getTitle());

        holder.itemView.setOnClickListener(v -> {
            Class<?> activityClass = currentAction.getActivityClass();
            Intent intent = new Intent(v.getContext(), activityClass);
            v.getContext().startActivity(intent);
        });
    }
    public void setOriginalList(ArrayList<Actions> originalList) {
        this.actionsList = originalList;
        this.filteredList = new ArrayList<>(originalList);
        notifyDataSetChanged();
    }
    public void filter(String text) {
        filteredList.clear();
        if (text.isEmpty()) {
            filteredList.addAll(actionsList);
        } else {
            String query = text.toLowerCase().trim();
            for (Actions item : actionsList) {
                if (item.getTitle().toLowerCase().contains(query)) {
                    filteredList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return actionsList.size();
    }



    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView actions;
        ImageView img;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            actions = itemView.findViewById(R.id.recyclerviewitem_title);
            img = itemView.findViewById(R.id.recyclerviewitem_imageView);
        }
    }
}
