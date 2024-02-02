package com.galaxiescoders.wastetracker.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.galaxiescoders.wastetracker.R;
import com.galaxiescoders.wastetracker.models.Vehicle;
import com.galaxiescoders.wastetracker.models.Zone;

import java.util.List;

public class StaffZoneAdapter extends RecyclerView.Adapter<StaffZoneAdapter.ViewHolder> {
    List<Zone> list;

    public StaffZoneAdapter(List<Zone> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public StaffZoneAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.staff_zone_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StaffZoneAdapter.ViewHolder holder, int position) {
        Zone zone  = list.get(position);
        holder.title.setText(zone.getTitle());
        holder.cons.setText(zone.getConstituency());
        holder.ward.setText(zone.getWards());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView cons;
        TextView ward;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.modelTxt);
            cons = itemView.findViewById(R.id.numberTxt);
            ward = itemView.findViewById(R.id.mantxt);
        }
    }
}
