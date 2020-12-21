package com.se.httpwww.se;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Dell on 1/18/2018.
 */

public class MainPageView extends BaseAdapter {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private TextView institute,desc,name,date,time;
    private ArrayList<VotingEventClass> titleArray;
    private ImageView img;

    public MainPageView(Context context, ArrayList<VotingEventClass> title){
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
            convertView = layoutInflater.inflate(R.layout.mainpageview, null);
        }


        name = (TextView)convertView.findViewById(R.id.name);
        desc = (TextView)convertView.findViewById(R.id.desc);
        institute = (TextView)convertView.findViewById(R.id.ins);
        img = (ImageView)convertView.findViewById(R.id.img);
//        date = (TextView)convertView.findViewById(R.id.date);
//        time = (TextView)convertView.findViewById(R.id.time);


        name.setText("\nEvent: " + titleArray.get(position).getName());
        desc.setText("Description: " + titleArray.get(position).getDesc());
        if(titleArray.get(position).getInstitute()!=null || titleArray.get(position).getInstitute()!="" || titleArray.get(position).getInstitute()!=" ")
            institute.setText("Place: " + titleArray.get(position).getInstitute());
        else
        {
            institute.setText("Place: Not Specified");
        }
//        date.setText("Description: " + titleArray.get(position).getDate());
//        time.setText("Description: " + titleArray.get(position).getTime());
//
        String imgUrl ="http://";
        imgUrl=imgUrl.concat(User.getInstance().getIp());
        imgUrl=imgUrl.concat("/SE/posters/");

        String a=imgUrl.concat(titleArray.get(position).getPos());

        Picasso.with(mContext).load(a).into(img);


        return convertView;

    }
}

