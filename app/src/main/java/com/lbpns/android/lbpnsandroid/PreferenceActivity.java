package com.lbpns.android.lbpnsandroid;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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

//        Button btn_restaurant = (Button) findViewById(R.id.btn_restaurant);
//        Button btn_cuisine = (Button) findViewById(R.id.btn_cuisine);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<>();
        list.add("By Restaurant");
        list.add("By Cuisine");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    restaurantFragment = new RestaurantFragment();
//                fragmentTransaction.add(R.id.fragment_container, restaurantFragment, "restaurant");


                    //                fragmentTransaction.add(R.id.fragment_container, restaurantFragment, "restaurant");
                    fragmentTransaction.replace(R.id.fragment_container, restaurantFragment);


                    fragmentTransaction.commit();
                } else {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    cuisineFragment = new CuisineFragment();
//                fragmentTransaction.add(R.id.fragment_container, cuisineFragment, "cuisine");

//                fragmentTransaction.add(R.id.fragment_container, cuisineFragment, "cuisine");
                    fragmentTransaction.replace(R.id.fragment_container, cuisineFragment);
                    fragmentTransaction.commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        btn_restaurant.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//                restaurantFragment = new RestaurantFragment();
////                fragmentTransaction.add(R.id.fragment_container, restaurantFragment, "restaurant");
//
//
//                //                fragmentTransaction.add(R.id.fragment_container, restaurantFragment, "restaurant");
//                fragmentTransaction.replace(R.id.fragment_container, restaurantFragment);
//
//
//                fragmentTransaction.commit();
//            }
//        });
//
//        btn_cuisine.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                cuisineFragment = new CuisineFragment();
////                fragmentTransaction.add(R.id.fragment_container, cuisineFragment, "cuisine");
//
////                fragmentTransaction.add(R.id.fragment_container, cuisineFragment, "cuisine");
//                fragmentTransaction.replace(R.id.fragment_container, cuisineFragment);
//                fragmentTransaction.commit();
//            }
//        });


    }


}
