package com.android.fastcampus.kwave.plot.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.fastcampus.kwave.plot.DataSource.ServerData;
import com.android.fastcampus.kwave.plot.R;
import com.bumptech.glide.Glide;

import static com.android.fastcampus.kwave.plot.R.id.ExplainText;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExDetailFragment extends Fragment {
    TextView explainText;
    ImageView explainImg, explainImg2;
    public static ServerData serverData;

    public ExDetailFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ex_detail, container, false);
        explainText = (TextView) view.findViewById(ExplainText);
        explainImg = (ImageView) view.findViewById(R.id.explainImg);
        explainImg2 = (ImageView) view.findViewById(R.id.explainImg2);
        explainText.setText(serverData.getPoster_title());
        
        Glide.with(container.getContext()).load(serverData.getThumbnail_img_1()).into(explainImg);
        Glide.with(container.getContext()).load(serverData.getThumbnail_img_2()).into(explainImg2);

        return view;
    }

}
