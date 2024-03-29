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
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.concurrent.ExecutionException;

public class SplashActivity extends AppCompatActivity {
    private Context _this = this;
    boolean bool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView rotate_image =(ImageView) findViewById(R.id.image1);
        RotateAnimation rotate = new RotateAnimation(30, 360, Animation.RELATIVE_TO_PARENT, 0.5f,  Animation.RELATIVE_TO_PARENT, 0.5f);
        rotate.setDuration(2500);
        rotate_image.startAnimation(rotate);
        boolean connectecd = Connectivity.isConnectedToInternet(_this);
        if (connectecd) {
            ServerRequestTask authenticateTask = new ServerRequestTask(SplashActivity.this,new ServerRequestTask.TaskHandler() {
                @Override
                public Object task() {
                    ServerCommunication server = new ServerCommunication(_this);
                    return server.authenticate();
                }


                @Override
                public void onTaskCompletion(Object o) {
                    bool = (boolean) o;
                    boolean authenticated = bool;
                    if (authenticated) {
                        Intent homeActivity = new Intent(_this, MainMenu.class);
                        startActivity(homeActivity);
                    } else {
                        Intent mainActivity = new Intent(_this, MainActivity.class);
                        startActivity(mainActivity);
                    }
                }


            });

            authenticateTask.execute();

//            try {
//                boolean authenticated = (boolean) authenticateTask.execute("boolean").get();
//                  boolean authenticated = bool;
//                if (authenticated) {
//                    Intent homeActivity = new Intent(_this, MainMenu.class);
//                    startActivity(homeActivity);
//                } else {
//                    Intent mainActivity = new Intent(_this, MainActivity.class);
//                    startActivity(mainActivity);
//                }

//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
        } else {
            Connectivity.noInternetToast(_this);
        }
    }

}
