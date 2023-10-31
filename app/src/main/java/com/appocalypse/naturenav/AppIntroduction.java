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

        addSlide(AppIntroFragment.createInstance(
                getString(R.string.slide1_title),
                getString(R.string.slide1_description),
                R.drawable.icon,
                com.github.appintro.R.color.appintro_background_color
        ));

        addSlide(AppIntroFragment.createInstance(
                getString(R.string.slide2_title),
                getString(R.string.slide2_description),
                R.drawable.ic_query_stats,
                com.github.appintro.R.color.appintro_background_color
        ));

        addSlide(AppIntroFragment.createInstance(
                getString(R.string.slide3_title),
                getString(R.string.slide3_description),
                R.drawable.ic_travel_explore,
                com.github.appintro.R.color.appintro_background_color
        ));

        addSlide(AppIntroFragment.createInstance(
                getString(R.string.slide4_title),
                getString(R.string.slide4_description),
                R.drawable.ic_stars,
                com.github.appintro.R.color.appintro_background_color
        ));

        addSlide(AppIntroFragment.createInstance(
                getString(R.string.slide5_title),
                getString(R.string.slide5_description),
                R.drawable.ic_my_location,
                com.github.appintro.R.color.appintro_background_color
        ));
        addSlide(AppIntroFragment.createInstance(
                getString(R.string.slide6_title),
                getString(R.string.slide6_description),
                R.drawable.ic_ads_click,
                com.github.appintro.R.color.appintro_background_color
        ));

        askForPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 5, true);

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
