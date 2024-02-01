package com.galaxiescoders.wastetracker.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.galaxiescoders.wastetracker.R;
import com.galaxiescoders.wastetracker.models.Vehicle;
import com.galaxiescoders.wastetracker.models.Zone;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdminToolsAdapter extends RecyclerView.Adapter<AdminToolsAdapter.ViewHolder> {
    ArrayList<Vehicle> vehicleList;

    public AdminToolsAdapter(ArrayList<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    @NonNull
    @Override
    public AdminToolsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_tools_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdminToolsAdapter.ViewHolder holder, int position) {
        Vehicle vehicle = vehicleList.get(position);
        holder.nameTxt.setText(vehicle.getModel());
        holder.statusTxt.setText(vehicle.getStatus());
        // Load the image using Picasso
        String imageUrl = vehicle.getImageUrl();
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.logo)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return vehicleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTxt;
        TextView statusTxt;
        TextView modelTxt;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.textView15);
            statusTxt = itemView.findViewById(R.id.status);
            modelTxt = itemView.findViewById(R.id.description);
            imageView = itemView.findViewById(R.id.imageView7);

        }
    }
}
