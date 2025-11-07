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
import br.com.techmaster.g7.dao.PermissaoDAO;
import br.com.techmaster.g7.dao.RecursoDAO;
import br.com.techmaster.g7.dao.RegraDAO;
import br.com.techmaster.g7.model.Perfil;
import br.com.techmaster.g7.model.Permissao;
import br.com.techmaster.g7.model.Recurso;
import br.com.techmaster.g7.model.RegraPermissaoDTO;

@WebServlet("/regras")
public class RegraServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private PerfilDAO perfilDAO;
    private RegraDAO regraDAO;
    private RecursoDAO recursoDAO;
    private PermissaoDAO permissaoDAO;

    public void init() {
        perfilDAO = new PerfilDAO();
        regraDAO = new RegraDAO();
        recursoDAO = new RecursoDAO();
        permissaoDAO = new PermissaoDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        String perfilIdParam = request.getParameter("perfilId");

        if (perfilIdParam == null || perfilIdParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/perfis");
            return;
        }
        
        int idPerfil = Integer.parseInt(perfilIdParam);
        
        try {
            if ("remove".equals(action)) {
                // Ação de remover
                int idRegra = Integer.parseInt(request.getParameter("regraId"));
                regraDAO.removerRegra(idRegra);
                
                // Redireciona de volta para a tela de gerenciamento
                response.sendRedirect(request.getContextPath() + "/regras?perfilId=" + idPerfil);
            } else {
                // Ação padrão: Mostrar gerenciamento
                mostrarGerenciamentoRegras(request, response, idPerfil);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Usado para ADICIONAR uma nova regra
        request.setCharacterEncoding("UTF-8");
        int idPerfil = Integer.parseInt(request.getParameter("idPerfil"));
        int idRecurso = Integer.parseInt(request.getParameter("idRecurso"));
        int idPermissao = Integer.parseInt(request.getParameter("idPermissao"));
        
        if (idRecurso > 0 && idPermissao > 0) {
            regraDAO.adicionarRegra(idPerfil, idRecurso, idPermissao);
        }
        
        // Redireciona de volta para a tela de gerenciamento
        response.sendRedirect(request.getContextPath() + "/regras?perfilId=" + idPerfil);
    }

    private void mostrarGerenciamentoRegras(HttpServletRequest request, HttpServletResponse response, int idPerfil) 
            throws ServletException, IOException {
        
        // 1. Busca o perfil (para mostrar o nome)
        Perfil perfil = perfilDAO.buscarPerfilPorId(idPerfil);
        
        // 2. Busca as regras que este perfil JÁ TEM
        List<RegraPermissaoDTO> regrasAssociadas = regraDAO.buscarRegrasAssociadas(idPerfil);
        
        // 3. Busca TUDO (Recursos e Permissões) para os formulários de adição
        // (Em um sistema complexo, otimizaríamos isso para mostrar apenas 
        // as regras *disponíveis*, mas isso é mais simples e funcional)
        List<Recurso> todosRecursos = recursoDAO.listarRecursos();
        List<Permissao> todasPermissoes = permissaoDAO.listarPermissoes();
        
        request.setAttribute("perfil", perfil);
        request.setAttribute("regrasAssociadas", regrasAssociadas);
        request.setAttribute("todosRecursos", todosRecursos);
        request.setAttribute("todasPermissoes", todasPermissoes);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/perfil-regras.jsp");
        dispatcher.forward(request, response);
    }
}