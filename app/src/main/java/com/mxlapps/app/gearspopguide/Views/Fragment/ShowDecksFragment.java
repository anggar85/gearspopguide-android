package com.mxlapps.app.gearspopguide.Views.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mxlapps.app.gearspopguide.Adapter.DeckCommentsAdapter;
import com.mxlapps.app.gearspopguide.Model.CommentsModel;
import com.mxlapps.app.gearspopguide.Model.DeckModel;
import com.mxlapps.app.gearspopguide.R;
import com.mxlapps.app.gearspopguide.Request.DataMaster;
import com.mxlapps.app.gearspopguide.Service.Resource;
import com.mxlapps.app.gearspopguide.Utils.Util;
import com.mxlapps.app.gearspopguide.ViewModel.DecksViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShowDecksFragment extends Fragment {

    private static final String TAG = "afkArenaMainActivity";
    private DeckModel deck;
    private ArrayList<CommentsModel> commentsModel = new ArrayList<>();
    private View rootView;
    View v;
    private DecksViewModel decksViewModel;

    ImageView imageViewDeck1;
    ImageView imageViewDeck2;
    ImageView imageViewDeck3;
    ImageView imageViewDeck4;
    ImageView imageViewDeck5;
    ImageView imageViewDeck6;
    ImageView imageViewDeck7;
    ImageView imageViewDeck8;
    TextView textView_cost;
    TextView textView_deckName1;
    TextView textView_deck_description;
    TextView textView_autor;
    Button button_voteUp;
    RecyclerView recycler_comments;

    public ShowDecksFragment() {
    }

   public ShowDecksFragment(DeckModel deck) {
        this.deck = deck;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = container.getRootView();
        v = inflater.inflate(R.layout.fragment_show_deck, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Gears Pop Deck");

        // ViewModels
        decksViewModel = ViewModelProviders.of(getActivity()).get(DecksViewModel.class);

        initViews();

        cargaInformacionDeck();
        requestShowDeck();

        initRecyclerView();


        return v;

    }

    private void cargaInformacionDeck() {

        // Carga Imagenes
        Picasso.get().load(deck.getPin1()).into(imageViewDeck1);
        Picasso.get().load(deck.getPin2()).into(imageViewDeck2);
        Picasso.get().load(deck.getPin3()).into(imageViewDeck3);
        Picasso.get().load(deck.getPin4()).into(imageViewDeck4);
        Picasso.get().load(deck.getPin5()).into(imageViewDeck5);
        Picasso.get().load(deck.getPin6()).into(imageViewDeck6);
        Picasso.get().load(deck.getPin7()).into(imageViewDeck7);
        Picasso.get().load(deck.getPin8()).into(imageViewDeck8);

        textView_cost.setText(deck.getCost());
        textView_deckName1.setText(deck.getName());
        textView_deck_description.setText(deck.getDesc());
        textView_autor.setText(deck.getAuthor());




    }

    private void initViews() {

        textView_cost  = v.findViewById(R.id.textView_cost);
        textView_deckName1  = v.findViewById(R.id.textView_deckName1);
        textView_deck_description  = v.findViewById(R.id.textView_deck_description);
        textView_autor  = v.findViewById(R.id.textView_autor);
        imageViewDeck1 = v.findViewById(R.id.imageViewDeck1);
        imageViewDeck2 = v.findViewById(R.id.imageViewDeck2);
        imageViewDeck3 = v.findViewById(R.id.imageViewDeck3);
        imageViewDeck4 = v.findViewById(R.id.imageViewDeck4);
        imageViewDeck5 = v.findViewById(R.id.imageViewDeck5);
        imageViewDeck6 = v.findViewById(R.id.imageViewDeck6);
        imageViewDeck7 = v.findViewById(R.id.imageViewDeck7);
        imageViewDeck8 = v.findViewById(R.id.imageViewDeck8);
        button_voteUp = v.findViewById(R.id.button_voteUp);
        recycler_comments = v.findViewById(R.id.recycler_comments);


    }


    private void requestShowDeck() {
        decksViewModel.show_deck(deck.getId()).observe(this, new Observer<Resource<DataMaster>>() {
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
                        commentsModel = dataMasterResource.data.getData().getDeckComments();
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

    private void initRecyclerView() {

        for (int x = 0; x < 15; x++){

            CommentsModel comment = new CommentsModel();
            comment.setComment("fyguhbjuyg hi g iu ggh iu erfg i ger g egrgerg ergu g");
            comment.setUser("ANgel " + x);

            commentsModel.add(comment);


        }


        int numberOfColumns = 1;
        DeckCommentsAdapter adapter = new DeckCommentsAdapter(commentsModel, getActivity(), 1);
        recycler_comments.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        recycler_comments.setNestedScrollingEnabled(false);
        recycler_comments.setHasFixedSize(true);
        recycler_comments.setAdapter(adapter);



    }



}
