package br.com.techmaster.g7.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.techmaster.g7.dao.UsuarioDAO;
import br.com.techmaster.g7.model.Perfil;
import br.com.techmaster.g7.model.Usuario;

@WebServlet("/associacao")
public class AssociacaoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UsuarioDAO usuarioDAO;

    public void init() {
        usuarioDAO = new UsuarioDAO(); // Reutilizamos o UsuarioDAO
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");

        try {
            String action = request.getParameter("action");
            String userIdParam = request.getParameter("userId");
            
            if (userIdParam == null || userIdParam.isEmpty()) {
                // Se não veio ID de usuário, volta para a lista
                response.sendRedirect(request.getContextPath() + "/usuarios");
                return;
            }
            
            int idUsuario = Integer.parseInt(userIdParam);

            if (action != null) {
                int idPerfil = Integer.parseInt(request.getParameter("perfilId"));
                
                if ("add".equals(action)) {
                    // Adicionar Perfil
                    usuarioDAO.adicionarPerfilUsuario(idUsuario, idPerfil);
                } else if ("remove".equals(action)) {
                    // Remover Perfil
                    usuarioDAO.removerPerfilUsuario(idUsuario, idPerfil);
                }
                
                // Redireciona de volta para a mesma tela de gerenciamento
                response.sendRedirect(request.getContextPath() + "/associacao?userId=" + idUsuario);
            
            } else {
                // Ação padrão: Mostrar a tela de gerenciamento
                mostrarGerenciamentoPerfis(request, response, idUsuario);
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void mostrarGerenciamentoPerfis(HttpServletRequest request, HttpServletResponse response, int idUsuario) 
            throws ServletException, IOException {
        
        // 1. Busca o usuário (para mostrar o nome)
        Usuario usuario = usuarioDAO.buscarUsuarioPorId(idUsuario);
        
        // 2. Busca os perfis que ele JÁ TEM
        List<Perfil> perfisAssociados = usuarioDAO.buscarPerfisAssociados(idUsuario);
        
        // 3. Busca os perfis que ele NÃO TEM (disponíveis)
        List<Perfil> perfisDisponiveis = usuarioDAO.buscarPerfisDisponiveis(idUsuario);
        
        request.setAttribute("usuario", usuario);
        request.setAttribute("perfisAssociados", perfisAssociados);
        request.setAttribute("perfisDisponiveis", perfisDisponiveis);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/usuario-perfis.jsp");
        dispatcher.forward(request, response);
    }
    
    // O doPost não é necessário aqui, pois usamos GET para simplicidade
}