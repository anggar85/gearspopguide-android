package com.mxlapps.app.gearspopguide.Views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

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
import com.squareup.picasso.Picasso;

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

        Toolbar toolbar = findViewById(R.id.toolbar_pinLIst);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("Gears Pop Guide");
            getSupportActionBar().setSubtitle("All Pins");
        }

        // ViewModels
        pinViewModel = ViewModelProviders.of(PinListActivity.this).get(PinViewModel.class);

        // Util.initAds(rootView, this, BuildConfig.AD_DETAIL);

        eventsRightDrawer();

        requestCargarListaDeHeroes();

        initFilter();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.menu_settings:
                drawer.openDrawer(Gravity.RIGHT);
                break;


        }
        return super.onOptionsItemSelected(item);

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
                initDetalleCargarInfo(pinModelsInternal.get(position));
            }

        });
        initDetalleCargarInfo(pinModelsInternal.get(0));
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


    private void initDetalleCargarInfo(PinModel pin) {

        ImageView imageView_pinImage = findViewById(R.id.imageView_pinImage);
        TextView name = findViewById(R.id.textView_Name);
        TextView desc = findViewById(R.id.textView_pin_desc);
        CardView cardViewSkill = findViewById(R.id.card_view_has_skill);
        TextView skillname = findViewById(R.id.textView_skillName);
        TextView skillDesc = findViewById(R.id.textView_skillDesc);


        TextView btn_race = findViewById(R.id.button_race);
        TextView btn_type = findViewById(R.id.button_type);
        TextView btn_role = findViewById(R.id.button_role);

        FrameLayout frameLayout_s1 =  findViewById(R.id.frameLayout_s1);
        FrameLayout frameLayout_s2 =  findViewById(R.id.frameLayout_s2);
        FrameLayout frameLayout_s3 =  findViewById(R.id.frameLayout_s3);

        ImageView imageViewStrong1 =  findViewById(R.id.imageView_strong1);
        ImageView imageViewStrong2 =  findViewById(R.id.imageView_strong2);
        ImageView imageViewStrong3 =  findViewById(R.id.imageView_strong3);


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
            cardViewSkill.setVisibility(View.VISIBLE);
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
