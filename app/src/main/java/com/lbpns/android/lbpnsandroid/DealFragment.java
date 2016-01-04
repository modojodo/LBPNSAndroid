package com.lbpns.android.lbpnsandroid;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Asad 15R on 1/4/2016.
 */
public class DealFragment extends ListFragment {

    DealFragmentItemClickListener ifaceItemClickListener;

    public interface DealFragmentItemClickListener{

        void onListFragmentItemClick(int position);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            ifaceItemClickListener = (DealFragmentItemClickListener) activity;
        }catch(Exception e){
            Toast.makeText(activity.getBaseContext(), "Exception", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ArrayAdapter<String> adpt = new ArrayAdapter<String>(inflater.getContext(),android.R.layout.simple_list_item_1, DealData.listTitleS);

        setListAdapter(adpt);

      //  return inflater.inflate(R.layout.activity_listview,null);

        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        ifaceItemClickListener.onListFragmentItemClick(position);

    }
}

