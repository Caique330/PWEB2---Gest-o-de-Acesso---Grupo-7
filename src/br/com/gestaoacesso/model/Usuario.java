package br.com.gestaoacesso.model;

public class Usuario {
    private int id_usuario;
    private String nome;
    private String login;
    private String senha;
    private String email;
    private boolean status;

    public Usuario() {}

    public Usuario(int id_usuario, String nome, String login, String senha, String email, boolean status) {
        this.id_usuario = id_usuario;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.email = email;
        this.status = status;
    }

    public int getId_usuario() { return id_usuario; }
    public void setId_usuario(int id_usuario) { this.id_usuario = id_usuario; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }
}
