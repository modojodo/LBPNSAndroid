package com.lbpns.android.lbpnsandroid;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

/**
 * Created by Asad 15R on 1/4/2016.
 */
public class DealDetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** Setting the layout for this activity */
        setContentView(R.layout.activity_deal_details);

        /** Getting the fragment manager for fragment related operations */
        FragmentManager fragmentManager = getFragmentManager();

        /** Getting the fragmenttransaction object, which can be used to add, remove or replace a fragment */
        FragmentTransaction fragmentTransacton = fragmentManager.beginTransaction();

        /** Instantiating the fragment CountryDetailsFragment */
        DealDetailsFragment detailsFragment = new DealDetailsFragment();

        /** Creating a bundle object to pass the data(the clicked item's position) from the activity to the fragment */
        Bundle b = new Bundle();

        /** Setting the data to the bundle object from the Intent*/
        b.putInt("position", getIntent().getIntExtra("position", 0));
        b.putStringArray("content", getIntent().getStringArrayExtra("content"));

        /** Setting the bundle object to the fragment */
        detailsFragment.setArguments(b);

        /** Adding the fragment to the fragment transaction */
        fragmentTransacton.add(R.id.deal_details_fragment_container, detailsFragment);

        /** Making this transaction in effect */
        fragmentTransacton.commit();

    }
}
