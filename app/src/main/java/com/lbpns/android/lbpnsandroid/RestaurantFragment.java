package com.lbpns.android.lbpnsandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

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
//        listDataHeader.add("KFC");
//        listDataHeader.add("Pizza Point");
//        listDataHeader.add("McDonalds");
//
        for (int i=0;i<DealData.listTitleS.length;i++){
            listDataHeader.add(DealData.listTitleS[i].toString());
        }

        // Adding child data


        List<String> cusisineList = new ArrayList<String>();

        for(int i=0;i<DealData.listTitleS.length;i++)
        {
            listDataChild.put(listDataHeader.get(i), DealData.listContent.get(i));
        }
//        kfc.add("Burger");
//        kfc.add("Chicken Wings");
//        kfc.add("Sandwich");
//        kfc.add("Fajita Roll");




//        listDataChild.put(listDataHeader.get(0),kfccuisines); // Header, Child data
//        listDataChild.put(listDataHeader.get(1), pizzapointcuisine);
//        listDataChild.put(listDataHeader.get(2), md);
    }
}


