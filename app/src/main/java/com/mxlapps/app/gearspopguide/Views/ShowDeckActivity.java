package com.mxlapps.app.gearspopguide.Views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.mxlapps.app.gearspopguide.Adapter.DeckCommentsAdapter;
import com.mxlapps.app.gearspopguide.BuildConfig;
import com.mxlapps.app.gearspopguide.Model.CommentsModel;
import com.mxlapps.app.gearspopguide.Model.DeckModel;
import com.mxlapps.app.gearspopguide.R;
import com.mxlapps.app.gearspopguide.Request.DataMaster;
import com.mxlapps.app.gearspopguide.Service.Resource;
import com.mxlapps.app.gearspopguide.Utils.Util;
import com.mxlapps.app.gearspopguide.ViewModel.DecksViewModel;
import com.mxlapps.app.gearspopguide.ViewModel.PinViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShowDeckActivity extends AppCompatActivity {

    private static final String TAG = "afkArenaMainActivity";
    private DeckModel deck;
    private ArrayList<CommentsModel> commentsModel = new ArrayList<>();
    private View rootView;
    View v;
    private DecksViewModel decksViewModel;

    ImageView imageViewDeck1;
    ImageView imageViewDeck2;
    ImageView imageViewDeck3;
    ImageView imageViewDeck4;
    ImageView imageViewDeck5;
    ImageView imageViewDeck6;
    ImageView imageViewDeck7;
    ImageView imageViewDeck8;
    TextView textView_cost;
    TextView textView_deckName1;
    TextView textView_deck_description;
    TextView textView_autor;
    Button button_voteUp;
    RecyclerView recycler_comments;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_deck);

        rootView = getWindow().getDecorView().findViewById(android.R.id.content);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_pinLIst);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            toolbar.setTitle("Gears Pop Guide - Pins");
        }

        // Revisa si se esta llegando desde una factura
        Intent intent = getIntent();
        if (intent.hasExtra("deck_data")) {
            String deck = intent.getStringExtra("deck_data");
            Gson gson = new Gson();
            this.deck =  gson.fromJson(deck, DeckModel.class);
        }

        // ViewModels
        decksViewModel = ViewModelProviders.of(ShowDeckActivity.this).get(DecksViewModel.class);

//        MobileAds.initialize(this, BuildConfig.AD_LIST);
//        AdView mAdView = findViewById(R.id.adViewListado);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        initViews();

        cargaInformacionDeck();
        requestShowDeck();

        initRecyclerView();
    }


    private void cargaInformacionDeck() {

        // Carga Imagenes
        Picasso.get().load(deck.getPin1()).into(imageViewDeck1);
        Picasso.get().load(deck.getPin2()).into(imageViewDeck2);
        Picasso.get().load(deck.getPin3()).into(imageViewDeck3);
        Picasso.get().load(deck.getPin4()).into(imageViewDeck4);
        Picasso.get().load(deck.getPin5()).into(imageViewDeck5);
        Picasso.get().load(deck.getPin6()).into(imageViewDeck6);
        Picasso.get().load(deck.getPin7()).into(imageViewDeck7);
        Picasso.get().load(deck.getPin8()).into(imageViewDeck8);

        textView_cost.setText(deck.getCost());
        textView_deckName1.setText(deck.getName());
        textView_deck_description.setText(deck.getDesc());
        textView_autor.setText(deck.getAuthor());
    }

    private void initViews() {

        textView_cost  = findViewById(R.id.textView_cost);
        textView_deckName1  = findViewById(R.id.textView_deckName1);
        textView_deck_description  = findViewById(R.id.textView_deck_description);
        textView_autor  = findViewById(R.id.textView_autor);
        imageViewDeck1 = findViewById(R.id.imageViewDeck1);
        imageViewDeck2 = findViewById(R.id.imageViewDeck2);
        imageViewDeck3 = findViewById(R.id.imageViewDeck3);
        imageViewDeck4 = findViewById(R.id.imageViewDeck4);
        imageViewDeck5 = findViewById(R.id.imageViewDeck5);
        imageViewDeck6 = findViewById(R.id.imageViewDeck6);
        imageViewDeck7 = findViewById(R.id.imageViewDeck7);
        imageViewDeck8 = findViewById(R.id.imageViewDeck8);
        button_voteUp = findViewById(R.id.button_voteUp);
        recycler_comments = findViewById(R.id.recycler_comments);


    }


    private void requestShowDeck() {
        decksViewModel.show_deck(deck.getId()).observe(this, new Observer<Resource<DataMaster>>() {
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
                Util.alertaMensajeCtx(dataMasterResource.message, ShowDeckActivity.this);
                break;
            case LOADING:
                Util.startLoading(rootView);
                break;
            case SUCCESS:
                Util.stopLoading(rootView);
                // Crea copia de listado de heroes
                switch (opcion) {
                    case 1:
                        commentsModel = dataMasterResource.data.getData().getDeckComments();
                }
                break;
            default:
                break;
        }
    }

    
    private void initRecyclerView() {

        for (int x = 0; x < 15; x++){

            CommentsModel comment = new CommentsModel();
            comment.setComment("fyguhbjuyg hi g iu ggh iu erfg i ger g egrgerg ergu g");
            comment.setUser("ANgel " + x);

            commentsModel.add(comment);


        }


        int numberOfColumns = 1;
        DeckCommentsAdapter adapter = new DeckCommentsAdapter(commentsModel, ShowDeckActivity.this, 1);
        recycler_comments.setLayoutManager(new GridLayoutManager(ShowDeckActivity.this, numberOfColumns));
        recycler_comments.setNestedScrollingEnabled(false);
        recycler_comments.setHasFixedSize(true);
        recycler_comments.setAdapter(adapter);



    }

}
