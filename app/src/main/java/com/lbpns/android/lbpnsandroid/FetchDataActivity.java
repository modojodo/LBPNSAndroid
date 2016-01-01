package com.lbpns.android.lbpnsandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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
import java.util.concurrent.ExecutionException;

public class FetchDataActivity extends AppCompatActivity {
    private final Context _this = this;
    private final static String TAG = "FetchDataActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_data);


        boolean connectecd = Connectivity.isConnectedToInternet(_this);
        if (connectecd) {

            ServerRequestTask fetchTask = new ServerRequestTask(new ServerRequestTask.TaskHandler() {
                @Override
                public JSONArray taskWithJSONArray() {
                    try {
                        URL url = new URL("http://192.168.0.101:3000/fetchDeals");
                        ServerCommunication server = new ServerCommunication(_this);
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
                JSONArray fetchedDeals =(JSONArray) fetchTask.execute("jsonarray").get();
                System.out.println("Fetched Deals");
                System.out.println(fetchedDeals);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }


        } else {
            Log.d("Inside onLogin else", TAG);
            Connectivity.noInternetToast(_this);
        }

    }
}
