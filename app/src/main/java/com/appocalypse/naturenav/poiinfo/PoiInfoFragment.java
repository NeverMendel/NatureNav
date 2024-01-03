package com.appocalypse.naturenav.poiinfo;

import static com.appocalypse.naturenav.utility.UnitConverter.formatDistance;
import static com.appocalypse.naturenav.utility.UnitConverter.formatDuration;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appocalypse.naturenav.R;
import com.appocalypse.naturenav.api.POI;
import com.appocalypse.naturenav.databinding.FragmentPoiInfoBinding;
import com.appocalypse.naturenav.utility.POITypes;

public class PoiInfoFragment extends Fragment {

    private FragmentPoiInfoBinding binding;
    private PoiInfoViewModel poiInfoViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPoiInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViewModel();
        observePoiData();
    }

    private void initializeViewModel() {
        poiInfoViewModel = new ViewModelProvider(requireActivity()).get(PoiInfoViewModel.class);
    }

    private void observePoiData() {
        poiInfoViewModel.getDisplayedPoiLiveData().observe(getViewLifecycleOwner(), poi -> {
            if (poi != null) {
                updateUIWithPoiData(poi);
            }
        });
    }

    private void updateUIWithPoiData(POI poi) {
        binding.poiTitleTextView.setText(poi.tags.get("name") == null ?
                requireContext().getString(POITypes.amenityToStringId.get(poi.tags.get("amenity"))) :
                poi.tags.get("name"));

        binding.poiAddressTextView.setText(getString(R.string.address_placeholder, poi.address));

        binding.poiAirDistanceTextView.setText(formatDistance(poi.airDistanceMeters));
        binding.poiRoadDistanceTextView.setText(formatDistance(poi.road.mLength * 1000.0));

        binding.poiDurationTextView.setText(formatDuration(poi.road.mLength * 1000.0 / 100.0 * 60.0));
    }
}