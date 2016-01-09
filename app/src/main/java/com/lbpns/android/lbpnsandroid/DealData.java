package com.lbpns.android.lbpnsandroid;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Asad 15R on 1/4/2016.
 */
public class DealData extends Activity {


    static ArrayList<String> listTitle;
    static ArrayList<List<String>> listContent;



    private Button btnDeal;
    static String listTitleS[], listContentS[];
    static List<String> individualRestaurantCuisines;
//    static String[] name = new String[]{"Zinger", "Burger", "Pizza", "Kabab", "Katakat", "Karahi", "Handi", "Biryani", "Sandwich", "Icecream"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashtemp);


        btnDeal = (Button) findViewById(R.id.btnDeal);
        btnDeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DealData.this, MainMenu.class);
                startActivity(i);
            }
        });

        ServerRequestTask fetchTask = new ServerRequestTask(new ServerRequestTask.TaskHandler() {
            @Override
            public JSONArray taskWithJSONArray() {
                try {
                    URL url = new URL("http://192.168.1.5:3000/getPreferencesByRestaurant");
                    ServerCommunication server = new ServerCommunication(getApplicationContext());
                    return server.getRequest(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public boolean taskWithBoolean() {
                return false;
            }
        });
        try {

            listTitle = new ArrayList<String>();
            listContent = new ArrayList<List<String>>();

            JSONArray fetchedDeals = (JSONArray) fetchTask.execute("jsonarray").get();
//            String s = fetchedDeals.toString();

            JSONArray jsonArray = new JSONArray();
            System.out.println(fetchedDeals);
            if (fetchedDeals != null) {
                int len = fetchedDeals.length();

                for (int i = 0; i < len; i++) {





                    JSONArray jsonArrayR = fetchedDeals.getJSONObject(i).getJSONArray("restaurant");



                    String rTitle = jsonArrayR.getString(0);


                    listTitle.add(rTitle);


                    JSONArray jsonArrayC = fetchedDeals.getJSONObject(i).getJSONArray("cuisines"); //cuisines
                    int cuisineLen = jsonArrayC.length();
                    individualRestaurantCuisines = new ArrayList<String>();
                    for (int j = 0; j < cuisineLen; j++) {
                        String cTitle = jsonArrayC.getString(j);
                        individualRestaurantCuisines.add(cTitle);
                    }
//                    String strArr[] =  new String[individualRestaurantCuisines.size()];
//                    strArr = individualRestaurantCuisines.toArray(strArr);
                    listContent.add(individualRestaurantCuisines);

//                    listContent.add(fetchedDeals.getJSONObject(i).getString("dealContent"));
                }


//                for (int i = 0; i < 1; i++) {
//
//                    for (int j = 0; j < fetchedDeals.getJSONObject(i).getJSONArray("cuisines").length(); j++) {
//
//                        JSONArray jsonArrayC = fetchedDeals.getJSONObject(i).getJSONArray("cuisines");
//                        String cTitle = jsonArrayC.getString(j);
//                        listContent.add(cTitle);
//
//                    }
//
////                    listContent.add(fetchedDeals.getJSONObject(i).getString("dealContent"));
//
//
//                }

            }



            listTitleS = new String[listTitle.size()];
            listTitleS = listTitle.toArray(listTitleS);

            listContentS = new String[individualRestaurantCuisines.size()];
            listContentS = individualRestaurantCuisines.toArray(listContentS);

//            System.out.println(list.get(1));
//
//            setListAdapter(new ArrayAdapter<String>(
//                    this,
//                    android.R.layout.simple_expandable_list_item_1,
//                    list));


//                    Intent i = new Intent(getApplicationContext(),DealActivity.class);
//                    startActivity(i);


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
