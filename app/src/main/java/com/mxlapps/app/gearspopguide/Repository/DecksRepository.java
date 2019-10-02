package com.mxlapps.app.gearspopguide.Repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.mxlapps.app.gearspopguide.Model.DeckModel;
import com.mxlapps.app.gearspopguide.Request.DataMaster;
import com.mxlapps.app.gearspopguide.Service.DecksApi;
import com.mxlapps.app.gearspopguide.Service.NetworkBoundResource;
import com.mxlapps.app.gearspopguide.Service.PinsApi;
import com.mxlapps.app.gearspopguide.Service.Resource;
import com.mxlapps.app.gearspopguide.Service.RetrofitRequest;

import retrofit2.Call;

public class DecksRepository {

    private DecksApi apiService;
    private  static DecksRepository instance;
    private Context context;

    public static DecksRepository getInstance(Context context){
        if(instance == null){
            instance = new DecksRepository(context);
        }
        return instance;
    }
    public DecksRepository(Context context) {
        apiService = RetrofitRequest.getInstance().create(DecksApi.class);
        this.context = context;
    }

    public LiveData<Resource<DataMaster>> createDeck(final DataMaster dataMaster) {

        return new NetworkBoundResource<DataMaster, DataMaster>() {
            @NonNull
            @Override
            protected Call<DataMaster> createCallRetrofit() {
                return apiService.createDeck(dataMaster);
            }
        }.getAsLiveData();
    }
    public LiveData<Resource<DataMaster>> getDecks(final String orderBy, final String column) {

        return new NetworkBoundResource<DataMaster, DataMaster>() {
            @NonNull
            @Override
            protected Call<DataMaster> createCallRetrofit() {
                return apiService.getDecks(orderBy, column);
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<DataMaster>> show_deck(final Integer deck_id) {

        return new NetworkBoundResource<DataMaster, DataMaster>() {
            @NonNull
            @Override
            protected Call<DataMaster> createCallRetrofit() {
                return apiService.show_deck(deck_id);
            }
        }.getAsLiveData();
    }




}
