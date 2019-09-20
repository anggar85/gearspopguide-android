package com.mxlapps.app.gearspopguide.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mxlapps.app.gearspopguide.Repository.UserRepository;
import com.mxlapps.app.gearspopguide.Request.DataMaster;
import com.mxlapps.app.gearspopguide.Service.Resource;

public class UserViewModel extends AndroidViewModel {

    private UserRepository repository;


    public UserViewModel(@NonNull Application application) {
        super(application);
        //Se crea una instancia del repositorio
        repository = UserRepository.getInstance(application);
    }


    public LiveData<Resource<DataMaster>> createUser(DataMaster dataMaster) {

        return repository.createUser(dataMaster);
    }


    public LiveData<Resource<DataMaster>> showUser(String user_token) {

        return repository.showUser(user_token);
    }





}
