package com.android.fastcampus.kwave.plot.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.android.fastcampus.kwave.plot.Fragments.CommentsFragment;
import com.android.fastcampus.kwave.plot.Fragments.ViewedFragment;
import com.android.fastcampus.kwave.plot.Fragments.WantFragment;

/**
 * Created by XPS on 2017-08-07.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new WantFragment();
        } if (position == 1){
            return new ViewedFragment();
        } if (position == 2){
            return new CommentsFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return "좋아한 전시";
        } else if (position == 1){
            return "관람한 전시";
        } else if (position == 2){
            return "남긴 관람평";
        }

        return super.getPageTitle(position);
    }
}
