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
    String genre;
    String ageTarget;

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
        setGenre();
        setAge();
        setValue();
        return view;
    }

    public void setValue(){
        textOrg.setText(serverData.getPlace());
        textPrice.setText(serverData.getFee());
        textGenre.setText(genre);
        textHomepage.setText("http://"+serverData.getLocation()+".co.kr");
        textTarget.setText(ageTarget);
    }


    public void setGenre(){
        switch(serverData.getGenre()){
            case "piece":
                genre = "조각";
                break;
            case "photo":
                genre = "사진";
                break;
            case "video":
                genre = "영상";
                break;
            case "install":
                genre = "설치";
                break;
            case "art":
                genre = "미술";
                break;
            default :
                genre = "기타";
                break;

        }
    }

    public void setAge(){
        switch(serverData.getGrade()){
            case "15":
                ageTarget = "15세 관람가";
                break;
            case "18":
                ageTarget = "미성년자 관람불가";
                break;
            default:
                ageTarget = "전체 관람가";
                break;
        }
    }
}