package com.lbpns.android.lbpnsandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Asad 15R on 1/8/2016.
 */
public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SettingsActivity.this,MainMenu.class);
        startActivity(intent);
    }
}
