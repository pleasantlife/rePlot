package com.android.fastcampus.kwave.plot.DataSource;

import android.content.Context;


import com.android.fastcampus.kwave.plot.R;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by msahakyan on 22/10/15.
 */
public class ExpandableListDataSource {

    /**
     * Returns fake data of films
     *
     * @param context
     * @return
     */
    public static Map<String, List<String>> getData(Context context) {
        Map<String, List<String>> expandableListData = new TreeMap<>();

        List<String> plotCategory = Arrays.asList(context.getResources().getStringArray(R.array.plotCategory));

        List<String> genre = Arrays.asList(context.getResources().getStringArray(R.array.genre));
        List<String> location = Arrays.asList(context.getResources().getStringArray(R.array.location));
//        List<String> dramaFilms = Arrays.asList(context.getResources().getStringArray(R.array.dramas));

        expandableListData.put(plotCategory.get(0), genre);
        expandableListData.put(plotCategory.get(1), location);
//        expandableListData.put(filmGenres.get(2), dramaFilms);

        return expandableListData;
    }
}
