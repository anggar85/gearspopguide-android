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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.mxlapps.app.gearspopguide.Adapter.PinsAdapter;
import com.mxlapps.app.gearspopguide.Model.PinModel;
import com.mxlapps.app.gearspopguide.R;
import com.mxlapps.app.gearspopguide.Request.DataMaster;
import com.mxlapps.app.gearspopguide.Service.Resource;
import com.mxlapps.app.gearspopguide.Utils.FilterAndSort;
import com.mxlapps.app.gearspopguide.Utils.Filters;
import com.mxlapps.app.gearspopguide.Utils.Util;
import com.mxlapps.app.gearspopguide.ViewModel.PinViewModel;

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
    // checkboxes
    private CheckBox rarity_legendary = null;
    private CheckBox rarity_ascend = null;
    private CheckBox rarity_common = null;

    private CheckBox classe_Agility = null;
    private CheckBox classe_Intelligence = null;
    private CheckBox classe_Strength = null;

    private CheckBox race_name_wilders = null;
    private CheckBox race_name_Maulers = null;
    private CheckBox race_name_Lightbearers = null;
    private CheckBox race_name_Hypogean = null;
    private CheckBox race_name_Celestian = null;
    private CheckBox race_name_Graveborn = null;

    private String GAME_LEVEL_EARLY = "tier_list_earlies";
    private String GAME_LEVEL_MID = "tier_list_mids";
    private String GAME_LEVEL_LATE = "tier_list_lates";
    private String OVERALL = "overall";
    private String PVP = "pvp";
    private String PVE = "pve";
    private String LAB = "lab";
    private String WRIZZ = "wrizz";
    private String SOREN = "soren";
    private String SECTION = "overall";
    private String GAME_LEVEL = "tier_list_earlies";

    private static int LAYOUT_GRID = 1;

    private NavigationView navigationView;

    private PinViewModel pinViewModel;

    DrawerLayout drawer;


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

        requestCargarListaDeHeroes(GAME_LEVEL_EARLY, SECTION);

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



    private String read(Context context, String fileName) {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (FileNotFoundException fileNotFound) {
            return null;
        } catch (IOException ioException) {
            return null;
        }
    }

    private boolean create(Context context, String fileName, String jsonString){
        String FILENAME = "hero_list.json";
        try {
            FileOutputStream fos = context.openFileOutput(fileName,Context.MODE_PRIVATE);
            if (jsonString != null) {
                fos.write(jsonString.getBytes());
            }
            fos.close();
            return true;
        } catch (FileNotFoundException fileNotFound) {
            return false;
        } catch (IOException ioException) {
            return false;
        }

    }

    public boolean isFilePresent(Context context, String fileName) {
        String path = context.getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(path);
        return file.exists();
    }


    private void initRecyclerView(final ArrayList<PinModel> pinModelsInternal) {
        RecyclerView recyclerView = v.findViewById(R.id.recyclerview_hero_list);
        // Recibe un arrayList de productosy la bandera si se quiere mostrar/ocultar el checkbox
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        int numberOfColumns = 4;

        PinsAdapter adapter = new PinsAdapter(pinModelsInternal, getActivity(), 1);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        recyclerView.setAdapter(adapter);

        adapter.SetOnItemClickListener(new PinsAdapter.OnItemClickListener() {
            @Override
            public void onHeroCardClick(int position) {
//                Util.startLoading(rootView);
//                Intent intent = new Intent(getActivity(), HeroDetailV2.class);
//                intent.putExtra("hero_id", pinModelsInternal.get(position).getId());
//                startActivityForResult(intent, 50);

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

                        Gson gson = new Gson();
                        String json = gson.toJson(dataMasterResource.data.getData());
                        Log.d(TAG, "initRecyclerView: no existe vamos a crearlo");
                        boolean isFilePresent = isFilePresent(getActivity(), "hero_list.json");
                        if(isFilePresent) {
                            String jsonString = read(getActivity(), "hero_list.json");
                            //do the json parsing here and do the rest of functionality of app
                            Log.d(TAG, "initRecyclerView: si existe, hay que leerlo o actualizarlo");
                        } else {
                            boolean isFileCreated = create(getActivity(), "hero_list.json", json);
                            if(isFileCreated) {
                                Log.d(TAG, "initRecyclerView: se creooo");
                                //proceed with storing the first todo  or show ui
                            } else {
                                Log.d(TAG, "initRecyclerView: no se pudo crear, hubo un error");
                                //show error or try again.
                            }
                        }

                }
                break;
            default:
                break;
        }
    }

    private void initListadoHeroes() {
        initRecyclerView(pinModels);
        initFilter();
        clearFilters();

    }

    private void initFilter() {
        View parentView = navigationView.getHeaderView(0);
        // Filtros
        Button button_reset_filters = parentView.findViewById(R.id.button_reset_filters);
        button_reset_filters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFilters();
//                refreshHeroes();
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

    private void requestCargarListaDeHeroes(String gameLevel, String section) {
//        boolean isFilePresent = isFilePresent(getActivity(), "hero_list.json");
//        if(isFilePresent) {
//            String jsonString = read(getActivity(), "hero_list.json");
//            //do the json parsing here and do the rest of functionality of app
//            Gson gson = new Gson();
//            Data hM = gson.fromJson(jsonString, Data.class);
//            Log.d(TAG, "initRecyclerView: si existe, hay que leerlo");
//            initRecyclerView(hM.getPinModel(),1);
//        } else {
            pinViewModel.getHeroList(gameLevel, section, "All", "All", "All").observe(this, new Observer<Resource<DataMaster>>() {
                @Override
                public void onChanged(Resource<DataMaster> dataMasterResource) {
                    procesaRespuesta(dataMasterResource, 1);
                }
            });
//        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }



}
