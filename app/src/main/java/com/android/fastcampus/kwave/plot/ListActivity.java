package com.android.fastcampus.kwave.plot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.fastcampus.kwave.plot.adapter.ListAdapter;

import static com.android.fastcampus.kwave.plot.MainActivity.datas;

public class ListActivity extends AppCompatActivity {
    RecyclerView recycler;
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recycler = (RecyclerView) findViewById(R.id.recycler);

        adapter = new ListAdapter();
        adapter.setData(datas);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));


    }
}
