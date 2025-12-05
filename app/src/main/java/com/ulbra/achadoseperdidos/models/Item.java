package com.ulbra.achadoseperdidos.models;

import com.google.gson.annotations.SerializedName;

public class Item {
    private int id;

    @SerializedName("nome")
    private String nome;

    @SerializedName("data_encontrado")
    private String dataEncontrado;

    @SerializedName("local_encontrado")
    private String localEncontrado;

    @SerializedName("local_buscar")
    private String localBuscar;

    private String tipo;

    @SerializedName("status")
    private String status;

    @SerializedName("data_cadastro")
    private String dataCadastro;

    @SerializedName("imagem")
    private String imagemUrl;

    // Getters e Setters
    public int getId() { return id; }

    public String getNome() { return nome; }

    public String getDataEncontrado() { return dataEncontrado; }

    public String getLocalEncontrado() { return localEncontrado; }

    public String getLocalBuscar() { return localBuscar; }

    public String getTipo() { return tipo; }

    public String getStatus() { return status; }

    public String getDataCadastro() { return dataCadastro; }

    public String getImagemUrl() { return imagemUrl; }
}
