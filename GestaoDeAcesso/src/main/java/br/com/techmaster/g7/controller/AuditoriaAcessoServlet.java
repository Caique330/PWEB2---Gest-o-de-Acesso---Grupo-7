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
import br.com.techmaster.g7.model.LogAcesso;

@WebServlet("/auditoria-acesso")
public class AuditoriaAcessoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private LogDAO logDAO;

    public void init() {
        logDAO = new LogDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        List<LogAcesso> listaLogsAcesso = logDAO.listarLogsAcesso();
        
        request.setAttribute("listaLogsAcesso", listaLogsAcesso);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/auditoria-acesso.jsp");
        dispatcher.forward(request, response);
    }
}