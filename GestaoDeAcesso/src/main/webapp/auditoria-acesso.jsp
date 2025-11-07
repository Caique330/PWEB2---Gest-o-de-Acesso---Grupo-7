<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- Importação da biblioteca de formatação (FMT) --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Auditoria de Acessos - TechMaster</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;700&display=swap');
        body { font-family: 'Roboto', sans-serif; background-color: #f4f7f6; margin: 0; padding: 0; }
        .header { background-color: #ffffff; padding: 1rem 2rem; box-shadow: 0 2px 5px rgba(0,0,0,0.1); display: flex; justify-content: space-between; align-items: center; }
        .header h1 { color: #007bff; margin: 0; font-weight: 700; }
        .header-nav { display: flex; align-items: center; }
        .header-nav a { text-decoration: none; color: #555; font-weight: 500; margin-left: 1rem; }
        .header-nav a.logout { color: #dc3545; }
        .header-nav .nav-group { margin-left: 2rem; border-left: 1px solid #ddd; padding-left: 1rem; }
        .container { padding: 2rem; max-width: 1400px; margin: 2rem auto; background-color: #fff; border-radius: 8px; box-shadow: 0 5px 15px rgba(0,0,0,0.05); }
        .page-header { margin-bottom: 2rem; }
        table { width: 100%; border-collapse: collapse; margin-top: 1rem; }
        th, td { padding: 0.9rem; text-align: left; border-bottom: 1px solid #ddd; }
        th { background-color: #f8f9fa; }
    </style>
</head>
<body>

    <div class="header">
        <h1>TechMaster G7</h1>
        <div class="header-nav">
            <a href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
            
            <div class="nav-group">
                <a href="${pageContext.request.contextPath}/auditoria-falha">Logs de Falha</a>
            </div>
            
            <div class="nav-group">
                <a href="${pageContext.request.contextPath}/logout" class="logout">Sair</a>
            </div>
        </div>
    </div>

    <div class="container">
        <div class="page-header">
            <h2>Relatório de Auditoria: Acessos Sucedidos</h2>
            <p>(Exibindo os 100 logs mais recentes)</p>
        </div>

        <table>
            <thead>
                <tr>
                    <th>ID Log</th>
                    <th>Data/Hora</th>
                    <th>Ação Executada (e Usuário)</th>
                    <th>ID Recurso</th>
                    <th>IP de Origem</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="log" items="${listaLogsAcesso}">
                    <tr>
                        <td>${log.id}</td>
                        <td>
                            <%-- LINHA CORRIGIDA (Linha 62): Removido o type="both" --%>
                            <fmt:formatDate value="${log.dataHoraAcesso}" pattern="dd/MM/yyyy HH:mm:ss" />
                        </td>
                        <td>${log.acaoExecutada}</td>
                        <td>${log.idRecurso}</td>
                        <td>${log.ipOrigem}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>