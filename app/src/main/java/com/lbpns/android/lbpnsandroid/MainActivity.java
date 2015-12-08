package com.lbpns.android.lbpnsandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    private Context _this = this;
    private Button mLoginScreenBtn;
    private Button mSignupScreenBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoginScreenBtn = (Button) findViewById(R.id.loginScreenBtn);
        mSignupScreenBtn = (Button) findViewById(R.id.signupScreenBtn);



        mLoginScreenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginActivity = new Intent(v.getContext(), LoginActivity.class);
                startActivity(loginActivity);
            }
        });
        mSignupScreenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupActivityy = new Intent(v.getContext(), SignupActivity.class);
                startActivity(signupActivityy);
            }
        });
//        if (Connectivity.isConnectedToInternet(_this)) {
//
//        } else {
//            Toast.makeText(MainActivity.this, R.string.network_not_connected_toast, Toast.LENGTH_SHORT).show();
//        }
    }
}
