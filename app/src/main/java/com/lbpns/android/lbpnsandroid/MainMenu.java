package com.lbpns.android.lbpnsandroid;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * Created by Asad 15R on 1/8/2016.
 */
public class MainMenu extends Activity {

    private ImageView iv2,iv3,iv4,iv5;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);


        iv3 = (ImageView) findViewById(R.id.imageView3);
        iv4 = (ImageView) findViewById(R.id.imageView4);
        iv5 = (ImageView) findViewById(R.id.imageView5);






//Set Preferences
        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this,SelectionMenu.class);
                startActivity(intent);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                overridePendingTransition(0,0);


            }
        });

//Settings
        iv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this,SettingsActivity.class);
                startActivity(intent);
            }
        });

//Maps
        iv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this,MapActivity.class);
                startActivity(intent);
            }
        });
    }
}
