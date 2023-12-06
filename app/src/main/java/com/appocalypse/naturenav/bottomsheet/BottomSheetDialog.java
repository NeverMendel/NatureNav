package com.appocalypse.naturenav.bottomsheet;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.appocalypse.naturenav.R;
import com.appocalypse.naturenav.databinding.FragmentBottomSheetDialogBinding;
import com.appocalypse.naturenav.poiinfo.PoiInfoFragment;
import com.appocalypse.naturenav.poilist.PoiListFragment;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetDialog extends BottomSheetDialogFragment {
    public static final String TAG = "BottomSheetDialog";

    private FragmentBottomSheetDialogBinding binding;

    private PoiListFragment poiListFragment;
    private PoiInfoFragment poiInfoFragment;

    private BottomSheetDialogViewModel bottomSheetDialogViewModel;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBottomSheetDialogBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        bottomSheetDialogViewModel = new ViewModelProvider(requireActivity()).get(BottomSheetDialogViewModel.class);

        poiListFragment = new PoiListFragment();
        poiInfoFragment = new PoiInfoFragment();

        getChildFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.bottom_sheet_fragment, poiListFragment)
                .add(R.id.bottom_sheet_fragment, poiInfoFragment)
                .show(poiListFragment)
                .hide(poiInfoFragment)
                .commit();

        bottomSheetDialogViewModel.getDisplayingListLiveData().observe(getViewLifecycleOwner(), displayingList -> {
            if (displayingList) {
                getChildFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .show(poiListFragment)
                        .hide(poiInfoFragment)
                        .commit();
            } else {
                getChildFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .hide(poiListFragment)
                        .show(poiInfoFragment)
                        .commit();
            }
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        Window window = getDialog().getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
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
