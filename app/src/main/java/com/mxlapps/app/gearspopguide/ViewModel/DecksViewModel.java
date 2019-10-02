package com.mxlapps.app.gearspopguide.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mxlapps.app.gearspopguide.Model.CommentsModel;
import com.mxlapps.app.gearspopguide.Model.DeckModel;
import com.mxlapps.app.gearspopguide.Repository.DecksRepository;
import com.mxlapps.app.gearspopguide.Repository.PinsRepository;
import com.mxlapps.app.gearspopguide.Request.Data;
import com.mxlapps.app.gearspopguide.Request.DataMaster;
import com.mxlapps.app.gearspopguide.Service.Resource;

public class DecksViewModel extends AndroidViewModel {

    private DecksRepository repository;


    public DecksViewModel(@NonNull Application application) {
        super(application);
        //Se crea una instancia del repositorio
        repository = DecksRepository.getInstance(application);
    }


    public LiveData<Resource<DataMaster>> getDecks(String orderBy, String column) {
        return repository.getDecks(orderBy, column);
    }


    public LiveData<Resource<DataMaster>> show_deck(Integer deck_id) {
        return repository.show_deck(deck_id);
    }






}
