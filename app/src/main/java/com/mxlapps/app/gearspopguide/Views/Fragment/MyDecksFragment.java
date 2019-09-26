package com.mxlapps.app.gearspopguide.Views.Fragment;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mxlapps.app.gearspopguide.Adapter.PinsDeckAdapter;
import com.mxlapps.app.gearspopguide.Model.DeckModel;
import com.mxlapps.app.gearspopguide.Model.PinModel;
import com.mxlapps.app.gearspopguide.R;
import com.mxlapps.app.gearspopguide.Request.DataMaster;
import com.mxlapps.app.gearspopguide.Service.Resource;
import com.mxlapps.app.gearspopguide.Utils.AppPreferences;
import com.mxlapps.app.gearspopguide.Utils.Util;
import com.mxlapps.app.gearspopguide.ViewModel.PinViewModel;
import com.squareup.picasso.Picasso;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    TextView textView_save_deck;
    TextView textView_cost;

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


    String deck1_id = "";
    String deck2_id = "";
    String deck3_id = "";
    String deck4_id = "";
    String deck5_id = "";
    String deck6_id = "";
    String deck7_id = "";
    String deck8_id = "";

    Double costo1 = 0.0;
    Double costo2 = 0.0;
    Double costo3 = 0.0;
    Double costo4 = 0.0;
    Double costo5 = 0.0;
    Double costo6 = 0.0;
    Double costo7 = 0.0;
    Double costo8 = 0.0;

    private DeckModel deckModel = new DeckModel();


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
        textView_cost = v.findViewById(R.id.textView_cost);
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

        textView_save_deck = v.findViewById(R.id.textView_save_deck);
        textView_save_deck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Valida si hay pins repetidos
                deckModel.setUser_token(AppPreferences.getInstance(getActivity()).getUserId());
                deckModel.setPin1(deck1_id);
                deckModel.setPin2(deck2_id);
                deckModel.setPin3(deck3_id);
                deckModel.setPin4(deck4_id);
                deckModel.setPin5(deck5_id);
                deckModel.setPin6(deck6_id);
                deckModel.setPin7(deck7_id);
                deckModel.setPin8(deck8_id);
                deckModel.setCost(String.valueOf(cost));

                if (buscaRepetidos()){
                    Log.d(TAG, "onClick: hay repetidos");
                }else{
                    Log.d(TAG, "onClick: NO hay repetidos");

                    CreateDeckFragment createDeckFragment = new CreateDeckFragment(deckModel);
                    createDeckFragment.show(getActivity().getSupportFragmentManager(),"createDeckFragment");

                }
            }
        });
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

                if (pinHolderSelected != 0){
                    String imgLink = pinModelsInternal.get(position).getSmallImage();
                    switch (pinHolderSelected){
                        case 1:
                            Picasso.get().load(imgLink).into(imageViewDeck1);
                            deck1_id = pinModelsInternal.get(position).getName();
                            costo1 = Double.valueOf(pinModelsInternal.get(position).getCost().toString());
                            break;
                        case 2:
                            Picasso.get().load(imgLink).into(imageViewDeck2);
                            deck2_id = pinModelsInternal.get(position).getName();
                            costo2 = Double.valueOf(pinModelsInternal.get(position).getCost().toString());

                            break;
                        case 3:
                            Picasso.get().load(imgLink).into(imageViewDeck3);
                            deck3_id = pinModelsInternal.get(position).getName();
                            costo3 = Double.valueOf(pinModelsInternal.get(position).getCost().toString());

                            break;
                        case 4:
                            Picasso.get().load(imgLink).into(imageViewDeck4);
                            deck4_id = pinModelsInternal.get(position).getName();
                            costo4 = Double.valueOf(pinModelsInternal.get(position).getCost().toString());

                            break;
                        case 5:
                            Picasso.get().load(imgLink).into(imageViewDeck5);
                            deck5_id = pinModelsInternal.get(position).getName();
                            costo5 = Double.valueOf(pinModelsInternal.get(position).getCost().toString());

                            break;
                        case 6:
                            Picasso.get().load(imgLink).into(imageViewDeck6);
                            deck6_id = pinModelsInternal.get(position).getName();
                            costo6 = Double.valueOf(pinModelsInternal.get(position).getCost().toString());

                            break;
                        case 7:
                            Picasso.get().load(imgLink).into(imageViewDeck7);
                            deck7_id = pinModelsInternal.get(position).getName();
                            costo7 = Double.valueOf(pinModelsInternal.get(position).getCost().toString());

                            break;
                        case 8:
                            Picasso.get().load(imgLink).into(imageViewDeck8);
                            deck8_id = pinModelsInternal.get(position).getName();
                            costo8 = Double.valueOf(pinModelsInternal.get(position).getCost().toString());

                            break;
                    }
                    pinHolderSelected = 0;
                }else{
                    Toast.makeText(getActivity(), "Please select a holder", Toast.LENGTH_SHORT).show();
                    pinHolderSelected = 0;
                }

                calculaCosto();

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

    public void calculaCosto(){
        Double costos = costo1 + costo2 + costo3 + costo4 + costo5 + costo6 + costo7 + costo8;
        int divisor = 0;
        if (costo1 > 0)
            divisor++;
        if (costo2 > 0)
            divisor++;
        if (costo3 > 0)
            divisor++;
        if (costo4 > 0)
            divisor++;
        if (costo5 > 0)
            divisor++;
        if (costo6 > 0)
            divisor++;
        if (costo7 > 0)
            divisor++;
        if (costo8 > 0)
            divisor++;

        if (costos == 0){
            cost = 0.0;
        }else{
            cost =  ((costos)/divisor);
        }

        textView_cost.setText("Costo: "+ String.format("%.2f", cost));
    }


    public Boolean buscaRepetidos(){
        ArrayList<String> pinsSeleccionados = new ArrayList<>();
        pinsSeleccionados.add(deck1_id);
        pinsSeleccionados.add(deck2_id);
        pinsSeleccionados.add(deck3_id);
        pinsSeleccionados.add(deck4_id);
        pinsSeleccionados.add(deck5_id);
        pinsSeleccionados.add(deck6_id);
        pinsSeleccionados.add(deck7_id);
        pinsSeleccionados.add(deck8_id);


        for (String pinName : pinsSeleccionados) {

            int cont = 0;
            for (int y = 0; y < pinsSeleccionados.size(); y++){
                if (pinName.compareToIgnoreCase(pinsSeleccionados.get(y)) == 0) {
                    if (pinName.compareToIgnoreCase("") != 0) {
                        cont++;
                    }
                }
                if (cont == 2){
                    return true;
                }
            }
            cont = 0;
        }

        return false;
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
