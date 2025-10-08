package br.com.gestaoacesso.model;

public class Perfil {
    private int id_perfil;
    private String nome;
    private String descricao;

    public Perfil() {}

    public Perfil(int id_perfil, String nome, String descricao) {
        this.id_perfil = id_perfil;
        this.nome = nome;
        this.descricao = descricao;
    }

    public int getId_perfil() { return id_perfil; }
    public void setId_perfil(int id_perfil) { this.id_perfil = id_perfil; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}
