package com.appocalypse.naturenav.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    private AuthViewModel authViewModel;

    private TextView nameTextView, emailTextView;
    private Button signOutButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        nameTextView = root.findViewById(R.id.profile_name_text_view);
        emailTextView = root.findViewById(R.id.profile_email_text_view);

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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.profile_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.logout){
            authViewModel.signOut();
            return true;
        }
        return false;
    }
}
