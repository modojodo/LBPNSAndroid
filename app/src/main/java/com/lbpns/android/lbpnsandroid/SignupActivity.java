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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class SignupActivity extends Activity {
    private static final String TAG = "SignupActivity";
    private Button mSignupBtn;
    private EditText signupEmailEdt;
    private EditText signupPasswordEdt;
    private final Context _this = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mSignupBtn = (Button) findViewById(R.id.signupBtn);
        signupEmailEdt = (EditText) findViewById(R.id.signupEmailText);
        signupPasswordEdt = (EditText) findViewById(R.id.signupPasswordText);
        mSignupBtn.setOnClickListener(new View.OnClickListener() {
            String email, password;

            @Override
            public void onClick(View v) {
                Log.d("Signup button pressed", TAG);
                email = signupEmailEdt.getText().toString().trim();
                password = signupPasswordEdt.getText().toString().trim();
                boolean connectecd = Connectivity.isConnectedToInternet(_this);
                if (connectecd) {
                    ServerRequestTask loginTask = new ServerRequestTask(new ServerRequestTask.TaskHandler() {
                        @Override
                        public boolean task() {
                            ServerCommunication server = new ServerCommunication(_this);
                            return server.signup(email, password);
                        }
                    });
                    try {
                        boolean signedUp = loginTask.execute().get();
                        if (signedUp) {
                            Intent homeActivity = new Intent(v.getContext(), HomeActivity.class);
                            startActivity(homeActivity);
                        } else {
                            Toast.makeText(_this, "User already exists", Toast.LENGTH_SHORT);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d("Inside onSignup else", TAG);
                    Connectivity.noInternetToast(_this);
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }
}
