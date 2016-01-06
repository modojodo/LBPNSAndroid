package com.lbpns.android.lbpnsandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Asad 15R on 12/16/2015.
 */
public class CuisineFragment extends Fragment {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private static ArrayList<String> listTitle;
    private static ArrayList<String> listContent;

    static String listTitleS[],listContentS[];

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

    void getData() {

        ServerRequestTask fetchTask = new ServerRequestTask(new ServerRequestTask.TaskHandler() {
            @Override
            public JSONArray taskWithJSONArray() {
                try {
                    URL url = new URL("http://192.168.1.10:3000/fetchDeals");
                    ServerCommunication server = new ServerCommunication(getContext());
                    return server.getRequest(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public boolean taskWithBoolean() {
                return false;
            }
        });
        try {

            listTitle = new ArrayList<String>();
            listContent = new ArrayList<String>();

            JSONArray fetchedDeals = null;
            try {
                fetchedDeals = (JSONArray) fetchTask.execute("jsonarray").get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            if (fetchedDeals != null) {
                int len = fetchedDeals.length();
                for (int i = 0; i < len; i++) {

                    listTitle.add(fetchedDeals.getJSONObject(i).getString("dealTitle"));

                    listContent.add(fetchedDeals.getJSONObject(i).getString("dealContent"));
                }

            }

            // listContent.add(fetchedDeals.getJSONObject(1).getString("dealContent"));

//            System.out.println(fetchedDeals.getJSONObject(1).getString("dealContent"));

            listTitleS = new String[listTitle.size()];
            listTitleS = listTitle.toArray(listTitleS);

            listContentS = new String[listContent.size()];
            listContentS = listContent.toArray(listContentS);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void constructData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Burger");
        listDataHeader.add("Pizza");
        listDataHeader.add("Sandwich");

        // Adding child data
        List<String> burger = new ArrayList<String>();
        burger.add("KFC");
        burger.add("McDonalds");

        List<String> pizza = new ArrayList<String>();
        pizza.add("Pizza Point");

        List<String> sandwich = new ArrayList<String>();
        sandwich.add("KFC");
        sandwich.add("McDonalds");

        listDataChild.put(listDataHeader.get(0), burger); // Header, Child data
        listDataChild.put(listDataHeader.get(1), pizza);
        listDataChild.put(listDataHeader.get(2), sandwich);
    }
}
