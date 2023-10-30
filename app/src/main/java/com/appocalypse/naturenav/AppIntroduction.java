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
                "Your Adventure Starts Here",
                "Explore the beauty of the great outdoors with NatureNav. Discover parks, trails, and more.",
                R.drawable.icon,
                com.github.appintro.R.color.appintro_background_color
        ));

        addSlide(AppIntroFragment.createInstance(
                "Effortless Search",
                "NatureNav makes finding points of interest easy with a powerful search feature. Just enter your query, and we'll do the rest.",
                R.drawable.baseline_query_stats_24,
                com.github.appintro.R.color.appintro_background_color
        ));

        addSlide(AppIntroFragment.createInstance(
                "Explore Points of Interest",
                "View a wide range of points of interest on our app, from parks to outdoor gyms. You'll never run out of options.",
                R.drawable.baseline_travel_explore_24,
                com.github.appintro.R.color.appintro_background_color
        ));

        addSlide(AppIntroFragment.createInstance(
                "Rank and Rate",
                "Give feedback and rate the places you visit. Help build a community of adventurers and enhance their experiences.",
                R.drawable.baseline_stars_24,
                com.github.appintro.R.color.appintro_background_color
        ));

        addSlide(AppIntroFragment.createInstance(
                "Location permission",
                "To unlock the full potential of NatureNav, grant us access to your location. Discover nearby nature spots effortlessly.",
                R.drawable.baseline_my_location_24,
                com.github.appintro.R.color.appintro_background_color
        ));
        addSlide(AppIntroFragment.createInstance(
                "Get Ready!",
                "Are you ready to start your adventure with NatureNav? Discover, explore, rate, and connect with fellow nature enthusiasts today!",
                R.drawable.baseline_ads_click_24,
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
