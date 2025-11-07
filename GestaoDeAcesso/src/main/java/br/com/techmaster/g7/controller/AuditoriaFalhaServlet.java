package br.com.techmaster.g7.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.techmaster.g7.dao.LogDAO;
import br.com.techmaster.g7.model.LogTentativaFalha;

@WebServlet("/auditoria-falha")
public class AuditoriaFalhaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private LogDAO logDAO;

    public void init() {
        logDAO = new LogDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        List<LogTentativaFalha> listaLogsFalha = logDAO.listarLogsFalha();
        
        request.setAttribute("listaLogsFalha", listaLogsFalha);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/auditoria-falha.jsp");
        dispatcher.forward(request, response);
    }
}