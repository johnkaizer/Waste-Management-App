package com.galaxiescoders.wastetracker.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.galaxiescoders.wastetracker.R;
import com.galaxiescoders.wastetracker.models.ComplaintModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ViewHolder> {

    private List<ComplaintModel> complaints = new ArrayList<>();

    // Constructor
    public ComplaintAdapter(List<ComplaintModel> complaints) {
        this.complaints = complaints;
    }

    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textComplaint, textDate, textStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textComplaint = itemView.findViewById(R.id.textComplaint);
            textDate = itemView.findViewById(R.id.textDate);
            textStatus = itemView.findViewById(R.id.textStatus);
        }

        public void bind(ComplaintModel complaint) {
            textComplaint.setText(complaint.getComplaint());
            textDate.setText(complaint.getComplaintDate());
            textStatus.setText(complaint.getStatus());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.complaint_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(complaints.get(position));
    }

    @Override
    public int getItemCount() {
        return complaints.size();
    }
}
