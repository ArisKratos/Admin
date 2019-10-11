package com.example.projetotcc10.Modelo;

public class Admin {

    private String id;
    private String nomeAdmin;
    private String emailAdmin;
    private String senhaAdmin;

    public Admin(String id, String nomeAdmin, String emailAdmin) {
        this.id = id;
        this.nomeAdmin = nomeAdmin;
        this.emailAdmin = emailAdmin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmailAdmin() {
        return emailAdmin;
    }

    public void setEmailAdmin(String emailAdmin) {
        this.emailAdmin = emailAdmin;
    }

    public String getSenhaAdmin() {
        return senhaAdmin;
    }

    public void setSenhaAdmin(String senhaAdmin) {
        this.senhaAdmin = senhaAdmin;
    }

    public String getNomeAdmin() {
        return nomeAdmin;
    }

    public void setNomeAdmin(String nomeAdmin) {
        this.nomeAdmin = nomeAdmin;
    }

}
