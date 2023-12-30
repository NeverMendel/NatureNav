package com.appocalypse.naturenav.auth;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class User {
    public String uid;
    public String name;
    public String username;
    public String memberSince;
    public boolean newUser;

    public User() {
    }

    public User(String uid, String name){
        this.uid = uid;
        this.name = name;
        memberSince = LocalDate.now().format(DateTimeFormatter.ofPattern("d/M/y"));
    }

    public User(String uid, String name, String username, String memberSince) {
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

    public static User fromMap(Map<String, Object> map) {
        User result = new User();
        if(map.containsKey("uid")) result.uid = (String) map.get("uid");
        if(map.containsKey("name")) result.uid = (String) map.get("name");
        if(map.containsKey("username")) result.uid = (String) map.get("username");
        if(map.containsKey("memberSince")) result.uid = (String) map.get("memberSince");

        return result;
    }


}
