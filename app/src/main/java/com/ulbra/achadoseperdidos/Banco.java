package com.ulbra.achadoseperdidos;

public class Banco {
    private String nomeItem;
    private String localizacao;
    private String dataEncontrada;
    private String tipo;
    private String imagemUrl; // URL da foto salva no Firebase Storage
    private String encontrado; // novo campo: onde foi encontrado

    public Banco() {
    }

    public Banco(String nomeItem, String localizacao, String dataEncontrada,
                 String tipo, String imagemUrl, String encontrado) {
        this.nomeItem = nomeItem;
        this.localizacao = localizacao;
        this.dataEncontrada = dataEncontrada;
        this.tipo = tipo;
        this.imagemUrl = imagemUrl;
        this.encontrado = encontrado;
    }

    public String getNomeItem() {
        return nomeItem;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public String getDataEncontrada() {
        return dataEncontrada;
    }

    public String getTipo() {
        return tipo;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public String getEncontrado() {
        return encontrado;
    }

    public void setEncontrado(String encontrado) {
        this.encontrado = encontrado;
    }
}
