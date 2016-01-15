package com.lbpns.android.lbpnsandroid;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
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
import java.util.List;

/**
 * Created by Asad 15R on 1/14/2016.
 */
public class PreferenceActivity extends Activity {
    private final String TAG = "PreferenceActivity";
    RestaurantFragment restaurantFragment;
    CuisineFragment cuisineFragment;
    Button btnUpdate;
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
                        JSONObject response = (JSONObject) o;
                        JSONArray responseArr;
//                        Log.d(TAG, response.toString());
                        try {
                            responseArr = response.getJSONArray("deals");
                            JSONObject obj =responseArr.getJSONObject(0);
                            String str =obj.getString("dealTitle");
                            Log.d(TAG,obj.toString());
                            Log.d(TAG,str);

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


}
