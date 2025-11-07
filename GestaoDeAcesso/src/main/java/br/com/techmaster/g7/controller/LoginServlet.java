package br.com.techmaster.g7.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import br.com.techmaster.g7.dao.UsuarioDAO;
import br.com.techmaster.g7.model.Usuario;
import br.com.techmaster.g7.dao.LogDAO;
import br.com.techmaster.g7.model.LogAcesso;
import br.com.techmaster.g7.model.LogTentativaFalha;
import br.com.techmaster.g7.util.RequestUtil;

// Mapeamos o Servlet para a URL 'login' (que é o 'action' do nosso form)
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Método GET: Apenas exibe a tela de login.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Se o usuário já estiver logado, redireciona para o dashboard
        if (request.getSession(false) != null && request.getSession(false).getAttribute("usuarioLogado") != null) {
            response.sendRedirect("dashboard.jsp"); // Próxima página que criaremos
            return;
        }
        
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");

        // Informações para auditoria
        String ip = RequestUtil.getIpFromRequest(request);
        String userAgent = RequestUtil.getUserAgentFromRequest(request);
        
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        LogDAO logDAO = new LogDAO();
        
        Usuario usuarioLogado = usuarioDAO.validarLogin(login, senha);

        if (usuarioLogado != null) {
            // Sucesso!
            HttpSession session = request.getSession();
            usuarioLogado.setSenhaHash(null); 
            session.setAttribute("usuarioLogado", usuarioLogado); 
            session.setMaxInactiveInterval(30 * 60); 

            // --- REGISTRO DE LOG (SUCESSO) ---
            LogAcesso logSucesso = new LogAcesso();
            logSucesso.setIdUsuario(usuarioLogado.getId());
            logSucesso.setIdRecurso(1); // 1 = "Sistema de Login" (assumido)
            logSucesso.setAcaoExecutada("Login Sucedido");
            logSucesso.setIpOrigem(ip);
            logDAO.registrarAcesso(logSucesso);
            // --- Fim do Log ---
            
            response.sendRedirect(request.getContextPath() + "/dashboard");
        
        } else {
            // Falha!
            
            // --- REGISTRO DE LOG (FALHA) ---
            LogTentativaFalha logFalha = new LogTentativaFalha();
            logFalha.setLoginTentado(login); // O login que o usuário tentou
            logFalha.setMotivoFalha("Login ou senha inválidos / Usuário inativo");
            logFalha.setIpOrigem(ip);
            logFalha.setUserAgent(userAgent);
            logDAO.registrarFalha(logFalha);
            // --- Fim do Log ---

            request.setAttribute("erroLogin", "Login ou senha inválidos, ou usuário inativo.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}