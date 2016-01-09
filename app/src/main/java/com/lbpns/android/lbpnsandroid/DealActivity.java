package com.lbpns.android.lbpnsandroid;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

/**
 * Created by Asad 15R on 1/4/2016.
 */
public class DealActivity extends Activity implements DealFragment.DealFragmentItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_main);

    }

    @Override
    public void onListFragmentItemClick(int position) {

        int orientation = getResources().getConfiguration().orientation;

        if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
            FragmentManager fragmentManager = getFragmentManager();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            Fragment prevFrag = fragmentManager.findFragmentByTag("com.lbpns.android.lbpnsandroid.dealtitle.details");

            if (prevFrag != null)
                fragmentTransaction.remove(prevFrag);

            DealDetailsFragment dfragment = new DealDetailsFragment();

            Bundle b = new Bundle();

            b.putInt("position", position);


            dfragment.setArguments(b);

            fragmentTransaction.add(R.id.detail_fragment_container, dfragment, "com.lbpns.android.lbpnsandroid.dealtitle.details");

            fragmentTransaction.addToBackStack(null);

            fragmentTransaction.commit();

        }else{
            Intent i = new Intent(this,DealDetailsActivity.class);

            i.putExtra("position",position);
            i.putExtra("content",DealData.listContentS);
            startActivity(i);
        }

    }

}
