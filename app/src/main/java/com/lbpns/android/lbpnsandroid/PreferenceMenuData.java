package com.lbpns.android.lbpnsandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Asad 15R on 1/6/2016.
 */
public class PreferenceMenuData extends Activity {

    static ArrayList<String> listCuisine;
    static ArrayList<String> listRestaurant;

    private Button btnDeal;
    static String listCuisineS[],listRestaurantS[];
    static String[] name = new String[]{"Zinger", "Burger", "Pizza", "Kabab", "Katakat", "Karahi", "Handi", "Biryani", "Sandwich", "Icecream"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);


        btnDeal = (Button) findViewById(R.id.btnDeal);

        ServerRequestTask fetchTask = new ServerRequestTask(PreferenceMenuData.this,new ServerRequestTask.TaskHandler() {
            @Override
            public JSONArray taskWithJSONArray() {
                try {
                    URL url = new URL("http://192.168.1.10:3000/fetchDeals");
                    ServerCommunication server = new ServerCommunication(getApplicationContext());
                    return server.getRequest(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void onTaskCompletion(JSONArray jsonArray) {

            }

            @Override
            public boolean taskWithBoolean() {
                return false;
            }
        });
        try {

            listCuisine = new ArrayList<String>();
            listRestaurant = new ArrayList<String>();

            JSONArray fetchedDeals = (JSONArray) fetchTask.execute("jsonarray").get();

            if (fetchedDeals != null) {
                int len = fetchedDeals.length();
                for (int i = 0; i < len; i++) {
                    listRestaurant.add(fetchedDeals.getJSONObject(i).getString("restaurants"));

                   // listCuisine.add(fetchedDeals.getJSONObject(i).getString("restaurants"));

                    JSONArray jsonArray = null;
                    jsonArray = fetchedDeals.getJSONArray(0);

                    JSONObject jsonObject = null;
                    jsonObject = jsonArray.getJSONObject(i);
               //    listCuisineS[i] = jsonArray.getString(i);
                    listCuisine.add(fetchedDeals.getJSONArray(i).getString(i));

//                    listCuisine.add(fetchedDeals.getJSONObject(i).getString("cuisi"));
                   // listCuisine.add(fetchedDeals.getJSONArray("cuisine");


                   // jsonArray = jsonObject.getJSONArray("cuisine");

//                    listRestaurant.add(fetchedDeals.getJSONObject(i).getString("dealContent"));
                }

            }

            // listContent.add(fetchedDeals.getJSONObject(1).getString("dealContent"));

//            System.out.println(fetchedDeals.getJSONObject(1).getString("dealContent"));

            listCuisineS = new String[listCuisine.size()];
            listCuisineS = listCuisine.toArray(listCuisineS);

            listRestaurantS = new String[listRestaurant.size()];
            listRestaurantS = listRestaurant.toArray(listRestaurantS);

//            System.out.println(list.get(1));
//
//            setListAdapter(new ArrayAdapter<String>(
//                    this,
//                    android.R.layout.simple_expandable_list_item_1,
//                    list));



        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
