package com.appocalypse.naturenav.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.appocalypse.naturenav.R;
import com.appocalypse.naturenav.auth.AuthViewModel;
import com.appocalypse.naturenav.auth.User;
import com.appocalypse.naturenav.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";

    private FragmentProfileBinding binding;

    TextView nameTextView, emailTextView;
    Button signOutButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AuthViewModel authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        nameTextView = root.findViewById(R.id.profile_name_text_view);
        emailTextView = root.findViewById(R.id.profile_email_text_view);
        signOutButton = root.findViewById(R.id.profile_sign_out_button);

        signOutButton.setOnClickListener(l -> authViewModel.signOut());

        authViewModel.getUser().observe(getViewLifecycleOwner(), this::onUserChange);

        onUserChange(authViewModel.getUser().getValue());

        return root;
    }

    private void onUserChange(User user){
        if(user != null){
            Log.i(TAG, "onUserChange: " + user.toString());
            nameTextView.setText(user.name);
            emailTextView.setText(user.email);
        } else {
            Log.i(TAG, "onUserChange: user is null");
        }
    }
}