package com.appocalypse.naturenav.auth;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class User {
    public final String uid;
    public final String name;
    public final String email;

    public User(String uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("name", name);
        result.put("email", email);

        return result;
    }
}
