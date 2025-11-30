package com.ulbra.achadoseperdidos;

import android.content.Context;
import android.content.SharedPreferences;

public class UsuarioSession {
    private static final String PREF_NAME = "AppPrefs";
    private static final String KEY_LOGGED_IN = "isLoggedIn";

    public static boolean isLoggedIn(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(KEY_LOGGED_IN, false);
    }

    public static void login(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(KEY_LOGGED_IN, true).apply();
    }

    public static void logout(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(KEY_LOGGED_IN, false).apply();
    }
}

