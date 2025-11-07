package br.com.techmaster.g7.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.techmaster.g7.dao.PerfilDAO;
import br.com.techmaster.g7.model.Perfil;

@WebServlet("/perfis")
public class PerfilServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PerfilDAO perfilDAO;

    public void init() {
        perfilDAO = new PerfilDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "new":
                    mostrarFormularioNovo(request, response);
                    break;
                case "edit":
                    mostrarFormularioEdicao(request, response);
                    break;
                case "delete":
                    excluirPerfil(request, response);
                    break;
                case "list":
                default:
                    listarPerfis(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        
        if (id == null || id.isEmpty()) {
            inserirPerfil(request, response);
        } else {
            atualizarPerfil(request, response);
        }
    }

    private void listarPerfis(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Perfil> listaPerfis = perfilDAO.listarPerfis();
        request.setAttribute("listaPerfis", listaPerfis);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/perfis.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioNovo(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/perfil-form.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioEdicao(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Perfil perfilExistente = perfilDAO.buscarPerfilPorId(id);
        request.setAttribute("perfil", perfilExistente);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/perfil-form.jsp");
        dispatcher.forward(request, response);
    }

    private void inserirPerfil(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        Perfil novoPerfil = new Perfil();
        novoPerfil.setNomePerfil(request.getParameter("nome"));
        novoPerfil.setDescricao(request.getParameter("descricao"));
        
        perfilDAO.inserirPerfil(novoPerfil);
        response.sendRedirect(request.getContextPath() + "/perfis?action=list");
    }

    private void atualizarPerfil(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        Perfil perfil = new Perfil();
        perfil.setId(Integer.parseInt(request.getParameter("id")));
        perfil.setNomePerfil(request.getParameter("nome"));
        perfil.setDescricao(request.getParameter("descricao"));
        
        perfilDAO.atualizarPerfil(perfil);
        response.sendRedirect(request.getContextPath() + "/perfis?action=list");
    }

    private void excluirPerfil(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        
        // Proteção: Não deixar excluir o perfil ADMIN (ID 1)
        if (id == 1) {
            response.sendRedirect(request.getContextPath() + "/perfis?action=list");
            return;
        }
        
        perfilDAO.excluirPerfil(id);
        response.sendRedirect(request.getContextPath() + "/perfis?action=list");
    }
}