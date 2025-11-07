package br.com.techmaster.g7.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Importações do Jackson
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Servlet Base para todas as nossas classes de API REST.
 * Fornece métodos utilitários para ler e escrever JSON.
 */
public abstract class BaseApiServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    // ObjectMapper é o "motor" do Jackson para ler/escrever JSON.
    // É recomendado criar apenas uma instância e reutilizá-la.
    protected final ObjectMapper objectMapper;

    public BaseApiServlet() {
        this.objectMapper = new ObjectMapper();
        // Configuração opcional: formata o JSON para ficar "bonito" (indentado)
        // Remova em produção para economizar bytes.
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    /**
     * Envia um objeto Java como resposta JSON.
     * Configura o status HTTP e o Content-Type.
     * * @param response O HttpServletResponse
     * @param object O objeto a ser serializado (ex: um Usuario, uma List<Usuario>)
     * @param statusCode O código de status HTTP (ex: 200, 201, 404)
     * @throws IOException
     */
    protected void writeJsonResponse(HttpServletResponse response, Object object, int statusCode) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(statusCode);
        
        PrintWriter out = response.getWriter();
        
        // Converte o objeto Java para uma string JSON e a escreve na resposta
        objectMapper.writeValue(out, object);
        
        out.flush();
        out.close();
    }
    
    /**
     * Envia uma resposta JSON simples de "sucesso" ou "erro".
     * * @param response O HttpServletResponse
     * @param message A mensagem a ser enviada
     * @param statusCode O código de status HTTP
     * @throws IOException
     */
    protected void writeJsonMessage(HttpServletResponse response, String message, int statusCode) throws IOException {
        // Cria um objeto simples só para a mensagem
        ObjectMapper tempMapper = new ObjectMapper();
        Object node = tempMapper.createObjectNode().put("message", message);
        writeJsonResponse(response, node, statusCode);
    }

    /**
     * Lê o corpo (body) de uma requisição HTTP e o converte de JSON para um objeto Java.
     * * @param <T> O tipo genérico da classe (ex: Usuario.class)
     * @param request O HttpServletRequest
     * @param clazz A classe para a qual o JSON deve ser convertido
     * @return O objeto Java preenchido.
     * @throws IOException
     */
    protected <T> T readJsonRequest(HttpServletRequest request, Class<T> clazz) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        
        String jsonBody = sb.toString();
        
        // Converte a string JSON para o objeto Java
        return objectMapper.readValue(jsonBody, clazz);
    }

    /**
     * Habilita o CORS (Cross-Origin Resource Sharing).
     * Essencial para que uma aplicação web (ex: React, Angular) 
     * em outro domínio possa chamar sua API.
     */
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        configureCorsHeaders(resp);
    }

    protected void configureCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*"); // Permite de qualquer origem
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }
}