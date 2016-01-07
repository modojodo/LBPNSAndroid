package com.lbpns.android.lbpnsandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends Activity {
    private static final String TAG = "LoginActivity";
    Context _this = this;
    private Button loginBtn;
    private EditText loginEmailEdt;
    private EditText loginPasswordEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        loginEmailEdt = (EditText) findViewById(R.id.loginEmailText);
        loginPasswordEdt = (EditText) findViewById(R.id.loginPasswordText);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            String email, password;

            @Override
            public void onClick(View v) {
                Log.d(TAG, "onLogin pressed");
                InputValidation.removeAllErrors(loginEmailEdt, loginPasswordEdt);
                boolean connectecd = Connectivity.isConnectedToInternet(_this);
                if (connectecd) {
                    email = loginEmailEdt.getText().toString().trim();
                    password = loginPasswordEdt.getText().toString().trim();
                    if (email.isEmpty()) {
                        InputValidation.errFieldEmpty(loginEmailEdt);
                    } else if (!InputValidation.isValidEmail(email)) {
                        InputValidation.errInvalidEmail(loginEmailEdt);
                    } else if (password.isEmpty()) {
                        InputValidation.errFieldEmpty(loginPasswordEdt);
                    } else if (!InputValidation.isValidPassword(password)) {
                        InputValidation.errInvalidPassword(loginPasswordEdt);
                    } else {
                        ServerRequestTask loginTask = new ServerRequestTask(new ServerRequestTask.TaskHandler() {
                            @Override
                            public boolean taskWithBoolean() {
                                ServerCommunication server = new ServerCommunication(_this);
                                return server.login(email, password);
                            }

                            @Override
                            public JSONArray taskWithJSONArray() {
                                return null;
                            }
                        });
                        try {
                            boolean loggedIn = (boolean) loginTask.execute("boolean").get();
                            if (loggedIn) {
                                Intent homeActivity = new Intent(v.getContext(), HomeActivity.class);
                                startActivity(homeActivity);
                            } else {
                                Toast.makeText(_this, "Invalid Login!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }


                } else {
                    Log.d("Inside onLogin else", TAG);
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
