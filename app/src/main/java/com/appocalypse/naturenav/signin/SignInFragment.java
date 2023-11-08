package com.appocalypse.naturenav.signin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.appocalypse.naturenav.R;
import com.appocalypse.naturenav.auth.AuthViewModel;
import com.appocalypse.naturenav.databinding.FragmentSignInBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class SignInFragment extends Fragment {
    private static final String TAG = "SignInFragment";
    private static final int RC_SIGN_IN = 9001;

    private FragmentSignInBinding binding;
    private AuthViewModel authViewModel;
    private GoogleSignInClient googleSignInClient;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);

        binding = FragmentSignInBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestId()
                .requestProfile()
                .requestIdToken("709356760756-ils02o79ecd3i6orib0993a2qhhuusiv.apps.googleusercontent.com")
                .build();

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso);
        authViewModel.setGoogleSignInClient(googleSignInClient);

        SignInButton signInButton = root.findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setColorScheme(SignInButton.COLOR_LIGHT);
        signInButton.setOnClickListener(l -> signIn());

        return root;
    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onStart() {
        super.onStart();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(requireContext());
        if(account != null)
            authViewModel.signIn(account);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            authViewModel.signIn(account);
        } catch (ApiException e) {
            Log.w(TAG, "signInResult: failed code=" + e.getStatusCode());
            authViewModel.signOut();
        }
    }
}
