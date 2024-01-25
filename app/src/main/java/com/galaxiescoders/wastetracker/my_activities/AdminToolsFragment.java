package com.galaxiescoders.wastetracker.my_activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.galaxiescoders.wastetracker.R;
import com.galaxiescoders.wastetracker.databinding.FragmentAdminToolsBinding;

public class AdminToolsFragment extends Fragment {
    private FragmentAdminToolsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminToolsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }
}