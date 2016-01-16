package com.lbpns.android.lbpnsandroid;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Created by Asad 15R on 1/8/2016.
 */
public class MainMenu extends Activity {

    private Button btnp,btnm,btns;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);


        btnp = (Button) findViewById(R.id.btn_preferences);
        btnm = (Button) findViewById(R.id.btn_maps);






//Set Preferences
        btnp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this,PreferenceActivity.class);
                startActivity(intent);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                overridePendingTransition(0,0);


            }
        });

        //Maps

        btnm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences myPrefs = getSharedPreferences("Notify", MODE_WORLD_READABLE);
                String key = myPrefs.getString(PreferenceActivity.checkKey, null);

                if(key!=null) {
                    Intent intent = new Intent(MainMenu.this, MapActivity.class);
                    startActivity(intent);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    overridePendingTransition(0, 0);
                }else{
                    Toast.makeText(getApplicationContext(),"Update Preferences to view Restaurants",Toast.LENGTH_LONG).show();
                }
            }
        });

//Settings



    }
}
