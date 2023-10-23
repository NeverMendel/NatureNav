package com.appocalypse.naturenav;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;

import org.osmdroid.config.Configuration;

import com.appocalypse.naturenav.MapFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Configuration.getInstance().setUserAgentValue(getString(R.string.app_name));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.main_activity_fragment, MapFragment.class, null)
                    .commit();
        }

        findViewById(R.id.search_text_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_activity_fragment, ListFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("Map")
                        .commit();
            }
        });

        findViewById(R.id.action_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().popBackStackImmediate();
            }
        });
    }
}