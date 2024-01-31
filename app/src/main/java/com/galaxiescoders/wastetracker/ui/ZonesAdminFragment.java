package com.galaxiescoders.wastetracker.ui;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.galaxiescoders.wastetracker.R;
import com.galaxiescoders.wastetracker.databinding.FragmentAdminToolsBinding;
import com.galaxiescoders.wastetracker.databinding.FragmentZonesAdminBinding;
import com.galaxiescoders.wastetracker.models.Zone;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ZonesAdminFragment extends Fragment {
    private FragmentZonesAdminBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentZonesAdminBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View dialogView = getLayoutInflater().inflate(R.layout.add_zones, null);
                EditText zoneTitle = dialogView.findViewById(R.id.idEdTitle);

                Spinner zoneCon = dialogView.findViewById(R.id.idCont);
                Spinner zoneWard = dialogView.findViewById(R.id.idWardSpinner);

                // Step 1: Create ArrayAdapter for Constituency Spinner
                ArrayAdapter<CharSequence> constituencyAdapter = ArrayAdapter.createFromResource(getContext(),
                        R.array.Constituency, android.R.layout.simple_spinner_item);
                constituencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // Step 2: Set ArrayAdapter to Constituency Spinner
                zoneCon.setAdapter(constituencyAdapter);

                // Step 4: Set OnItemSelectedListener for Constituency Spinner
                zoneCon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        // Step 5: Update Spinner based on selected constituency
                        String selectedConstituency = parentView.getItemAtPosition(position).toString();
                        int wardArrayResourceId = getResources().getIdentifier(selectedConstituency, "array", getContext().getPackageName());

                        if (wardArrayResourceId != 0) {
                            // Step 2: Create ArrayAdapter for Ward Spinner
                            ArrayAdapter<CharSequence> wardAdapter = ArrayAdapter.createFromResource(getContext(),
                                    wardArrayResourceId, android.R.layout.simple_spinner_item);
                            wardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            // Step 3: Set ArrayAdapter to Ward Spinner
                            zoneWard.setAdapter(wardAdapter);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // Do nothing here
                    }
                });


                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                dialogView.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title = zoneTitle.getText().toString();
                        String constituency = zoneCon.getSelectedItem().toString();
                        String wards = zoneWard.getSelectedItem().toString();

                        // Check if any of the fields are empty
                        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(constituency) || TextUtils.isEmpty(wards)) {
                            Toast.makeText(getContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Zones");
                        Zone zone = new Zone();
                        databaseReference.push().setValue(zone);

                        dialog.dismiss();
                    }
                });

                dialogView.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                dialog.show();
            }
        });


        return root;
    }
}