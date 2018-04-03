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
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Receita;


/**
 *
 * @author takedown
 */
public class ReceitaDAO {

    private final Connection conexao;

    public ReceitaDAO() {
        this.conexao = new ConnectionFactory().getConnection();
    }

    public void inserir(Receita receita) {
        String sql = "INSERT INTO `financa`.`receita` (`valor`,`data`,`descricao`,`recebido`,`conta_id`,`categoria_id`) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setDouble(1, receita.getValor());
            stmt.setObject(2, receita.getData());
            stmt.setString(3, receita.getDescricao());
            stmt.setBoolean(4, receita.isRecebido());
            stmt.setInt(5, receita.getConta().getId());
            stmt.setInt(6, receita.getCategoria().getId());
            stmt.execute();
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(ReceitaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public double listaTotal(int idUsuario) {
        {
            String sql = "SELECT sum(valor) as valor FROM financa.receita JOIN financa.conta ON conta_id = conta.id  where usuario_id = ?";
            try {
                double total = 0;
                PreparedStatement stmt = conexao.prepareStatement(sql);
                stmt.setInt(1, idUsuario);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    total = rs.getInt("valor");
                }
                rs.close();
                stmt.close();
                return total;
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    


}
