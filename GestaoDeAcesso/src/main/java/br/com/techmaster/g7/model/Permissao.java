package br.com.techmaster.g7.model;

// Representa a entidade T_PERMISSAO
public class Permissao {

    private int id;
    private String nomePermissao;
    
    // Construtores
    public Permissao() {}
    
    public Permissao(int id, String nomePermissao) {
        this.id = id;
        this.nomePermissao = nomePermissao;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNomePermissao() { return nomePermissao; }
    public void setNomePermissao(String nomePermissao) { this.nomePermissao = nomePermissao; }
}