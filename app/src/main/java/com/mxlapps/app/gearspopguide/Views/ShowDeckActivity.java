package com.mxlapps.app.gearspopguide.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.mxlapps.app.gearspopguide.Adapter.DeckCommentsAdapter;
import com.mxlapps.app.gearspopguide.BuildConfig;
import com.mxlapps.app.gearspopguide.Model.CommentsModel;
import com.mxlapps.app.gearspopguide.Model.DeckModel;
import com.mxlapps.app.gearspopguide.R;
import com.mxlapps.app.gearspopguide.Request.DataMaster;
import com.mxlapps.app.gearspopguide.Service.Resource;
import com.mxlapps.app.gearspopguide.Utils.AppPreferences;
import com.mxlapps.app.gearspopguide.Utils.Util;
import com.mxlapps.app.gearspopguide.ViewModel.DecksViewModel;
import com.mxlapps.app.gearspopguide.ViewModel.ExtraViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShowDeckActivity extends AppCompatActivity {

    private static final String TAG = "afkArenaMainActivity";
    private DeckModel deck;
    private ArrayList<CommentsModel> commentsModel = new ArrayList<>();
    private View rootView;
    View v;
    private DecksViewModel decksViewModel;
    private ExtraViewModel extraViewModel;
    DeckCommentsAdapter adapter;

    ImageView imageViewDeck1;
    ImageView imageViewDeck2;
    ImageView imageViewDeck3;
    ImageView imageViewDeck4;
    ImageView imageViewDeck5;
    ImageView imageViewDeck6;
    ImageView imageViewDeck7;
    ImageView imageViewDeck8;
    ImageView imageView_like;
    TextView textView_cost;
    TextView textView_deckName1;
    TextView textView_deck_description;
    TextView textView_autor;
    TextInputEditText textInputEditText_nuevo_comentario;
    RecyclerView recycler_comments;



    @SuppressLint("ClickableViewAccessibility")
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
        extraViewModel = ViewModelProviders.of(ShowDeckActivity.this).get(ExtraViewModel.class);

        MobileAds.initialize(this, BuildConfig.AD_LIST);
        AdView mAdView = findViewById(R.id.ads_detail_deck);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        initViews();

        cargaInformacionDeck();
        requestShowDeck();


        textInputEditText_nuevo_comentario.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (textInputEditText_nuevo_comentario.getRight() - textInputEditText_nuevo_comentario.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        if (textInputEditText_nuevo_comentario.getText().toString().compareToIgnoreCase("") == 0){
                            Toast.makeText(ShowDeckActivity.this, "Write a comment", Toast.LENGTH_SHORT).show();
                        }else{
                            Log.d(TAG, "onTouch: se trata de enviar comentario");
                            CommentsModel comment = new CommentsModel();
                            comment.setItem_id(deck.getId());
                            comment.setSection("decks");
                            comment.setComment(textInputEditText_nuevo_comentario.getText().toString());
                            comment.setUser(String.valueOf(AppPreferences.getInstance(ShowDeckActivity.this).getUsuario_id()));
                            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                            extraViewModel.createComment(comment).observe(ShowDeckActivity.this, new Observer<Resource<DataMaster>>() {
                                @Override
                                public void onChanged(Resource<DataMaster> dataMasterResource) {
                                    procesaRespuesta(dataMasterResource, 2);
                                }
                            });
                        }
                        return true;
                    }
                }
                return false;
            }
        });


        imageView_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


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

        textView_cost.setText("Energy: " + deck.getCost());
        textView_deckName1.setText(deck.getName());
        textView_deck_description.setText(deck.getDesc());
        textView_autor.setText("By: " + deck.getAuthor());
    }

    private void initViews() {

        textInputEditText_nuevo_comentario  = findViewById(R.id.textInputEditText_nuevo_comentario);
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
        imageView_like = findViewById(R.id.imageView_like);
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
                        commentsModel = dataMasterResource.data.getData().getDeck().getComments();
                        initRecyclerViewComentarios();
                        break;
                    case 2:

                        commentsModel = dataMasterResource.data.getData().getComments();
                        initRecyclerViewComentarios();

                        textInputEditText_nuevo_comentario.setText("");

                        Toast.makeText(this, "Comments Added!", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            default:
                break;
        }
    }

    
    private void initRecyclerViewComentarios() {
        if (commentsModel.size() > 0){
            int numberOfColumns = 1;
            adapter = new DeckCommentsAdapter(commentsModel, ShowDeckActivity.this, 1);
            recycler_comments.setLayoutManager(new GridLayoutManager(ShowDeckActivity.this, numberOfColumns));
            recycler_comments.setNestedScrollingEnabled(false);
            recycler_comments.setHasFixedSize(true);
            recycler_comments.setAdapter(adapter);
        }else{
            Log.d(TAG, "initRecyclerViewComentarios: ----------------------------><");
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
