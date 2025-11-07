package br.com.techmaster.g7.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.techmaster.g7.model.Recurso;
import br.com.techmaster.g7.util.H2Connection;

public class RecursoDAO {

    /**
     * Lista todos os recursos.
     */
    public List<Recurso> listarRecursos() {
        List<Recurso> recursos = new ArrayList<>();
        String sql = "SELECT * FROM T_RECURSO ORDER BY nome_recurso";
        
        try (Connection conn = H2Connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                recursos.add(new Recurso(
                    rs.getInt("id_recurso"),
                    rs.getString("nome_recurso"),
                    rs.getString("tipo_recurso")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar recursos: " + e.getMessage());
            e.printStackTrace();
        }
        return recursos;
    }

    /**
     * Busca um recurso específico pelo ID.
     */
    public Recurso buscarRecursoPorId(int id) {
        String sql = "SELECT * FROM T_RECURSO WHERE id_recurso = ?";
        
        try (Connection conn = H2Connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Recurso(
                        rs.getInt("id_recurso"),
                        rs.getString("nome_recurso"),
                        rs.getString("tipo_recurso")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar recurso por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Insere um novo recurso.
     */
    public void inserirRecurso(Recurso recurso) {
        String sql = "INSERT INTO T_RECURSO (nome_recurso, tipo_recurso) VALUES (?, ?)";
        
        try (Connection conn = H2Connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, recurso.getNomeRecurso());
            pstmt.setString(2, recurso.getTipoRecurso());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Erro ao inserir recurso: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Atualiza um recurso.
     */
    public void atualizarRecurso(Recurso recurso) {
        String sql = "UPDATE T_RECURSO SET nome_recurso = ?, tipo_recurso = ? WHERE id_recurso = ?";
        
        try (Connection conn = H2Connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, recurso.getNomeRecurso());
            pstmt.setString(2, recurso.getTipoRecurso());
            pstmt.setInt(3, recurso.getId());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar recurso: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Exclui um recurso.
     */
    public void excluirRecurso(int id) {
        String sqlRegra = "DELETE FROM T_REGRA_PERMISSAO WHERE id_recurso = ?";
        String sqlLog = "DELETE FROM T_LOG_ACESSO WHERE id_recurso = ?";
        String sqlRecurso = "DELETE FROM T_RECURSO WHERE id_recurso = ?";
        
        Connection conn = null;
        try {
            conn = H2Connection.getConnection();
            conn.setAutoCommit(false); // Transação

            try (PreparedStatement pstmt = conn.prepareStatement(sqlRegra)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }
            try (PreparedStatement pstmt = conn.prepareStatement(sqlLog)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }
            try (PreparedStatement pstmt = conn.prepareStatement(sqlRecurso)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }
            
            conn.commit();

        } catch (SQLException e) {
            System.err.println("Erro ao excluir recurso: " + e.getMessage());
            try { if (conn != null) conn.rollback(); } catch (SQLException e2) { e2.printStackTrace(); }
        } finally {
            try { if (conn != null) { conn.setAutoCommit(true); conn.close(); } } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}