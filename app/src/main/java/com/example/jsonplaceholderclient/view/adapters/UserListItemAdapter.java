package com.example.jsonplaceholderclient.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jsonplaceholderclient.R;
import com.example.jsonplaceholderclient.models.User;
import com.example.jsonplaceholderclient.utilities.OnUserClick;

import java.util.ArrayList;
import java.util.List;

public class UserListItemAdapter extends RecyclerView.Adapter<UserListItemAdapter.ViewHolder> {
    private List<User> users = new ArrayList<>();
    private OnUserClick onUserClick;

    public UserListItemAdapter(OnUserClick onUserClick) {
        this.onUserClick = onUserClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final User user = users.get(position);
        holder.textName.setText(user.getName());
        holder.textUsername.setText(user.getUsername());
        holder.textEmail.setText(user.getEmail());

        holder.userListItemParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUserClick.onClick(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textName, textUsername, textEmail;
        private CardView userListItemParent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textUsername = itemView.findViewById(R.id.textUsername);
            textEmail = itemView.findViewById(R.id.textEmail);

            userListItemParent = itemView.findViewById(R.id.userListItemParent);
        }
    }
}
