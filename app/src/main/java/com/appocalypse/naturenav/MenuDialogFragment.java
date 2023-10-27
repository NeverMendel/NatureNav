package com.appocalypse.naturenav;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.fragment.app.DialogFragment;

import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;

public class MenuDialogFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_dialog, container, false);
        view.findViewById(R.id.profile_menu_button).setOnClickListener(v -> {
            startActivity(new Intent(getContext(), ProfileActivity.class));
            dismiss();
        });
        view.findViewById(R.id.app_intro_menu_button).setOnClickListener(v -> {
            startActivity(new Intent(getContext(), AppIntroduction.class));
            dismiss();
        });
        view.findViewById(R.id.about_us_menu_button).setOnClickListener(v -> {
            startActivity(new Intent(getContext(), AboutUsActivity.class));
            dismiss();
        });
        view.findViewById(R.id.open_source_menu_button).setOnClickListener(v -> {
            startActivity(new Intent(getContext(), OssLicensesMenuActivity.class));
            dismiss();
        });
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.Theme_NatureNav);
        return dialog;
    }

}
