package com.mxlapps.app.gearspopguide.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.mxlapps.app.gearspopguide.Repository.AfkRepository;
import com.mxlapps.app.gearspopguide.Request.DataMaster;
import com.mxlapps.app.gearspopguide.Service.Resource;

public class PinViewModel extends AndroidViewModel {

    private AfkRepository repository;


    public PinViewModel(@NonNull Application application) {
        super(application);
        //Se crea una instancia del repositorio
        repository = AfkRepository.getInstance(application);
    }


    public LiveData<Resource<DataMaster>> getHeroList(String gameLevel,  String section, String rarity,  String classe,  String race_name) {

        return repository.getHeroList(gameLevel, section,rarity, classe, race_name);
    }


    public LiveData<Resource<DataMaster>> getHeroDetail(Integer hero_id) {

        return repository.getHeroDetail(hero_id);
    }




}
