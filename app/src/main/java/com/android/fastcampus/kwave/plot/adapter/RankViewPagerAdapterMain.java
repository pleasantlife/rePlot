package com.android.fastcampus.kwave.plot.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by kwave on 2017-08-02.
 * 메인 페이지의 랭킹 Best 이미지를 보는 ViewPagerAdapter
 */

public class RankViewPagerAdapterMain extends FragmentStatePagerAdapter {

    public RankViewPagerAdapterMain(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // 해당하는 page의 Fragment를 생성합니다.
        return RankViewPagerFragmentMain.create(position);
    }

    @Override
    public int getCount() {
        return 5;  // 총 5개의 page를 보여줍니다.
    }

}

