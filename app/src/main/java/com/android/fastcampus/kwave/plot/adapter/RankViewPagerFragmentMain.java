package com.android.fastcampus.kwave.plot.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.fastcampus.kwave.plot.DataSource.Data;
import com.android.fastcampus.kwave.plot.DataSource.ServerData;
import com.android.fastcampus.kwave.plot.R;


/**
 * Created by kwave on 2017-08-02.
 * Rank Best Iamge of ViewPager of the fragment in MainActivity
 */

public class RankViewPagerFragmentMain extends Fragment {
    ServerData serverData = new ServerData();
    Data datas = new Data();
    ImageView rankBest;
    private int mPageNumber;

    public static RankViewPagerFragmentMain create(int pageNumber) {
        RankViewPagerFragmentMain fragment = new RankViewPagerFragmentMain();
        Bundle args = new Bundle();
        args.putInt("page", pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt("page");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.rank_fragment_main, container, false);
        rankBest = ((ImageView) rootView.findViewById(R.id.rankBest));
        rankBest.setImageResource(datas.rankBestImage[mPageNumber]);
        return rootView;
    }
}