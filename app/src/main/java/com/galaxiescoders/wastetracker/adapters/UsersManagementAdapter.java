package com.galaxiescoders.wastetracker.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.galaxiescoders.wastetracker.R;
import com.galaxiescoders.wastetracker.models.User;

import java.util.ArrayList;

public class UsersManagementAdapter extends RecyclerView.Adapter<UsersManagementAdapter.ViewHolder> {
    ArrayList<User> usersList;
    public UsersManagementAdapter(ArrayList<User> usersList) {
        this.usersList = usersList;
    }
    @NonNull
    @Override
    public UsersManagementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.users_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull UsersManagementAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        User user = usersList.get(position);
        holder.nameTxt.setText(user.getFullName());
        holder.phone.setText(user.getPhone());
        holder.email.setText(user.getEmail());
        holder.role.setText(user.getRole());
    }


    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTxt;
        TextView phone;
        TextView email;
        TextView role;
        ImageButton menuBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.username);
            phone = itemView.findViewById(R.id.phone);
            email = itemView.findViewById(R.id.email);
            role = itemView.findViewById(R.id.role_txt);
            menuBtn = itemView.findViewById(R.id.optionsBtn);
            
        }
    }
}
