package br.com.gestaoacesso.model;

import java.time.LocalDateTime;

public class Acesso {
    private int id_acesso;
    private int fk_usuario;
    private int fk_sistema;
    private LocalDateTime data_hora;
    private String acao;

    public Acesso() {}

    public Acesso(int id_acesso, int fk_usuario, int fk_sistema, LocalDateTime data_hora, String acao) {
        this.id_acesso = id_acesso;
        this.fk_usuario = fk_usuario;
        this.fk_sistema = fk_sistema;
        this.data_hora = data_hora;
        this.acao = acao;
    }

    public int getId_acesso() { return id_acesso; }
    public void setId_acesso(int id_acesso) { this.id_acesso = id_acesso; }

    public int getFk_usuario() { return fk_usuario; }
    public void setFk_usuario(int fk_usuario) { this.fk_usuario = fk_usuario; }

    public int getFk_sistema() { return fk_sistema; }
    public void setFk_sistema(int fk_sistema) { this.fk_sistema = fk_sistema; }

    public LocalDateTime getData_hora() { return data_hora; }
    public void setData_hora(LocalDateTime data_hora) { this.data_hora = data_hora; }

    public String getAcao() { return acao; }
    public void setAcao(String acao) { this.acao = acao; }
}
