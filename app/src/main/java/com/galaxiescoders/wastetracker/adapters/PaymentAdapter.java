package com.galaxiescoders.wastetracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.galaxiescoders.wastetracker.R;
import com.galaxiescoders.wastetracker.models.Payment;

import java.util.ArrayList;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {
    ArrayList<Payment>list;

    public PaymentAdapter(ArrayList<Payment> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public PaymentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.subscription_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull PaymentAdapter.ViewHolder holder, int position) {
        Payment payment = list.get(position);
        holder.transaction.setText(payment.getHouseNumber());
        holder.startD.setText(payment.getPaymentDate());
        holder.endD.setText(payment.getExpiryDate());
        holder.title.setText(payment.getPaymentPackage());
        holder.amount.setText(payment.getPaymentAmount());

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
        TextView hsNo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            startD = itemView.findViewById(R.id.startDate);
            endD = itemView.findViewById(R.id.endDate);
            transaction = itemView.findViewById(R.id.transctionId);
            amount = itemView.findViewById(R.id.amount);
            title = itemView.findViewById(R.id.title);
        }
    }
}
