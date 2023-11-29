package com.appocalypse.naturenav.poilist;

import static android.view.WindowManager.*;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.appocalypse.naturenav.databinding.FragmentPoiListBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class PoiListFragment extends BottomSheetDialogFragment {
    public static final String TAG = "PoiListFragment";

    private FragmentPoiListBinding binding;

    private final PoiListAdapter adapter = new PoiListAdapter();

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PoiListViewModel listViewModel =
                new ViewModelProvider(requireActivity()).get(PoiListViewModel.class);

        binding = FragmentPoiListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Log.i(TAG, "onCreateView: " + getViewLifecycleOwner().toString());
        Log.i(TAG, "onCreateView: " + getViewLifecycleOwner().getLifecycle().getCurrentState());

        listViewModel.getData().observe(getViewLifecycleOwner(), data -> {
            if (data != null) {
                adapter.setItems(data);
                adapter.setCurrentLocation(listViewModel.getLocation());
                Log.i(TAG, "onCreateView: " + data.toString());
            }
        });

        binding.poiListView.setAdapter(adapter);
        binding.poiListView.setLayoutManager(new LinearLayoutManager(requireContext()));

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        Window window = getDialog().getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.setFlags(LayoutParams.FLAG_NOT_FOCUSABLE,
                LayoutParams.FLAG_NOT_FOCUSABLE);
        window.setDimAmount(0);

        WindowManager.LayoutParams params = window.getAttributes();
        params.height = 1500;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);

   }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
    }
}
