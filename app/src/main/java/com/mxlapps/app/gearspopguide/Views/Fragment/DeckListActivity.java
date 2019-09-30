package com.mxlapps.app.gearspopguide.Views.Fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mxlapps.app.gearspopguide.Adapter.DecksAdapter;
import com.mxlapps.app.gearspopguide.BuildConfig;
import com.mxlapps.app.gearspopguide.Model.DeckModel;
import com.mxlapps.app.gearspopguide.R;
import com.mxlapps.app.gearspopguide.Request.DataMaster;
import com.mxlapps.app.gearspopguide.Service.Resource;
import com.mxlapps.app.gearspopguide.Utils.Util;
import com.mxlapps.app.gearspopguide.ViewModel.DecksViewModel;
import com.mxlapps.app.gearspopguide.ViewModel.PinViewModel;
import com.mxlapps.app.gearspopguide.Views.PinListActivity;

import java.util.ArrayList;

public class DeckListActivity extends AppCompatActivity {

    private static final String TAG = "afkArenaMainActivity";
    private ArrayList<DeckModel> deckModels;
    private View rootView;
    View v;
    private DecksViewModel decksViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_list);

        rootView = getWindow().getDecorView().findViewById(android.R.id.content);

//        ((AppCompatActivity) DeckListActivity.this).getSupportActionBar().setTitle("Gears Pop Decks");

        // ViewModels
        decksViewModel = ViewModelProviders.of(DeckListActivity.this).get(DecksViewModel.class);

        requestCargarListaDecks();

        FloatingActionButton button_drawer_filtros = findViewById(R.id.button_crear_deck);
        button_drawer_filtros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                MyDecksFragment newGamefragment = new MyDecksFragment();
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, newGamefragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();

            }
        });


        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_deckLIst);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            toolbar.setTitle("Gears Pop Guide - Decks");
        }


        MobileAds.initialize(this, BuildConfig.AD_LIST);
        AdView mAdView = findViewById(R.id.adViewListado);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



    }


    private void initRecyclerView(final ArrayList<DeckModel> deckModelsInternal) {
        RecyclerView recyclerView = findViewById(R.id.recyclerview_deck_list);

        int numberOfColumns = 1;
        DecksAdapter adapter = new DecksAdapter(deckModelsInternal, DeckListActivity.this, 1);
        recyclerView.setLayoutManager(new GridLayoutManager(DeckListActivity.this, numberOfColumns));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        adapter.SetOnItemClickListener(new DecksAdapter.OnItemClickListener() {
            @Override
            public void onDeckCardClick(int position) {
                Log.d(TAG, "onDeckCardClick: click en deck");

//                ShowDecksFragment showDecksFragment = new ShowDecksFragment(deckModelsInternal.get(position));
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, showDecksFragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
            }
        });
    }

    private void requestCargarListaDecks() {
        decksViewModel.getDecks().observe(this, new Observer<Resource<DataMaster>>() {
            @Override
            public void onChanged(Resource<DataMaster> dataMasterResource) {
                procesaRespuesta(dataMasterResource, 1);
            }
        });
    }

    private void procesaRespuesta(Resource<DataMaster> dataMasterResource, int opcion) {
        /* Este opcion va a poder procesar la respuesta del server y ejecutar el callback dependiendo del parametro @opcion*/
        switch (dataMasterResource.status) {
            case ERROR:
                Util.stopLoading(rootView);
                Util.alertaMensajeCtx(dataMasterResource.message, DeckListActivity.this);
                break;
            case LOADING:
                Util.startLoading(rootView);
                break;
            case SUCCESS:
                Util.stopLoading(rootView);
                // Crea copia de listado de heroes
                switch (opcion) {
                    case 1:
                        deckModels = dataMasterResource.data.getData().getDecks();
                        initRecyclerView(deckModels);
                }
                break;
            default:
                break;
        }
    }

}
