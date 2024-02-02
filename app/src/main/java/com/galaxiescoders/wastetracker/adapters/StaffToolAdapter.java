package com.galaxiescoders.wastetracker.adapters;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.galaxiescoders.wastetracker.R;
import com.galaxiescoders.wastetracker.models.Payment;
import com.galaxiescoders.wastetracker.models.Vehicle;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class StaffToolAdapter extends RecyclerView.Adapter<StaffToolAdapter.ViewHolder> {
    List<Vehicle> list;

    public StaffToolAdapter(List<Vehicle> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public StaffToolAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.staff_tool_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StaffToolAdapter.ViewHolder holder, int position) {
        Vehicle vehicle  = list.get(position);
        holder.model.setText(vehicle.getModel());
        holder.reg.setText(vehicle.getRegNum());
        holder.yom.setText(vehicle.getYOM());
        // Load the image using Picasso
        String imageUrl = vehicle.getImageUrl();
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.logo)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView model;
        TextView reg;
        TextView yom;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.info_image);
            model = itemView.findViewById(R.id.modelTxt);
            reg = itemView.findViewById(R.id.numberTxt);
            yom = itemView.findViewById(R.id.mantxt);
        }
    }
}
