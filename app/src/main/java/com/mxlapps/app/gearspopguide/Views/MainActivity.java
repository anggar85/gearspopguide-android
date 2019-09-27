package com.mxlapps.app.gearspopguide.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.mxlapps.app.gearspopguide.Adapter.CustomPagerAdapter;
import com.mxlapps.app.gearspopguide.Model.NewsModel;
import com.mxlapps.app.gearspopguide.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//
//
//        ViewPager viewPager =  findViewById(R.id.viewpager_news);
//        viewPager.setAdapter(new CustomPagerAdapter(getSupportFragmentManager(), null));
//        viewPager.setOffscreenPageLimit(5);

        ArrayList<NewsModel> news_element = new ArrayList<>();
        for (int x = 0; x < 5; x++){

            NewsModel model = new NewsModel();
            model.setTitle("iygu");
            news_element.add(model);


        }

        ViewPager viewPager = findViewById(R.id.viewpager_news);
        CustomPagerAdapter heroDetailAdapter = new CustomPagerAdapter(getSupportFragmentManager(), news_element);
        viewPager.setOffscreenPageLimit(5);

        viewPager.setAdapter(heroDetailAdapter);



    }
}
