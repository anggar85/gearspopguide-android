package com.mxlapps.app.gearspopguide.Views.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mxlapps.app.gearspopguide.Adapter.PinsAdapter;
import com.mxlapps.app.gearspopguide.Adapter.PinsDeckAdapter;
import com.mxlapps.app.gearspopguide.Model.PinModel;
import com.mxlapps.app.gearspopguide.R;
import com.mxlapps.app.gearspopguide.Request.DataMaster;
import com.mxlapps.app.gearspopguide.Service.Resource;
import com.mxlapps.app.gearspopguide.Utils.Util;
import com.mxlapps.app.gearspopguide.ViewModel.PinViewModel;
import com.mxlapps.app.gearspopguide.Views.DetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MyDecksFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "afkArenaMainActivity";
    private ArrayList<PinModel> pinModels;
    private ArrayList<PinModel> pinSeleccionados;
    private Double cost;
    private int pinHolderSelected = 0;
    private ArrayList<Integer> pinIds = new ArrayList<>();
    private View rootView;
    View v;
    private PinViewModel pinViewModel;
    DrawerLayout drawer;

    // variables para filtrar
    private String RACE = "All";
    private String ROLE = "All";
    private String TYPE = "All";
    private String COVER = "All";

    ImageView imageViewDeck1;
    ImageView imageViewDeck2;
    ImageView imageViewDeck3;
    ImageView imageViewDeck4;
    ImageView imageViewDeck5;
    ImageView imageViewDeck6;
    ImageView imageViewDeck7;
    ImageView imageViewDeck8;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = container.getRootView();
        v =  inflater.inflate(R.layout.fragment_my_decks, container, false);
        drawer = v.findViewById(R.id.coordinatorLayout_listado_heroes);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Gears Pop Guide");
        // ViewModels
        pinViewModel = ViewModelProviders.of(getActivity()).get(PinViewModel.class);
        requestCargarListaDeHeroes();


        agreaListenrClickHolders();


        return v;
    }

    private void agreaListenrClickHolders() {
        imageViewDeck1 = v.findViewById(R.id.imageViewDeck1);
        imageViewDeck1.setOnClickListener(this);

        imageViewDeck2 = v.findViewById(R.id.imageViewDeck2);
        imageViewDeck2.setOnClickListener(this);

        imageViewDeck3 = v.findViewById(R.id.imageViewDeck3);
        imageViewDeck3.setOnClickListener(this);

        imageViewDeck4 = v.findViewById(R.id.imageViewDeck4);
        imageViewDeck4.setOnClickListener(this);

        imageViewDeck5 = v.findViewById(R.id.imageViewDeck5);
        imageViewDeck5.setOnClickListener(this);

        imageViewDeck6 = v.findViewById(R.id.imageViewDeck6);
        imageViewDeck6.setOnClickListener(this);

        imageViewDeck7 = v.findViewById(R.id.imageViewDeck7);
        imageViewDeck7.setOnClickListener(this);

        imageViewDeck8 = v.findViewById(R.id.imageViewDeck8);
        imageViewDeck8.setOnClickListener(this);
    }


    private void initRecyclerView(final ArrayList<PinModel> pinModelsInternal) {
        RecyclerView recyclerView = v.findViewById(R.id.recycler_pinListDeck);
        int numberOfColumns = 4;
        PinsDeckAdapter adapter = new PinsDeckAdapter(pinModelsInternal, getActivity(), 1);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        final int color = Color.parseColor("#00000000");
        adapter.SetOnItemClickListener(new PinsDeckAdapter.OnItemClickListener() {
            @Override
            public void onHeroCardClick(int position) {
                Log.d(TAG, "onHeroCardClick: " + pinIds.toString());
                // Valida si ya fue agregado el pin
                boolean yaEsta = false;
                for (int x = 0; x < pinIds.size(); x++){
                    if (pinModelsInternal.get(position).getId() ==  pinIds.get(x)){
                        yaEsta = true;
                        break;
                    }
                }

                if (!yaEsta){
                    // Solo si el pin no esta, continuamos y agregamos el id al array
                    Log.d(TAG, "onHeroCardClick: " + pinIds.toString());

                    if (pinHolderSelected != 0){
                        pinIds.add(pinModelsInternal.get(position).getId());
                        String imgLink = pinModelsInternal.get(position).getSmallImage();
                        switch (pinHolderSelected){
                            case 1:
                                Picasso.get().load(imgLink).into(imageViewDeck1);
                                break;
                            case 2:
                                Picasso.get().load(imgLink).into(imageViewDeck2);
                                break;
                            case 3:
                                Picasso.get().load(imgLink).into(imageViewDeck3);
                                break;
                            case 4:
                                Picasso.get().load(imgLink).into(imageViewDeck4);
                                break;
                            case 5:
                                Picasso.get().load(imgLink).into(imageViewDeck5);
                                break;
                            case 6:
                                Picasso.get().load(imgLink).into(imageViewDeck6);
                                break;
                            case 7:
                                Picasso.get().load(imgLink).into(imageViewDeck7);
                                break;
                            case 8:
                                Picasso.get().load(imgLink).into(imageViewDeck8);
                                break;
                        }
                        pinHolderSelected = 0;
                    }else{
                        Toast.makeText(getActivity(), "Please select a holder", Toast.LENGTH_SHORT).show();
                        pinHolderSelected = 0;
                    }
                }else{
                    // Ya existe, manda un mensaje
                    Toast.makeText(getActivity(), "You can't repeat pins", Toast.LENGTH_SHORT).show();
                    pinHolderSelected = 0;
                }

                imageViewDeck1.setBackgroundColor(color);
                imageViewDeck2.setBackgroundColor(color);
                imageViewDeck3.setBackgroundColor(color);
                imageViewDeck4.setBackgroundColor(color);
                imageViewDeck5.setBackgroundColor(color);
                imageViewDeck6.setBackgroundColor(color);
                imageViewDeck7.setBackgroundColor(color);
                imageViewDeck8.setBackgroundColor(color);
            }

        });

    }




    private void procesaRespuesta(Resource<DataMaster> dataMasterResource, int opcion) {
        /* Este opcion va a poder procesar la respuesta del server y ejecutar el callback dependiendo del parametro @opcion*/
        switch (dataMasterResource.status) {
            case ERROR:
                Util.stopLoading(rootView);
                Util.alertaMensajeCtx(dataMasterResource.message, getActivity());
                break;
            case LOADING:
                Util.startLoading(rootView);
                break;
            case SUCCESS:
                Util.stopLoading(rootView);
                // Crea copia de listado de heroes
                switch (opcion) {
                    case 1:
                        pinModels = dataMasterResource.data.getData().getPins();
                        initListadoHeroes();
                }
                break;
            default:
                break;
        }
    }

    private void initListadoHeroes() {
        initRecyclerView(pinModels);
    }


    private void requestCargarListaDeHeroes() {
        pinViewModel.getPinList(RACE, ROLE, TYPE, COVER).observe(this, new Observer<Resource<DataMaster>>() {
            @Override
            public void onChanged(Resource<DataMaster> dataMasterResource) {
                procesaRespuesta(dataMasterResource, 1);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onClick(View view) {
        int color = Color.parseColor("#5e43c9");
        switch (view.getId()){
            case R.id.imageViewDeck1:
                imageViewDeck1.setBackgroundColor(color);
                pinHolderSelected = 1;
                break;
            case R.id.imageViewDeck2:
                imageViewDeck2.setBackgroundColor(color);
                pinHolderSelected = 2;
                break;
            case R.id.imageViewDeck3:
                imageViewDeck3.setBackgroundColor(color);
                pinHolderSelected = 3;
                break;
            case R.id.imageViewDeck4:
                imageViewDeck4.setBackgroundColor(color);
                pinHolderSelected = 4;
                break;
            case R.id.imageViewDeck5:
                imageViewDeck5.setBackgroundColor(color);
                pinHolderSelected = 5;
                break;
            case R.id.imageViewDeck6:
                imageViewDeck6.setBackgroundColor(color);
                pinHolderSelected = 6;
                break;
            case R.id.imageViewDeck7:
                imageViewDeck7.setBackgroundColor(color);
                pinHolderSelected = 7;
                break;
            case R.id.imageViewDeck8:
                imageViewDeck8.setBackgroundColor(color);
                pinHolderSelected = 8;
                break;
        }
    }
}
