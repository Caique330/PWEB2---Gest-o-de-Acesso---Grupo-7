package br.com.techmaster.g7.model;

/**
 * Representa a entidade T_PERFIL.
 * Este POJO será usado para serialização/desserialização JSON.
 */
public class Perfil {

    private int id;
    private String nomePerfil;
    private String descricao;

    // Construtores
    public Perfil() {}
    
    public Perfil(int id, String nomePerfil, String descricao) {
        this.id = id;
        this.nomePerfil = nomePerfil;
        this.descricao = descricao;
    }

    // Getters e Setters (Essenciais para o Jackson)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomePerfil() {
        return nomePerfil;
    }

    public void setNomePerfil(String nomePerfil) {
        this.nomePerfil = nomePerfil;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}