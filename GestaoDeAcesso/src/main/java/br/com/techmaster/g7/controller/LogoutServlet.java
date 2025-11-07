package br.com.techmaster.g7.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import br.com.techmaster.g7.dao.LogDAO;
import br.com.techmaster.g7.model.LogAcesso;
import br.com.techmaster.g7.model.Usuario;
import br.com.techmaster.g7.util.RequestUtil;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
            
            // --- REGISTRO DE LOG (LOGOUT) ---
            if (usuarioLogado != null) {
                LogDAO logDAO = new LogDAO();
                LogAcesso logLogout = new LogAcesso();
                logLogout.setIdUsuario(usuarioLogado.getId());
                logLogout.setIdRecurso(1); // 1 = "Sistema de Login"
                logLogout.setAcaoExecutada("Logout Sucedido");
                logLogout.setIpOrigem(RequestUtil.getIpFromRequest(request));
                logDAO.registrarAcesso(logLogout);
            }
            // --- Fim do Log ---
            
            session.invalidate(); // Invalida a sessão
        }
        
        response.sendRedirect(request.getContextPath() + "/login");
    }
}