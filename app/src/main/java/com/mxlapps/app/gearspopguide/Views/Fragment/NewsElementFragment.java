package com.mxlapps.app.gearspopguide.Views.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mxlapps.app.gearspopguide.Adapter.DeckCommentsAdapter;
import com.mxlapps.app.gearspopguide.Model.DeckCommentsModel;
import com.mxlapps.app.gearspopguide.Model.DeckModel;
import com.mxlapps.app.gearspopguide.Model.NewsModel;
import com.mxlapps.app.gearspopguide.R;
import com.mxlapps.app.gearspopguide.Request.DataMaster;
import com.mxlapps.app.gearspopguide.Service.Resource;
import com.mxlapps.app.gearspopguide.Utils.Util;
import com.mxlapps.app.gearspopguide.ViewModel.DecksViewModel;
import com.squareup.picasso.Picasso;

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
