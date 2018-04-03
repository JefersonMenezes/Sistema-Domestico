/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Transferencia;

/**
 *
 * @author zion
 */
public class TransferenciaDAO {

    private final Connection conexao;
    
    public TransferenciaDAO(){
        conexao = new ConnectionFactory().getConnection();
    }

    public void inserir(Transferencia acao) {
        String sql = "INSERT INTO `financa`.`transferencia` (`observacao`,`valor`,`data`,`contaorigem_id`,`contadestino_id`) VALUES (?,?,?,?,?)";
           try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, acao.getDescricao());
            stmt.setDouble(2, acao.getValor());
            stmt.setObject(3, acao.getData());
            stmt.setInt(4, acao.getContaOrigem().getId());
            stmt.setInt(5, acao.getContaDestino().getId());
            stmt.execute();
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(CategoriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
