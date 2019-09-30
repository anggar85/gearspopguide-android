package com.mxlapps.app.gearspopguide.Views.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mxlapps.app.gearspopguide.BuildConfig;
import com.mxlapps.app.gearspopguide.Model.DeckModel;
import com.mxlapps.app.gearspopguide.R;
import com.mxlapps.app.gearspopguide.Request.Data;
import com.mxlapps.app.gearspopguide.Request.DataMaster;
import com.mxlapps.app.gearspopguide.Service.DecksApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeckSaveNewDeckFragment extends BottomSheetDialogFragment {

    DeckModel deckModel = new DeckModel();
    View v;
    Button button_deck_save;

    public static DeckSaveNewDeckFragment newInstance() {
        return new DeckSaveNewDeckFragment();
    }

    public DeckSaveNewDeckFragment() {
    }

    public DeckSaveNewDeckFragment(DeckModel deckModel) {
        this.deckModel = deckModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_deck_save_new_deck, container, false);
        Log.d("bottomSheet", "onCreateView: " + deckModel.getPin1());

        button_deck_save = v.findViewById(R.id.button_deck_save);
        button_deck_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_deck_save.setEnabled(false);

                // Recolecta los datos
                TextInputEditText name = v.findViewById(R.id.deckName);
                TextInputEditText desc = v.findViewById(R.id.deckDesc);

                if (name.getText().toString().compareToIgnoreCase("") == 0){
                    Toast.makeText(getActivity(), "Insert a name for your Deck", Toast.LENGTH_SHORT).show();
                    button_deck_save.setEnabled(true);
                    return;
                }
                if (desc.getText().toString().compareToIgnoreCase("") == 0){
                    Toast.makeText(getActivity(), "Insert a description for your Deck", Toast.LENGTH_SHORT).show();
                    button_deck_save.setEnabled(true);
                    return;
                }

                deckModel.setName(name.getText().toString());
                deckModel.setDesc(desc.getText().toString());


                Data data  = new Data();
                data.setDeck(deckModel);
                DataMaster dataMaster = new DataMaster();
                dataMaster.setData(data);

                String URL_SERVER_DOCDIGITALES = BuildConfig.API_BASE_URL;
                Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .addInterceptor(httpLoggingInterceptor)
                        .build();
                Retrofit retrofit = new retrofit2.Retrofit.Builder()
                        .baseUrl(URL_SERVER_DOCDIGITALES)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(okHttpClient)
                        .build();
                DecksApi afk_api = retrofit.create(DecksApi.class);
                Call call = afk_api.createDeck(dataMaster);
                call.enqueue(new Callback<DataMaster>() {
                    @Override
                    public void onResponse(Call<DataMaster> call, Response<DataMaster> response) {
                        if (response.body() != null) {
                            // Todo bien
                            if (response.body().getData() != null){
                                if (!response.body().getData().isError()){
                                    Toast.makeText(getActivity(), "Your deck is waiting for moderation", Toast.LENGTH_LONG).show();
                                    dismiss();
                                }else{
                                    Toast.makeText(getActivity(), "Error :(", Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(getActivity(), "Error :( ..", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Log.d("ERRORRRRRR", "onResponse: " + t.getMessage());
                        button_deck_save.setEnabled(true);

                    }
                });

            }
        });

        return v;
    }

}
