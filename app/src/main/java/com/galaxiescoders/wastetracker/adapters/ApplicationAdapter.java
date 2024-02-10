package com.galaxiescoders.wastetracker.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.galaxiescoders.wastetracker.R;
import com.galaxiescoders.wastetracker.models.ApplicationModel;
import com.galaxiescoders.wastetracker.models.JobModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ViewHolder> {
    List<ApplicationModel>list;
    Context context;

    public ApplicationAdapter(List<ApplicationModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ApplicationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.application_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicationAdapter.ViewHolder holder, int position) {
        ApplicationModel applicationModel  = list.get(position);
        holder.titleTxt.setText(applicationModel.getName());
        holder.Salary.setText(applicationModel.getIdNo());
        holder.deadline.setText(applicationModel.getTitle());
        holder.desc.setText(applicationModel.getDate());

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
        Button applybutton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.modelTxt);
            Salary = itemView.findViewById(R.id.numberTxt);
            deadline = itemView.findViewById(R.id.mantxt);
            desc = itemView.findViewById(R.id.description);
            applybutton = itemView.findViewById(R.id.applyBtn);

        }
    }
}
