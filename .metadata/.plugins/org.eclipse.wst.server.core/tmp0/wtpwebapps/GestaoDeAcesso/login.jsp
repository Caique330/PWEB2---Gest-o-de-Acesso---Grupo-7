<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>TechMaster - Gestão de Acesso</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;700&display=swap');

        body {
            font-family: 'Roboto', sans-serif;
            background-color: #f0f2f5;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .login-container {
            background-color: #ffffff;
            padding: 2.5rem;
            border-radius: 10px;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
            text-align: center;
        }

        .login-container h1 {
            color: #333;
            margin-bottom: 0.5rem;
            font-weight: 700;
        }

        .login-container p {
            color: #777;
            margin-bottom: 2rem;
        }

        .form-group {
            margin-bottom: 1.5rem;
            text-align: left;
        }

        .form-group label {
            display: block;
            margin-bottom: 0.5rem;
            font-weight: 500;
            color: #555;
        }

        .form-group input {
            width: 100%;
            padding: 0.75rem;
            border: 1px solid #ddd;
            border-radius: 5px;
            box-sizing: border-box; /* Garante que o padding não quebre o layout */
        }

        .btn-login {
            width: 100%;
            padding: 0.85rem;
            border: none;
            border-radius: 5px;
            background-color: #007bff;
            color: white;
            font-size: 1rem;
            font-weight: 700;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .btn-login:hover {
            background-color: #0056b3;
        }

        .error-message {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
            padding: 0.75rem;
            border-radius: 5px;
            margin-top: 1.5rem;
        }
    </style>
</head>
<body>

    <div class="login-container">
        <h1>TechMaster</h1>
        <p>Módulo de Gestão de Acesso</p>

        <form action="login" method="POST">
            <div class="form-group">
                <label for="login">Login</label>
                <input type="text" id="login" name="login" required>
            </div>
            
            <div class="form-group">
                <label for="senha">Senha</label>
                <input type="password" id="senha" name="senha" required>
            </div>
            
            <button type="submit" class="btn-login">Entrar</button>
        </form>

        <c:if test="${not empty erroLogin}">
            <div class="error-message">
                ${erroLogin}
            </div>
        </c:if>

    </div>

</body>
</html>