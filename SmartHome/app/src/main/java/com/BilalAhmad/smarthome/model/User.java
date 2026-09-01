package com.BilalAhmad.smarthome.model;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String uid;
    private String name;
    private String email;
    private String role; //for admin/user
    private String homeId;
    private boolean isFirstLogin;
    private Map<String, Boolean> allowedRooms;

    //empty constructor is needed
    public User(){
        this.allowedRooms = new HashMap<>();
    }

    public User(String uid, String name, String email, String role, boolean isFirstLogin){
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.role = role;
        this.isFirstLogin = isFirstLogin;
        this.allowedRooms = new HashMap<>();
    }

    //getters and setters
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getHomeId() {
        return homeId;
    }
    public void setHomeId(String homeId) {
        this.homeId = homeId;
    }
    public boolean isFirstLogin() {
        return isFirstLogin;
    }
    public void setFirstLogin(boolean firstLogin) {
        isFirstLogin = firstLogin;
    }
    public Map<String, Boolean> getAllowedRooms() {
        return allowedRooms;
    }
    public void setAllowedRooms(Map<String, Boolean> allowedRooms) {
        this.allowedRooms = allowedRooms;
    }

}
