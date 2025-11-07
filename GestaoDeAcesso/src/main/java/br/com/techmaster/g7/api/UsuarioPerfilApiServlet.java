package br.com.techmaster.g7.api;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.techmaster.g7.dao.UsuarioDAO;
import br.com.techmaster.g7.model.Perfil;

/**
 * API RESTful para gerenciar a associação entre Usuários e Perfis.
 * Mapeado para /api/usuarios/{id}/perfis/*
 */
@WebServlet("/api/usuarios/*/perfis/*")
public class UsuarioPerfilApiServlet extends BaseApiServlet {

    private static final long serialVersionUID = 1L;
    private UsuarioDAO usuarioDAO;

    // Regex para extrair IDs da URL: /api/usuarios/{idUsuario}/perfis/{idPerfil}
    private final Pattern pathPattern = Pattern.compile("/(\\d+)/perfis(?:/(\\d+))?");

    @Override
    public void init() throws ServletException {
        super.init();
        this.usuarioDAO = new UsuarioDAO();
    }

    /**
     * GET /api/usuarios/{id}/perfis -> Lista perfis de um usuário
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        configureCorsHeaders(resp);
        
        Integer idUsuario = getUsuarioId(req);
        if (idUsuario == null) {
            writeJsonMessage(resp, "ID de usuário inválido na URL.", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        List<Perfil> perfis = usuarioDAO.buscarPerfisAssociados(idUsuario);
        writeJsonResponse(resp, perfis, HttpServletResponse.SC_OK);
    }

    /**
     * POST /api/usuarios/{idUsuario}/perfis/{idPerfil} -> Associa um perfil a um usuário
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        configureCorsHeaders(resp);
        
        Integer idUsuario = getUsuarioId(req);
        Integer idPerfil = getPerfilId(req);

        if (idUsuario == null || idPerfil == null) {
            writeJsonMessage(resp, "Formato de URL inválido. Esperado: /api/usuarios/{idUsuario}/perfis/{idPerfil}", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        usuarioDAO.adicionarPerfilUsuario(idUsuario, idPerfil);
        writeJsonMessage(resp, "Perfil associado com sucesso.", HttpServletResponse.SC_CREATED);
    }
    
    /**
     * DELETE /api/usuarios/{idUsuario}/perfis/{idPerfil} -> Remove associação
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        configureCorsHeaders(resp);
        
        Integer idUsuario = getUsuarioId(req);
        Integer idPerfil = getPerfilId(req);

        if (idUsuario == null || idPerfil == null) {
            writeJsonMessage(resp, "Formato de URL inválido. Esperado: /api/usuarios/{idUsuario}/perfis/{idPerfil}", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        usuarioDAO.removerPerfilUsuario(idUsuario, idPerfil);
        writeJsonMessage(resp, "Perfil desassociado com sucesso.", HttpServletResponse.SC_OK);
    }


    // Métodos utilitários para extrair IDs da URL
    
    private Integer getUsuarioId(HttpServletRequest req) {
        Matcher matcher = pathPattern.matcher(req.getPathInfo());
        if (matcher.find()) {
            try {
                return Integer.parseInt(matcher.group(1));
            } catch (Exception e) {}
        }
        return null;
    }

    private Integer getPerfilId(HttpServletRequest req) {
        Matcher matcher = pathPattern.matcher(req.getPathInfo());
        if (matcher.find()) {
            try {
                return Integer.parseInt(matcher.group(2));
            } catch (Exception e) {}
        }
        return null;
    }
}