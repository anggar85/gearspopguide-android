package com.mxlapps.app.gearspopguide.Adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.mxlapps.app.gearspopguide.Model.NewsModel;

import java.util.ArrayList;

public class CustomPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<NewsModel> news_element;

    public CustomPagerAdapter(FragmentManager fm, ArrayList<NewsModel> news_element){
        super(fm);
        this.news_element = news_element;
    }
    @Override
    public Fragment getItem(int position) {
//        switch (position){
//            case 0: return new NewsElementFragment(news_element);
//            case 1: return new NewsElementFragment(news_element);
//        }
        return null;
    }
    @Override
    public int getCount() {
        return 5;
    }


}
