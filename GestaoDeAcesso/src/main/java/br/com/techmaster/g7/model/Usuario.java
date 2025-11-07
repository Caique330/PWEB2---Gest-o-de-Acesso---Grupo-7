package br.com.techmaster.g7.model;

import java.util.ArrayList;
import java.util.List;


/**
 * Representa a entidade T_USUARIO.
 * Inclui uma lista de perfis associados.
 */
public class Usuario {

    private int id;
    private String nomeCompleto;
    private String login;
    private String email;
    private String senhaHash; // Usado apenas internamente no DAO, não expor na sessão!
    private String status;
    
    // Relacionamento: Um usuário pode ter vários perfis [cite: 10]
    private List<Perfil> perfis = new ArrayList<>();

    // Construtores
    public Usuario() {}

    // Getters e Setters (pode gerar no Eclipse: Alt + Shift + S > R)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Perfil> getPerfis() {
        return perfis;
    }

    public void setPerfis(List<Perfil> perfis) {
        this.perfis = perfis;
    }
    
    // Método utilitário
    public void adicionarPerfil(Perfil perfil) {
        this.perfis.add(perfil);
    }
}