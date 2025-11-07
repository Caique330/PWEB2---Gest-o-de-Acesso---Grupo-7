package br.com.techmaster.g7.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {

    /**
     * Gera um hash SHA-256 para uma senha.
     * (Este é o código 100% correto)
     */
    public static String gerarHashSHA256(String senha) {
        if (senha == null || senha.isEmpty()) {
            return null;
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(senha.getBytes(StandardCharsets.UTF_8));

            // Converte bytes para hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar hash: Algoritmo SHA-256 não encontrado", e);
        }
    }
}