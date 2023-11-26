package com.appocalypse.naturenav;

import static android.provider.Settings.System.getString;
import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class UsernameDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        EditText editText = new EditText(requireContext());
        return new AlertDialog.Builder(requireContext())
                .setTitle("Choose a username")
                .setView(editText)
                .setPositiveButton(getString(R.string.confirm), (dialog, which) -> {

                })
                .setNegativeButton(getString(R.string.cancel), (dialog, which) -> {

                })
                .create();
    }

    public static String TAG = "UsernameDialogFragment";
}
