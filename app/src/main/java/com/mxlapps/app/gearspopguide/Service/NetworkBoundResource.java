package com.mxlapps.app.gearspopguide.Service;

import android.annotation.SuppressLint;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


// ResultType: Type for the Resource data.
// RequestType: Type for the API response.
public abstract class NetworkBoundResource<ResultType, RequestType> {

    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();
    @SuppressWarnings("unchecked")
    @MainThread
    public NetworkBoundResource() {
        setValue((Resource<ResultType>) Resource.loading(null));
        fetchFromNetwork();
    }

    private void fetchFromNetwork() {

        Call<RequestType> apiResponse = createCallRetrofit();

        //noinspection NullableProblems
        apiResponse.enqueue(new Callback<RequestType>() {
            @SuppressWarnings("unchecked")
            @Override
            public void onResponse(Call<RequestType> call, Response<RequestType> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    setValue((Resource<ResultType>) Resource.success(response.body()));
                }
                else {
                    assert response.body() != null;
                    setValue((Resource<ResultType>) Resource.success(response.body()));
                }
            }
            @SuppressWarnings("unchecked")
            @Override
            public void onFailure(Call<RequestType> call, Throwable t) {
                setValue((Resource<ResultType>) Resource.error(t.getMessage(),null));
            }
        });
    }


    @NonNull
    @MainThread
    protected abstract Call<RequestType> createCallRetrofit();

    @SuppressLint("NewApi")
    private void setValue(Resource<ResultType> newData) {
        if (!Objects.equals(result.getValue(), newData)) {
            result.setValue(newData);
        }
    }
    public final LiveData<Resource<ResultType>> getAsLiveData() {
        return result;
    }
}