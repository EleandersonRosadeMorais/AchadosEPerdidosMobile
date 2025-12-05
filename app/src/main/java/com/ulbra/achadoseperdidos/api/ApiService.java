package com.ulbra.achadoseperdidos.api;

import com.ulbra.achadoseperdidos.models.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    // 🔹 Itens (lista completa)
    @GET("items")
    Call<List<Item>> listarItens();

    // 🔹 Buscar item por ID (endpoint PHP específico)
    @GET("JSONApiFeed.php")
    Call<Item> getItemById(@Query("id") int id);
}
