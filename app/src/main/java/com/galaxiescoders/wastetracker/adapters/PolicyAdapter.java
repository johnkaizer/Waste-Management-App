package com.galaxiescoders.wastetracker.adapters;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.galaxiescoders.wastetracker.R;
import com.galaxiescoders.wastetracker.models.PolicyModel;

import java.util.List;

public class PolicyAdapter extends RecyclerView.Adapter<PolicyAdapter.ViewHolder> {
    List<PolicyModel>list;
    Context context;

    public PolicyAdapter(Context context, List<PolicyModel> list) {
        this.list = list;
        this.context=context;
    }

    @NonNull
    @Override
    public PolicyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.policy_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PolicyAdapter.ViewHolder holder, int position) {
        PolicyModel policyModel = list.get(position);
        holder.titleTxt.setText(policyModel.getTitle());
        holder.dateTxt.setText(policyModel.getDate());
        holder.likes.setText(policyModel.getLikes());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show the PopupMenu
                showPopupMenu(view, policyModel);
            }
        });
    }

    private void showPopupMenu(View view, PolicyModel policyModel) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.inflate(R.menu.policy_popup_menu); // Create a menu resource file (policy_popup_menu.xml)

        // Set click listener for menu items
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.view_details) {
                    // Handle view details action
                    showDetailsDialog(policyModel);
                    return true;
                } else {
                    // Add more menu items as needed
                    return false;
                }
            }
        });

        // Show the popup menu
        popupMenu.show();
    }

    private void showDetailsDialog(PolicyModel policyModel) {
        // Implement your logic to display details in a dialog or fragment
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(policyModel.getTitle());
        builder.setMessage("Content: " + policyModel.getContent() + "\n"
                + "Date: " + policyModel.getDate());
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt;
        TextView dateTxt;
        TextView likes;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.modelTxt);
            dateTxt = itemView.findViewById(R.id.numberTxt);
            likes = itemView.findViewById(R.id.likesTxt);

        }
    }
}
