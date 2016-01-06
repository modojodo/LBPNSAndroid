package com.lbpns.android.lbpnsandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Asad 15R on 1/6/2016.
 */
public class SplashScreen extends Activity {

    private static ArrayList<String> listTitle;
    private static ArrayList<String> listContent;

    static String listTitleS[], listContentS[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        ServerRequestTask fetchTask = new ServerRequestTask(new ServerRequestTask.TaskHandler() {
            @Override
            public JSONArray taskWithJSONArray() {
                try {
                    URL url = new URL("http://192.168.1.2:3000/fetchDeals");
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

            int secondsDelayed = 2;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    startActivity(new Intent(SplashScreen.this, DealActivity.class));
                    finish();
                }
            }, secondsDelayed * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
