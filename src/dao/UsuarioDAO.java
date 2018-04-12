/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Usuario;

/**
 *
 * @author zion
 */
public class UsuarioDAO {
    
     private final Connection conexao;

    public UsuarioDAO() {
        this.conexao = new ConnectionFactory().getConnection();
    }

    public void inserir(Usuario usuario) {
        System.out.println("Inserindo usuario...");
        String sql = "INSERT INTO financa.usuario (login,senha,nome,email,ativo) VALUE (?,?,?,?,?)";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, usuario.getLogin());
            stmt.setString(2, usuario.getSenha());
            stmt.setString(3, usuario.getNome());
            stmt.setString(4, usuario.getEmail());
            stmt.setBoolean(5, usuario.isAtivo());

            stmt.execute();
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Usuario> listar() {
        String sql = "SELECT * FROM usuario";
        try {

            List<Usuario> usuarios = new ArrayList<Usuario>();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setLogin(rs.getString("login"));
                usuario.setAtivo(rs.getBoolean("ativo"));
                usuario.setSenha(rs.getString("senha"));

                usuarios.add(usuario);
            }
            rs.close();
            stmt.close();
            return usuarios;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}

