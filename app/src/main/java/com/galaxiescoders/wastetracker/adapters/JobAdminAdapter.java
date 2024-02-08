package com.galaxiescoders.wastetracker.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.galaxiescoders.wastetracker.R;
import com.galaxiescoders.wastetracker.models.JobModel;
import com.galaxiescoders.wastetracker.models.Zone;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class JobAdminAdapter extends RecyclerView.Adapter<JobAdminAdapter.ViewHolder> {
    List<JobModel>list;
    Context context;

    public JobAdminAdapter(List<JobModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public JobAdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.job_item_admin, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull JobAdminAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        JobModel jobModel  = list.get(position);
        holder.titleTxt.setText(jobModel.getTitle());
        holder.Salary.setText(jobModel.getSalary());
        holder.deadline.setText(jobModel.getDeadline());
        holder.desc.setText(jobModel.getDescription());
        holder.deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobModel jobToDelete = list.get(position);
                deleteJobByTitle(jobToDelete.getTitle());
                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, list.size());

                Toast.makeText(context, "Job deleted successfully", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void deleteJobByTitle(String title) {
        DatabaseReference jobsRef = FirebaseDatabase.getInstance().getReference("Jobs");

        // Query to find the job with the matching title
        Query query = jobsRef.orderByChild("title").equalTo(title);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Delete the tool from Firebase
                    snapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
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
        Button deletebutton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.modelTxt);
            Salary = itemView.findViewById(R.id.numberTxt);
            deadline = itemView.findViewById(R.id.mantxt);
            desc = itemView.findViewById(R.id.description);
            deletebutton = itemView.findViewById(R.id.applyBtn);

        }
    }
}
