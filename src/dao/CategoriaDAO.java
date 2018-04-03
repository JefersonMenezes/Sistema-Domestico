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
import modelo.Categoria;

/**
 *
 * @author zion
 */
public class CategoriaDAO {
     private final Connection conexao;

    public CategoriaDAO() {
        this.conexao = new ConnectionFactory().getConnection();
    }

    public void inserir(Categoria categoria) {
        String sql = "INSERT INTO `financa`.`categoria` (`nome`, `tipo_categoria`) VALUES (?,?)";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, categoria.getNome());
            stmt.setString(2, String.valueOf(categoria.getTipo()));
            stmt.execute();
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(CategoriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Categoria> listaDespesas() {
        String sql = "SELECT * FROM financa.categoria where tipo_categoria = 'D'";
        try {
            List<Categoria> despesas = new ArrayList<Categoria>();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Categoria despesa = new Categoria();
                despesa.setId(rs.getInt("id"));
                despesa.setNome(rs.getString("nome"));
                despesas.add(despesa);
            }
            rs.close();
            stmt.close();
            return despesas;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);

        }
    }

    public List<Categoria> listaReceitas() {
        String sql = "SELECT * FROM financa.categoria where tipo_categoria = 'R'";
        try {
            List<Categoria> despesas = new ArrayList<Categoria>();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Categoria despesa = new Categoria();
                despesa.setId(rs.getInt("id"));
                despesa.setNome(rs.getString("nome"));
                despesas.add(despesa);
            }
            rs.close();
            stmt.close();
            return despesas;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);

        }
    }
}

