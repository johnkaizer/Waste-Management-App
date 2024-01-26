package com.galaxiescoders.wastetracker.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.galaxiescoders.wastetracker.R;
import com.galaxiescoders.wastetracker.databinding.FragmentAdminToolsBinding;
import com.galaxiescoders.wastetracker.databinding.FragmentZonesAdminBinding;

public class ZonesAdminFragment extends Fragment {
    private FragmentZonesAdminBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentZonesAdminBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        return root;
    }
}