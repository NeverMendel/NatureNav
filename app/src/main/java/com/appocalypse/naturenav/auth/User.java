package com.appocalypse.naturenav.auth;

import androidx.annotation.NonNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class User {
    public String uid;
    public String name;
    public String username;
    public Date memberSince;

    public User() {
    }

    public User(String uid, String name, String username, Date memberSince) {
        this.uid = uid;
        this.name = name;
        this.username = username;
        this.memberSince = memberSince;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", memberSince=" + memberSince +
                '}';
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("name", name);
        result.put("username", username);
        result.put("memberSince", memberSince);

        return result;
    }
}
