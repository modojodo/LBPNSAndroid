package com.lbpns.android.lbpnsandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Asad 15R on 12/16/2015.
 */
public class RestaurantFragment extends Fragment {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_explistview,container,false);


        expListView = (ExpandableListView) rootView.findViewById(R.id.lvExpanded);



        // prepare list data
        constructData();

        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        return rootView;
    }

    private void constructData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("KFC");
        listDataHeader.add("Pizza Point");
        listDataHeader.add("McDonalds");
//
//        for (int i=0;i<DealData.listTitleS.length;i++){
//            listDataHeader.add(DealData.listTitleS[i].toString());
//        }

        // Adding child data
        List<String> kfc = new ArrayList<String>();
        kfc.add("Burger");
        kfc.add("Chicken Wings");
        kfc.add("Sandwich");
        kfc.add("Fajita Roll");


        List<String> pizzapoint = new ArrayList<String>();
        pizzapoint.add("Chicken Tikka");
        pizzapoint.add("Fajita");
        pizzapoint.add("Afghani Tikka");
        pizzapoint.add("Garlic Bread");


        List<String> md = new ArrayList<String>();
        md.add("Chicken Burger");
        md.add("Jumbo Zinger");
        md.add("Club Sandwich");
        md.add("Beef Burger");


        listDataChild.put(listDataHeader.get(0), kfc); // Header, Child data
        listDataChild.put(listDataHeader.get(1), pizzapoint);
        listDataChild.put(listDataHeader.get(2), md);
    }
}

