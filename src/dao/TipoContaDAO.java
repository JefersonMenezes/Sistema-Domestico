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
import modelo.TipoConta;

/**
 *
 * @author zion
 */
public class TipoContaDAO {
    private final Connection conexao;

    public TipoContaDAO() {
        this.conexao = new ConnectionFactory().getConnection();
    }


    public void inserir(TipoConta tipo) {
        String sql = "INSERT INTO `financa`.`tipo_conta` (`tipo`) VALUES (?)";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, tipo.getTipo());
            stmt.execute();
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(TipoContaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<TipoConta> listar() {
        String sql = "select * from tipo_conta";
        
        try {
            List<TipoConta> tipos = new ArrayList<TipoConta>();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                TipoConta tipo = new TipoConta();
                tipo.setId(rs.getInt("id"));
                tipo.setTipo(rs.getString("tipo"));
                tipos.add(tipo);
            }
            rs.close();
            stmt.close();
            return tipos;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);}
    }
}
