package br.com.gestaoacesso.model;

import java.time.LocalDateTime;

public class TentativaInvalida {
    private int id_tentativa;
    private String usuario_login;
    private LocalDateTime data_hora;
    private String motivo;
    private String origem_ip;

    public TentativaInvalida() {}

    public TentativaInvalida(int id_tentativa, String usuario_login, LocalDateTime data_hora, String motivo, String origem_ip) {
        this.id_tentativa = id_tentativa;
        this.usuario_login = usuario_login;
        this.data_hora = data_hora;
        this.motivo = motivo;
        this.origem_ip = origem_ip;
    }

    public int getId_tentativa() { return id_tentativa; }
    public void setId_tentativa(int id_tentativa) { this.id_tentativa = id_tentativa; }

    public String getUsuario_login() { return usuario_login; }
    public void setUsuario_login(String usuario_login) { this.usuario_login = usuario_login; }

    public LocalDateTime getData_hora() { return data_hora; }
    public void setData_hora(LocalDateTime data_hora) { this.data_hora = data_hora; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getOrigem_ip() { return origem_ip; }
    public void setOrigem_ip(String origem_ip) { this.origem_ip = origem_ip; }
}
