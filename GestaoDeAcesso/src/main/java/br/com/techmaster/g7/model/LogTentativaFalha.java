package br.com.techmaster.g7.model;

import java.util.Date;

/**
 * Representa T_LOG_TENTATIVA_FALHA
 * Alterado para usar java.util.Date para compatibilidade com JSTL/Jackson
 */
public class LogTentativaFalha {

    private int id;
    private String loginTentado;
    private Date dataHoraTentativa;
    private String motivoFalha;
    private String ipOrigem;
    private String userAgent;
    
    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getLoginTentado() { return loginTentado; }
    public void setLoginTentado(String loginTentado) { this.loginTentado = loginTentado; }
    public String getMotivoFalha() { return motivoFalha; }
    public void setMotivoFalha(String motivoFalha) { this.motivoFalha = motivoFalha; }
    public String getIpOrigem() { return ipOrigem; }
    public void setIpOrigem(String ipOrigem) { this.ipOrigem = ipOrigem; }
    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }

    public Date getDataHoraTentativa() {
        return dataHoraTentativa;
    }
    public void setDataHoraTentativa(Date dataHoraTentativa) {
        this.dataHoraTentativa = dataHoraTentativa;
    }
}