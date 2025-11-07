package br.com.techmaster.g7.api;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.techmaster.g7.dao.LogDAO;
import br.com.techmaster.g7.model.LogAcesso;
import br.com.techmaster.g7.model.LogTentativaFalha;

/**
 * API RESTful para consulta de Logs de Auditoria.
 * Mapeado para /api/logs/*
 */
@WebServlet("/api/logs/*")
public class LogApiServlet extends BaseApiServlet {

    private static final long serialVersionUID = 1L;
    private LogDAO logDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        this.logDAO = new LogDAO();
    }

    /**
     * GET /api/logs/acesso -> Lista logs de acesso
     * GET /api/logs/falha -> Lista logs de falha
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        configureCorsHeaders(resp);
        
        String pathInfo = req.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            writeJsonMessage(resp, "Especifique o tipo de log: /api/logs/acesso ou /api/logs/falha", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (pathInfo.equals("/acesso")) {
            // Listar Logs de Acesso
            List<LogAcesso> logs = logDAO.listarLogsAcesso();
            writeJsonResponse(resp, logs, HttpServletResponse.SC_OK);
            
        } else if (pathInfo.equals("/falha")) {
            // Listar Logs de Falha
            List<LogTentativaFalha> logs = logDAO.listarLogsFalha();
            writeJsonResponse(resp, logs, HttpServletResponse.SC_OK);
            
        } else {
            writeJsonMessage(resp, "Endpoint de log não reconhecido.", HttpServletResponse.SC_NOT_FOUND);
        }
    }
}