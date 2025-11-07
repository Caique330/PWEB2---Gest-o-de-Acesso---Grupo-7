package br.com.techmaster.g7.model;

/**
 * DTO (Data Transfer Object) para exibir as regras de permissão.
 * Ele não representa uma tabela, mas sim uma junção de várias.
 */
public class RegraPermissaoDTO {

    private int idRegra;
    private int idPerfil;
    private int idRecurso;
    private int idPermissao;
    
    // Nomes (para exibição)
    private String nomeRecurso;
    private String nomePermissao;

    // Construtores
    public RegraPermissaoDTO() {}

    // Getters e Setters (Necessários para o Jackson)
    public int getIdRegra() { return idRegra; }
    public void setIdRegra(int idRegra) { this.idRegra = idRegra; }
    public int getIdPerfil() { return idPerfil; }
    public void setIdPerfil(int idPerfil) { this.idPerfil = idPerfil; }
    public int getIdRecurso() { return idRecurso; }
    public void setIdRecurso(int idRecurso) { this.idRecurso = idRecurso; }
    public int getIdPermissao() { return idPermissao; }
    public void setIdPermissao(int idPermissao) { this.idPermissao = idPermissao; }
    public String getNomeRecurso() { return nomeRecurso; }
    public void setNomeRecurso(String nomeRecurso) { this.nomeRecurso = nomeRecurso; }
    public String getNomePermissao() { return nomePermissao; }
    public void setNomePermissao(String nomePermissao) { this.nomePermissao = nomePermissao; }
}