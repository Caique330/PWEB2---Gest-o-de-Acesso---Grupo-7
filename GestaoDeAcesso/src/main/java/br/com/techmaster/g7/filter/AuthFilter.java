package br.com.techmaster.g7.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import br.com.techmaster.g7.dao.LogDAO;
import br.com.techmaster.g7.model.LogTentativaFalha;
import br.com.techmaster.g7.util.RequestUtil;

/**
 * Este filtro protege todas as páginas, exceto o login.
 */
// Mapeamento: "/*" pega TUDO.
@WebFilter("/*") 
public class AuthFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String uri = httpRequest.getRequestURI();
        HttpSession session = httpRequest.getSession(false);

        boolean isLoggedIn = (session != null && session.getAttribute("usuarioLogado") != null);
        
        // URIs públicas
        boolean isLoginURI = uri.endsWith("/login");
        boolean isLoginPage = uri.endsWith("/login.jsp");
        
        // Ignorar recursos estáticos (CSS, JS, Imagens)
        // (Ajuste os paths se necessário)
        boolean isStaticResource = uri.contains("/css/") || 
                                   uri.contains("/js/") || 
                                   uri.contains("/img/");

        if (isLoggedIn || isLoginURI || isLoginPage || isStaticResource) {
            // Deixa passar
            chain.doFilter(request, response);
        } else {
            // Acesso não autenticado a uma área protegida
            
            // --- REGISTRO DE LOG (SUSPEITO) ---
            String ip = RequestUtil.getIpFromRequest(httpRequest);
            String userAgent = RequestUtil.getUserAgentFromRequest(httpRequest);
            
            LogDAO logDAO = new LogDAO();
            LogTentativaFalha logFalha = new LogTentativaFalha();
            logFalha.setLoginTentado(null); // Não sabemos o login
            logFalha.setMotivoFalha("Acesso não autenticado à URI: " + uri);
            logFalha.setIpOrigem(ip);
            logFalha.setUserAgent(userAgent);
            logDAO.registrarFalha(logFalha);
            // --- Fim do Log ---
            
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
        }
    }
}