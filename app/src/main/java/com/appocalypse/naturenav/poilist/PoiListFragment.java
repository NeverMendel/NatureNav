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

import com.appocalypse.naturenav.bottomsheet.BottomSheetDialogViewModel;
import com.appocalypse.naturenav.databinding.FragmentPoiListBinding;
import com.appocalypse.naturenav.poiinfo.PoiInfoViewModel;
import com.appocalypse.naturenav.utility.PoiFinder;

public class PoiListFragment extends Fragment {
    public static final String TAG = "PoiListFragment";

    private FragmentPoiListBinding binding;

    private final PoiListAdapter adapter = new PoiListAdapter();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PoiListViewModel listViewModel =
                new ViewModelProvider(requireActivity()).get(PoiListViewModel.class);
        BottomSheetDialogViewModel bottomSheetDialogViewModel = new ViewModelProvider(requireActivity()).get(BottomSheetDialogViewModel.class);
        PoiInfoViewModel poiInfoViewModel = new ViewModelProvider(requireActivity()).get(PoiInfoViewModel.class);

        binding = FragmentPoiListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Log.i(TAG, "onCreateView: " + getViewLifecycleOwner().toString());
        Log.i(TAG, "onCreateView: " + getViewLifecycleOwner().getLifecycle().getCurrentState());

        PoiFinder poiFinder = PoiFinder.getInstance();

        poiFinder.getPoisLiveData().observe(getViewLifecycleOwner(), data -> {
            if (data != null) {
                adapter.setItems(data);
                adapter.setCurrentLocation(poiFinder.getLocation());
                Log.i(TAG, "onCreateView: " + data.toString());
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
