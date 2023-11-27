package com.appocalypse.naturenav.auth;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Users {
    private final static String TAG = "Users";

    private static Users instance;
    private final DatabaseReference database;

    private final MutableLiveData<AuthenticationUser> authenticationUserMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();

    private Users() {
        database = FirebaseDatabase.getInstance().getReference();
    }

    public static Users getInstance() {
        if (instance == null) {
            instance = new Users();
        }
        return instance;
    }

    public LiveData<AuthenticationUser> getAuthenticationUserLiveData(){
        return authenticationUserMutableLiveData;
    }

    public LiveData<User> getUserLiveData(){
        return userMutableLiveData;
    }

    public void setAuthenticationUser(AuthenticationUser authenticationUser){
        authenticationUserMutableLiveData.setValue(authenticationUser);
        if(authenticationUser == null) return;

        database.child("users").child(authenticationUser.uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e(TAG, "Error getting data", task.getException());
                }
                else {
                    Log.d(TAG, String.valueOf(task.getResult().getValue()));
                    userMutableLiveData.setValue((User) task.getResult().getValue());
                }
            }
        });
    }

    public void updateUser(User user){
        database.child("users").child(user.uid).setValue(user);
    }
}
