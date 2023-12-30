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

import java.time.format.DateTimeFormatter;

public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";

    private FragmentProfileBinding binding;

    private AuthViewModel authViewModel;

    private Users users;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        users = Users.getInstance();
        onUserChange(users.getUser());

        users.getUserLiveData().observe(getViewLifecycleOwner(), this::onUserChange);

        return root;
    }

    private void onUserChange(User user) {
        if(user == null){
            Log.e(TAG, "onUserChange: user is null");
            return;
        }

        binding.profileNameTextView.setText(user.name);
        if(user.username != null) {
            binding.profileUsernameTextView.setText(getString(R.string.username_placeholder, user.username));
        } else {
            Log.i(TAG, "onUserChange: username is null");
            showUsernameDialog();
        }
        binding.memberSinceTextView.setText(getString(R.string.member_since_placeholder, user.memberSince));
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
        if(id == R.id.modify_profile){
            showUsernameDialog();
        }
        return false;
    }

    private void showUsernameDialog(){
        UsernameDialogFragment fragment = new UsernameDialogFragment();
        fragment.setUsername(users.getUser().username);
        fragment.show(getChildFragmentManager(), UsernameDialogFragment.TAG);
    }
}
