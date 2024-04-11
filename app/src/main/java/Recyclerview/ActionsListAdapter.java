package Recyclerview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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

public class ActionsListAdapter extends RecyclerView.Adapter<ActionsListAdapter.RecyclerViewHolder>{
    private ArrayList<Actions> actionslist = new ArrayList<>();
    private OnItemClickListener listener;

    public ActionsListAdapter(OnItemClickListener listener) {
        this.listener = listener;
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
        Actions currentAction = actionslist.get(position);
        holder.img.setImageResource(currentAction.getImageResId());
        holder.actions.setText(currentAction.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the activity class for the current item
                Class<?> activityClass = currentAction.getActivityClass();
                // Start the corresponding activity
                Intent intent = new Intent(v.getContext(), activityClass);
                v.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return actionslist.size();
    }

    public void setList(ArrayList<Actions>actionslist){

        this.actionslist= actionslist;
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