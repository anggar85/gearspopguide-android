package com.mxlapps.app.gearspopguide.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
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

        PinModel pinModel =  gson.fromJson(pinData, PinModel.class);

        initDetalleCargarInfo();

        Log.d("ddojdojd", "onCreate: dddd");


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

        ImageView imageView = findViewById(R.id.imageView_pinImage);
        TextView name = findViewById(R.id.textView_pin_name);
        TextView desc = findViewById(R.id.textView_pin_desc);
        CardView cardViewSkill = findViewById(R.id.card_view_has_skill);

        ImageView imageViewStrong1 =  findViewById(R.id.imageView_strong1);
        ImageView imageViewStrong2 =  findViewById(R.id.imageView_strong2);
        ImageView imageViewStrong3 =  findViewById(R.id.imageView_strong3);

        TextView strong1 = findViewById(R.id.textView_strong1);
        TextView strong2 = findViewById(R.id.textView_strong2);
        TextView strong3 = findViewById(R.id.textView_strong3);

        ImageView imageViewWeak1 =  findViewById(R.id.imageView_strong1);
        ImageView imageViewWeak2 =  findViewById(R.id.imageView_strong2);
        ImageView imageViewWeak3 =  findViewById(R.id.imageView_strong3);

        TextView weak1 = findViewById(R.id.textView_strong1);
        TextView weak2 = findViewById(R.id.textView_strong2);
        TextView weak3 = findViewById(R.id.textView_strong3);


//        Picasso.get().load(pin.getSmallImage()).into(imageView);



    }


}
