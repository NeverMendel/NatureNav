package com.appocalypse.naturenav.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class AuthViewModel extends ViewModel {

    private GoogleSignInClient googleSignInClient;
    private final MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();

    public AuthViewModel() {

    }

    public void setGoogleSignInClient(GoogleSignInClient googleSignInClient) {
        this.googleSignInClient = googleSignInClient;
    }

    public LiveData<User> getUser(){
        return userMutableLiveData;
    }

    public void signIn(GoogleSignInAccount account){
        User user = new User(account.getId(), account.getDisplayName(), account.getEmail());
        userMutableLiveData.setValue(user);
    }

    public void signOut(){
        if(googleSignInClient != null)
            googleSignInClient.signOut();
        userMutableLiveData.setValue(null);
    }
}
