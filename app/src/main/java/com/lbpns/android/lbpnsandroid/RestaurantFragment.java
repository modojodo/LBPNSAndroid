package com.lbpns.android.lbpnsandroid;

import android.app.SearchManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.text.TextUtils;
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
public class RestaurantFragment extends Fragment{

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    CheckedTextView lblListItem;
    List<String> listDataHeader;
    private JSONArray deals;

    private TextView listHeaderTextView;

    HashMap<String, List<String>> listDataChild;
    static ArrayList<String> listTitle;
    static ArrayList<List<String>> listContent;


    private SearchView searchView;
    private Button btnDeal;
    static String listTitleS[], listContentS[];
    static List<String> individualRestaurantCuisines;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_explistview,container,false);


        getData();

        expListView = (ExpandableListView) rootView.findViewById(R.id.lvExpanded);





        // prepare list data
        constructData();

        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);



        return rootView;
    }

    void getData() {
        ServerRequestTask fetchTask = new ServerRequestTask(getActivity(),new ServerRequestTask.TaskHandler() {
            @Override
            public JSONArray taskWithJSONArray() {
                try {
                    URL url = new URL(ServerCommunication.GET_PREFERENCES_BY_RESTAURANT);
                    ServerCommunication server = new ServerCommunication(getContext());
                    return server.getRequest(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void onTaskCompletion(JSONArray jsonArray) {
                System.out.println(jsonArray.toString());
                deals = jsonArray;
            }

            @Override
            public boolean taskWithBoolean() {
                return false;
            }
        });

        try {

            listTitle = new ArrayList<String>();
            listContent = new ArrayList<List<String>>();

            fetchTask.execute("jsonarray");

            JSONArray jsonArray = new JSONArray();
          //  System.out.println(fetchedDeals);
            if (deals != null) {
                int len = deals.length();

                for (int i = 0; i < len; i++) {



                    JSONArray jsonArrayR = deals.getJSONObject(i).getJSONArray("restaurant");



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

//
                }


//
            }



            listTitleS = new String[listTitle.size()];
            listTitleS = listTitle.toArray(listTitleS);

//            listContentS = new String[individualRestaurantCuisines.size()];
//            listContentS = individualRestaurantCuisines.toArray(listContentS);




        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    private void constructData() {


        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        for (int i=0;i<listTitleS.length;i++){
            listDataHeader.add(listTitleS[i].toString());
        }






        for(int i=0;i<listTitleS.length;i++)
        {
            listDataChild.put(listDataHeader.get(i), listContent.get(i));
        }



    }







}


