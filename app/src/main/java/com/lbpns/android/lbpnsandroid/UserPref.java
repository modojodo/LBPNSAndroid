package com.lbpns.android.lbpnsandroid;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Set;

/**
 * Created by Asad 15R on 1/4/2016.
 */
public class UserPref{

   private SharedPreferences sharedPref ;
   private static String PREF = "MyPref";
   private String Title,Content,Quantity,Branch,Latitude,Longitude;
   private int Price;
   private double lat,lon;
    private Context context;
    private JSONArray jSONArray;
    private JSONObject jSONObject;
    String keyD;




    void setUserNotificationPref(String key,String value)
    {

        SharedPreferences userNotify = context.getSharedPreferences("Notify", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userNotify.edit();

        editor.putString(key, value);
        editor.commit();
    }

    void getUserNotificationPref(String key)
    {
        SharedPreferences userNotify = context.getSharedPreferences("Notify", Context.MODE_PRIVATE);
        String location = userNotify.getString(key,null);
    }



}
