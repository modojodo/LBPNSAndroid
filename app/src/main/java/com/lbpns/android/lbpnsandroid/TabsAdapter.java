package com.lbpns.android.lbpnsandroid;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Asad 15R on 12/12/2015.
 */
public class TabsAdapter extends FragmentPagerAdapter {

    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new RestaurantFragment();
            case 1:
                return new CuisineFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}

