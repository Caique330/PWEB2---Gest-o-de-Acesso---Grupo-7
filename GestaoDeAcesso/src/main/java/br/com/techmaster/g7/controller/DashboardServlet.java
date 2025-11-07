package br.com.techmaster.g7.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // (O filtro AuthFilter já garantiu que o usuário está logado)
        
        // Apenas exibe a página JSP do dashboard
        request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
    }
}