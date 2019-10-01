package com.mxlapps.app.gearspopguide.Repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.mxlapps.app.gearspopguide.Request.DataMaster;
import com.mxlapps.app.gearspopguide.Service.NetworkBoundResource;
import com.mxlapps.app.gearspopguide.Service.Resource;
import com.mxlapps.app.gearspopguide.Service.RetrofitRequest;
import com.mxlapps.app.gearspopguide.Service.ExtraApi;

import retrofit2.Call;

public class ExtraRepository {

    private ExtraApi apiService;
    private static ExtraRepository instance;
    private Context context;

    public static ExtraRepository getInstance(Context context){
        if(instance == null){
            instance = new ExtraRepository(context);
        }
        return instance;
    }
    public ExtraRepository(Context context) {
        apiService = RetrofitRequest.getInstance().create(ExtraApi.class);
        this.context = context;
    }



    public LiveData<Resource<DataMaster>> getContributors() {

        return new NetworkBoundResource<DataMaster, DataMaster>() {
            @NonNull
            @Override
            protected Call<DataMaster> createCallRetrofit() {
                return apiService.getContributors("1234567890");
            }
        }.getAsLiveData();
    }



    public LiveData<Resource<DataMaster>> getNews() {

        return new NetworkBoundResource<DataMaster, DataMaster>() {
            @NonNull
            @Override
            protected Call<DataMaster> createCallRetrofit() {
                return apiService.getNews("1234567890");
            }
        }.getAsLiveData();
    }


    public LiveData<Resource<DataMaster>> getItems() {

        return new NetworkBoundResource<DataMaster, DataMaster>() {
            @NonNull
            @Override
            protected Call<DataMaster> createCallRetrofit() {
                return apiService.getItems("1234567890");
            }
        }.getAsLiveData();
    }


    public LiveData<Resource<DataMaster>> getRoleDefinitions() {

        return new NetworkBoundResource<DataMaster, DataMaster>() {
            @NonNull
            @Override
            protected Call<DataMaster> createCallRetrofit() {
                return apiService.getRoleDefinitions("1234567890");
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<DataMaster>> getFaq() {

        return new NetworkBoundResource<DataMaster, DataMaster>() {
            @NonNull
            @Override
            protected Call<DataMaster> createCallRetrofit() {
                return apiService.getFaq("1234567890");
            }
        }.getAsLiveData();
    }


    public LiveData<Resource<DataMaster>> getAboutUs() {

        return new NetworkBoundResource<DataMaster, DataMaster>() {
            @NonNull
            @Override
            protected Call<DataMaster> createCallRetrofit() {
                return apiService.getAboutUs("1234567890");
            }
        }.getAsLiveData();
    }


    public LiveData<Resource<DataMaster>> add_suggestion(final DataMaster dataMaster) {

        return new NetworkBoundResource<DataMaster, DataMaster>() {
            @NonNull
            @Override
            protected Call<DataMaster> createCallRetrofit() {
                return apiService.add_suggestion("1234567890",dataMaster);
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<DataMaster>> createComment(final DataMaster dataMaster) {

        return new NetworkBoundResource<DataMaster, DataMaster>() {
            @NonNull
            @Override
            protected Call<DataMaster> createCallRetrofit() {
                return apiService.createComment(dataMaster);
            }
        }.getAsLiveData();
    }

}
