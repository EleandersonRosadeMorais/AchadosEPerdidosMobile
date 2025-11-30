package com.ulbra.achadoseperdidos.models;

public class Item {
    private int id;
    private String nomeItem;
    private String localizacao;
    private String dataEncontrada;
    private String tipo;
    private String imagemUrl;
    private String encontrado;

    // 🔹 Construtor vazio (necessário para Retrofit/Gson)
    public Item() {}

    // 🔹 Construtor completo
    public Item(int id, String nomeItem, String localizacao, String dataEncontrada,
                String tipo, String imagemUrl, String encontrado) {
        this.id = id;
        this.nomeItem = nomeItem;
        this.localizacao = localizacao;
        this.dataEncontrada = dataEncontrada;
        this.tipo = tipo;
        this.imagemUrl = imagemUrl;
        this.encontrado = encontrado;
    }

    // 🔹 Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNomeItem() { return nomeItem; }
    public void setNomeItem(String nomeItem) { this.nomeItem = nomeItem; }

    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }

    public String getDataEncontrada() { return dataEncontrada; }
    public void setDataEncontrada(String dataEncontrada) { this.dataEncontrada = dataEncontrada; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getImagemUrl() { return imagemUrl; }
    public void setImagemUrl(String imagemUrl) { this.imagemUrl = imagemUrl; }

    public String getEncontrado() { return encontrado; }
    public void setEncontrado(String encontrado) { this.encontrado = encontrado; }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", nomeItem='" + nomeItem + '\'' +
                ", localizacao='" + localizacao + '\'' +
                ", dataEncontrada='" + dataEncontrada + '\'' +
                ", tipo='" + tipo + '\'' +
                ", imagemUrl='" + imagemUrl + '\'' +
                ", encontrado='" + encontrado + '\'' +
                '}';
    }
}
