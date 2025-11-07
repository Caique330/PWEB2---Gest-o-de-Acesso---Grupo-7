<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gerenciar Regras do Perfil - TechMaster</title>
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
        .btn-danger { background-color: #dc3545; color: white; padding: 0.3rem 0.6rem; font-size: 0.9rem; }
        .btn-primary { background-color: #007bff; color: white; cursor: pointer; border: none; font-size: 1rem;}

        /* Layout de Regras */
        .regra-container {
            display: flex;
            gap: 2rem;
            margin-top: 1.5rem;
        }
        .regra-box-associado { flex: 2; } /* Mais largo */
        .regra-box-novo { flex: 1; }     /* Mais estreito */
        
        .regra-box {
            border: 1px solid #ddd;
            border-radius: 8px;
            background-color: #fdfdfd;
        }
        .regra-box h3 {
            margin: 0; padding: 1rem; background-color: #f8f9fa;
            border-bottom: 1px solid #ddd; border-radius: 8px 8px 0 0;
        }
        
        /* Formulário de Adição */
        .form-add-regra { padding: 1rem; }
        .form-group { margin-bottom: 1rem; }
        .form-group label { display: block; margin-bottom: 0.5rem; font-weight: 500; }
        .form-group select { width: 100%; padding: 0.75rem; border: 1px solid #ddd; border-radius: 5px; }
        
        /* Lista de Regras */
        .regra-list { list-style: none; padding: 0; margin: 0; max-height: 400px; overflow-y: auto; }
        .regra-list li {
            display: flex; justify-content: space-between; align-items: center;
            padding: 0.8rem 1rem; border-bottom: 1px solid #eee;
        }
        .regra-list li span { font-weight: 500; }
        .empty-list { padding: 1rem; color: #777; font-style: italic; }
    </style>
</head>
<body>
    <div class="header">
        <h1>TechMaster G7</h1>
        <div class="header-nav">
            <a href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
            <a href="${pageContext.request.contextPath}/usuarios">Usuários</a>
            <a href="${pageContext.request.contextPath}/perfis">Perfis</a>
            <a href="${pageContext.request.contextPath}/recursos">Recursos</a>
            <a href="${pageContext.request.contextPath}/logout" class.logout="logout">Sair</a>
        </div>
    </div>

    <div class="container">
        <h2>Gerenciar Regras de Permissão</h2>
        <p>Perfil: <strong>${perfil.nomePerfil}</strong> (ID: ${perfil.id})</p>

        <div class="regra-container">
            <div class="regra-box regra-box-novo">
                <h3>Adicionar Permissão</h3>
                <form class="form-add-regra" action="${pageContext.request.contextPath}/regras" method="POST">
                    <input type="hidden" name="idPerfil" value="${perfil.id}">
                    
                    <div class="form-group">
                        <label for="idRecurso">1. Selecione o Recurso</label>
                        <select id="idRecurso" name="idRecurso" required>
                            <option value="">-- Recursos --</option>
                            <c:forEach var="rec" items="${todosRecursos}">
                                <option value="${rec.id}">${rec.nomeRecurso}</option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="idPermissao">2. Selecione a Permissão</label>
                        <select id="idPermissao" name="idPermissao" required>
                            <option value="">-- Permissões --</option>
                             <c:forEach var="perm" items="${todasPermissoes}">
                                <option value="${perm.id}">${perm.nomePermissao}</option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <button type="submit" class="btn btn-primary" style="width: 100%;">Adicionar</button>
                </form>
            </div>
            
            <div class="regra-box regra-box-associado">
                <h3>Permissões Associadas</h3>
                <ul class="regra-list">
                    <c:forEach var="regra" items="${regrasAssociadas}">
                        <li>
                            <span>${regra.descricaoCompleta}</span>
                            <a href="${pageContext.request.contextPath}/regras?action=remove&perfilId=${perfil.id}&regraId=${regra.idRegra}"
                               class="btn btn-danger">Remover</a>
                        </li>
                    </c:forEach>
                    <c:if test="${empty regrasAssociadas}">
                        <li class="empty-list">Nenhuma permissão concedida a este perfil.</li>
                    </c:if>
                </ul>
            </div>
        </div>
        
        <a href="${pageContext.request.contextPath}/perfis" class="btn btn-secondary">Voltar para Perfis</a>
    </div>
</body>
</html>