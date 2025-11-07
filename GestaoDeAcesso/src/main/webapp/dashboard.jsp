<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard - TechMaster</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;700&display=swap');
        body {
            font-family: 'Roboto', sans-serif;
            background-color: #f4f7f6;
            margin: 0;
            padding: 0;
        }
        .header {
            background-color: #ffffff;
            padding: 1rem 2rem;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .header h1 {
            color: #007bff;
            margin: 0;
            font-weight: 700;
        }
        .header-nav { display: flex; align-items: center; }
        .header-nav a { text-decoration: none; color: #555; font-weight: 500; margin-left: 1rem; }
        .header-nav .nav-group { margin-left: 2rem; border-left: 1px solid #ddd; padding-left: 1rem; }
        .header-nav a.logout { color: #dc3545; }
        
        .container {
            padding: 2rem;
            max-width: 1200px;
            margin: 2rem auto;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.05);
        }
        .welcome-box {
            background-color: #e6f7ff;
            border: 1px solid #b3e0ff;
            color: #0056b3;
            padding: 1.5rem;
            border-radius: 5px;
            margin-bottom: 1.5rem;
        }
        
        /* ESTILOS DE BOTÃO (para todos os JSPs) */
        .btn { 
            text-decoration: none; 
            padding: 0.6rem 1.2rem; 
            border-radius: 5px; 
            font-weight: 500; 
            transition: background-color 0.3s ease; 
            display: inline-block;
            margin-top: 5px;
            margin-bottom: 5px;
        }
        .btn-primary { background-color: #007bff; color: white; }
        .btn-primary:hover { background-color: #0056b3; }
        .btn-secondary { background-color: #6c757d; color: white; }
        .btn-info { background-color: #17a2b8; color: white; }
        .btn-danger { background-color: #dc3545; color: white; }
    </style>
</head>
<body>

    <div class="header">
        <h1>TechMaster G7</h1>
        <div class="header-nav">
            <a href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
            
            <div class="nav-group">
                <a href="${pageContext.request.contextPath}/logout" class="logout">Sair</a>
            </div>
        </div>
    </div>

    <div class="container">
        
        <c:if test="${empty sessionScope.usuarioLogado}">
            <c:redirect url="/login.jsp" />
        </c:if>

        <div class="welcome-box">
            <h2>Bem-vindo(a), ${sessionScope.usuarioLogado.nomeCompleto}!</h2>
            <p>Seu status é: <strong>${sessionScope.usuarioLogado.status}</strong></p>
        </div>

        <h3>Seus Perfis de Acesso:</h3>
        <ul>
            <c:forEach var="perfil" items="${sessionScope.usuarioLogado.perfis}">
                <li><strong>${perfil.nomePerfil}</strong> (${perfil.descricao})</li>
            </c:forEach>
        </ul>
        
        <hr style="margin: 2rem 0;">
        
        <h3>Administração</h3>
        <p>Acesse os módulos de gestão do sistema.</p>
        <a href="${pageContext.request.contextPath}/usuarios" class="btn btn-primary">Gerenciar Usuários</a>
        <a href="${pageContext.request.contextPath}/perfis" class="btn btn-primary" style="margin-left: 10px;">Gerenciar Perfis</a>
        <a href="${pageContext.request.contextPath}/recursos" class="btn btn-primary" style="margin-left: 10px;">Gerenciar Recursos</a>

        <hr style="margin: 2rem 0;">
        
        <h3>Auditoria e Relatórios</h3>
        <p>Consulte os registros de atividade do sistema.</p>
        <a href="${pageContext.request.contextPath}/auditoria-acesso" class="btn btn-secondary">Logs de Acesso</a>
        <a href="${pageContext.request.contextPath}/auditoria-falha" class="btn btn-secondary" style="margin-left: 10px;">Logs de Falha</a>

    </div>

</body>
</html>