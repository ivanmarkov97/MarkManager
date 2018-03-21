package com.example.ivan.markmanager;

/**
 * Created by Ivan on 06.03.2018.
 */

public class UserAuthClass {
    private static String token;
    private static String id;

    public UserAuthClass() {
        ;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        UserAuthClass.token = token;
    }

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        UserAuthClass.id = id;
    }
}
