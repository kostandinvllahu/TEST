package com.example.orthodoxsaintdatabase.repositories;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.orthodoxsaintdatabase.Activity.MainActivity;
import com.example.orthodoxsaintdatabase.models.GetSaints;
import com.example.orthodoxsaintdatabase.network.ApiClient;
import com.example.orthodoxsaintdatabase.network.ApiService;
import com.example.orthodoxsaintdatabase.responses.SaintsDatabaseResponses;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllSaintsRepositories {

    private ApiService apiService;
    Context context;

    public AllSaintsRepositories(){
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<GetSaints> getAllSaints(int ID){
        ID = 42;
        MutableLiveData<GetSaints> data = new MutableLiveData<>();
        String test = data.toString();
        if(data != null){
            apiService.getAllSaints(ID).enqueue(new Callback<GetSaints>() {

                @Override
                public void onResponse(@NonNull Call<GetSaints> call, @NonNull Response<GetSaints> response) {
                    data.setValue(response.body());
                }

                @Override
                public void onFailure(@NonNull Call<GetSaints> call, @NonNull Throwable t) {
                    data.setValue(null);
                    //Toast.makeText(context, "Vlerat vine null", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(context, "Vlerat vine null", Toast.LENGTH_SHORT).show();
        }

        return data;
    }

}
