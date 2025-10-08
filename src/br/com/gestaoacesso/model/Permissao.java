package br.com.gestaoacesso.model;

public class Permissao {
    private int id_permissao;
    private String nome;
    private String tipo;

    public Permissao() {}

    public Permissao(int id_permissao, String nome, String tipo) {
        this.id_permissao = id_permissao;
        this.nome = nome;
        this.tipo = tipo;
    }

    public int getId_permissao() { return id_permissao; }
    public void setId_permissao(int id_permissao) { this.id_permissao = id_permissao; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
