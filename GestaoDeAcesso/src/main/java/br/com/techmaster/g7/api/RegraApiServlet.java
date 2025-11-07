package br.com.techmaster.g7.api;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.techmaster.g7.dao.RegraDAO;
import br.com.techmaster.g7.model.RegraPermissaoDTO;

/**
 * API RESTful para gerenciar as Regras de Permissão (Associação Perfil <-> Recurso/Permissão).
 * Mapeado para /api/perfis/{id}/regras/*
 */
@WebServlet("/api/perfis/*/regras/*")
public class RegraApiServlet extends BaseApiServlet {

    private static final long serialVersionUID = 1L;
    private RegraDAO regraDAO;

    // Regex: /api/perfis/{idPerfil}/regras/{idRegra}
    private final Pattern pathPattern = Pattern.compile("/(\\d+)/regras(?:/(\\d+))?");

    @Override
    public void init() throws ServletException {
        super.init();
        this.regraDAO = new RegraDAO();
    }

    /**
     * GET /api/perfis/{id}/regras -> Lista regras de um perfil
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        configureCorsHeaders(resp);
        
        Integer idPerfil = getPerfilId(req);
        if (idPerfil == null) {
            writeJsonMessage(resp, "ID de perfil inválido na URL.", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        List<RegraPermissaoDTO> regras = regraDAO.buscarRegrasAssociadas(idPerfil);
        writeJsonResponse(resp, regras, HttpServletResponse.SC_OK);
    }

    /**
     * POST /api/perfis/{idPerfil}/regras
     * Espera um JSON no body com: {"idRecurso": X, "idPermissao": Y}
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        configureCorsHeaders(resp);
        
        Integer idPerfil = getPerfilId(req);
        if (idPerfil == null) {
            writeJsonMessage(resp, "ID de perfil inválido na URL.", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // Usamos o DTO para ler o body
        RegraPermissaoDTO novaRegra = readJsonRequest(req, RegraPermissaoDTO.class);

        if (novaRegra.getIdRecurso() == 0 || novaRegra.getIdPermissao() == 0) {
            writeJsonMessage(resp, "idRecurso e idPermissao são obrigatórios no JSON.", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        
        regraDAO.adicionarRegra(idPerfil, novaRegra.getIdRecurso(), novaRegra.getIdPermissao());
        writeJsonMessage(resp, "Regra de permissão associada.", HttpServletResponse.SC_CREATED);
    }
    
    /**
     * DELETE /api/perfis/{idPerfil}/regras/{idRegra} -> Remove uma regra específica
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        configureCorsHeaders(resp);
        
        Integer idPerfil = getPerfilId(req);
        Integer idRegra = getRegraId(req);

        if (idPerfil == null || idRegra == null) {
            writeJsonMessage(resp, "Formato de URL inválido. Esperado: /api/perfis/{idPerfil}/regras/{idRegra}", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        regraDAO.removerRegra(idRegra);
        writeJsonMessage(resp, "Regra de permissão desassociada.", HttpServletResponse.SC_OK);
    }


    // Métodos utilitários para extrair IDs da URL
    
    private Integer getPerfilId(HttpServletRequest req) {
        Matcher matcher = pathPattern.matcher(req.getPathInfo());
        if (matcher.find()) {
            try {
                return Integer.parseInt(matcher.group(1));
            } catch (Exception e) {}
        }
        return null;
    }

    private Integer getRegraId(HttpServletRequest req) {
        Matcher matcher = pathPattern.matcher(req.getPathInfo());
        if (matcher.find()) {
            try {
                return Integer.parseInt(matcher.group(2));
            } catch (Exception e) {}
        }
        return null;
    }
}