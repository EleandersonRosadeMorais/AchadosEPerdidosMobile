package com.ulbra.achadoseperdidos.api;

import com.ulbra.achadoseperdidos.models.Administrador;
import com.ulbra.achadoseperdidos.models.ApiResponse;
import com.ulbra.achadoseperdidos.models.Item;
import com.ulbra.achadoseperdidos.models.LoginRequest;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {

    // 🔹 Administrador
    @POST("/admin")
    Call<ApiResponse> cadastrarAdmin(@Body Administrador admin);

    @POST("/login")
    Call<ApiResponse> login(@Body LoginRequest request);

    // 🔹 Itens
    @GET("/items")
    Call<List<Item>> listarItens();

    // 🔹 Registrar item com imagem (Multipart)
    @Multipart
    @POST("/items")
    Call<ApiResponse> registrarItem(
            @Part("nomeItem") RequestBody nomeItem,
            @Part("localizacao") RequestBody localizacao,
            @Part("dataEncontrada") RequestBody dataEncontrada,
            @Part("tipo") RequestBody tipo,
            @Part("encontrado") RequestBody encontrado,
            @Part MultipartBody.Part imagem
    );
}

