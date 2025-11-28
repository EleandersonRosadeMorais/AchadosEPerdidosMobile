package com.ulbra.achadoseperdidos.api;

import com.ulbra.achadoseperdidos.models.Administrador;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/admin")
    Call<Administrador> cadastrarAdmin(@Body Administrador admin);
}
