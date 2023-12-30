package com.appocalypse.naturenav.auth;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    public String uid;
    public String name;
    public String username;
    public String memberSince;
    public boolean newUser;
    public List<Long> likedPoi;
    public List<Long> dislikedPoi;

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

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", memberSince='" + memberSince + '\'' +
                ", newUser=" + newUser +
                ", likedPoi=" + likedPoi +
                ", dislikedPoi=" + dislikedPoi +
                '}';
    }
}
