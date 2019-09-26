package com.mxlapps.app.gearspopguide.Views.Fragment;

import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mxlapps.app.gearspopguide.Adapter.DecksAdapter;
import com.mxlapps.app.gearspopguide.Model.DeckModel;
import com.mxlapps.app.gearspopguide.R;
import com.mxlapps.app.gearspopguide.Request.DataMaster;
import com.mxlapps.app.gearspopguide.Service.Resource;
import com.mxlapps.app.gearspopguide.Utils.Util;
import com.mxlapps.app.gearspopguide.ViewModel.DecksViewModel;

import java.util.ArrayList;

public class ListDecksFragment extends Fragment {

    private static final String TAG = "afkArenaMainActivity";
    private ArrayList<DeckModel> deckModels;
    private View rootView;
    View v;
    private DecksViewModel decksViewModel;

    // variables para filtrar
    private String RACE = "All";
    private String ROLE = "All";
    private String TYPE = "All";
    private String COVER = "All";



    public ListDecksFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = container.getRootView();
        v = inflater.inflate(R.layout.fragment_list_decks, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Gears Pop Decks");

        // ViewModels
        decksViewModel = ViewModelProviders.of(getActivity()).get(DecksViewModel.class);

        requestCargarListaDecks();

        FloatingActionButton button_drawer_filtros = v.findViewById(R.id.button_crear_deck);
        button_drawer_filtros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyDecksFragment newGamefragment = new MyDecksFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, newGamefragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();



            }
        });

        return v;

    }



    private void initRecyclerView(final ArrayList<DeckModel> deckModelsInternal) {
        RecyclerView recyclerView = v.findViewById(R.id.recyclerview_deck_list);

        int numberOfColumns = 1;
        DecksAdapter adapter = new DecksAdapter(deckModelsInternal, getActivity(), 1);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        adapter.SetOnItemClickListener(new DecksAdapter.OnItemClickListener() {
            @Override
            public void onDeckCardClick(int position) {
                Log.d(TAG, "onDeckCardClick: click en deck");
                //                Util.startLoading(rootView);
//                Intent intent = new Intent(getActivity(), DetailActivity.class);
//
//                // Convierte objeto a string
//                GsonBuilder builder = new GsonBuilder();
//                Gson gson = builder.create();
//
//                String pinJson = gson.toJson(deckModelsInternal.get(position));
//
//
//                intent.putExtra("pin_data", pinJson);
//                startActivityForResult(intent, 50);
//                Log.d(TAG, "onHeroCardClick: with pin ->" + deckModelsInternal.get(position).getName());
            }
        });
    }

    private void requestCargarListaDecks() {
        decksViewModel.getDecks().observe(this, new Observer<Resource<DataMaster>>() {
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
                        deckModels = dataMasterResource.data.getData().getDecks();
                        initRecyclerView(deckModels);
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }



}
