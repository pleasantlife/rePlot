package com.android.fastcampus.kwave.plot.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.fastcampus.kwave.plot.DataSource.Data;
import com.android.fastcampus.kwave.plot.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxx on 2017. 8. 8..
 */

public class ExReviewAdapter extends RecyclerView.Adapter<ExReviewAdapter.Holder> {

    List<Data> list = new ArrayList<>();
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

//        holder.textUserId.setText();
//        holder.textContent.setText();
//        holder.textDate.setText();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        TextView textUserId;
        TextView textContent;
        TextView textDate;

        public Holder(View v) {
            super(v);
            textUserId = (TextView) v.findViewById(R.id.textUserId);
            textContent = (TextView) v.findViewById(R.id.textContent);
            textDate = (TextView) v.findViewById(R.id.textDate);
        }
    }
}
