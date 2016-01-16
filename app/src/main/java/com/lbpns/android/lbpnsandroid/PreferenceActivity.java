package com.lbpns.android.lbpnsandroid;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Asad 15R on 1/14/2016.
 */
public class PreferenceActivity extends Activity {
    private final String TAG = "PreferenceActivity";
    RestaurantFragment restaurantFragment;
    CuisineFragment cuisineFragment;
    final float RADIUS = 1000;
    private static final long EXPIRATION_TIME = -1;
    Button btnUpdate;
    Context context;
    static String keyD, valueDeals;
    JSONArray responseArr;
    String choice = null;
    URL url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        btnUpdate = (Button) findViewById(R.id.btn_update);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<>();
        list.add("Restaurant");
        list.add("Cuisine");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (choice) {
                    case "cuisine":
                        try {
                            url = new URL(ServerCommunication.GET_USER_PREFERENCE_DEALS_BY_CUISINE);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "restaurant":
                        try {
                            url = new URL(ServerCommunication.GET_USER_PREFERENCE_DEALS_BY_RESTAURANT);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                Toast.makeText(getApplicationContext(), choice, Toast.LENGTH_SHORT).show();
//
                ServerRequestTask task = new ServerRequestTask(PreferenceActivity.this, new ServerRequestTask.TaskHandler() {
                    @Override
                    public Object task() {
                        String urlParameters = "";
                        if (ExpandableListAdapter.userPref.length() <= 0) {
                            Log.d(TAG, "inside length if");
//                            Toast.makeText(PreferenceActivity.this, "Please select atleast one preference!", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d(TAG, "inside length else");
                            try {
                                urlParameters = String.format("preferences=%s", URLEncoder.encode(ExpandableListAdapter.userPref.toString(), ServerCommunication.CHARSET));
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            return ServerCommunication.postRequest(urlParameters, url);
                        }
                        return null;
                    }

                    @Override
                    public void onTaskCompletion(Object o) {
                        JSONObject jsonObject = (JSONObject) o;
                        JSONArray response = null;
                        try {
                            response = jsonObject.getJSONArray("deals");

//                        Log.d(TAG, response.toString());
                            if (response != null) {
                                Log.d(TAG, "inside null if");

//                            responseArr = response.getJSONArray("deals");
                                String key = null, value = null;
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject obj = response.getJSONObject(i);
                                    Iterator<String> keys = obj.keys();

                                    if (keys.hasNext()) {
                                        key = (String) keys.next(); // First key in our json object
                                        value = obj.getJSONArray(key).toString();
                                    }
                                    if (key != null && value != null) {
                                        Log.d(TAG, "inside null if key , value");
                                        Log.d(TAG, key);
                                        Log.d(TAG, value);
//                                    UserPref userPref = new UserPref();
                                        setUserNotificationPref(key, value);
                                        String location[] = key.split(",");
                                        double lat = Double.parseDouble(location[0]);
                                        double lng = Double.parseDouble(location[1]);
//                                        System.out.println(" " + lat);
//                                        System.out.println(" " + lng);
                                        JSONArray dealsArr = new JSONArray(value);
                                        int rand = (int) (Math.random() * (dealsArr.length()));
                                        JSONObject firstDeal = dealsArr.getJSONObject(rand);
                                        boolean maxDeals;
                                        if (value.length() >= 1) {
                                            maxDeals = true;
                                        } else {
                                            maxDeals = false;
                                        }
                                        String dealTitle = firstDeal.getString("dealTitle");
                                        String dealContent;
                                        if (firstDeal.has("dealContent")) {
                                            dealContent = firstDeal.getString("dealContent");
                                        } else {
                                            dealContent = null;
                                        }
                                        String price = firstDeal.getString("price");
                                        addProximityAlert((int) System.currentTimeMillis(), lat, lng, maxDeals, dealTitle, dealContent, price, dealsArr.length(),value);

                                    }

                                }



                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        Log.d(TAG, (String) o.toString());
                    }
                });
                task.execute();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    choice = "restaurant";
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    restaurantFragment = new RestaurantFragment();
//                fragmentTransaction.add(R.id.fragment_container, restaurantFragment, "restaurant");


                    //                fragmentTransaction.add(R.id.fragment_container, restaurantFragment, "restaurant");
                    fragmentTransaction.replace(R.id.fragment_container, restaurantFragment);


                    fragmentTransaction.commit();
                } else {
                    choice = "cuisine";
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    cuisineFragment = new CuisineFragment();
//                fragmentTransaction.add(R.id.fragment_container, cuisineFragment, "cuisine");

//                fragmentTransaction.add(R.id.fragment_container, cuisineFragment, "cuisine");
                    fragmentTransaction.replace(R.id.fragment_container, cuisineFragment);
                    fragmentTransaction.commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void addProximityAlert(int requestCode, double lat, double lon, boolean max, String title, String content, String price, int moreDealsLength,String value) {
        Log.d(TAG, "addProximityAlert called!");
        LocationManager lManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Intent intent = new Intent("com.lbpns.android.lbpnsandroid");
        intent.putExtra("dealTitle", title);
        intent.putExtra("value",value);
        intent.putExtra("dealContent", content);
        intent.putExtra("moreDealsLength", moreDealsLength);
        intent.putExtra("price", price);
        intent.putExtra("max", max);
        intent.putExtra("lat",lat);
        intent.putExtra("lon",lon);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), requestCode, intent, 0);

        if (lManager != null) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                lManager.addProximityAlert(lat, lon, RADIUS, EXPIRATION_TIME, pendingIntent);
                Toast.makeText(getApplicationContext(), "Proximity Alert Added!", Toast.LENGTH_LONG).show();

            }
        }
    }

    void setUserNotificationPref(String key, String value) {
        SharedPreferences userNotify = getSharedPreferences("Notify", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userNotify.edit();

        editor.putString(key, value);
        editor.commit();
    }


}
