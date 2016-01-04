package com.lbpns.android.lbpnsandroid;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Asad 15R on 1/4/2016.
 */
public class DealData extends Activity {


    private static ArrayList<String> listTitle;
    private static ArrayList<String> listContent;

    private Button btnDeal;
    static String listTitleS[],listContentS[];
    static String[] name = new String[]{"Zinger", "Burger", "Pizza", "Kabab", "Katakat", "Karahi", "Handi", "Biryani", "Sandwich", "Icecream"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashtemp);


        btnDeal = (Button) findViewById(R.id.btnDeal);

        ServerRequestTask fetchTask = new ServerRequestTask(new ServerRequestTask.TaskHandler() {
            @Override
            public JSONArray taskWithJSONArray() {
                try {
                    URL url = new URL("http://192.168.0.102:3000/fetchDeals");
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
            listContent = new ArrayList<String>();

            JSONArray fetchedDeals = (JSONArray) fetchTask.execute("jsonarray").get();

            if (fetchedDeals != null) {
                int len = fetchedDeals.length();
                for (int i = 0; i < len; i++) {
                    listTitle.add(fetchedDeals.getJSONObject(i).getString("dealTitle"));
                    listContent.add(fetchedDeals.getJSONObject(i).getString("dealContent"));
                }

            }

           // listContent.add(fetchedDeals.getJSONObject(1).getString("dealContent"));

//            System.out.println(fetchedDeals.getJSONObject(1).getString("dealContent"));

            listTitleS = new String[listTitle.size()];
            listTitleS = listTitle.toArray(listTitleS);

            listContentS = new String[listContent.size()];
            listContentS = listContent.toArray(listContentS);

//            System.out.println(list.get(1));
//
//            setListAdapter(new ArrayAdapter<String>(
//                    this,
//                    android.R.layout.simple_expandable_list_item_1,
//                    list));

            btnDeal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(),DealActivity.class);
                    startActivity(i);
                }
            });

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
