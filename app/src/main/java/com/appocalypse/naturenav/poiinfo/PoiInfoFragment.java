package com.appocalypse.naturenav.poiinfo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.TextView;

import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appocalypse.naturenav.R;
import com.appocalypse.naturenav.api.POI;
import com.appocalypse.naturenav.databinding.FragmentPoiInfoBinding;
import com.appocalypse.naturenav.utility.POITypes;

import java.text.DecimalFormat;


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

        binding.poiAddressTextView.setText(getString(R.string.address) + " " + poi.address);

        updateDistanceTextView(binding.poiAirDistanceTextView, poi.airDistanceMeters, R.string.air_distance_placeholder);
        updateDistanceTextView(binding.poiRoadDistanceTextView, poi.roadDistanceMeters, R.string.road_distance_placeholder);

        // convert seconds to minutes
        binding.poiDurationTextView.setText(getString(R.string.duration_placeholder, formatOneSignificantDigit(poi.roadDistanceSeconds / 60.0)));
    }

    private void updateDistanceTextView(TextView textView, double distanceMeters, @StringRes int stringResourceId) {
        String formattedDistance;
        if (distanceMeters >= 1000.0) {
            double distanceKm = distanceMeters / 1000.0;
            formattedDistance = formatOneSignificantDigit(distanceKm);
        } else {
            formattedDistance = formatOneSignificantDigit(distanceMeters);
        }
        textView.setText(getString(stringResourceId, formattedDistance, " km"));
    }

    private String formatOneSignificantDigit(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        return decimalFormat.format(number);
    }

}