package com.se.httpwww.se;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Dell on 1/18/2018.
 */

public class CandidatePageView extends BaseAdapter {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private TextView name;
    private ArrayList<CandidatesClass> titleArray;
    private ImageView imageView;

    public CandidatePageView(Context context, ArrayList<CandidatesClass> title){
        mContext = context;
        titleArray = title;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return titleArray.size();
    }

    @Override
    public Object getItem(int position) {
        return titleArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.votingpageview, null);
        }


        name = (TextView)convertView.findViewById(R.id.name);
//        desc = (TextView)convertView.findViewById(R.id.desc);
//        institute = (TextView)convertView.findViewById(R.id.institute);



        name.setText("\nEvent: " + titleArray.get(position).getName());
        //desc.setText("Description: " + titleArray.get(position).getDesc());
        //institute.setText("Place: " + titleArray.get(position).getInstitute());



        return convertView;

    }
}

