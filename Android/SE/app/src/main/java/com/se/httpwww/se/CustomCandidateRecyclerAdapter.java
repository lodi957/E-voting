package com.se.httpwww.se;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Dell on 9/26/2017.
 */

public class CustomCandidateRecyclerAdapter extends RecyclerView.Adapter<CustomCandidateRecyclerAdapter.CustomViewHolder> implements Filterable {

    ArrayList<CandidatesClass> items;
    Context ctx;
    CandidatesClass productsObject;

    ArrayList<CandidatesClass> filterItems;
    CustomCandidateRecyclerAdapter.CustomFilter filter;



    public CustomCandidateRecyclerAdapter(ArrayList<CandidatesClass> items, Context c) {
        this.items = items;
        ctx=c;
        this.filterItems=items;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_candidate,parent,false));
    }

    @Override
    public void onBindViewHolder(final CustomCandidateRecyclerAdapter.CustomViewHolder holder, final int position) {
        final CandidatesClass item = items.get(position);
        holder.name.setText(item.getName());


        String imgUrl ="http://";
        imgUrl=imgUrl.concat(User.getInstance().getIp());
        imgUrl=imgUrl.concat("/SE/pics/");

        //String Url ="http://";
      //  Url=Url.concat(Info.getInstance().getIp());
      //  Url=Url.concat("/SE/SignUpFile.php");

        String a=imgUrl.concat(item.getPic());

        Picasso.with(ctx).load(a).into(holder.pic);




        holder.vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BackgroundOnVote backgroundTask = new BackgroundOnVote(ctx);
                backgroundTask.execute(item.getId(), User.getInstance().getName(),item.getEvent_id());


            }
        });



    }

    @Override
    public int getItemCount() {
        return items.size();
    }



    @Override
    public Filter getFilter() {

        if(filter == null)
        {
            filter=new CustomFilter();
        }
        return filter;
    }



    static class CustomViewHolder extends RecyclerView.ViewHolder{


        TextView name;
        Button vote;
        ImageView pic;


        public CustomViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            vote=(Button)itemView.findViewById(R.id.vote);



            pic=(ImageView)itemView.findViewById(R.id.pic);



        }
    }



    class CustomFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            FilterResults results=new FilterResults();
            if(charSequence!=null && charSequence.length()>0)
            {
                //String charSequence1="B";
                charSequence=charSequence.toString().toUpperCase();

                ArrayList<CandidatesClass> filters=new ArrayList<CandidatesClass>();

                for(int i=0;i<filterItems.size();i++)
                {

                    //Toast.makeText(ctx, "i is: "+i+" "+charSequence, Toast.LENGTH_SHORT).show();
                    if(filterItems.get(i).getName().toUpperCase().contains(charSequence))
                    {
                        // ModelProducts p =new ModelProducts(filterItems.get(i).getProductName(),filterItems.get(i).getProductDesc(),
                        //       filterItems.get(i).getProductPrice(),filterItems.get(i).getImgName());
                        CandidatesClass p= User.getInstance().candidatesList.get(i);
                        // Toast.makeText(ctx, "i is: "+i+" "+p.getProductName(), Toast.LENGTH_SHORT).show();
                        filters.add(p);
                    }
                }
                results.count=filters.size();
                results.values=filters;
            }else
            {
                results.count=filterItems.size();
                results.values=filterItems;

            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            items=(ArrayList<CandidatesClass>) filterResults.values;
            notifyDataSetChanged();

        }
    }
}



