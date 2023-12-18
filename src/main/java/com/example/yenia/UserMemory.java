package com.example.yenia;

public class UserMemory {
    // Users saved variables
    public static String username;
    public static String password;

    // Get the current user's username
    public static String getName(){
        return username;
    }

    // Get the current user's password
    public static String getPassword(){
        return password;
    }

    // This will update the current user and its informations
    public static void storeUser(String newUsername, String newPassword){
        username = newUsername;
        password = newPassword;
    }
}
