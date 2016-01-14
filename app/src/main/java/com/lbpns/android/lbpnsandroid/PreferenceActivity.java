package com.lbpns.android.lbpnsandroid;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Asad 15R on 1/14/2016.
 */
public class PreferenceActivity extends Activity {

    RestaurantFragment restaurantFragment;
    CuisineFragment cuisineFragment;
    int count =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_preference);

        Button btn_restaurant = (Button) findViewById(R.id.btn_restaurant);
        Button btn_cuisine = (Button) findViewById(R.id.btn_cuisine);

        btn_restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                restaurantFragment = new RestaurantFragment();
//                fragmentTransaction.add(R.id.fragment_container, restaurantFragment, "restaurant");


        //                fragmentTransaction.add(R.id.fragment_container, restaurantFragment, "restaurant");
                fragmentTransaction.replace(R.id.fragment_container,restaurantFragment);


                fragmentTransaction.commit();
            }
        });

        btn_cuisine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                cuisineFragment = new CuisineFragment();
//                fragmentTransaction.add(R.id.fragment_container, cuisineFragment, "cuisine");

//                fragmentTransaction.add(R.id.fragment_container, cuisineFragment, "cuisine");
                fragmentTransaction.replace(R.id.fragment_container,cuisineFragment);
                fragmentTransaction.commit();
            }
        });


    }


}
