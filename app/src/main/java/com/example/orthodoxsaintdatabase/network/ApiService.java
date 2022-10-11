package com.example.orthodoxsaintdatabase.network;

import com.example.orthodoxsaintdatabase.models.GetSaints;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("APIselectAllSaints.php")
    Call<GetSaints>getAllSaints();
}
