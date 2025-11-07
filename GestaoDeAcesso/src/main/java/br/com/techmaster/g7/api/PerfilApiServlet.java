package br.com.techmaster.g7.api;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.techmaster.g7.dao.PerfilDAO;
import br.com.techmaster.g7.model.Perfil;

/**
 * API RESTful para o CRUD de Perfis.
 * Mapeado para /api/perfis/*
 */
@WebServlet("/api/perfis/*")
public class PerfilApiServlet extends BaseApiServlet { // <-- Herda do BaseApiServlet
    
    private static final long serialVersionUID = 1L;
    private PerfilDAO perfilDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        this.perfilDAO = new PerfilDAO();
    }

    /**
     * READ (Leitura)
     * GET /api/perfis -> Lista todos os perfis
     * GET /api/perfis/1 -> Busca o perfil com ID 1
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        configureCorsHeaders(resp);
        
        Integer perfilId = getResourceId(req);

        if (perfilId == null) {
            // Nenhum ID foi passado -> Listar todos
            List<Perfil> perfis = perfilDAO.listarPerfis();
            writeJsonResponse(resp, perfis, HttpServletResponse.SC_OK); // 200 OK
        } else {
            // ID foi passado -> Buscar por ID
            Perfil perfil = perfilDAO.buscarPerfilPorId(perfilId);
            if (perfil == null) {
                writeJsonMessage(resp, "Perfil não encontrado", HttpServletResponse.SC_NOT_FOUND); // 404
                return;
            }
            writeJsonResponse(resp, perfil, HttpServletResponse.SC_OK); // 200 OK
        }
    }

    /**
     * CREATE (Criação)
     * POST /api/perfis -> Cria um novo perfil
     * O JSON do perfil deve ser enviado no corpo (body) da requisição.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        configureCorsHeaders(resp);
        
        // Lê o JSON do corpo da requisição e converte para um objeto Perfil
        Perfil novoPerfil = readJsonRequest(req, Perfil.class);
        
        perfilDAO.inserirPerfil(novoPerfil);
        
        writeJsonMessage(resp, "Perfil criado com sucesso", HttpServletResponse.SC_CREATED); // 201 Created
    }

    /**
     * UPDATE (Atualização)
     * PUT /api/perfis/1 -> Atualiza o perfil com ID 1
     * O JSON com as atualizações deve ser enviado no corpo (body).
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        configureCorsHeaders(resp);
        
        Integer perfilId = getResourceId(req);
        if (perfilId == null) {
            writeJsonMessage(resp, "ID do perfil é obrigatório", HttpServletResponse.SC_BAD_REQUEST); // 400
            return;
        }

        // Lê o JSON do corpo
        Perfil perfil = readJsonRequest(req, Perfil.class);
        
        // Garante que o ID do objeto é o mesmo da URL
        perfil.setId(perfilId);
        
        perfilDAO.atualizarPerfil(perfil);
        
        writeJsonResponse(resp, perfil, HttpServletResponse.SC_OK); // 200 OK
    }

    /**
     * DELETE (Remoção)
     * DELETE /api/perfis/1 -> Exclui o perfil com ID 1
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        configureCorsHeaders(resp);
        
        Integer perfilId = getResourceId(req);
        if (perfilId == null) {
            writeJsonMessage(resp, "ID do perfil é obrigatório", HttpServletResponse.SC_BAD_REQUEST); // 400
            return;
        }

        // Regra de negócio: Não permitir exclusão do perfil 'ADMINISTRADOR' (ID 1)
        if (perfilId == 1) {
            writeJsonMessage(resp, "Não é permitido excluir o perfil 'ADMINISTRADOR'", HttpServletResponse.SC_FORBIDDEN); // 403
            return;
        }

        // Verifica se o perfil existe
        if (perfilDAO.buscarPerfilPorId(perfilId) == null) {
            writeJsonMessage(resp, "Perfil não encontrado", HttpServletResponse.SC_NOT_FOUND); // 404
            return;
        }
        
        perfilDAO.excluirPerfil(perfilId);
        
        writeJsonMessage(resp, "Perfil excluído com sucesso", HttpServletResponse.SC_OK); // 200 OK
    }

    /**
     * Método utilitário para extrair o ID da URL (ex: /1)
     */
    private Integer getResourceId(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            return null; // Nenhum ID
        }
        
        String[] parts = pathInfo.split("/");
        if (parts.length > 1) {
            try {
                return Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                // Ignora se não for um número
            }
        }
        return null;
    }
}