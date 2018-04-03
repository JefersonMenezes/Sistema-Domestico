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
import javax.swing.JOptionPane;
import modelo.Despesa;

/**
 *
 * @author zion
 */
public class DespesaDAO {

    private final Connection conexao;

    public DespesaDAO() {
        this.conexao = new ConnectionFactory().getConnection();
    }

    public void inserir(Despesa despesa) {
        String sql = "INSERT INTO `financa`.`despesa` (`valor`,`data`,`descricao`,`pago`,`conta_id`,`categoria_id`) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setDouble(1, despesa.getValor());
            stmt.setObject(2, despesa.getData());
            stmt.setString(3, despesa.getDescricao());
            stmt.setBoolean(4, despesa.isPago());
            stmt.setInt(5, despesa.getConta().getId());
            stmt.setInt(6, despesa.getCategoria().getId());

            stmt.execute();
            stmt.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar salvar despesa!");
        }

    }

    

}
