package com.android.fastcampus.kwave.plot.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.fastcampus.kwave.plot.R;
import com.android.fastcampus.kwave.plot.adapter.ListAdapter;

import static com.android.fastcampus.kwave.plot.MainActivity.datas;


/**
 * A simple {@link Fragment} subclass.
 */
public class WantFragment extends Fragment {

    RecyclerView recyclerView;
    ListAdapter adapter;

    public WantFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_want, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        adapter = new ListAdapter();
        adapter.setData(datas);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return view;
    }

}
