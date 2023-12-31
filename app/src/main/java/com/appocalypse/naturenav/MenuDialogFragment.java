package com.appocalypse.naturenav;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;

public class MenuDialogFragment extends DialogFragment {
    public static final String TAG = "MenuDialogFragment";

    interface DismissEventListener {
        void onDismiss();
    }

    private DismissEventListener dismissEventListener;

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
        view.findViewById(R.id.setting_menu_button).setOnClickListener(v -> {
            startActivity(new Intent(getContext(), SettingsActivity.class));
            dismiss();
        });
        view.findViewById(R.id.about_us_menu_button).setOnClickListener(v -> {
            startActivity(new Intent(getContext(), AboutUsActivity.class));
            dismiss();
        });
        view.findViewById(R.id.privacy_policy_menu_button).setOnClickListener(v -> {
            startActivity(new Intent(getContext(), PrivacyPolicyActivity.class));
            dismiss();
        });
        view.findViewById(R.id.terms_and_conditions_button).setOnClickListener(v -> {
            startActivity(new Intent(getContext(), TermsAndConditionsActivity.class));
            dismiss();
        });
        view.findViewById(R.id.open_source_menu_button).setOnClickListener(v -> {
            startActivity(new Intent(getContext(), OssLicensesMenuActivity.class));
            dismiss();
        });

        view.findViewById(R.id.menu_close_button).setOnClickListener(v -> dismiss());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Window window = getDialog().getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND,
                WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setDimAmount(0.5f);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if(dismissEventListener != null)
            dismissEventListener.onDismiss();
    }

    public void setDismissEventListener(DismissEventListener dismissEventListener) {
        this.dismissEventListener = dismissEventListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        if(dialog.getWindow() != null){
            Window window = dialog.getWindow();
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        return dialog;
    }

}
