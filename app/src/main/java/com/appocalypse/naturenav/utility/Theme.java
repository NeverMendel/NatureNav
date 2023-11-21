package com.appocalypse.naturenav.utility;

import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

public class Theme {
    private static final String TAG = "Theme";

    public static void setTheme(String theme){
        int themeId = -1;
        switch (theme) {
            case "auto":
                themeId = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
                break;
            case "light":
                themeId = AppCompatDelegate.MODE_NIGHT_NO;
                break;
            case "dark":
                themeId = AppCompatDelegate.MODE_NIGHT_YES;
                break;
            default:
                Log.e(TAG, "onCreatePreferences: theme not found - " + theme);
                break;
        }
        AppCompatDelegate.setDefaultNightMode(themeId);
    }
}
