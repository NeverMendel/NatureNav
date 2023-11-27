package com.appocalypse.naturenav.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class AuthViewModel extends ViewModel {
    public static final String TAG = "AuthViewModel";

    private GoogleSignInClient googleSignInClient;

    public AuthViewModel() {

    }

    public void setGoogleSignInClient(GoogleSignInClient googleSignInClient) {
        this.googleSignInClient = googleSignInClient;
    }

    public void signIn(GoogleSignInAccount account) {
        AuthenticationUser authenticationUser = new AuthenticationUser(account.getId(), account.getDisplayName());
        Users.getInstance().setAuthenticationUser(authenticationUser);
        Log.i(TAG, "signIn: logged user id " + account.getId());
    }

    public void signOut() {
        if (googleSignInClient != null)
            googleSignInClient.signOut();
        Users.getInstance().setAuthenticationUser(null);
    }
}
