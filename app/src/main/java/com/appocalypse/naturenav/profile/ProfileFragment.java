package com.appocalypse.naturenav.profile;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ShareCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.appocalypse.naturenav.R;
import com.appocalypse.naturenav.UsernameDialogFragment;
import com.appocalypse.naturenav.auth.AuthViewModel;
import com.appocalypse.naturenav.auth.User;
import com.appocalypse.naturenav.auth.Users;
import com.appocalypse.naturenav.databinding.FragmentProfileBinding;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
            signOut();
            return true;
        }
        if(id == R.id.modify_profile){
            showUsernameDialog();
            return true;
        }
        if(id == R.id.delete_profile){
            showDeleteDialog();
            return true;
        }
        if(id == R.id.export_profile_data){
            exportProfileData();
            return true;
        }
        return false;
    }

    private void signOut(){
        authViewModel.signOut();
    }

    private void showUsernameDialog(){
        UsernameDialogFragment fragment = new UsernameDialogFragment();
        fragment.setUsername(users.getUser().username);
        fragment.show(getChildFragmentManager(), UsernameDialogFragment.TAG);
    }

    private void showDeleteDialog(){
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.confirm_profile_deletion)
                .setMessage(R.string.profile_deletion_warning)
//                .setIcon(R.drawable.ic_warning_24)
                .setPositiveButton(R.string.delete, (dialog, whichButton) -> {users.deleteUser(users.getUser()); signOut();})
                .setNegativeButton(R.string.cancel, null).show();
    }

    private void exportProfileData() {
        File file = new File(requireActivity().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "exported_data.txt");
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(users.getUser().toJson());
            writer.close();
        } catch (IOException e){
            Log.e(TAG, "exportProfileData: error writing exported data", e);
        }

        Uri uri = FileProvider.getUriForFile(requireContext(), requireContext().getPackageName() + ".provider", file.getAbsoluteFile());

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setData(uri);
        startActivity(intent);
    }
}
