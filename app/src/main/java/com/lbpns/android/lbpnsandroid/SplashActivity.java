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

import java.util.concurrent.ExecutionException;

public class SplashActivity extends AppCompatActivity {
    private Context _this = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        boolean connectecd = Connectivity.isConnectedToInternet(_this);
        if (connectecd) {
            ServerRequestTask authenticateTask = new ServerRequestTask(new ServerRequestTask.TaskHandler() {
                @Override
                public boolean task() {
                    ServerCommunication server = new ServerCommunication(_this);
                    return server.authenticate();
                }
            });
            try {
                boolean authenticated = authenticateTask.execute().get();
                if (authenticated) {
                    Intent homeActivity = new Intent(_this, HomeActivity.class);
                    startActivity(homeActivity);
                } else {
                    Intent mainActivity = new Intent(_this, MainActivity.class);
                    startActivity(mainActivity);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            Connectivity.noInternetToast(_this);
        }
    }

}
