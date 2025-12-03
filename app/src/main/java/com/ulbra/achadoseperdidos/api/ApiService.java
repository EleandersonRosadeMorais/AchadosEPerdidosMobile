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
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

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

    // 🔹 Novo endpoint para buscar item por ID
    @GET("/items/{id}")
    Call<Item> getItemById(@Path("id") int id);

    // 🔹 Excluir item
    @DELETE("/items/{id}")
    Call<ApiResponse> excluirItem(@Path("id") int id);

    // 🔹 Editar item
    @PUT("/items/{id}")
    Call<ApiResponse> editarItem(@Path("id") int id, @Body Item item);
}
