package com.mxlapps.app.gearspopguide.Views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mxlapps.app.gearspopguide.Adapter.PinsAdapter;
import com.mxlapps.app.gearspopguide.BuildConfig;
import com.mxlapps.app.gearspopguide.Model.PinModel;
import com.mxlapps.app.gearspopguide.R;
import com.mxlapps.app.gearspopguide.Request.DataMaster;
import com.mxlapps.app.gearspopguide.Service.Resource;
import com.mxlapps.app.gearspopguide.Utils.Util;
import com.mxlapps.app.gearspopguide.ViewModel.PinViewModel;

import java.util.ArrayList;

public class PinListActivity extends AppCompatActivity {

    private static final String TAG = "afkArenaMainActivity";
    private ArrayList<PinModel> pinModels;
    private View rootView;
    View v;
    private NavigationView navigationView;
    private PinViewModel pinViewModel;
    DrawerLayout drawer;

    // variables para filtrar
    private String RACE = "All";
    private String ROLE = "All";
    private String TYPE = "All";
    private String COVER = "All";

    private Boolean isFilterReset = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_list);


        rootView = getWindow().getDecorView().findViewById(android.R.id.content);

        drawer = findViewById(R.id.coordinatorLayout_listado_heroes);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_pinLIst);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            toolbar.setTitle("Gears Pop Guide - Pins");
        }


        // ViewModels
        pinViewModel = ViewModelProviders.of(PinListActivity.this).get(PinViewModel.class);

        MobileAds.initialize(this, BuildConfig.AD_LIST);
        AdView mAdView = findViewById(R.id.adViewListado);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        eventsRightDrawer();

        requestCargarListaDeHeroes();

        initFilter();


    }



    private void eventsRightDrawer() {
        // Eventos en drawer derecho - filtros, etc
        drawer = findViewById(R.id.coordinatorLayout_listado_heroes);
        navigationView = findViewById(R.id.nav_view_filter);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        });


        FloatingActionButton button_drawer_filtros = findViewById(R.id.button_drawer_filtros);
        button_drawer_filtros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.RIGHT);
            }
        });
    }



    private void initRecyclerView(final ArrayList<PinModel> pinModelsInternal) {
        RecyclerView recyclerView = findViewById(R.id.recyclerview_hero_list);

        int numberOfColumns = 1;

        PinsAdapter adapter = new PinsAdapter(pinModelsInternal, PinListActivity.this, 1);

        recyclerView.setLayoutManager(new GridLayoutManager(PinListActivity.this, numberOfColumns));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

        adapter.SetOnItemClickListener(new PinsAdapter.OnItemClickListener() {
            @Override
            public void onHeroCardClick(int position) {
//                Util.startLoading(rootView);
                Intent intent = new Intent(PinListActivity.this, PinDetailActivity.class);

                // Convierte objeto a string
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                String pinJson = gson.toJson(pinModelsInternal.get(position));

                intent.putExtra("pin_data", pinJson);
                startActivityForResult(intent, 50);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }

        });
    }




    private void procesaRespuesta(Resource<DataMaster> dataMasterResource, int opcion) {
        /* Este opcion va a poder procesar la respuesta del server y ejecutar el callback dependiendo del parametro @opcion*/
        switch (dataMasterResource.status) {
            case ERROR:
                Util.stopLoading(rootView);
                Util.alertaMensajeCtx(dataMasterResource.message, PinListActivity.this);
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
                Log.d(TAG, "onClick: limpiar filtrooss");
                isFilterReset = true;
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


    private void requestCargarListaDeHeroes() {
        pinViewModel.getPinList(RACE, ROLE, TYPE, COVER).observe(this, new Observer<Resource<DataMaster>>() {
            @Override
            public void onChanged(Resource<DataMaster> dataMasterResource) {
                procesaRespuesta(dataMasterResource, 1);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Util.stopLoading(rootView);

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
