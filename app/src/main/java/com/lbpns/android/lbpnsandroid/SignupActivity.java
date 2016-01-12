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

import org.json.JSONArray;

import java.util.concurrent.ExecutionException;

public class SignupActivity extends Activity {
    private static final String TAG = "SignupActivity";
    private Button mSignupBtn;
    private EditText signupEmailEdt;
    private EditText signupPasswordEdt;
    private EditText signupConfPasswordEdt;
    private final Context _this = this;
    private boolean bool;
    ServerRequestTask loginTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mSignupBtn = (Button) findViewById(R.id.signupBtn);
        signupEmailEdt = (EditText) findViewById(R.id.signupEmailText);
        signupPasswordEdt = (EditText) findViewById(R.id.signupPasswordText);
        signupConfPasswordEdt = (EditText) findViewById(R.id.signupConfPasswordText);
        mSignupBtn.setOnClickListener(new View.OnClickListener() {
            String email, password, confPassword;

            @Override
            public void onClick(View v) {
                Log.d("Signup button pressed", TAG);

                boolean connectecd = Connectivity.isConnectedToInternet(_this);
                InputValidation.removeAllErrors(signupEmailEdt, signupPasswordEdt, signupConfPasswordEdt);
                if (connectecd) {
                    email = signupEmailEdt.getText().toString().trim();
                    password = signupPasswordEdt.getText().toString().trim();
                    confPassword = signupConfPasswordEdt.getText().toString().trim();
                    if (email.isEmpty()) {
                        InputValidation.errFieldEmpty(signupEmailEdt);
                    } else if (!InputValidation.isValidEmail(email)) {
                        InputValidation.errInvalidEmail(signupEmailEdt);
                    } else if (password.isEmpty()) {
                        InputValidation.errFieldEmpty(signupPasswordEdt);
                    } else if (!InputValidation.isValidPassword(password)) {
                        InputValidation.errInvalidPassword(signupPasswordEdt);
                    } else if (!InputValidation.isSamePassword(password, confPassword)) {
                        InputValidation.errNotSamePassword(signupPasswordEdt, signupConfPasswordEdt);
                    } else {


                        loginTask = new ServerRequestTask(SignupActivity.this,new ServerRequestTask.TaskHandler() {
                            @Override
                            public boolean taskWithBoolean() {
                                ServerCommunication server = new ServerCommunication(_this);
                                return server.signup(email, password);
                            }

                            @Override
                            public JSONArray taskWithJSONArray() {
                                return null;
                            }

                            @Override
                            public void onTaskCompletion(Object o) {
                                bool = (boolean) o;
                                boolean signedUp = bool;

                                if (signedUp) {
                                    System.out.println("signedUp: " + signedUp);
                                    Intent homeActivity = new Intent(SignupActivity.this, MainMenu.class);
                                    startActivity(homeActivity);
                                } else {
                                    System.out.println("signedUp: "+signedUp);
                                    Toast.makeText(_this, "User already exists", Toast.LENGTH_SHORT).show();
                                }

                            }

                        });

                        loginTask.execute("boolean");


//                            boolean signedUp = (boolean) loginTask.execute("boolean").get();
//                            boolean signedUp = bool;
//
//                            if (signedUp) {
//                                System.out.println("signedUp: " + signedUp);
//                                Intent homeActivity = new Intent(SignupActivity.this, HomeActivity.class);
//                                startActivity(homeActivity);
//                            } else {
//                                System.out.println("signedUp: "+signedUp);
//                                Toast.makeText(_this, "User already exists", Toast.LENGTH_SHORT).show();
//                            }

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
