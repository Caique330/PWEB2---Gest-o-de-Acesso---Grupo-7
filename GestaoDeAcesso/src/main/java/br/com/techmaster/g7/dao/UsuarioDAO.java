package br.com.techmaster.g7.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.techmaster.g7.model.Perfil;
import br.com.techmaster.g7.model.Usuario;
import br.com.techmaster.g7.util.H2Connection;
import br.com.techmaster.g7.util.HashUtil;

public class UsuarioDAO {

    /**
     * Valida o login e senha USANDO HASH.
     */
    public Usuario validarLogin(String login, String senha) {
        // Gera o hash da senha que o usuário digitou
        String hashDaSenhaDigitada = HashUtil.gerarHashSHA256(senha);
        
        String sql = "SELECT * FROM T_USUARIO WHERE login = ? AND senha_hash = ? AND status = 'ATIVO'";
        
        try (Connection conn = H2Connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, login);
            pstmt.setString(2, hashDaSenhaDigitada); // Compara HASH com HASH

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("id_usuario"));
                    usuario.setNomeCompleto(rs.getString("nome_completo"));
                    usuario.setLogin(rs.getString("login"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setStatus(rs.getString("status"));
                    
                    // Busca os perfis deste usuário
                    usuario.setPerfis(buscarPerfisPorUsuario(usuario.getId(), conn));
                    
                    return usuario;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao validar login: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null; // Não encontrou
    }

    /**
     * Busca os perfis associados a um ID de usuário (privado, para o login).
     */
    private List<Perfil> buscarPerfisPorUsuario(int idUsuario, Connection conn) throws SQLException {
        List<Perfil> perfis = new ArrayList<>();
        
        String sql = "SELECT p.id_perfil, p.nome_perfil, p.descricao " +
                     "FROM T_PERFIL p " +
                     "JOIN T_USUARIO_PERFIL up ON p.id_perfil = up.id_perfil " +
                     "WHERE up.id_usuario = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idUsuario);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Perfil perfil = new Perfil();
                    perfil.setId(rs.getInt("id_perfil"));
                    perfil.setNomePerfil(rs.getString("nome_perfil"));
                    perfil.setDescricao(rs.getString("descricao"));
                    perfis.add(perfil);
                }
            }
        }
        return perfis;
    }

    /**
     * Lista todos os usuários do banco (para o CRUD).
     */
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id_usuario, nome_completo, login, email, status FROM T_USUARIO ORDER BY nome_completo";
        
        try (Connection conn = H2Connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id_usuario"));
                usuario.setNomeCompleto(rs.getString("nome_completo"));
                usuario.setLogin(rs.getString("login"));
                usuario.setEmail(rs.getString("email"));
                usuario.setStatus(rs.getString("status"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar usuários: " + e.getMessage());
            e.printStackTrace();
        }
        return usuarios;
    }

    /**
     * Busca um usuário específico pelo ID (para o formulário de edição).
     */
    public Usuario buscarUsuarioPorId(int id) {
        String sql = "SELECT * FROM T_USUARIO WHERE id_usuario = ?";
        
        try (Connection conn = H2Connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("id_usuario"));
                    usuario.setNomeCompleto(rs.getString("nome_completo"));
                    usuario.setLogin(rs.getString("login"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setStatus(rs.getString("status"));
                    return usuario;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Insere um novo usuário no banco (com senha hasheada).
     */
    public void inserirUsuario(Usuario usuario) {
        String sql = "INSERT INTO T_USUARIO (nome_completo, login, email, senha_hash, status) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = H2Connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            String hashSenha = HashUtil.gerarHashSHA256(usuario.getSenhaHash());
            
            pstmt.setString(1, usuario.getNomeCompleto());
            pstmt.setString(2, usuario.getLogin());
            pstmt.setString(3, usuario.getEmail());
            pstmt.setString(4, hashSenha); // Salva o hash
            pstmt.setString(5, usuario.getStatus());
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Erro ao inserir usuário: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Atualiza um usuário.
     */
    public void atualizarUsuario(Usuario usuario) {
        boolean atualizarSenha = (usuario.getSenhaHash() != null && !usuario.getSenhaHash().isEmpty());
        String sql;
        
        if (atualizarSenha) {
            sql = "UPDATE T_USUARIO SET nome_completo = ?, login = ?, email = ?, status = ?, senha_hash = ? WHERE id_usuario = ?";
        } else {
            sql = "UPDATE T_USUARIO SET nome_completo = ?, login = ?, email = ?, status = ? WHERE id_usuario = ?";
        }

        try (Connection conn = H2Connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, usuario.getNomeCompleto());
            pstmt.setString(2, usuario.getLogin());
            pstmt.setString(3, usuario.getEmail());
            pstmt.setString(4, usuario.getStatus());
            
            if (atualizarSenha) {
                String hashNovaSenha = HashUtil.gerarHashSHA256(usuario.getSenhaHash());
                pstmt.setString(5, hashNovaSenha);
                pstmt.setInt(6, usuario.getId());
            } else {
                pstmt.setInt(5, usuario.getId());
            }
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar usuário: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Exclui um usuário.
     */
    public void excluirUsuario(int id) {
        String sqlPerfil = "DELETE FROM T_USUARIO_PERFIL WHERE id_usuario = ?";
        String sqlLogAcesso = "DELETE FROM T_LOG_ACESSO WHERE id_usuario = ?"; 
        String sqlLogFalha = "DELETE FROM T_LOG_TENTATIVA_FALHA WHERE login_tentado = (SELECT login FROM T_USUARIO WHERE id_usuario = ?)"; 
        String sqlUsuario = "DELETE FROM T_USUARIO WHERE id_usuario = ?";
        
        Connection conn = null;
        try {
            conn = H2Connection.getConnection();
            conn.setAutoCommit(false); 

            try (PreparedStatement pstmt = conn.prepareStatement(sqlPerfil)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }
            try (PreparedStatement pstmt = conn.prepareStatement(sqlLogAcesso)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }
            try (PreparedStatement pstmt = conn.prepareStatement(sqlLogFalha)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }
            try (PreparedStatement pstmt = conn.prepareStatement(sqlUsuario)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }
            
            conn.commit(); 

        } catch (SQLException e) {
            System.err.println("Erro ao excluir usuário: " + e.getMessage());
            try { if (conn != null) conn.rollback(); } catch (SQLException e2) { e2.printStackTrace(); }
        } finally {
            try { if (conn != null) { conn.setAutoCommit(true); conn.close(); } } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    // --- MÉTODOS DE ASSOCIAÇÃO (Usados pela API de Associação) ---

    /**
     * Busca os perfis que JÁ ESTÃO associados a um usuário.
     */
    public List<Perfil> buscarPerfisAssociados(int idUsuario) {
        List<Perfil> perfis = new ArrayList<>();
        String sql = "SELECT p.id_perfil, p.nome_perfil, p.descricao " +
                     "FROM T_PERFIL p " +
                     "JOIN T_USUARIO_PERFIL up ON p.id_perfil = up.id_perfil " +
                     "WHERE up.id_usuario = ?";

        try (Connection conn = H2Connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idUsuario);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    perfis.add(new Perfil(
                        rs.getInt("id_perfil"),
                        rs.getString("nome_perfil"),
                        rs.getString("descricao")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return perfis;
    }

    /**
     * Associa um perfil a um usuário (INSERT em T_USUARIO_PERFIL).
     */
    public void adicionarPerfilUsuario(int idUsuario, int idPerfil) {
        String sql = "INSERT INTO T_USUARIO_PERFIL (id_usuario, id_perfil) VALUES (?, ?)";
        
        try (Connection conn = H2Connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idUsuario);
            pstmt.setInt(2, idPerfil);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            if (!e.getSQLState().equals("23505")) { // Ignora erro de chave duplicada
                e.printStackTrace();
            }
        }
    }

    /**
     * Remove a associação de um perfil de um usuário (DELETE de T_USUARIO_PERFIL).
     */
    public void removerPerfilUsuario(int idUsuario, int idPerfil) {
        String sql = "DELETE FROM T_USUARIO_PERFIL WHERE id_usuario = ? AND id_perfil = ?";
        
        try (Connection conn = H2Connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idUsuario);
            pstmt.setInt(2, idPerfil);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	public List<Perfil> buscarPerfisDisponiveis(int idUsuario) {
		// TODO Auto-generated method stub
		return null;
	}
}