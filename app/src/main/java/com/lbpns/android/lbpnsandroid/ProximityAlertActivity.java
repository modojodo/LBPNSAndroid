package com.lbpns.android.lbpnsandroid;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

/**
 * Created by Asad 15R on 1/14/2016.
 */
public class ProximityAlertActivity {


    Context context;
    int requestCode =0;

    LocationManager locManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    Intent intent = new Intent("com.lbpns.android.lbpnsandroid");
    PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), requestCode, intent, 0);



    }

