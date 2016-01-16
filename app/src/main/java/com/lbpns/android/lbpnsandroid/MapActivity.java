package com.lbpns.android.lbpnsandroid;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by Asad 15R on 1/8/2016.
 */
public class MapActivity extends Activity {

    GoogleMap googleMap;

    LatLng latLng = new LatLng(24.955635,67.154491);

    private ArrayList<LatLng> latlngs = new ArrayList<>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        latlngs.add(new LatLng(12.334343, 33.43434));

        googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                R.id.map)).getMap();

       setMarker();


//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View v = getLayoutInflater().inflate(R.layout.map_info, null);

                // Getting the position from the marker
                LatLng latLng = marker.getPosition();

                // Getting reference to the TextView to set latitude
                TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);

                // Getting reference to the TextView to set longitude
                TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);

                // Setting the latitude
                tvLat.setText("Latitude:" + latLng.latitude);

                // Setting the longitude
                tvLng.setText("Longitude:" + latLng.longitude);

                // Returning the view containing InfoWindow contents
                return v;

            }
        });


    }
        void setMarker(){

            String location[] = PreferenceActivity.checkKey.split(",");
            double lat = Double.parseDouble(location[0]);
            double lng = Double.parseDouble(location[1]);

//            for (int i = 0; i < yourArrayList.size(); i++) {
//                double lati=Double.parseDouble(pins.get(i).latitude);
//                double longLat=Double.parseDouble(pins.get(i).longitude);
//                MAP.addMarker(new MarkerOptions().position(
//                        new LatLng(lati,longLat))
//                        .title(pins.get(i)
//                                .pinname)
//                        .snippet(pins.get(i).address));
//            }

//        MarkerOptions markerOptions = new MarkerOptions();
//
//        // Setting position on the MarkerOptions
//
//        markerOptions.position(latLng);
//
//        // Animating to the currently touched position
//        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//
//        // Adding marker on the GoogleMap
//        Marker marker = googleMap.addMarker(markerOptions);
//
//        // Showing InfoWindow on the GoogleMap
//        marker.showInfoWindow();
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MapActivity.this,MainMenu.class);
        startActivity(intent);
    }
}
