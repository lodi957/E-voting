package com.se.httpwww.se;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

/**
 * Created by Dell on 4/7/2018.
 */

public class ResultsPage extends ListActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ResultsPageView MyAdapter = new ResultsPageView(this, User.getInstance().resultsEventsList);
        setListAdapter(MyAdapter);

    }



    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

     //   BackgroundGetCandidates bgc= new BackgroundGetCandidates(this);
      //  bgc.execute(Info.getInstance().votingEventsList.get(position).getId());

    }
}
