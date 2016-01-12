package com.lbpns.android.lbpnsandroid;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FetchDataActivity extends ListActivity {
    private final Context _this = this;
    private final static String TAG = "FetchDataActivity";
    ArrayList<String> list;
    TextView selection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listviewexampleactivity);

        selection = (TextView) findViewById(R.id.selection);

      //  ListView listview = (ListView) findViewById(R.id.listview);

//        setListAdapter(new ArrayAdapter<String>(
//                this,
//                android.R.layout.simple_expandable_list_item_1,
//                new String[]{"Test","test","test"}));


        boolean connectecd = Connectivity.isConnectedToInternet(_this);
        if (connectecd) {

            ServerRequestTask fetchTask = new ServerRequestTask(FetchDataActivity.this,new ServerRequestTask.TaskHandler() {
                @Override
                public JSONArray taskWithJSONArray() {
                    try {
                        URL url = new URL("http://192.168.0.107:3000/fetchDeals");
                        ServerCommunication server = new ServerCommunication(_this);
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
            try {
                list = new ArrayList<String>();
                JSONArray fetchedDeals =(JSONArray) fetchTask.execute("jsonarray").get();

                if(fetchedDeals != null)
                {
                    int len = fetchedDeals.length();
                    for(int i=0;i<len;i++){
                        list.add(fetchedDeals.getJSONObject(i).getString("dealTitle"));
                    }

                }

                Toast.makeText(this,list.get(0),Toast.LENGTH_LONG);

                System.out.println(list.get(1));

                setListAdapter(new ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_expandable_list_item_1,
                        list));

             //     ArrayAdapter apt = new ArrayAdapter<>(this,R.layout.activity_listviewexampleactivity,new String[]{"Test","test","test"});
               //   listview.setAdapter(apt);

//                System.out.println("Fetched Deals");
//                System.out.println(fetchedDeals);


            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        } else {
            Log.d("Inside onLogin else", TAG);
            Connectivity.noInternetToast(_this);
        }

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String text = " position:" + position + "  " + list.get(0);
        selection.setText(text);
    }
}
