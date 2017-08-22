package com.android.fastcampus.kwave.plot.DataSource;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by kwave on 2017-08-02.
 */

public class Loader {
    public static ArrayList<Data> getData(Context context){
        ArrayList<Data> result = new ArrayList<>();
        for(int i=1 ; i<=5 ; i++){
            Data data = new Data();
            data.title = "전시회"+i;
            data.date_end = "2017. 08. 0"+i;
            data.exhibition = "fastcampus";
            data.date_start = "2017. 07. " + (10+i);
            data.star = (float) (4.5 -(i/10));

//            data.setImage("irene", context);
            result.add(data);
        }
        return result;
    }
}
