package com.galaxiescoders.wastetracker.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.galaxiescoders.wastetracker.R;
import com.galaxiescoders.wastetracker.models.Payment;

import java.util.ArrayList;

public class AdminPaymentAdapter extends RecyclerView.Adapter<AdminPaymentAdapter.ViewHolder> {
    ArrayList<Payment>list;

    public AdminPaymentAdapter(ArrayList<Payment> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public AdminPaymentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.subscription_item_admin, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull AdminPaymentAdapter.ViewHolder holder, int position) {
        Payment payment = list.get(position);
        holder.transaction.setText(payment.getHouseNumber());
        holder.startD.setText(payment.getPaymentDate());
        holder.endD.setText(payment.getExpiryDate());
        holder.title.setText(payment.getPaymentPackage());
        holder.amount.setText(payment.getPaymentAmount());
        holder.username.setText(payment.getUserName());
        holder.location.setText(payment.getLocation());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView startD;
        TextView endD;
        TextView transaction;
        TextView amount;
        TextView title;
        TextView location;
        TextView username;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            startD = itemView.findViewById(R.id.startDate);
            endD = itemView.findViewById(R.id.endDate);
            transaction = itemView.findViewById(R.id.transctionId);
            amount = itemView.findViewById(R.id.amount);
            title = itemView.findViewById(R.id.title);
            location = itemView.findViewById(R.id.locationEt);
            username = itemView.findViewById(R.id.nameEt);
        }
    }
}
