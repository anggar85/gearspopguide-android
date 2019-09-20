package com.mxlapps.app.gearspopguide.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mxlapps.app.gearspopguide.Repository.ExtraRepository;
import com.mxlapps.app.gearspopguide.Request.DataMaster;
import com.mxlapps.app.gearspopguide.Service.Resource;

public class ExtraViewModel extends AndroidViewModel {

    private ExtraRepository repository;


    public ExtraViewModel(@NonNull Application application) {
        super(application);
        //Se crea una instancia del repositorio
        repository = ExtraRepository.getInstance(application);
    }


    public LiveData<Resource<DataMaster>> getContributors() {

        return repository.getContributors();
    }

    public LiveData<Resource<DataMaster>> getNews() {

        return repository.getNews();
    }

    public LiveData<Resource<DataMaster>> getItems() {

        return repository.getItems();
    }

    public LiveData<Resource<DataMaster>> getRoleDefinitions() {

        return repository.getRoleDefinitions();
    }

    public LiveData<Resource<DataMaster>> getFaq() {

        return repository.getFaq();
    }


    public LiveData<Resource<DataMaster>> getAboutUs() {

        return repository.getAboutUs();
    }

    public LiveData<Resource<DataMaster>> add_suggestion(DataMaster dataMaster) {

        return repository.add_suggestion(dataMaster);
    }





}
