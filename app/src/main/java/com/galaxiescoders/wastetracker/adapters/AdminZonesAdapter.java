package com.galaxiescoders.wastetracker.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.galaxiescoders.wastetracker.R;
import com.galaxiescoders.wastetracker.models.Zone;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdminZonesAdapter extends RecyclerView.Adapter<AdminZonesAdapter.ViewHolder> {
    ArrayList<Zone> zonesList;
    Context context;

    public AdminZonesAdapter(ArrayList<Zone> zonesList, Context context) {
        this.zonesList = zonesList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdminZonesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_zones_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull AdminZonesAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Zone zone = zonesList.get(position);
        holder.nameTxt.setText(zone.getTitle());
        holder.statusTxt.setText(zone.getStatus());
        holder.constTxt.setText(zone.getConstituency());
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Display the flow menu
                showPopupMenu(view, position);
            }
        });

    }


    private void showPopupMenu(View view, int position) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.flow_menu, popupMenu.getMenu());

        // Set the click listener for menu items
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.edit_menu) {
                    showAssignPopup(zonesList.get(position), position);
                    return true;
                } else if (item.getItemId() == R.id.delete_menu) {
                    Zone zoneToDelete = zonesList.get(position);
                    deleteZoneByTitle(zoneToDelete.getTitle());
                    zonesList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, zonesList.size());

                    Toast.makeText(context, "Zone deleted successfully", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });

        popupMenu.show();
    }

    private void showAssignPopup(Zone zone, int position) {
        DialogPlus dialogPlus = DialogPlus.newDialog(context)
                .setGravity(Gravity.CENTER)
                .setMargin(50, 0, 50, 0)
                .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.assign))
                .setExpanded(false)
                .create();

        View assignView = dialogPlus.getHolderView();

        EditText editStaffId = assignView.findViewById(R.id.staff_id);
        Button assignButton = assignView.findViewById(R.id.submit_btn);

        // Set initial values based on the selected zone
        editStaffId.setText(zone.getStaffId());

        assignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String staffId = editStaffId.getText().toString().trim();
                if (!staffId.isEmpty()) {
                    // Update the zone with the assigned staff ID and status "assigned"
                    updateZone(zone, staffId, "assigned", dialogPlus);
                } else {
                    Toast.makeText(context, "Please enter the Staff ID", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialogPlus.show();
    }

    // Method to update the zone with assigned staff ID and status
    private void updateZone(Zone zone, String staffId, String status, DialogPlus dialogPlus) {
        DatabaseReference zonesRef = FirebaseDatabase.getInstance().getReference("Zones");

        // Use the title of the zone as the key for updating
        String zoneTitle = zone.getTitle();

        zonesRef.child(zoneTitle).child("staffId").setValue(staffId);
        zonesRef.child(zoneTitle).child("status").setValue(status)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Dismiss the dialogPlus only after Firebase update is successful
                            dialogPlus.dismiss();
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "Failed to update zone", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void deleteZoneByTitle(String titleToDelete) {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference("Zones");

        // Query to find the zone with the matching title
        Query query = productsRef.orderByChild("title").equalTo(titleToDelete);

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
        return zonesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTxt;
        TextView statusTxt;
        TextView constTxt;
        ImageButton imageButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.textView15);
            statusTxt = itemView.findViewById(R.id.status);
            constTxt = itemView.findViewById(R.id.description);
            imageButton = itemView.findViewById(R.id.imageButton);
        }
    }
}
