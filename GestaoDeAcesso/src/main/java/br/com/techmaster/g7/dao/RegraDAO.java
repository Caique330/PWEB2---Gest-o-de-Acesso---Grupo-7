package br.com.techmaster.g7.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.techmaster.g7.model.RegraPermissaoDTO;
import br.com.techmaster.g7.util.H2Connection;

public class RegraDAO {

    /**
     * Busca as regras que JÁ ESTÃO associadas a um perfil.
     * (Usado no GET /api/perfis/{id}/regras)
     */
    public List<RegraPermissaoDTO> buscarRegrasAssociadas(int idPerfil) {
        List<RegraPermissaoDTO> regras = new ArrayList<>();
        String sql = "SELECT reg.id_regra, reg.id_perfil, reg.id_recurso, reg.id_permissao, " +
                     "rec.nome_recurso, perm.nome_permissao " +
                     "FROM T_REGRA_PERMISSAO reg " +
                     "JOIN T_RECURSO rec ON reg.id_recurso = rec.id_recurso " +
                     "JOIN T_PERMISSAO perm ON reg.id_permissao = perm.id_permissao " +
                     "WHERE reg.id_perfil = ?";
        
        try (Connection conn = H2Connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idPerfil);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    RegraPermissaoDTO regra = new RegraPermissaoDTO();
                    regra.setIdRegra(rs.getInt("id_regra"));
                    regra.setIdPerfil(rs.getInt("id_perfil"));
                    regra.setIdRecurso(rs.getInt("id_recurso"));
                    regra.setIdPermissao(rs.getInt("id_permissao"));
                    regra.setNomeRecurso(rs.getString("nome_recurso"));
                    regra.setNomePermissao(rs.getString("nome_permissao"));
                    regras.add(regra);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return regras;
    }

    /**
     * Adiciona uma nova regra (associando um recurso/permissão a um perfil).
     * (Usado no POST /api/perfis/{id}/regras)
     */
    public void adicionarRegra(int idPerfil, int idRecurso, int idPermissao) {
        String sql = "INSERT INTO T_REGRA_PERMISSAO (id_perfil, id_recurso, id_permissao) VALUES (?, ?, ?)";
        
        try (Connection conn = H2Connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idPerfil);
            pstmt.setInt(2, idRecurso);
            pstmt.setInt(3, idPermissao);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            if (!e.getSQLState().equals("23505")) { // Ignora duplicados
                e.printStackTrace();
            }
        }
    }

    /**
     * Remove uma regra pelo seu ID único.
     * (Usado no DELETE /api/perfis/{id}/regras/{idRegra})
     */
    public void removerRegra(int idRegra) {
        String sql = "DELETE FROM T_REGRA_PERMISSAO WHERE id_regra = ?";
        
        try (Connection conn = H2Connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idRegra);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}