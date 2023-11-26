package com.appocalypse.naturenav.auth;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationUser {
    public final String uid;
    public final String name;

    public AuthenticationUser(String uid, String name) {
        this.uid = uid;
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("name", name);

        return result;
    }
}
