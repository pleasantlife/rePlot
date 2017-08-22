package com.android.fastcampus.kwave.plot.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.fastcampus.kwave.plot.DataSource.ServerData;
import com.android.fastcampus.kwave.plot.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExInfoFragment extends Fragment {

    TextView textOrg, textGenre, textTarget, textPrice, textHomepage;
    public static ServerData serverData;

    public ExInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ex_info, container, false);
        textOrg = (TextView) view.findViewById(R.id.textOrg);
        textGenre = (TextView) view.findViewById(R.id.textGenre);
        textTarget = (TextView) view.findViewById(R.id.textTarget);
        textPrice = (TextView) view.findViewById(R.id.textPrice);
        textHomepage = (TextView) view.findViewById(R.id.textHomepage);
        setValue();
        return view;
    }

    public void setValue(){
        textOrg.setText(serverData.getPlace());
        textPrice.setText(serverData.getFee());
        textGenre.setText(serverData.getGenre());
        textHomepage.setText(serverData.getLocation());
        textTarget.setText(serverData.getGrade());
    }
}
