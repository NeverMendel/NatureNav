package com.appocalypse.naturenav.poilist;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.appocalypse.naturenav.api.POI;
import com.appocalypse.naturenav.bottomsheet.BottomSheetDialogViewModel;
import com.appocalypse.naturenav.databinding.FragmentPoiListBinding;
import com.appocalypse.naturenav.map.MapViewModel;
import com.appocalypse.naturenav.poiinfo.PoiInfoViewModel;
import com.appocalypse.naturenav.utility.PoiFinder;

public class PoiListFragment extends Fragment {
    public static final String TAG = "PoiListFragment";
    private final PoiListAdapter adapter = new PoiListAdapter();
    private FragmentPoiListBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PoiListViewModel listViewModel =
                new ViewModelProvider(requireActivity()).get(PoiListViewModel.class);
        BottomSheetDialogViewModel bottomSheetDialogViewModel = new ViewModelProvider(requireActivity()).get(BottomSheetDialogViewModel.class);
        PoiInfoViewModel poiInfoViewModel = new ViewModelProvider(requireActivity()).get(PoiInfoViewModel.class);

        binding = FragmentPoiListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.poiProgressBar.show();

        listViewModel.getPoiLiveData().observe(getViewLifecycleOwner(), data -> {
            if (data != null && data.size() > 0) {
                data.sort(new POI.AirDistanceComparator());
                adapter.setItems(data);
                binding.poiProgressBar.hide();
            } else {
                binding.poiProgressBar.show();
            }
        });

        adapter.setOnClick(item -> {
            poiInfoViewModel.setDisplayedPoi(item);
            bottomSheetDialogViewModel.setDisplayingList(false);
        });

        binding.poiListView.setAdapter(adapter);
        binding.poiListView.setLayoutManager(new LinearLayoutManager(requireContext()));

        return root;
    }
}
