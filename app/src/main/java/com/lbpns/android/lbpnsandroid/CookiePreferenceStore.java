package com.lbpns.android.lbpnsandroid;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Umer on 12/8/2015.
 */
public class CookiePreferenceStore {
    private static final String FILE_NAME = "Cookie";
    private static final String KEY = "connect.sid";

    public static void saveValue(Context context, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY, value);
        editor.commit();
    }

    public static String getValue(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(KEY, null);
        return value;
    }
    public static void removeValue(Context context) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.remove(KEY);
        editor.commit();
    }
}
