package com.appocalypse.naturenav.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.appocalypse.naturenav.R;
import com.appocalypse.naturenav.UsernameDialogFragment;
import com.appocalypse.naturenav.auth.AuthViewModel;
import com.appocalypse.naturenav.auth.AuthenticationUser;
import com.appocalypse.naturenav.auth.User;
import com.appocalypse.naturenav.auth.Users;
import com.appocalypse.naturenav.databinding.FragmentProfileBinding;
import com.google.android.material.imageview.ShapeableImageView;

public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";

    private FragmentProfileBinding binding;

    private AuthViewModel authViewModel;

    private TextView nameTextView, usernameTextView;
    private ShapeableImageView profilePictureImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        nameTextView = root.findViewById(R.id.profile_name_text_view);
        usernameTextView = root.findViewById(R.id.profile_username_text_view);
        profilePictureImageView = root.findViewById(R.id.profile_picture_image_view);

        Users.getInstance().getUserLiveData().observe(getViewLifecycleOwner(), this::onUserChange);

        onUserChange(Users.getInstance().getUserLiveData().getValue());

        return root;
    }

    private void onUserChange(User user) {
        if (user != null) {
            Log.i(TAG, "onUserChange: " + user.toString());
            nameTextView.setText(user.name);
            usernameTextView.setText(user.username);
        } else {
            Log.i(TAG, "onUserChange: user is null");
            new UsernameDialogFragment().show(getChildFragmentManager(), UsernameDialogFragment.TAG);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.profile_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            authViewModel.signOut();
            return true;
        }
        return false;
    }
}
