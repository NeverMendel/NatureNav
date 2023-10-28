package com.appocalypse.naturenav.list;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.appocalypse.naturenav.R;
import com.appocalypse.naturenav.databinding.FragmentListBinding;

import org.osmdroid.bonuspack.location.POI;

import java.util.ArrayList;

public class ListFragment extends Fragment {
    private static final String TAG = "ListFragment";

    private FragmentListBinding binding;

    private final ListAdapter adapter = new ListAdapter();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ListViewModel listViewModel =
                new ViewModelProvider(requireActivity()).get(ListViewModel.class);

        binding = FragmentListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Log.i(TAG, "onCreateView: " + getViewLifecycleOwner().toString());
        Log.i(TAG, "onCreateView: " + getViewLifecycleOwner().getLifecycle().getCurrentState());

        listViewModel.getData().observe(getViewLifecycleOwner(), data -> {
            adapter.setItems(data);
            Log.i(TAG, "onCreateView: " + data.toString());
        });

        binding.listView.setAdapter(adapter);
        binding.listView.setLayoutManager(new LinearLayoutManager(requireContext()));

        return root;
    }
}
