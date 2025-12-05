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
import retrofit2.http.Query;

public interface ApiService {

    // 🔹 Administrador
    @POST("admin")
    Call<ApiResponse> cadastrarAdmin(@Body Administrador admin);

    @POST("login")
    Call<ApiResponse> login(@Body LoginRequest request);

    // 🔹 Itens (lista completa)
    @GET("items")
    Call<List<Item>> listarItens();

    // 🔹 Registrar item com imagem (Multipart)
    @Multipart
    @POST("items")
    Call<ApiResponse> registrarItem(
            @Part("nome") RequestBody nome,
            @Part("local_encontrado") RequestBody localEncontrado,
            @Part("local_buscar") RequestBody localBuscar,
            @Part("data_encontrado") RequestBody dataEncontrado,
            @Part("tipo") RequestBody tipo,
            @Part("status") RequestBody status,
            @Part MultipartBody.Part imagem
    );

    // 🔹 Buscar item por ID (endpoint PHP específico)
    @GET("JSONApiFeed.php")
    Call<Item> getItemById(@Query("id") int id);

    // 🔹 Excluir item
    @DELETE("items/{id}")
    Call<ApiResponse> excluirItem(@Path("id") int id);

    // 🔹 Editar item
    @PUT("items/{id}")
    Call<ApiResponse> editarItem(@Path("id") int id, @Body Item item);
}
