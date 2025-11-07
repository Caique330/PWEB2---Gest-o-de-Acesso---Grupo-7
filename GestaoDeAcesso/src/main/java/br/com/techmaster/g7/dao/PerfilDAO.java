package br.com.techmaster.g7.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.techmaster.g7.model.Perfil;
import br.com.techmaster.g7.util.H2Connection;

public class PerfilDAO {

    /**
     * Lista todos os perfis. (Usado no GET /api/perfis)
     */
    public List<Perfil> listarPerfis() {
        List<Perfil> perfis = new ArrayList<>();
        String sql = "SELECT * FROM T_PERFIL ORDER BY nome_perfil";
        
        try (Connection conn = H2Connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Perfil perfil = new Perfil();
                perfil.setId(rs.getInt("id_perfil"));
                perfil.setNomePerfil(rs.getString("nome_perfil"));
                perfil.setDescricao(rs.getString("descricao"));
                perfis.add(perfil);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar perfis: " + e.getMessage());
            e.printStackTrace();
        }
        return perfis;
    }

    /**
     * Busca um perfil específico pelo ID. (Usado no GET /api/perfis/1)
     */
    public Perfil buscarPerfilPorId(int id) {
        String sql = "SELECT * FROM T_PERFIL WHERE id_perfil = ?";
        
        try (Connection conn = H2Connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Perfil perfil = new Perfil();
                    perfil.setId(rs.getInt("id_perfil"));
                    perfil.setNomePerfil(rs.getString("nome_perfil"));
                    perfil.setDescricao(rs.getString("descricao"));
                    return perfil;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar perfil por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Insere um novo perfil. (Usado no POST /api/perfis)
     */
    public void inserirPerfil(Perfil perfil) {
        String sql = "INSERT INTO T_PERFIL (nome_perfil, descricao) VALUES (?, ?)";
        
        try (Connection conn = H2Connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, perfil.getNomePerfil());
            pstmt.setString(2, perfil.getDescricao());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Erro ao inserir perfil: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Atualiza um perfil. (Usado no PUT /api/perfis/1)
     */
    public void atualizarPerfil(Perfil perfil) {
        String sql = "UPDATE T_PERFIL SET nome_perfil = ?, descricao = ? WHERE id_perfil = ?";
        
        try (Connection conn = H2Connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, perfil.getNomePerfil());
            pstmt.setString(2, perfil.getDescricao());
            pstmt.setInt(3, perfil.getId());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar perfil: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Exclui um perfil. (Usado no DELETE /api/perfis/1)
     * Remove associações de T_USUARIO_PERFIL e T_REGRA_PERMISSAO.
     */
    public void excluirPerfil(int id) {
        String sqlUsuarioPerfil = "DELETE FROM T_USUARIO_PERFIL WHERE id_perfil = ?";
        String sqlRegraPermissao = "DELETE FROM T_REGRA_PERMISSAO WHERE id_perfil = ?";
        String sqlPerfil = "DELETE FROM T_PERFIL WHERE id_perfil = ?";
        
        Connection conn = null;
        try {
            conn = H2Connection.getConnection();
            conn.setAutoCommit(false); // Inicia transação

            // 1. Deleta associações com usuários
            try (PreparedStatement pstmt = conn.prepareStatement(sqlUsuarioPerfil)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }
            
            // 2. Deleta associações com regras de permissão
            try (PreparedStatement pstmt = conn.prepareStatement(sqlRegraPermissao)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }
            
            // 3. Deleta o perfil
            try (PreparedStatement pstmt = conn.prepareStatement(sqlPerfil)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }
            
            conn.commit(); // Confirma

        } catch (SQLException e) {
            System.err.println("Erro ao excluir perfil: " + e.getMessage());
            e.printStackTrace();
            try { if (conn != null) conn.rollback(); } catch (SQLException e2) { e2.printStackTrace(); }
        } finally {
            try { if (conn != null) { conn.setAutoCommit(true); conn.close(); } } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}