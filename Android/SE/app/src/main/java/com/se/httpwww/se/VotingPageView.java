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


public class VotingPageView extends BaseAdapter {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private TextView institute,desc,name;
    private ArrayList<VotingEventClass> titleArray;
    private ImageView img;
    private Button goCandidates;

    public VotingPageView(Context context, ArrayList<VotingEventClass> title){
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
        desc = (TextView)convertView.findViewById(R.id.desc);
        institute = (TextView)convertView.findViewById(R.id.ins);
        img = (ImageView)convertView.findViewById(R.id.img);
        goCandidates = (Button)convertView.findViewById(R.id.goCandidates);


        name.setText("\nEvent: " + titleArray.get(position).getName());
        desc.setText("Description: " + titleArray.get(position).getDesc());
        institute.setText("Place: " + titleArray.get(position).getInstitute());

        String imgUrl ="http://";
        imgUrl=imgUrl.concat(User.getInstance().getIp());
        imgUrl=imgUrl.concat("/SE/posters/");

        String a=imgUrl.concat(titleArray.get(position).getPos());

        Picasso.with(mContext).load(a).into(img);

        goCandidates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BackgroundGetCandidates bgc= new BackgroundGetCandidates(mContext);
                bgc.execute(User.getInstance().votingEventsList.get(position).getId());

            }
        });


        return convertView;

    }
}

