package com.galaxiescoders.wastetracker.adapters;

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
import com.galaxiescoders.wastetracker.SubscriptionActivity;
import com.galaxiescoders.wastetracker.models.Subscription;

import java.util.ArrayList;

public class PackagesAdapter extends RecyclerView.Adapter<PackagesAdapter.ViewHolder> {
    ArrayList<Subscription> list;

    public PackagesAdapter(ArrayList<Subscription> list) {
        this.list = list;
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

        // Handle description
        String description = subscriptionPackages.getDescription();
        String[] descriptionParts = description.split(",");

        // Trim each part to remove leading or trailing whitespaces
        for (int i = 0; i < descriptionParts.length; i++) {
            descriptionParts[i] = descriptionParts[i].trim();
        }

        // Join the array elements with line breaks
        String formattedDescription = TextUtils.join("\n", descriptionParts);

        holder.descriptionTextView.setText(formattedDescription);
        holder.getStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start SubscriptionActivity and pass the selected package details
                if (subscriptionPackages != null) {
                    Intent intent = new Intent(v.getContext(), SubscriptionActivity.class);
                    intent.putExtra("amount", subscriptionPackages.getAmount() );
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
