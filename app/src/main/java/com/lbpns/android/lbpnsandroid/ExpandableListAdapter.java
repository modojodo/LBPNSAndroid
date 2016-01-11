package com.lbpns.android.lbpnsandroid;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Asad 15R on 12/16/2015.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private static final double LATITUDE = 24.909898;
    private static final double LONGITUDE = 67.085690;
    private static final float RADIUS = 500;
    private static final int request = 5;
    private static final long EXPIRATION_TIME = -1;
    private static final String TAG = "ExpandableListAdapter";
    private Context context;
    private List<String> listHeader;
    // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> listChild;
    private boolean checkBoxValue;

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData) {
        this.context = context;
        this.listHeader = listDataHeader;
        this.listChild = listChildData;
    }

    private String titleSelected[];
    private String contentSelected[];

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listChild.get(this.listHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);
        final String headerTitle = (String) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        final CheckedTextView txtListChild = (CheckedTextView) convertView
                .findViewById(R.id.lblListItem);


        txtListChild.setText(childText);


        loadSavedPreferences(headerTitle + childText);
        if (checkBoxValue) {
            txtListChild.setChecked(true);

        } else {
            txtListChild.setChecked(false);

        }


        txtListChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtListChild.isChecked()) {
                    txtListChild.setChecked(false);
                    savePreferences(headerTitle + childText, false);

                    // Toast.makeText(context,childText + " unselected!",Toast.LENGTH_SHORT).show();
                    removeProximityAlert(childPosition + request);

                } else {


                    System.out.println("Inside not checked");

                    txtListChild.setChecked(true);
//                    Toast.makeText(context,childText + " Proximity Alert Added",Toast.LENGTH_SHORT).show();
                    savePreferences(headerTitle + childText, true);

//                    JSONArray jsonArray = null;
//                    JSONObject jsonObject = null;
//                    for(int i=0;i<RestaurantFragment.listTitleS.length;i++) {
//
//                        for (int x = 0; x < RestaurantFragment.listTitleS.length; x++) {
//                            if (headerTitle.equals(RestaurantFragment.listTitleS[x])) {
////                                try {
//////                                    jsonObject.put("restaurant",headerTitle.toString());
//////                                } catch (JSONException e) {
//////                                    e.printStackTrace();
//////                                }
//                                Log.d("Check",headerTitle);
//                            }
//                        }
//
//                        for (int y = 0; y < RestaurantFragment.listContentS.length; y++) {
//                            if (childText.equals(RestaurantFragment.listContentS[y])) {
////                                try {
////                                    jsonObject.put("cuisine",childText.toString());
////                                } catch (JSONException e) {
////                                    e.printStackTrace();
////                                }
//                            }
//                        }
//                        jsonArray.put(jsonObject);
//                    }

                    //FOR CUISINE

                    Log.d(TAG, headerTitle.toString().trim());
                    Log.d(TAG, childText.toString().trim());
//                    Log.d("ASAD",jsonArray.toString());

                    if (headerTitle.equals("Burger") && txtListChild.equals("KFC")) {
                        double lat = 24.909898, lon = 67.085690;
                        addProximityAlert(childPosition + request, lat, lon);
                        // userPref.setSharedPref();

                    } else if (headerTitle.equals("Burger") && txtListChild.equals("McDonalds")) {
                        double lat = 24.798466, lon = 67.034419;
                        addProximityAlert(childPosition + request, lat, lon);
                    } else if (headerTitle.equals("Pizza") && childText.equals("Pizza Point")) {
                        double lat = 24.928619, lon = 67.112992;
                        addProximityAlert(childPosition + request, lat, lon);
                    } else if (headerTitle.equals("Sandwich") && childText.equals("McDonalds")) {
                        double lat = 24.798466, lon = 67.034419;
                        addProximityAlert(childPosition + request, lat, lon);
                    } else if (headerTitle.equals("Sandwich") && childText.equals("KFC")) {
                        double lat = 24.883264, lon = 67.161269;
                        addProximityAlert(childPosition + request, lat, lon);
                    } else if (listHeader.get(groupPosition).equals("KFC") && listChild.get(listHeader.get(groupPosition)).get(childPosition).equals("123a")) {
                        System.out.println("Inside KFC");
                        double lat = 24.909898, lon = 67.085690;
                        addProximityAlert(childPosition + request, lat, lon);
                    } else if (headerTitle.equals("KFC") && childText.equals("Juice")) {
                        double lat = 24.909898, lon = 67.085690;
                        addProximityAlert(childPosition + request, lat, lon);

                    } else if (listHeader.toString().equals("KFC") && childText.equals("Juice")) {
                        double lat = 24.909898, lon = 67.085690;
                        addProximityAlert(childPosition + request, lat, lon);

                    } else if (headerTitle.equals("KFC") && childText.equals("Wings")) {
                        double lat = 24.909898, lon = 67.085690;
                        System.out.println("Inside KFC");
                        addProximityAlert(childPosition + request, lat, lon);

                    } else if (headerTitle.equals("Pizza Point") && childText.equals("Chicken Tikka")) {
                        double lat = 24.928619, lon = 67.112992;
                        addProximityAlert(childPosition + request, lat, lon);

                    } else if (headerTitle.equals("Pizza Point") && childText.equals("Fajita")) {
                        double lat = 24.928619, lon = 67.112992;
                        addProximityAlert(childPosition + request, lat, lon);

                    } else if (headerTitle.equals("Pizza Point") && childText.equals("Afghani Tikka")) {
                        double lat = 24.928619, lon = 67.112992;
                        addProximityAlert(childPosition + request, lat, lon);

                    } else if (headerTitle.equals("Pizza Point") && childText.equals("Garlic Bread")) {
                        double lat = 24.928619, lon = 67.112992;
                        addProximityAlert(childPosition + request, lat, lon);

                    } else if (headerTitle.equals("McDonalds") && childText.equals("Chicken Burger")) {
                        double lat = 24.798466, lon = 67.034419;
                        addProximityAlert(childPosition + request, lat, lon);

                    } else if (headerTitle.equals("McDonalds") && childText.equals("Jumbo Zinger")) {
                        double lat = 24.909898, lon = 67.085690;
                        addProximityAlert(childPosition + request, lat, lon);

                    } else if (headerTitle.equals("McDonalds") && childText.equals("Club Sandwich")) {
                        double lat = 24.909898, lon = 67.085690;
                        addProximityAlert(childPosition + request, lat, lon);

                    }
                }


            }
        });


        return convertView;
    }

    private void removeProximityAlert(int requestCode) {
        LocationManager lManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Intent intent = new Intent("com.lbpns.android.lbpnsandroid");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), requestCode, intent, 0);
        if (ContextCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            lManager.removeProximityAlert(pendingIntent);
            Toast.makeText(context.getApplicationContext(), "Proximity Alert Removed!", Toast.LENGTH_LONG).show();

        }
    }

    private void addProximityAlert(int requestCode, double lat, double lon) {
        Log.d(TAG, "addProximityAlert called!");
        LocationManager lManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Intent intent = new Intent("com.lbpns.android.lbpnsandroid");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), requestCode, intent, 0);

        if (lManager != null) {
            if (ContextCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                lManager.addProximityAlert(lat, lon, RADIUS, EXPIRATION_TIME, pendingIntent);
                Toast.makeText(context.getApplicationContext(), "Proximity Alert Added!", Toast.LENGTH_LONG).show();

            }
        }

    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listChild.get(this.listHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    private void savePreferences(String key, boolean value) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(key, value);

        editor.commit();

    }

    private void loadSavedPreferences(String key) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        checkBoxValue = sharedPreferences.getBoolean(key, false);


    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        return convertView;
    }



    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}