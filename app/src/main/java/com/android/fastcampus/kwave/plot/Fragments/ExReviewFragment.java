package com.android.fastcampus.kwave.plot.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.fastcampus.kwave.plot.DataSource.ServerData;
import com.android.fastcampus.kwave.plot.R;
import com.android.fastcampus.kwave.plot.adapter.ExReviewAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExReviewFragment extends Fragment {
    RecyclerView reviewRecycler;
    ExReviewAdapter ExAdapter;
    public static ServerData serverData;

    public ExReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ex_review, container, false);
        reviewRecycler = (RecyclerView) view.findViewById(R.id.reviewRecycler);
        return view;
    }

}
