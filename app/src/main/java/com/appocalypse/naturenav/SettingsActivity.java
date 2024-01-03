package com.appocalypse.naturenav;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.appocalypse.naturenav.utility.Theme;

public class SettingsActivity extends AppCompatActivity {
    public final static String TAG = "SettingsActivity";
    public final static String SETTINGS_SHARED_PREFERENCES = TAG + ".SETTINGS_SHARED_PREFERENCES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.settings);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        private static final String TAG = "SettingsFragment";
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

//            findPreference("language_key").setOnPreferenceChangeListener((preference, newValue) -> {
//                String languageCode = (String) newValue;
//                LanguageManager.setLocale(requireContext(), languageCode);
//
//                requireActivity().recreate();
//
//                return true;
//            });

            findPreference("app_theme").setOnPreferenceChangeListener(((preference, newValue) -> {
                String theme = (String) newValue;
                Theme.setTheme(theme);
                return true;
            }));
        }
    }

    public static String getSetting(Context context, String name){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Log.i(TAG, "getSetting: preference retrieved: " + preferences.getString(name, ""));
        return preferences.getString(name, "");
    }
}