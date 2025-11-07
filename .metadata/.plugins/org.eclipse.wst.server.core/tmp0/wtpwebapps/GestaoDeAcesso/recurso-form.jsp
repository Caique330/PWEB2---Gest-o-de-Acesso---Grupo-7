<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulário de Recurso - TechMaster</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;700&display=swap');
        body { font-family: 'Roboto', sans-serif; background-color: #f4f7f6; margin: 0; padding: 0; }
        .header { background-color: #ffffff; padding: 1rem 2rem; box-shadow: 0 2px 5px rgba(0,0,0,0.1); display: flex; justify-content: space-between; align-items: center; }
        .header h1 { color: #007bff; margin: 0; font-weight: 700; }
        .header-nav a { text-decoration: none; color: #555; font-weight: 500; margin-left: 1rem; }
        .header-nav a.logout { color: #dc3545; }
        .container { padding: 2rem; max-width: 800px; margin: 2rem auto; background-color: #fff; border-radius: 8px; box-shadow: 0 5px 15px rgba(0,0,0,0.05); }
        .btn { text-decoration: none; padding: 0.6rem 1.2rem; border-radius: 5px; font-weight: 500; cursor: pointer; border: none; font-size: 1rem;}
        .btn-primary { background-color: #007bff; color: white; }
        .btn-secondary { background-color: #6c757d; color: white; }
        .form-group { margin-bottom: 1.5rem; }
        .form-group label { display: block; margin-bottom: 0.5rem; font-weight: 500; color: #555; }
        .form-group input, .form-group select {
            width: 100%; padding: 0.75rem; border: 1px solid #ddd;
            border-radius: 5px; box-sizing: border-box; font-family: 'Roboto', sans-serif;
        }
        .form-actions { margin-top: 2rem; }
    </style>
</head>
<body>
    <div class="header">
        <h1>TechMaster G7</h1>
        <div class="header-nav">
            <a href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
            <a href="${pageContext.request.contextPath}/recursos">Recursos</a>
            <a href="${pageContext.request.contextPath}/logout" class="logout">Sair</a>
        </div>
    </div>
    <div class="container">
        <h2>
            <c:if test="${empty recurso}">Novo Recurso</c:if>
            <c:if test="${not empty recurso}">Editar Recurso (ID: ${recurso.id})</c:if>
        </h2>
        <form action="${pageContext.request.contextPath}/recursos" method="POST">
            <c:if test="${not empty recurso}">
                <input type="hidden" name="id" value="${recurso.id}" />
            </c:if>
            <div class="form-group">
                <label for="nome">Nome do Recurso</label>
                <input type="text" id="nome" name="nome" value="${recurso.nomeRecurso}" required>
            </div>
            <div class="form-group">
                <label for="tipo">Tipo de Recurso</label>
                <select id="tipo" name="tipo">
                    <option value="SISTEMA" <c:if test="${recurso.tipoRecurso == 'SISTEMA'}">selected</c:if>>
                        SISTEMA (Ex: Faturamento, RH)
                    </option>
                    <option value="SERVICO" <c:if test="${recurso.tipoRecurso == 'SERVICO'}">selected</c:if>>
                        SERVIÇO (Ex: API de Clientes, API de Pagamentos)
                    </option>
                    <option value="RECURSO" <c:if test="${recurso.tipoRecurso == 'RECURSO'}">selected</c:if>>
                        RECURSO (Ex: Relatório Específico, Funcionalidade)
                    </option>
                </select>
            </div>
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Salvar</button>
                <a href="${pageContext.request.contextPath}/recursos" class="btn btn-secondary">Cancelar</a>
            </div>
        </form>
    </div>
</body>
</html>