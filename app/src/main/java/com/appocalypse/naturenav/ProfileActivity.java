package com.appocalypse.naturenav;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.appocalypse.naturenav.auth.AuthViewModel;
import com.appocalypse.naturenav.profile.ProfileFragment;
import com.appocalypse.naturenav.signin.SignInFragment;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";

    private SignInFragment signInFragment;
    private ProfileFragment profileFragment;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ActionBar actionBar = Objects.requireNonNull(getSupportActionBar());
        actionBar.setTitle(getString(R.string.profile));

        signInFragment = new SignInFragment();
        profileFragment = new ProfileFragment();
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.profile_activity_fragment, signInFragment)
                .add(R.id.profile_activity_fragment, profileFragment)
                .show(signInFragment)
                .hide(profileFragment)
                .commit();

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        authViewModel.getUser().observe(this, user -> {
            Log.i(TAG, "ProfileActivity fragment change");
            if(user == null) {
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .show(signInFragment)
                        .hide(profileFragment)
                        .commit();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .hide(signInFragment)
                        .show(profileFragment)
                        .commit();
            }
        });
    }
}