package com.mxlapps.app.gearspopguide.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.mxlapps.app.gearspopguide.Repository.PinsRepository;
import com.mxlapps.app.gearspopguide.Request.DataMaster;
import com.mxlapps.app.gearspopguide.Service.Resource;

public class PinViewModel extends AndroidViewModel {

    private PinsRepository repository;


    public PinViewModel(@NonNull Application application) {
        super(application);
        //Se crea una instancia del repositorio
        repository = PinsRepository.getInstance(application);
    }


    public LiveData<Resource<DataMaster>> getPinList(String race, String role, String type, String cover) {

        return repository.getPinList(race, role, type, cover);
    }


    public LiveData<Resource<DataMaster>> getHeroDetail(Integer hero_id) {

        return repository.getHeroDetail(hero_id);
    }




}
