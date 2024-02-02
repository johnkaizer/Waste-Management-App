package com.galaxiescoders.wastetracker.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.galaxiescoders.wastetracker.R;
import com.galaxiescoders.wastetracker.models.JobModel;
import com.galaxiescoders.wastetracker.models.Vehicle;

import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder> {
    List<JobModel>list;

    public JobAdapter(List<JobModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public JobAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.job_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull JobAdapter.ViewHolder holder, int position) {
        JobModel jobModel  = list.get(position);
        holder.titleTxt.setText(jobModel.getTitle());
        holder.Salary.setText(jobModel.getSalary());
        holder.deadline.setText(jobModel.getDeadline());
        holder.desc.setText(jobModel.getDescription());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt;
        TextView Salary;
        TextView deadline;
        TextView desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.modelTxt);
            Salary = itemView.findViewById(R.id.numberTxt);
            deadline = itemView.findViewById(R.id.mantxt);
            desc = itemView.findViewById(R.id.description);

        }
    }
}
