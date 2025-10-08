package br.com.gestaoacesso.model;

public class Sistema {
    private int id_sistema;
    private String nome;
    private String descricao;

    public Sistema() {}

    public Sistema(int id_sistema, String nome, String descricao) {
        this.id_sistema = id_sistema;
        this.nome = nome;
        this.descricao = descricao;
    }

    public int getId_sistema() { return id_sistema; }
    public void setId_sistema(int id_sistema) { this.id_sistema = id_sistema; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}
