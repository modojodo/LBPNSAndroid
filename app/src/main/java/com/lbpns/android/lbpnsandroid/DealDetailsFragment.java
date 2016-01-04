package com.lbpns.android.lbpnsandroid;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Asad 15R on 1/4/2016.
 */
public class DealDetailsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.deal_details_fragment_layout,null);

        TextView txt = (TextView) v.findViewById(R.id.deal_details);

        Bundle b = getArguments();

    //    txt.setText(DealData.listContentS);

        if(DealData.listContentS[b.getInt("position")] != "") {
            txt.setText(DealData.listContentS[b.getInt("position")]);
        }
      //  txt.setText(b.getStringArray("content").toString());
      //  txt.setText(DealData.listContentS.toString());
        return v;

    }
}
