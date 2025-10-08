package br.com.gestaoacesso.controller;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class UsuarioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UsuarioServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().append("Servlet funcionando! Projeto Gestão de Acesso");
    }
}
