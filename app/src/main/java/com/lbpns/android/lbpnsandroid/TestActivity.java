package com.lbpns.android.lbpnsandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Asad 15R on 1/15/2016.
 */
public class TestActivity extends Activity {

   private TextView txtViewDealsData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Intent intent = getIntent();

        String data[] =  intent.getStringArrayExtra("dealsData");

        txtViewDealsData = (TextView) findViewById(R.id.txtViewDealsData);

        for(int i=0;i<data.length;i++) {
            txtViewDealsData.append(data[0]);
        }




    }
}
