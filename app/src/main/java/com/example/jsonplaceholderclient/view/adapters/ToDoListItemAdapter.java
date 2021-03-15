package com.example.jsonplaceholderclient.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jsonplaceholderclient.R;
import com.example.jsonplaceholderclient.models.ToDo;

import java.util.ArrayList;
import java.util.List;

public class ToDoListItemAdapter extends RecyclerView.Adapter<ToDoListItemAdapter.ViewHolder> {
    private List<ToDo> toDos = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.to_do_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ToDo toDo = toDos.get(position);
        holder.textTitle.setText(toDo.getTitle());
        holder.textCompleted.setText("Completed: " + toDo.getCompleted());
    }

    @Override
    public int getItemCount() {
        return toDos.size();
    }

    public void setToDos(List<ToDo> toDos) {
        this.toDos = toDos;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textTitle, textCompleted;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.textTitle);
            textCompleted = itemView.findViewById(R.id.textCompleted);
        }
    }
}
