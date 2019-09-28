package com.mxlapps.app.gearspopguide.Views.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.mxlapps.app.gearspopguide.Model.NewsModel;
import com.mxlapps.app.gearspopguide.R;
import com.mxlapps.app.gearspopguide.ViewModel.DecksViewModel;

import java.util.ArrayList;

public class NewsElementFragment extends Fragment {

    private static final String TAG = "afkArenaMainActivity";
    private ArrayList<NewsModel> news_element;
    private View rootView;
    View v;
    private DecksViewModel decksViewModel;


    public NewsElementFragment() {
    }

//   public NewsElementFragment(ArrayList<NewsModel> news_element) {
//        this.news_element = news_element;
//    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = container.getRootView();
        v = inflater.inflate(R.layout.page_news, container, false);

//        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Gears Pop Deck");

        // ViewModels
        decksViewModel = ViewModelProviders.of(getActivity()).get(DecksViewModel.class);

        return v;

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }





}
