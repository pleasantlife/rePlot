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

import static com.android.fastcampus.kwave.plot.MainActivity.datas;

/**
 * Created by kwave on 2017-07-04.
 * 메인 페이지의 랭킹 6~10위 리스트를 만드는 RecyclerView Adapter
 */


public class RankRecyclerAdapterMain extends RecyclerView.Adapter<RankRecyclerAdapterMain.Holder>{
    List<ServerData> data = new ArrayList<>();
    Context context = null;

    public void setData(List<ServerData> data){
        this.data = data;
    }

    @Override
    public Holder onCreateViewHolder (ViewGroup parent,int viewType){
        if (context == null) {
            this.context = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_main_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder (Holder holder,int position){
        ServerData serverData = data.get(position);
        holder.setPosition(position);
        holder.textTitleMain.setText(serverData.getPoster_title());
        holder.textDateEndMain.setText(serverData.getDate_end());
        holder.textExhibitionMain.setText(serverData.getLocation());
        Glide.with(context).load(data.get(position).getPoster_img()).into(holder.rankRecyclerViewImageMain);
    }

    @Override
    public int getItemCount () {
        return data.size();
//        return 5;
    }
    class Holder extends RecyclerView.ViewHolder {
        private int position;
        private TextView textTitleMain, textDateStartMain, textFromToMain, textDateEndMain, textExhibitionMain;
        private RatingBar starMain;
        private ImageView rankRecyclerViewImageMain;

        public Holder(View v) {
            super(v);
            textTitleMain = (TextView) v.findViewById(R.id.textTitle_main);
            textDateEndMain = (TextView) v.findViewById(R.id.textDateEnd_main);
            textExhibitionMain = (TextView) v.findViewById(R.id.textExhibition_main);
            rankRecyclerViewImageMain = (ImageView) v.findViewById(R.id.rankRecyclerViewImage_main);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DetailActivity.class);
                    intent.putExtra("ListId", "MainActivity");
                    intent.putExtra("POSITION", position);
                    intent.putExtra("fromMain", data.get(position));
                    v.getContext().startActivity(intent);
                }
            });
        }

        public void setPosition(int position) {
            this.position = position;
        }


        public void setImage(int position) {
            this.rankRecyclerViewImageMain.setImageResource(position);
        }
    }
}
