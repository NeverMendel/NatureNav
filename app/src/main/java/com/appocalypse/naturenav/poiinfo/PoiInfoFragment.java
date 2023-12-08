package com.appocalypse.naturenav.poiinfo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appocalypse.naturenav.R;
import com.appocalypse.naturenav.databinding.FragmentPoiInfoBinding;
import com.appocalypse.naturenav.utility.POITypes;

import org.osmdroid.bonuspack.location.POI;

public class PoiInfoFragment extends Fragment {

    private FragmentPoiInfoBinding binding;
    private PoiInfoViewModel poiInfoViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPoiInfoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        poiInfoViewModel = new ViewModelProvider(requireActivity()).get(PoiInfoViewModel.class);

        poiInfoViewModel.getDisplayedPoiLiveData().observe(getViewLifecycleOwner(), poi -> {
            if (poi != null) {
                binding.poiTitleTextView.setText(poi.tags.get("name") == null ? requireContext().getString(POITypes.amenityToStringId.get(poi.tags.get("amenity"))) : poi.tags.get("name"));
                binding.poiAddressTextView.setText(getText( R.string.address ) +" "+ poi.address);
                binding.poiAirDistanceTextView.setText(getString(R.string.air_distance_placeholder, (int) poi.airDistanceMeters ));
                binding.poiRoadDistanceTextView.setText(getString(R.string.road_distance_placeholder,  poi.roadDistanceMeters));
                // convert seconds to minutes
                binding.poiDurationTextView.setText(getString(R.string.duration_placeholder, poi.roadDistanceSeconds/60));



            }
        });


        return root;
    }
}