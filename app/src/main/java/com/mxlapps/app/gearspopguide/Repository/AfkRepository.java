package com.mxlapps.app.gearspopguide.Repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.mxlapps.app.gearspopguide.Request.DataMaster;
import com.mxlapps.app.gearspopguide.Service.AFKApi;
import com.mxlapps.app.gearspopguide.Service.NetworkBoundResource;
import com.mxlapps.app.gearspopguide.Service.Resource;
import com.mxlapps.app.gearspopguide.Service.RetrofitRequest;

import retrofit2.Call;

public class AfkRepository {

    private AFKApi apiService;
    private  static AfkRepository instance;
    private Context context;

    public static AfkRepository getInstance(Context context){
        if(instance == null){
            instance = new AfkRepository(context);
        }
        return instance;
    }
    public AfkRepository(Context context) {
        apiService = RetrofitRequest.getInstance().create(AFKApi.class);
        this.context = context;
    }

    public LiveData<Resource<DataMaster>> getHeroList(final String gameLevel, final String section, final String rarity, final String classe, final String race_name) {

        return new NetworkBoundResource<DataMaster, DataMaster>() {
            @NonNull
            @Override
            protected Call<DataMaster> createCallRetrofit() {
                return apiService.getHeroList("1234567890", gameLevel, section, rarity, classe, race_name);
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
