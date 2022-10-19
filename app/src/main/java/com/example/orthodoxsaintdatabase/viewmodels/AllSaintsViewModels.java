package com.example.orthodoxsaintdatabase.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.orthodoxsaintdatabase.models.GetSaints;
import com.example.orthodoxsaintdatabase.repositories.AllSaintsRepositories;
import com.example.orthodoxsaintdatabase.responses.SaintsDatabaseResponses;

public class AllSaintsViewModels extends ViewModel {

    private AllSaintsRepositories allSaintsRepositories;

    public AllSaintsViewModels (){
        allSaintsRepositories = new AllSaintsRepositories();
    }

    public LiveData<GetSaints> getAllSaints(int ID){
        ID = 42;
        return allSaintsRepositories.getAllSaints(ID);
    }
}
