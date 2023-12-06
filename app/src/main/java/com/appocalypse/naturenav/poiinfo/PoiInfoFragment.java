package com.appocalypse.naturenav.poiinfo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appocalypse.naturenav.databinding.FragmentPoiInfoBinding;

public class PoiInfoFragment extends Fragment {

    private FragmentPoiInfoBinding binding;
    private PoiInfoViewModel poiInfoViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPoiInfoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        poiInfoViewModel = new ViewModelProvider(requireActivity()).get(PoiInfoViewModel.class);

        return root;
    }
}