<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Associar Perfis - TechMaster</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;700&display=swap');
        body { font-family: 'Roboto', sans-serif; background-color: #f4f7f6; margin: 0; padding: 0; }
        .header { background-color: #ffffff; padding: 1rem 2rem; box-shadow: 0 2px 5px rgba(0,0,0,0.1); display: flex; justify-content: space-between; align-items: center; }
        .header h1 { color: #007bff; margin: 0; font-weight: 700; }
        .header-nav a { text-decoration: none; color: #555; font-weight: 500; margin-left: 1rem; }
        .header-nav a.logout { color: #dc3545; }
        .container { padding: 2rem; max-width: 1000px; margin: 2rem auto; background-color: #fff; border-radius: 8px; box-shadow: 0 5px 15px rgba(0,0,0,0.05); }
        .btn { text-decoration: none; padding: 0.6rem 1.2rem; border-radius: 5px; font-weight: 500; }
        .btn-secondary { background-color: #6c757d; color: white; display: inline-block; margin-top: 1rem; }
        
        /* Layout de Associação */
        .assoc-container {
            display: flex;
            justify-content: space-between;
            gap: 2rem;
            margin-top: 1.5rem;
        }
        .assoc-box {
            flex: 1;
            border: 1px solid #ddd;
            border-radius: 8px;
            background-color: #fdfdfd;
        }
        .assoc-box h3 {
            margin: 0;
            padding: 1rem;
            background-color: #f8f9fa;
            border-bottom: 1px solid #ddd;
            border-radius: 8px 8px 0 0;
        }
        .profile-list {
            list-style: none;
            padding: 0;
            margin: 0;
            max-height: 400px;
            overflow-y: auto;
        }
        .profile-list li {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 0.8rem 1rem;
            border-bottom: 1px solid #eee;
        }
        .profile-list li:last-child {
            border-bottom: none;
        }
        .profile-list .btn-action {
            text-decoration: none;
            padding: 0.3rem 0.6rem;
            border-radius: 4px;
            color: white;
            font-size: 0.9rem;
        }
        .btn-add { background-color: #28a745; } /* Verde */
        .btn-remove { background-color: #dc3545; } /* Vermelho */
        
        .empty-list {
            padding: 1rem;
            color: #777;
            font-style: italic;
        }
    </style>
</head>
<body>
    <div class="header">
        <h1>TechMaster G7</h1>
        <div class="header-nav">
            <a href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
            <a href="${pageContext.request.contextPath}/usuarios">Usuários</a>
            <a href="${pageContext.request.contextPath}/perfis">Perfis</a>
            <a href="${pageContext.request.contextPath}/logout" class="logout">Sair</a>
        </div>
    </div>

    <div class="container">
        <h2>Gerenciar Perfis de Usuário</h2>
        <p>Usuário: <strong>${usuario.nomeCompleto}</strong> (ID: ${usuario.id})</p>

        <div class="assoc-container">
            <div class="assoc-box">
                <h3>Perfis Associados</h3>
                <ul class="profile-list">
                    <c:forEach var="perfil" items="${perfisAssociados}">
                        <li>
                            <span>${perfil.nomePerfil}</span>
                            <a href="${pageContext.request.contextPath}/associacao?action=remove&userId=${usuario.id}&perfilId=${perfil.id}"
                               class="btn-action btn-remove">Remover</a>
                        </li>
                    </c:forEach>
                    <c:if test="${empty perfisAssociados}">
                        <li class="empty-list">Nenhum perfil associado.</li>
                    </c:if>
                </ul>
            </div>

            <div class="assoc-box">
                <h3>Perfis Disponíveis</h3>
                 <ul class="profile-list">
                    <c:forEach var="perfil" items="${perfisDisponiveis}">
                        <li>
                            <span>${perfil.nomePerfil}</span>
                             <a href="${pageContext.request.contextPath}/associacao?action=add&userId=${usuario.id}&perfilId=${perfil.id}"
                               class="btn-action btn-add">Adicionar</a>
                        </li>
                    </c:forEach>
                    <c:if test="${empty perfisDisponiveis}">
                        <li class="empty-list">Nenhum perfil disponível.</li>
                    </c:if>
                </ul>
            </div>
        </div>
        
        <a href="${pageContext.request.contextPath}/usuarios" class="btn btn-secondary">Voltar para Usuários</a>
    </div>
</body>
</html>