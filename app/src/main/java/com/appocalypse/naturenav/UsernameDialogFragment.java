package com.appocalypse.naturenav;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.appocalypse.naturenav.auth.User;
import com.appocalypse.naturenav.auth.Users;

public class UsernameDialogFragment extends DialogFragment {
    protected String username;
    protected EditText usernameEditText;

    public void setUsername(String username) {
        this.username = username;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.username_dialog, null);
        usernameEditText = view.findViewById(R.id.username_dialog_edit_text);

        if (username != null) {
            usernameEditText.setText(username);
        }

        return new AlertDialog.Builder(requireContext())
                .setTitle("Choose a username")
                .setView(view)
                .setPositiveButton(getString(R.string.confirm), (dialog, which) -> {
                    Users users = Users.getInstance();
                    User user = users.getUser();
                    user.username = usernameEditText.getText().toString().strip();
                    users.updateUser(user);
                })
                .setNegativeButton(getString(R.string.cancel), null)
                .create();
    }

    public static String TAG = "UsernameDialogFragment";
}
