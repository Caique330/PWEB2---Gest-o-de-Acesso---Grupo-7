<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulário de Usuário - TechMaster</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;700&display=swap');
        body { font-family: 'Roboto', sans-serif; background-color: #f4f7f6; margin: 0; padding: 0; }
        .header { background-color: #ffffff; padding: 1rem 2rem; box-shadow: 0 2px 5px rgba(0,0,0,0.1); display: flex; justify-content: space-between; align-items: center; }
        .header h1 { color: #007bff; margin: 0; font-weight: 700; }
        .header-nav { display: flex; align-items: center; }
        .header-nav a { text-decoration: none; color: #555; font-weight: 500; margin-left: 1rem; }
        .header-nav .nav-group { margin-left: 2rem; border-left: 1px solid #ddd; padding-left: 1rem; }
        .header-nav a.logout { color: #dc3545; }
        
        .container { padding: 2rem; max-width: 800px; margin: 2rem auto; background-color: #fff; border-radius: 8px; box-shadow: 0 5px 15px rgba(0,0,0,0.05); }
        
        /* Estilos de Botão */
        .btn { 
            text-decoration: none; 
            padding: 0.6rem 1.2rem; 
            border-radius: 5px; 
            font-weight: 500; 
            cursor: pointer; 
            border: none; 
            font-size: 1rem;
            margin-right: 10px;
        }
        .btn-primary { background-color: #007bff; color: white; }
        .btn-secondary { background-color: #6c757d; color: white; }
        
        /* Estilos de Formulário */
        .form-group { margin-bottom: 1.5rem; }
        .form-group label { display: block; margin-bottom: 0.5rem; font-weight: 500; color: #555; }
        .form-group input, .form-group select {
            width: 100%; 
            padding: 0.75rem; 
            border: 1px solid #ddd;
            border-radius: 5px; 
            box-sizing: border-box; /* Importante para o padding não estourar a largura */
            font-family: 'Roboto', sans-serif;
            font-size: 1rem;
        }
        .form-actions { margin-top: 2rem; }
    </style>
</head>
<body>
    <div class="header">
        <h1>TechMaster G7</h1>
        <div class="header-nav">
            <a href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
            <a href="${pageContext.request.contextPath}/usuarios">Usuários</a>
            <div class="nav-group">
                <a href="${pageContext.request.contextPath}/logout" class="logout">Sair</a>
            </div>
        </div>
    </div>

    <div class="container">
        <h2>
            <c:if test="${empty usuario.id}">Novo Usuário</c:if>
            <c:if test="${not empty usuario.id}">Editar Usuário (ID: ${usuario.id})</c:if>
        </h2>

        <form action="${pageContext.request.contextPath}/usuarios" method="POST">
            
            <c:if test="${not empty usuario.id}">
                <input type="hidden" name="id" value="${usuario.id}" />
            </c:if>

            <div class="form-group">
                <label for="nome">Nome Completo</label>
                <input type="text" id="nome" name="nome" value="${usuario.nomeCompleto}" required>
            </div>
            
            <div class="form-group">
                <label for="login">Login</label>
                <input type="text" id="login" name="login" value="${usuario.login}" required>
            </div>

            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" value="${usuario.email}" required>
            </div>

            <div class="form-group">
                <label for="senha">Senha</label>
                <input type="password" id="senha" name="senha" 
                       <c:if test="${empty usuario.id}">required</c:if> >
                <c:if test="${not empty usuario.id}">
                    <small>(Deixe em branco para não alterar a senha)</small>
                </c:if>
            </div>
            
            <div class="form-group">
                <label for="status">Status</label>
                <select id="status" name="status">
                    <option value="ATIVO" <c:if test="${usuario.status == 'ATIVO'}">selected</c:if>>
                        ATIVO
                    </option>
                    <option value="INATIVO" <c:if test="${usuario.status == 'INATIVO'}">selected</c:if>>
                        INATIVO
                    </option>
                    <option value="BLOQUEADO" <c:if test="${usuario.status == 'BLOQUEADO'}">selected</c:if>>
                        BLOQUEADO
                    </option>
                </select>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Salvar</button>
                <a href="${pageContext.request.contextPath}/usuarios" class="btn btn-secondary">Cancelar</a>
            </div>
        </form>
    </div>
</body>
</html>