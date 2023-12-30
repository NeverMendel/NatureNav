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

import java.util.Map;

public class Users {
    private final static String TAG = "Users";

    private static Users instance;
    private final DatabaseReference database;

    private final MutableLiveData<AuthenticationUser> authenticationUserMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();

    private Users() {
        database = FirebaseDatabase.getInstance().getReference("users");
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

        database.child(authenticationUser.uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e(TAG, "Error getting user from firebase", task.getException());
                }
                else {
                    User user;
                    // if new user
                    if(task.getResult().getValue() == null){
                        Log.i(TAG, "New user");
                         user = new User(authenticationUser.uid, authenticationUser.name);
                         user.newUser = true;
                    } else {
                        Log.i(TAG, "Retrieved user from firebase: " + String.valueOf(task.getResult().getValue()));
                        user = task.getResult().getValue(User.class);
                        Log.i(TAG, "Built user from firebase: " + user);
                    }
                    userMutableLiveData.postValue(user);
                }
            }
        });
    }

    public User getUser(){
        return userMutableLiveData.getValue();
    }

    public void updateUser(User user){
        user.newUser = false;
        database.child(user.uid).setValue(user);
        userMutableLiveData.postValue(user);
    }
}
