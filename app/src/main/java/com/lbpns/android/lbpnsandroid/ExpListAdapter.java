package com.lbpns.android.lbpnsandroid;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Asad 15R on 12/12/2015.
 */
public class ExpListAdapter extends BaseExpandableListAdapter {

    private static final double LATITUDE = 24.909898;
    private static final double LONGITUDE = 67.085690;
    private static final float RADIUS = 1000;
    private static final int request = 5;
    private static final long EXPIRATION_TIME = -1;
    private Context context;
    private List<String> listHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> listChild;

    public ExpListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData) {
        this.context = context;
        this.listHeader = listDataHeader;
        this.listChild = listChildData;
    }

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
    public View getChildView(int groupPosition, final int childPosition,
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

        txtListChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtListChild.isChecked()) {
                    txtListChild.setChecked(false);
                    // Toast.makeText(context,childText + " unselected!",Toast.LENGTH_SHORT).show();
                    removeProximityAlert(childPosition + request);
                } else {
                    txtListChild.setChecked(true);
                    // Toast.makeText(context,childText + " Proximity Alert Added",Toast.LENGTH_SHORT).show();

                    //FOR CUISINE

                    if (headerTitle == "Burger" && childText == "KFC") {
                        double lat = 24.909898, lon = 67.085690;
                        addProximityAlert(childPosition + request, lat, lon);
                    } else if (headerTitle == "Burger" && childText == "McDonalds") {
                        double lat = 24.798466, lon = 67.034419;
                        addProximityAlert(childPosition + request, lat, lon);
                    } else if (headerTitle == "Pizza" && childText == "Pizza Point") {
                        double lat = 24.928619, lon = 67.112992;
                        addProximityAlert(childPosition + request, lat, lon);
                    } else if (headerTitle == "Sandwich" && childText == "McDonalds") {
                        double lat = 24.798466, lon = 67.034419;
                        addProximityAlert(childPosition + request, lat, lon);
                    } else if (headerTitle == "Sandwich" && childText == "KFC") {
                        double lat = 24.909898, lon = 67.085690;
                        addProximityAlert(childPosition + request, lat, lon);
                    }

                    //FOR RESTAURANT

                    if (headerTitle == "KFC" && childText == "Burger") {
                        double lat = 24.909898, lon = 67.085690;
                        addProximityAlert(childPosition + request, lat, lon);
                    } else if (headerTitle == "KFC" && childText == "Chicken Wings") {
                        double lat = 24.909898, lon = 67.085690;
                        addProximityAlert(childPosition + request, lat, lon);

                    } else if (headerTitle == "KFC" && childText == "Sandwich") {
                        double lat = 24.909898, lon = 67.085690;
                        addProximityAlert(childPosition + request, lat, lon);

                    } else if (headerTitle == "KFC" && childText == "Fajita Roll") {
                        double lat = 24.909898, lon = 67.085690;
                        addProximityAlert(childPosition + request, lat, lon);

                    } else if (headerTitle == "Pizza Point" && childText == "Chicken Tikka") {
                        double lat = 24.928619, lon = 67.112992;
                        addProximityAlert(childPosition + request, lat, lon);

                    } else if (headerTitle == "Pizza Point" && childText == "Fajita") {
                        double lat = 24.928619, lon = 67.112992;
                        addProximityAlert(childPosition + request, lat, lon);

                    } else if (headerTitle == "Pizza Point" && childText == "Afghani Tikka") {
                        double lat = 24.928619, lon = 67.112992;
                        addProximityAlert(childPosition + request, lat, lon);

                    } else if (headerTitle == "Pizza Point" && childText == "Garlic Bread") {
                        double lat = 24.928619, lon = 67.112992;
                        addProximityAlert(childPosition + request, lat, lon);

                    } else if (headerTitle == "McDonalds" && childText == "Chicken Burger") {
                        double lat = 24.798466, lon = 67.034419;
                        addProximityAlert(childPosition + request, lat, lon);

                    } else if (headerTitle == "McDonalds" && childText == "Jumbo Zinger") {
                        double lat = 24.909898, lon = 67.085690;
                        addProximityAlert(childPosition + request, lat, lon);

                    } else if (headerTitle == "McDonalds" && childText == "Club Sandwich") {
                        double lat = 24.909898, lon = 67.085690;
                        addProximityAlert(childPosition + request, lat, lon);

                    }else if (headerTitle == "McDonalds" && childText == "Beef Burger") {
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
        Intent intent = new Intent("com.iulbpns.lbpnsandroid");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(),requestCode,intent,0);
        if (ContextCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            lManager.removeProximityAlert(pendingIntent);
            Toast.makeText(context.getApplicationContext(), "Proximity Alert Removed!", Toast.LENGTH_LONG).show();

        }
    }

    private void addProximityAlert(int requestCode,double lat,double lon){
        LocationManager lManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Intent intent = new Intent("com.iulbpns.lbpnsandroid");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(),requestCode,intent,0);

        if(lManager != null){
            if (ContextCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                lManager.addProximityAlert(lat,lon, RADIUS, EXPIRATION_TIME, pendingIntent);
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


