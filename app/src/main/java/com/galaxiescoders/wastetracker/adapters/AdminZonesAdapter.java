package com.galaxiescoders.wastetracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.galaxiescoders.wastetracker.R;
import com.galaxiescoders.wastetracker.models.Zone;

import java.util.ArrayList;

public class AdminZonesAdapter extends RecyclerView.Adapter<AdminZonesAdapter.ViewHolder> {
    ArrayList<Zone> zonesList;

    public AdminZonesAdapter(ArrayList<Zone> zonesList) {
        this.zonesList = zonesList;
    }

    @NonNull
    @Override
    public AdminZonesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_zones_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull AdminZonesAdapter.ViewHolder holder, int position) {
        Zone zone = zonesList.get(position);
        holder.nameTxt.setText(zone.getTitle());
        holder.statusTxt.setText(zone.getStatus());
        holder.constTxt.setText(zone.getConstituency());

    }

    @Override
    public int getItemCount() {
        return zonesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTxt;
        TextView statusTxt;
        TextView constTxt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.textView15);
            statusTxt = itemView.findViewById(R.id.status);
            constTxt = itemView.findViewById(R.id.description);
        }
    }
}
