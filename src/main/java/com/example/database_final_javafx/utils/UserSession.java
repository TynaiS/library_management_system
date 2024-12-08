package com.example.database_final_javafx.utils;

import com.example.database_final_javafx.entity.User;

public class UserSession {
    private static User currentUser; // Static variable to store the current user

    // Private constructor to prevent instantiation
    private UserSession() {}

    // Static method to get the current user
    public static User getUser() {
        return currentUser;
    }

    // Static method to set the current user
    public static void setUser(User user) {
        currentUser = user;
    }

    // Static method to clear the user session
    public static void clearUser() {
        currentUser = null;
    }
}
