package com.ulbra.achadoseperdidos.models;

public class Administrador {

    private int id_pk;
    private String nome;
    private String cpf;
    private String email;
    private String senha;

    // 🔹 Construtor vazio (necessário para Retrofit/Gson)
    public Administrador() {
    }

    // 🔹 Construtor completo
    public Administrador(int id_pk, String nome, String cpf, String email, String senha) {
        this.id_pk = id_pk;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
    }

    // 🔹 Getters e Setters
    public int getId_pk() {
        return id_pk;
    }

    public void setId_pk(int id_pk) {
        this.id_pk = id_pk;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    // 🔹 toString para debug/logs
    @Override
    public String toString() {
        return "Administrador{" +
                "id_pk=" + id_pk +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }
}
