package com.lbpns.android.lbpnsandroid;

import android.app.Fragment;
import android.app.SearchManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

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
public class RestaurantFragment extends Fragment {
    private final static String TAG = "RestaurantFragment";
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    CheckedTextView lblListItem;
    List<String> listDataHeader;
    ServerRequestTask fetchTask;

    private TextView listHeaderTextView;

    HashMap<String, List<String>> listDataChild;
    static ArrayList<String> listTitle;
    static ArrayList<List<String>> listContent;


    private SearchView searchView;
    private Button btnDeal;
    static String listTitleS[], listContentS[];
    static List<String> individualRestaurantCuisines;


    @Override
    public void onStart() {
        super.onStart();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_explistview, container, false);

        getData();
        fetchTask.execute();

//        getData();
        expListView = (ExpandableListView) rootView.findViewById(R.id.lvExpanded);
//        // prepare list data
//        constructData();

//        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

        // setting list adapter
//        expListView.setAdapter(listAdapter);


        return rootView;
    }

    void getData() {
        fetchTask = new ServerRequestTask(getActivity(), new ServerRequestTask.TaskHandler() {
            @Override
            public Object task() {
                try {
                    URL url = new URL(ServerCommunication.GET_PREFERENCES_BY_RESTAURANT);
                    ServerCommunication server = new ServerCommunication(getActivity());
                    return server.getRequest(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void onTaskCompletion(Object o) {
                JSONArray jsonArray = (JSONArray) o;
                populateData(jsonArray);
                Toast.makeText(getActivity(), "Testing", Toast.LENGTH_LONG).show();
                // prepare list data
                constructData();

                Log.d("T", jsonArray.toString() + "Hello");
                listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

                expListView.setAdapter(listAdapter);
                Log.d("T", "After Task");
            }
        });


    }

    private void populateData(JSONArray deals) {
        listTitle = new ArrayList<String>();
        listContent = new ArrayList<List<String>>();


//        JSONArray deals;
        //  System.out.println(fetchedDeals);
//        deals = jsonArraynew;
        if (deals != null) {
            int len = deals.length();


            for (int i = 0; i < len; i++) {
                JSONArray jsonArrayR = null;
                try {
                    jsonArrayR = deals.getJSONObject(i).getJSONArray("restaurant");
                    String rTitle = jsonArrayR.getString(0);
                    listTitle.add(rTitle);
                    JSONArray jsonArrayC = deals.getJSONObject(i).getJSONArray("cuisines"); //cuisines
                    int cuisineLen = jsonArrayC.length();
                    individualRestaurantCuisines = new ArrayList<String>();
                    for (int j = 0; j < cuisineLen; j++) {
                        String cTitle = jsonArrayC.getString(j);
                        individualRestaurantCuisines.add(cTitle);
                    }
//                    String strArr[] =  new String[individualRestaurantCuisines.size()];
//                    strArr = individualRestaurantCuisines.toArray(strArr);
                    listContent.add(individualRestaurantCuisines);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


//
        }


        listTitleS = new String[listTitle.size()];
        listTitleS = listTitle.toArray(listTitleS);

        listContentS = new String[individualRestaurantCuisines.size()];
        listContentS = individualRestaurantCuisines.toArray(listContentS);


    }


    private void constructData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        for (int i = 0; i < listTitleS.length; i++) {
            listDataHeader.add(listTitleS[i]);
        }


        for (int i = 0; i < listTitleS.length; i++) {
            listDataChild.put(listDataHeader.get(i), listContent.get(i));
        }


    }


}


