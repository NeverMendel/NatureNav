package com.appocalypse.naturenav;

import androidx.fragment.app.Fragment;

import android.Manifest;

import com.github.appintro.AppIntro;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.github.appintro.AppIntroFragment;
import com.github.appintro.AppIntroPageTransformerType;

public class AppIntroduction extends AppIntro {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        addSlide(AppIntroFragment.createInstance(getString(R.string.welcome_to_nature_nav),
                "This is a demo example in java of AppIntro library, with a custom background on each slide!",
                R.drawable.icon,
                com.github.appintro.R.color.appintro_background_color
        ));

        addSlide(AppIntroFragment.createInstance(
                "Clean App Intros",
                "This library offers developers the ability to add clean app intros at the start of their apps.",
                R.drawable.icon,
                com.github.appintro.R.color.appintro_background_color
        ));

        addSlide(AppIntroFragment.createInstance(
                "Simple, yet Customizable",
                "The library offers a lot of customization, while keeping it simple for those that like simple.",
                R.drawable.icon,
                com.github.appintro.R.color.appintro_background_color
        ));

        addSlide(AppIntroFragment.createInstance(
                "Location permission",
                "NatureNav requires access to your location to function",
                R.drawable.icon,
                com.github.appintro.R.color.appintro_background_color
        ));

        askForPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 3, true);

        setTransformer(AppIntroPageTransformerType.Fade.INSTANCE);
        showStatusBar(true);
        setColorTransitionsEnabled(true);
        setSystemBackButtonLocked(true);
        setWizardMode(true);
        setImmersiveMode();
        setIndicatorEnabled(true);
        setButtonsEnabled(true);
    }

    @Override
    protected void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }

    @Override
    protected void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }
}
