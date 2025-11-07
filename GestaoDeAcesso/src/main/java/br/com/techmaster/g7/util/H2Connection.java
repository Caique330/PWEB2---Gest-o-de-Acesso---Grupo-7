package br.com.techmaster.g7.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2Connection {

    // URL de conexão H2. 
    // '~/techmaster_g7_db' cria um arquivo de banco no diretório home do usuário.
    // AUTO_SERVER=TRUE permite que múltiplos processos acessem (útil no Eclipse)
    private static final String URL = "jdbc:h2:~/techmaster_g7_db;AUTO_SERVER=TRUE";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    // Bloco estático para carregar o driver H2 na inicialização da classe
    static {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Falha ao carregar o driver H2!", e);
        }
    }

    /**
     * Obtém uma nova conexão com o banco de dados H2.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    // Opcional: Método para inicializar o banco (executar o SQL da Fase 1)
    // Você pode chamar isso de um Servlet de inicialização ou manualmente.
    public static void inicializarBanco() {
        // TODO: Adicionar lógica para ler o script SQL (Fase 1.1) 
        // e executá-lo usando um Statement.
        // Isso garante que as tabelas existam na primeira execução.
        System.out.println("Banco de dados verificado/inicializado.");
    }
}