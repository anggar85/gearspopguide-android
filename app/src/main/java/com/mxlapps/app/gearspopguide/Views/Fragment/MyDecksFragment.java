package com.mxlapps.app.gearspopguide.Views.Fragment;

import android.content.Intent;
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
import android.widget.RadioGroup;

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

import java.util.ArrayList;


public class MyDecksFragment extends Fragment {

    private static final String TAG = "afkArenaMainActivity";
    private ArrayList<PinModel> pinModels;
    private View rootView;
    View v;
    private PinViewModel pinViewModel;
    DrawerLayout drawer;

    // variables para filtrar
    private String RACE = "All";
    private String ROLE = "All";
    private String TYPE = "All";
    private String COVER = "All";


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

        return v;
    }




    private void initRecyclerView(final ArrayList<PinModel> pinModelsInternal) {
        RecyclerView recyclerView = v.findViewById(R.id.recycler_pinListDeck);
        int numberOfColumns = 6;
        PinsDeckAdapter adapter = new PinsDeckAdapter(pinModelsInternal, getActivity(), 1);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        adapter.SetOnItemClickListener(new PinsDeckAdapter.OnItemClickListener() {
            @Override
            public void onHeroCardClick(int position) {
                Log.d(TAG, "onHeroCardClick: with pin ->" + pinModelsInternal.get(position).getName());
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


}
