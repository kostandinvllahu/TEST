package com.example.orthodoxsaintdatabase.network;

import com.example.orthodoxsaintdatabase.models.GetSaints;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @POST("APIselectSaintByID.php")
    Call<GetSaints>getAllSaints(@Query("42") int ID);
}
