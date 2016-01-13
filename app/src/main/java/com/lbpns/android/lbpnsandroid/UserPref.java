package com.lbpns.android.lbpnsandroid;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import java.util.Map;
import java.util.Set;

/**
 * Created by Asad 15R on 1/4/2016.
 */
public class UserPref extends Activity {

   private SharedPreferences sharedPref ;
   private static String PREF = "MyPref";
   private String Title,Content,Quantity,Branch,Latitude,Longitude;
   private int Price;
   private double lat,lon;



    void setSharedPref(String title,String content,int price,String quantity,String branch,double lat,double lon){

       sharedPref = getSharedPreferences(PREF, Context.MODE_PRIVATE);
       SharedPreferences.Editor editor = sharedPref.edit();

        String latitude,longitude;
        latitude = Double.toString(lat);
        longitude = Double.toString(lon);

        editor.putString("dealTitle", title);
        editor.putString("dealContent", content);
        editor.putInt("price", price);
        editor.putString("quantity",quantity);
        editor.putString("branch",branch);
        editor.putString("latitude",latitude);
        editor.putString("longitude",longitude);
        editor.commit();
    }

    void getSharedPref(){


        sharedPref = getSharedPreferences(PREF,Context.MODE_PRIVATE);

        Title = sharedPref.getString("dealTitle",null);
        Content = sharedPref.getString("dealContent",null);
        Price = sharedPref.getInt("price",0);
        Quantity = sharedPref.getString("quantity",null);
        Branch = sharedPref.getString("branch",null);
        Latitude = sharedPref.getString("latitude",null);
        Longitude = sharedPref.getString("longitude",null);

    }


}
