package com.galaxiescoders.wastetracker.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.galaxiescoders.wastetracker.R;
import com.galaxiescoders.wastetracker.my_activities.SubscriptionActivity;
import com.galaxiescoders.wastetracker.models.Subscription;

import java.util.ArrayList;

public class PackagesAdapter extends RecyclerView.Adapter<PackagesAdapter.ViewHolder> {
    ArrayList<Subscription> list;
    Context context;

    public PackagesAdapter(Context context, ArrayList<Subscription> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.packages_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Subscription subscriptionPackages = list.get(position);

        holder.titleTextView.setText(subscriptionPackages.getTitle());
        holder.amountTextView.setText(String.valueOf(subscriptionPackages.getAmount()));
        holder.descriptionTextView.setText(subscriptionPackages.getDescription());
        holder.getStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start SubscriptionActivity and pass the selected package details
                if (subscriptionPackages != null) {
                    Intent intent = new Intent(v.getContext(), SubscriptionActivity.class);
                    intent.putExtra("amount", subscriptionPackages.getAmount() );
                    intent.putExtra("title", subscriptionPackages.getTitle() );
                    v.getContext().startActivity(intent);
                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView amountTextView;
        TextView descriptionTextView;
        Button getStartedBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.textViewTitle);
            amountTextView = itemView.findViewById(R.id.textViewAmount);
            descriptionTextView = itemView.findViewById(R.id.textViewDescription);
            getStartedBtn = itemView.findViewById(R.id.btnGetStarted);
        }
    }
}
