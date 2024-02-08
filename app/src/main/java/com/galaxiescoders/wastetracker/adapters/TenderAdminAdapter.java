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
import com.galaxiescoders.wastetracker.models.TenderModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class TenderAdminAdapter extends RecyclerView.Adapter<TenderAdminAdapter.ViewHolder> {
    List<TenderModel>list;
    Context context;

    public TenderAdminAdapter(List<TenderModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public TenderAdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tender_admin_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TenderAdminAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        TenderModel tenderModel  = list.get(position);
        holder.titleTxt.setText(tenderModel.getTitle());
        holder.Salary.setText(tenderModel.getAmount());
        holder.deadline.setText(tenderModel.getDeadline());
        holder.desc.setText(tenderModel.getDescription());
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TenderModel tenderToDelete = list.get(position);
                deleteTenderByTitle(tenderToDelete.getTitle());
                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, list.size());

                Toast.makeText(context, "Tender deleted successfully", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void deleteTenderByTitle(String title) {
        DatabaseReference tendersRef = FirebaseDatabase.getInstance().getReference("Tenders");

        // Query to find the tender with the matching title
        Query query = tendersRef.orderByChild("title").equalTo(title);

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
        Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.modelTxt);
            Salary = itemView.findViewById(R.id.numberTxt);
            deadline = itemView.findViewById(R.id.mantxt);
            desc = itemView.findViewById(R.id.description);
            deleteButton = itemView.findViewById(R.id.applyBtn);

        }
    }
}
