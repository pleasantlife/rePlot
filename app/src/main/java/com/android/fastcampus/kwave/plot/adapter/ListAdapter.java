package com.android.fastcampus.kwave.plot.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.fastcampus.kwave.plot.DataSource.ServerData;
import com.android.fastcampus.kwave.plot.DetailActivity;
import com.android.fastcampus.kwave.plot.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by maxx on 2017. 8. 7..
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.Holder> {
    List<ServerData> data = new ArrayList<>();

    Context context = null;
    public void setData(List<ServerData> datas){
        this.data = datas;
    }
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(context == null){
            this.context = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_list_list, parent,false);
        return new Holder(view);

    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        ServerData serverData = data.get(position);
        holder.setPosition(position);
        holder.title.setText(serverData.getPoster_title());
        holder.location.setText(serverData.getLocation());
        holder.period.setText(serverData.getDate_start());
        Glide.with(context).load(data.get(position).getPoster_img()).into(holder.poster);
            //Glide.with(context).load(datas.image).into(holder.poster);
            //holder.ratingBar.setRating(datas.star);

    }

    @Override
    public int getItemCount() {
        return data.size();

    }


    class Holder extends RecyclerView.ViewHolder{
        TextView title, location, period;
        ImageView poster;
        RatingBar ratingBar;
        int position;
        public Holder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            location = (TextView) v.findViewById(R.id.location);
            period = (TextView) v.findViewById(R.id.period);
            poster = (ImageView) v.findViewById(R.id.poster);
            ratingBar = (RatingBar) v.findViewById(R.id.writeRatingBar);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("ListId", "category");
                    intent.putExtra("POSITION" , position);
                    intent.putExtra("fromList",  data.get(position));
                    context.startActivity(intent);
                }
            });
        }
        public void setPosition(int position){
            this.position = position;
        }
    }

}
