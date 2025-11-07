package br.com.techmaster.g7.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.techmaster.g7.dao.RecursoDAO;
import br.com.techmaster.g7.model.Recurso;

@WebServlet("/recursos")
public class RecursoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private RecursoDAO recursoDAO;

    public void init() {
        recursoDAO = new RecursoDAO();
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
                    excluirRecurso(request, response);
                    break;
                case "list":
                default:
                    listarRecursos(request, response);
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
            inserirRecurso(request, response);
        } else {
            atualizarRecurso(request, response);
        }
    }

    private void listarRecursos(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Recurso> listaRecursos = recursoDAO.listarRecursos();
        request.setAttribute("listaRecursos", listaRecursos);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/recursos.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioNovo(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/recurso-form.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioEdicao(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Recurso recursoExistente = recursoDAO.buscarRecursoPorId(id);
        request.setAttribute("recurso", recursoExistente);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/recurso-form.jsp");
        dispatcher.forward(request, response);
    }

    private void inserirRecurso(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        Recurso novoRecurso = new Recurso();
        novoRecurso.setNomeRecurso(request.getParameter("nome"));
        novoRecurso.setTipoRecurso(request.getParameter("tipo"));
        
        recursoDAO.inserirRecurso(novoRecurso);
        response.sendRedirect(request.getContextPath() + "/recursos?action=list");
    }

    private void atualizarRecurso(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        Recurso recurso = new Recurso();
        recurso.setId(Integer.parseInt(request.getParameter("id")));
        recurso.setNomeRecurso(request.getParameter("nome"));
        recurso.setTipoRecurso(request.getParameter("tipo"));
        
        recursoDAO.atualizarRecurso(recurso);
        response.sendRedirect(request.getContextPath() + "/recursos?action=list");
    }

    private void excluirRecurso(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        
        // Proteção: Não deixar excluir o 'Sistema de Login' (ID 1)
        if (id == 1) {
            response.sendRedirect(request.getContextPath() + "/recursos?action=list");
            return;
        }
        
        recursoDAO.excluirRecurso(id);
        response.sendRedirect(request.getContextPath() + "/recursos?action=list");
    }
}