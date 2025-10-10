<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>TechMaster - Sistema de Gestão de Acesso</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(to right, #0072ff, #00c6ff);
            margin: 0;
            padding: 0;
        }

        header {
            background-color: #003366;
            color: white;
            padding: 15px;
            text-align: center;
        }

        main {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 80vh;
        }

        .login-container {
            background-color: white;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0px 0px 15px rgba(0,0,0,0.2);
            width: 350px;
            text-align: center;
        }

        .login-container h2 {
            color: #003366;
            margin-bottom: 25px;
        }

        input[type="text"],
        input[type="password"] {
            width: 90%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        button {
            background-color: #003366;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            transition: 0.3s;
        }

        button:hover {
            background-color: #0055aa;
        }

        footer {
            text-align: center;
            background-color: #003366;
            color: white;
            padding: 10px;
            position: fixed;
            width: 100%;
            bottom: 0;
        }

        .info {
            margin-top: 20px;
            font-size: 0.9em;
            color: gray;
        }
    </style>
</head>
<body>
    <header>
        <h1>TechMaster - Sistema de Gestão de Acesso</h1>
    </header>

    <main>
        <div class="login-container">
            <h2>Login de Usuário</h2>
            <form action="UsuarioServlet" method="post">
                <input type="text" name="login" placeholder="Digite seu login" required>
                <input type="password" name="senha" placeholder="Digite sua senha" required>
                <button type="submit">Entrar</button>
            </form>
            <div class="info">
                <p>Desenvolvido pelo Grupo 7 - PWEB2</p>
                <p>Versão 1.0 | Banco H2 | JSP e Servlet</p>
            </div>
        </div>
    </main>

    <footer>
        <p>© 2025 TechMaster - Todos os direitos reservados.</p>
    </footer>
</body>
</html>
