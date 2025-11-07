package br.com.techmaster.g7.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.techmaster.g7.model.Permissao;
import br.com.techmaster.g7.util.H2Connection;

public class PermissaoDAO {

    /**
     * Lista todos os tipos de permissão.
     */
    public List<Permissao> listarPermissoes() {
        List<Permissao> permissoes = new ArrayList<>();
        String sql = "SELECT * FROM T_PERMISSAO ORDER BY nome_permissao";
        
        try (Connection conn = H2Connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                permissoes.add(new Permissao(
                    rs.getInt("id_permissao"),
                    rs.getString("nome_permissao")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar permissões: " + e.getMessage());
            e.printStackTrace();
        }
        return permissoes;
    }

    /**
     * Insere um novo tipo de permissão.
     */
    public void inserirPermissao(Permissao permissao) {
        String sql = "INSERT INTO T_PERMISSAO (nome_permissao) VALUES (?)";
        try (Connection conn = H2Connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, permissao.getNomePermissao());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Exclui um tipo de permissão.
     * CUIDADO: Remove as regras associadas.
     */
    public void excluirPermissao(int id) {
        String sqlRegra = "DELETE FROM T_REGRA_PERMISSAO WHERE id_permissao = ?";
        String sqlPermissao = "DELETE FROM T_PERMISSAO WHERE id_permissao = ?";
        
        Connection conn = null;
        try {
            conn = H2Connection.getConnection();
            conn.setAutoCommit(false); // Transação

            // 1. Deleta regras
            try (PreparedStatement pstmt = conn.prepareStatement(sqlRegra)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }
            
            // 2. Deleta permissão
            try (PreparedStatement pstmt = conn.prepareStatement(sqlPermissao)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }
            
            conn.commit();

        } catch (SQLException e) {
            System.err.println("Erro ao excluir permissão: " + e.getMessage());
            try { if (conn != null) conn.rollback(); } catch (SQLException e2) { e2.printStackTrace(); }
        } finally {
            try { if (conn != null) { conn.setAutoCommit(true); conn.close(); } } catch (SQLException e) { e.printStackTrace(); }
        }
    }
    
    // (Não implementaremos 'update' ou 'buscarPorId' para esta tabela
    // simples, 'inserir' e 'excluir' são suficientes)
}