package com.mxlapps.app.gearspopguide.Repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.mxlapps.app.gearspopguide.Request.DataMaster;
import com.mxlapps.app.gearspopguide.Service.PinsApi;
import com.mxlapps.app.gearspopguide.Service.NetworkBoundResource;
import com.mxlapps.app.gearspopguide.Service.Resource;
import com.mxlapps.app.gearspopguide.Service.RetrofitRequest;

import retrofit2.Call;

public class AfkRepository {

    private PinsApi apiService;
    private  static AfkRepository instance;
    private Context context;

    public static AfkRepository getInstance(Context context){
        if(instance == null){
            instance = new AfkRepository(context);
        }
        return instance;
    }
    public AfkRepository(Context context) {
        apiService = RetrofitRequest.getInstance().create(PinsApi.class);
        this.context = context;
    }

    public LiveData<Resource<DataMaster>> getPinList(final String race, final String role, final String type, final String cover) {

        return new NetworkBoundResource<DataMaster, DataMaster>() {
            @NonNull
            @Override
            protected Call<DataMaster> createCallRetrofit() {
                return apiService.getPinList(race, role, type, cover);
            }
        }.getAsLiveData();
    }

  public LiveData<Resource<DataMaster>> getHeroDetail(final Integer hero_id) {

        return new NetworkBoundResource<DataMaster, DataMaster>() {
            @NonNull
            @Override
            protected Call<DataMaster> createCallRetrofit() {
                return apiService.getHeroDetail(hero_id, "1234567890");
            }
        }.getAsLiveData();
    }



}
