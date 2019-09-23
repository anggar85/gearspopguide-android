package com.mxlapps.app.gearspopguide.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.mxlapps.app.gearspopguide.BuildConfig;
import com.mxlapps.app.gearspopguide.Model.PinModel;
import com.mxlapps.app.gearspopguide.R;
import com.mxlapps.app.gearspopguide.Request.DataMaster;
import com.mxlapps.app.gearspopguide.Service.Resource;
import com.mxlapps.app.gearspopguide.Utils.Util;
import com.mxlapps.app.gearspopguide.ViewModel.PinViewModel;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private PinViewModel pinViewModel;
    private View rootView;
    private PinModel pin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        rootView = getWindow().getDecorView().findViewById(android.R.id.content);

//        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Gears Pop Guide");

        // ViewModels
        pinViewModel = ViewModelProviders.of(DetailActivity.this).get(PinViewModel.class);


        Intent intent = getIntent();
        String  pinData = intent.getStringExtra("pin_data");

        Gson gson = new Gson();

        this.pin =  gson.fromJson(pinData, PinModel.class);

        initDetalleCargarInfo();

        Log.d("ddojdojd", "onCreate: dddd");

        MobileAds.initialize(this, BuildConfig.AD_LIST);
        AdView mAdView = findViewById(R.id.adViewDetalle);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


//        pinViewModel.getHeroDetail(hero_id).observe(DetailActivity.this, new Observer<Resource<DataMaster>>() {
//            @Override
//            public void onChanged(Resource<DataMaster> dataMasterResource) {
//                procesaRespuesta(dataMasterResource, 1);
//            }
//        });


    }


    private void procesaRespuesta(Resource<DataMaster> dataMasterResource, int opcion) {
        /* Este opcion va a poder procesar la respuesta del server y ejecutar el callback dependiendo del parametro @opcion*/
        switch (dataMasterResource.status) {
            case ERROR:
                Util.stopLoading(rootView);
                Util.alertaMensajeCtx(dataMasterResource.message, DetailActivity.this);
                break;
            case LOADING:
                Util.startLoading(rootView);
                break;
            case SUCCESS:
                Util.stopLoading(rootView);
                // Crea copia de listado de heroes
                switch (opcion) {
                    case 1:
                        pin = dataMasterResource.data.getData().getPin();
                        initDetalleCargarInfo();
                }
                break;
            default:
                break;
        }
    }

    private void initDetalleCargarInfo() {

        ImageView imageView_pinImage = findViewById(R.id.imageView_pinImage);
        TextView name = findViewById(R.id.textView_Name);
        TextView desc = findViewById(R.id.textView_pin_desc);
        CardView cardViewSkill = findViewById(R.id.card_view_has_skill);
        TextView skillname = findViewById(R.id.textView_skillName);
        TextView skillDesc = findViewById(R.id.textView_skillDesc);


        Button btn_race = findViewById(R.id.button_race);
        Button btn_type = findViewById(R.id.button_type);
        Button btn_role = findViewById(R.id.button_role);

        FrameLayout frameLayout_s1 =  findViewById(R.id.frameLayout_s1);
        FrameLayout frameLayout_s2 =  findViewById(R.id.frameLayout_s2);
        FrameLayout frameLayout_s3 =  findViewById(R.id.frameLayout_s3);

        ImageView imageViewStrong1 =  findViewById(R.id.imageView_strong1);
        ImageView imageViewStrong2 =  findViewById(R.id.imageView_strong2);
        ImageView imageViewStrong3 =  findViewById(R.id.imageView_strong3);

//        TextView strong1 = findViewById(R.id.textView_strong1);
//        TextView strong2 = findViewById(R.id.textView_strong2);
//        TextView strong3 = findViewById(R.id.textView_strong3);

        // Weaks


        FrameLayout frameLayout_w1 =  findViewById(R.id.frameLayout_w1);
        FrameLayout frameLayout_w2 =  findViewById(R.id.frameLayout_w2);
        FrameLayout frameLayout_w3 =  findViewById(R.id.frameLayout_w3);

        ImageView imageViewWeak1 =  findViewById(R.id.imagesView_weak1);
        ImageView imageViewWeak2 =  findViewById(R.id.imageView_weak_2);
        ImageView imageViewWeak3 =  findViewById(R.id.imageView_weak3);

//        TextView weak1 = findViewById(R.id.textView_strong1);
//        TextView weak2 = findViewById(R.id.textView_strong2);
//        TextView weak3 = findViewById(R.id.textView_strong3);


        Picasso.get().load(pin.getSmallImage()).into(imageView_pinImage);
        name.setText(pin.getName());
        desc.setText(pin.getDesc());

        btn_race.setText(pin.getRace());
        btn_type.setText(pin.getType());
        btn_role.setText(pin.getRole());

        if (pin.getSkillName().compareToIgnoreCase("") != 0){
            // Tiene skill
            skillname.setText(pin.getSkillName());
            skillDesc.setText(pin.getSkillDesc());
        }else{
            cardViewSkill.setVisibility(View.GONE);
        }

        // Valida streng y weak
        if (pin.getStrong().size() > 0){
            Picasso.get().load(pin.getStrong().get(0)).into(imageViewStrong1);
            frameLayout_s1.setVisibility(View.VISIBLE);
        }

        if (pin.getStrong().size() > 1){
            Picasso.get().load(pin.getStrong().get(1)).into(imageViewStrong2);
            frameLayout_s2.setVisibility(View.VISIBLE);
        }

        if (pin.getStrong().size() > 2){
            Picasso.get().load(pin.getStrong().get(2)).into(imageViewStrong3);
            frameLayout_s3.setVisibility(View.VISIBLE);
        }

        // Weak
        if (pin.getWeak().size() > 0){
            Picasso.get().load(pin.getWeak().get(0)).into(imageViewWeak1);
            frameLayout_w1.setVisibility(View.VISIBLE);
        }

        if (pin.getWeak().size() > 1){
            Picasso.get().load(pin.getWeak().get(1)).into(imageViewWeak2);
            frameLayout_w2.setVisibility(View.VISIBLE);
        }

        if (pin.getWeak().size() > 2){
            Picasso.get().load(pin.getWeak().get(2)).into(imageViewWeak3);
            frameLayout_w3.setVisibility(View.VISIBLE);
        }






    }


}
