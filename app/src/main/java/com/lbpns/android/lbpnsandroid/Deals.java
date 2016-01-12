package com.lbpns.android.lbpnsandroid;

import android.app.Activity;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Asad 15R on 1/2/2016.
 */
public class Deals extends Activity {

    static ArrayList<String> listRestaurantByRestaurant;
    static ArrayList<ArrayList<String>> listCuisineByRestaurant;

    static ArrayList<String> listCuisineByCuisine;
    static ArrayList<ArrayList<String>> listRestaurantByCuisine;

    static String listRestaurantByRestaurantS[], listCuisineByRestaurantS[];
    static String listCuisineByCuisineS[], listRestaurantyCuisineS[];

    static ArrayList<String> individualRestaurantCuisines;
    static ArrayList<String> individualCuisineRestaurants;

    ServerRequestTask fetchTask;
    private Context context;

    // final Context context = null;

    void makerServerRequest(final Context context, final String path) {
        fetchTask = new ServerRequestTask(Deals.this,new ServerRequestTask.TaskHandler() {
            @Override
            public JSONArray taskWithJSONArray() {
                try {
                    URL url = new URL("http://192.168.1.5:3000/" + path);
                    ServerCommunication server = new ServerCommunication(context);
                    return server.getRequest(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void onTaskCompletion(Object o) {

            }



            @Override
            public boolean taskWithBoolean() {
                return false;
            }
        });
    }

    void getPreferenceByRestaurant() {

        listRestaurantByRestaurant = new ArrayList<String>();
        listCuisineByRestaurant = new ArrayList<ArrayList<String>>();

        JSONArray fetchedDeals = null;
        try {
            fetchedDeals = (JSONArray) fetchTask.execute("jsonarray").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        if (fetchedDeals != null) {
            int len = fetchedDeals.length();

            for (int i = 0; i < len; i++) {


                JSONArray jsonArrayR = null;
                String rTitle = null;

                try {
                    jsonArrayR = fetchedDeals.getJSONObject(i).getJSONArray("restaurant");
                    rTitle = jsonArrayR.getString(0);
                    listRestaurantByRestaurant.add(rTitle);

                    JSONArray jsonArrayC = fetchedDeals.getJSONObject(i).getJSONArray("cuisines"); //cuisines
                    int cuisineLen = jsonArrayC.length();
                    individualRestaurantCuisines = new ArrayList<String>();
                    for (int j = 0; j < cuisineLen; j++) {
                        String cTitle = jsonArrayC.getString(j);
                        individualRestaurantCuisines.add(cTitle);

                        listCuisineByRestaurant.add(individualRestaurantCuisines);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                listRestaurantByRestaurantS = new String[listRestaurantByRestaurant.size()];
                listRestaurantByRestaurantS = listRestaurantByRestaurant.toArray(listRestaurantByRestaurantS);

//        listContentS = new String[individualRestaurantCuisines.size()];
//        listContentS = individualRestaurantCuisines.toArray(listContentS);


            }
        }
    }

    void getPreferencesByCuisine() {

        listCuisineByCuisine = new ArrayList<String>();
        listRestaurantByCuisine = new ArrayList<ArrayList<String>>();

        JSONArray fetchedDeals = null;
        try {
            fetchedDeals = (JSONArray) fetchTask.execute("jsonarray").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        if (fetchedDeals != null) {
            int len = fetchedDeals.length();

            for (int i = 0; i < len; i++) {


                JSONArray jsonArrayR = null;
                String rTitle = null;

                try {
                    jsonArrayR = fetchedDeals.getJSONObject(i).getJSONArray("restaurant");
                    rTitle = jsonArrayR.getString(0);
                    listCuisineByCuisine.add(rTitle);

                    JSONArray jsonArrayC = fetchedDeals.getJSONObject(i).getJSONArray("cuisines"); //cuisines
                    int cuisineLen = jsonArrayC.length();
                    individualCuisineRestaurants = new ArrayList<String>();
                    for (int j = 0; j < cuisineLen; j++) {
                        String cTitle = jsonArrayC.getString(j);
                        individualCuisineRestaurants.add(cTitle);

                        listRestaurantByCuisine.add(individualCuisineRestaurants);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                listCuisineByCuisineS = new String[listCuisineByCuisine.size()];
                listCuisineByCuisineS = listCuisineByCuisine.toArray(listCuisineByCuisineS);

//        listContentS = new String[individualRestaurantCuisines.size()];
//        listContentS = individualRestaurantCuisines.toArray(listContentS);


            }

        }
    }
}
