package com.mxlapps.app.gearspopguide.Repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.mxlapps.app.gearspopguide.Request.DataMaster;
import com.mxlapps.app.gearspopguide.Service.UserApi;
import com.mxlapps.app.gearspopguide.Service.NetworkBoundResource;
import com.mxlapps.app.gearspopguide.Service.Resource;
import com.mxlapps.app.gearspopguide.Service.RetrofitRequest;

import retrofit2.Call;

public class UserRepository {

    private UserApi apiService;
    private  static UserRepository instance;
    private Context context;

    public static UserRepository getInstance(Context context){
        if(instance == null){
            instance = new UserRepository(context);
        }
        return instance;
    }
    public UserRepository(Context context) {
        apiService = RetrofitRequest.getInstance().create(UserApi.class);
        this.context = context;
    }

    public LiveData<Resource<DataMaster>> createUser(final DataMaster dataMaster) {

        return new NetworkBoundResource<DataMaster, DataMaster>() {
            @NonNull
            @Override
            protected Call<DataMaster> createCallRetrofit() {
                return apiService.createUser("1234567890",dataMaster);
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<DataMaster>> showUser(final String user_token) {

        return new NetworkBoundResource<DataMaster, DataMaster>() {
            @NonNull
            @Override
            protected Call<DataMaster> createCallRetrofit() {
                return apiService.showUser("1234567890",user_token);
            }
        }.getAsLiveData();
    }




}
