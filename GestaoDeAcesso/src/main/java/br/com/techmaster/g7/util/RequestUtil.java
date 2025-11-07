package br.com.techmaster.g7.util;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {

    /**
     * Obtém o endereço IP de origem da requisição.
     * Tenta buscar em 'X-Forwarded-For' (para proxies) antes de 'getRemoteAddr'.
     */
    public static String getIpFromRequest(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // Se houver múltiplos IPs (proxy), pega o primeiro
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }

    /**
     * Obtém o User-Agent da requisição (informações do navegador/dispositivo).
     */
    public static String getUserAgentFromRequest(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }
}