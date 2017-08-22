package com.android.fastcampus.kwave.plot.adapter;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.fastcampus.kwave.plot.DataSource.Data;
import com.android.fastcampus.kwave.plot.DataSource.ServerData;
import com.android.fastcampus.kwave.plot.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by kwave on 2017-08-02.
 */

public class FindRecyclerAdapterSearch extends RecyclerView.Adapter<FindRecyclerAdapterSearch.ViewHolder> {

    private final List<ServerData> data;
    private final List<Data> data1 = new ArrayList<>();

    public FindRecyclerAdapterSearch(final List<ServerData> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_search, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ServerData serverData = data.get(position);
        Data datas = data1.get(position);
        holder.setTextTitle(serverData.getPoster_title());
        holder.setTextDateEnd(serverData.getDate_end());
        holder.setTextExhibition(serverData.getLocation());
        holder.setImage(datas.rankBestImage[position*2]);
        holder.setPosition(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private int position;
        private TextView textTitle_search;
        private TextView textDateEnd_search;
        private TextView textExhibition_search;
        private ImageView rankRecyclerViewImage_search;
        public ViewHolder(View v) {
            super(v);
            textTitle_search = (TextView) v.findViewById(R.id.textTitle_search);
            textDateEnd_search = (TextView) v.findViewById(R.id.textDateEnd_search);
            textExhibition_search = (TextView) v.findViewById(R.id.textExhibition_search);
            rankRecyclerViewImage_search = (ImageView) v.findViewById(R.id.rankRecyclerViewImage_search);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ListActivity.class);
                    intent.putExtra("LIST_POSITION", position);
                    v.getContext().startActivity(intent);
                }
            });
        }
        public void setPosition(int position){
            this.position = position;
        }


        public String getTextTitle() {
            return textTitle_search.getText().toString();
        }

        public void setTextTitle(String title) {
            textTitle_search.setText(title);
        }

        public String getTextDateEnd() {
            return textDateEnd_search.getText().toString();
        }

        public void setTextDateEnd(String dateEnd) {
            textDateEnd_search.setText(dateEnd);
        }

        public String getTextExhibition() {
            return textExhibition_search.getText().toString();
        }

        public void setTextExhibition(String exhibition) {
            textExhibition_search.setText(exhibition);
        }

        public ImageView getRankRecyclerViewImage_main() {
            return rankRecyclerViewImage_search;
        }

        public void setImage(int position) {
            this.rankRecyclerViewImage_search.setImageResource(position);
        }
    }
}
