package com.springguide.oauth2.authSample.entity;

public enum Role {
    ADMIN,
    USER,
    GUEST;

    // Method to return the default value
    public static Role getDefault() {
        return USER; // Set your default value here
    }


    //set role based on string shared by user.

    // Method to get Role from a string
    public static Role fromString(String role) {
        try {
            return Role.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            return getDefault(); // Return default role if input is invalid
        }
    }
}
