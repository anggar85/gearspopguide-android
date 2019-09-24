package com.mxlapps.app.gearspopguide.Views.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mxlapps.app.gearspopguide.Adapter.PinsAdapter;
import com.mxlapps.app.gearspopguide.Model.PinModel;
import com.mxlapps.app.gearspopguide.R;
import com.mxlapps.app.gearspopguide.Request.DataMaster;
import com.mxlapps.app.gearspopguide.Service.Resource;
import com.mxlapps.app.gearspopguide.Utils.FilterAndSort;
import com.mxlapps.app.gearspopguide.Utils.Filters;
import com.mxlapps.app.gearspopguide.Utils.Util;
import com.mxlapps.app.gearspopguide.ViewModel.PinViewModel;
import com.mxlapps.app.gearspopguide.Views.DetailActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ListPinsFragment extends Fragment {

    private static final String TAG = "afkArenaMainActivity";
    private ArrayList<PinModel> pinModels;
    private View rootView;
    View v;
    private Filters filters = null;
    private String GAME_LEVEL_EARLY = "tier_list_earlies";
    private String SECTION = "overall";
    private NavigationView navigationView;
    private PinViewModel pinViewModel;
    DrawerLayout drawer;

    // variables para filtrar
    private String RACE = "All";
    private String ROLE = "All";
    private String TYPE = "All";
    private String COVER = "All";

    private Boolean isFilterReset = false;


    public ListPinsFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = container.getRootView();
        v = inflater.inflate(R.layout.fragment_list_heroes, container, false);
        drawer = v.findViewById(R.id.coordinatorLayout_listado_heroes);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Gears Pop Guide");

        // ViewModels
        pinViewModel = ViewModelProviders.of(getActivity()).get(PinViewModel.class);
//        UserViewModel userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);

        eventsRightDrawer();

        requestCargarListaDeHeroes();

        initFilter();

        return v;

    }


    private void eventsRightDrawer() {
        // Eventos en drawer derecho - filtros, etc
        drawer = v.findViewById(R.id.coordinatorLayout_listado_heroes);
        navigationView = v.findViewById(R.id.nav_view_filter);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        });


        FloatingActionButton button_drawer_filtros = v.findViewById(R.id.button_drawer_filtros);
        button_drawer_filtros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.RIGHT);
            }
        });
    }



    private void initRecyclerView(final ArrayList<PinModel> pinModelsInternal) {
        RecyclerView recyclerView = v.findViewById(R.id.recyclerview_hero_list);

        int numberOfColumns = 3;

        PinsAdapter adapter = new PinsAdapter(pinModelsInternal, getActivity(), 1);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

        adapter.SetOnItemClickListener(new PinsAdapter.OnItemClickListener() {
            @Override
            public void onHeroCardClick(int position) {
                Util.startLoading(rootView);
                Intent intent = new Intent(getActivity(), DetailActivity.class);

                // Convierte objeto a string
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                String pinJson = gson.toJson(pinModelsInternal.get(position));


                intent.putExtra("pin_data", pinJson);
                startActivityForResult(intent, 50);
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
//        initFilter();
//        clearFilters();

    }

    private void initFilter() {
        final View parentView = navigationView.getHeaderView(0);
        // Filtros

        final RadioGroup radioGroupRace = parentView.findViewById(R.id.race_group);
        final RadioGroup radioGroupRole = parentView.findViewById(R.id.role_group);
        final RadioGroup radioGroupType = parentView.findViewById(R.id.type_group);
        final RadioGroup radioGroupCover = parentView.findViewById(R.id.cover_group);



        Button button_reset_filters = parentView.findViewById(R.id.button_reset_filters);
        button_reset_filters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFilters();
                Log.d(TAG, "onClick: limpiar filtrooss");

                isFilterReset = true;
//                refreshHeroes();
                RACE = "All";
                ROLE = "All";
                TYPE = "All";
                COVER = "All";
                radioGroupRace.check(R.id.race_all);
                radioGroupRole.check(R.id.role_all);
                radioGroupType.check(R.id.type_all);
                radioGroupCover.check(R.id.cover_all);
                requestCargarListaDeHeroes();

                isFilterReset = false;

            }
        });


        radioGroupRace.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch (checkedId){
                    case R.id.race_all:
                        RACE = "All";
                        break;
                    case R.id.race_cgo:
                        RACE = "cgo";
                        break;
                    case R.id.race_locust:
                        RACE = "locust";
                        break;
                }

                if (!isFilterReset)
                    requestCargarListaDeHeroes();
            }
        });


        radioGroupRole.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch (checkedId){
                    case R.id.role_all:
                        ROLE = "All";
                        break;
                    case R.id.role_Bruiser:
                        ROLE = "Bruiser";
                        break;
                    case R.id.role_removal:
                        ROLE = "Removal";
                        break;
                    case R.id.role_scout:
                        ROLE = "Scout";
                        break;
                    case R.id.role_tank:
                        ROLE = "Tank";
                        break;
                    case R.id.role_threat:
                        ROLE = "Threat";
                        break;
                    case R.id.role_utility:
                        ROLE = "Utility";
                        break;
                }
                if (!isFilterReset)
                    requestCargarListaDeHeroes();
            }
        });


        radioGroupType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch (checkedId){
                    case R.id.type_all:
                        TYPE = "All";
                        break;
                    case R.id.type_common:
                        TYPE = "Common";
                        break;
                    case R.id.type_epic:
                        TYPE = "Epic";
                        break;
                    case R.id.type_rare:
                        TYPE = "Rare";
                        break;
                    case R.id.type_legendary:
                        TYPE = "Legendary";
                        break;
                }
                if (!isFilterReset)
                    requestCargarListaDeHeroes();
            }
        });


        radioGroupCover.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch (checkedId){
                    case R.id.cover_all:
                        COVER = "All";
                        break;
                    case R.id.cover_yes:
                        COVER = "0";
                        break;
                    case R.id.cover_no:
                        COVER = "1";
                        break;
                }
                if (!isFilterReset)
                    requestCargarListaDeHeroes();
            }
        });








    }



    private void refreshHeroes() {
        ArrayList<PinModel> heroListClone = FilterAndSort.filterHeroes(pinModels, filters);
        initRecyclerView(heroListClone);
    }

    private void clearFilters() {
        //Stars


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
