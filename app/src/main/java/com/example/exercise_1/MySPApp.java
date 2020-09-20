package com.example.exercise_1;

import android.content.Context;
import android.content.SharedPreferences;

public class MySPApp {

    private static MySPApp instance;
    private SharedPreferences prefs;

    public static MySPApp getInstance() {
        return instance;
    }

    private MySPApp(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences("MAIN_SP", Context.MODE_PRIVATE);
    }

    public static MySPApp initHelper(Context context) {
        if (instance == null)
            instance = new MySPApp(context);
        return instance;
    }

    public void putString(String key, String value) {
        prefs.edit().putString(key, value).apply();
    }

    public String getString(String key, String def) {
        return prefs.getString(key, def);
    }

    public void putBoolean(String key, boolean value) {
        prefs.edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key, boolean def) {
        return prefs.getBoolean(key, def);
    }

    public void putDouble(String KEY, double defValue) {
        putString(KEY, String.valueOf(defValue));
    }

    public double getDouble(String KEY, double defValue) {
        return Double.parseDouble(getString(KEY, String.valueOf(defValue)));
    }
}
