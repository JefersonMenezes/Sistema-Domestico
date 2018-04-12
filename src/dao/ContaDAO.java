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
import modelo.Conta;
import modelo.TipoConta;
import modelo.Usuario;

/**
 *
 * @author takedown
 */
public class ContaDAO {

    private final Connection conexao;

    public ContaDAO() {
        this.conexao = new ConnectionFactory().getConnection();
    }

    public void inserir(Conta conta) {
        String sql = "INSERT INTO `financa`.`conta` (`nome`,`saldo_inicial`,`usuario_id`,`tipo_conta_id`,`inclui_soma` ) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, conta.getNome());
            stmt.setDouble(2, conta.getSaldoInicial());
            stmt.setInt(3, conta.getUsuario().getId());
            stmt.setInt(4, conta.getTipoConta().getId());
            stmt.setBoolean(5, conta.isIncluiSoma());
            stmt.execute();

            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ContaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   

    public List<Conta> listaContasUser(Usuario user) {
        String sql = "SELECT conta.id, conta.nome, conta.saldo_inicial, conta.inclui_soma, tipo_conta.tipo, tipo_conta.id as tipo_id FROM financa.conta JOIN financa.tipo_conta ON conta.tipo_conta_id = tipo_conta.id WHERE usuario_id = ?";
        try {
            List<Conta> contas = new ArrayList<Conta>();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, user.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Conta conta = new Conta();
                conta.setId(rs.getInt("id"));
                conta.setNome(rs.getString("nome"));
                conta.setSaldoInicial(rs.getDouble("saldo_inicial"));
                conta.setIncluiSoma(rs.getBoolean("inclui_soma"));
                
                TipoConta tipo = new TipoConta();
                tipo.setTipo(rs.getString("tipo"));
                tipo.setId(rs.getInt("tipo_id"));
                
                conta.setTipoConta(tipo);

                contas.add(conta);

            }
            rs.close();
            stmt.close();
            return contas;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void altera(Conta conta) {
        String sql = "UPDATE conta SET nome = ?, saldo_inicial = ?, tipo_conta_id = ? WHERE id = ?";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            
            stmt.setString(1, conta.getNome());
            stmt.setDouble(2, conta.getSaldoInicial());
            stmt.setInt(3, conta.getTipoConta().getId());
            stmt.setInt(4, conta.getId());

            stmt.execute();
            stmt.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public double listaSomaContas(int idUser) {
         String sql = "SELECT sum(saldo_inicial) FROM financa.conta where usuario_id = ? and inclui_soma = true;";
        try {
            double total = 0;
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idUser);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                total = rs.getDouble("sum(saldo_inicial)");
            }
            rs.close();
            stmt.close();
            return total;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
        public void setSomaSaldo(double valor, int id) {
        String sql = "UPDATE financa.conta SET saldo_inicial = saldo_inicial + ? WHERE id = ?";
        
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setDouble(1, valor);
            stmt.setInt(2, id);
            stmt.execute();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReceitaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

        public void setSubtraiSaldo(double valor, int id) {
        String sql = "UPDATE financa.conta SET saldo_inicial = saldo_inicial - ? WHERE id = ?";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setDouble(1, valor);
            stmt.setInt(2, id);
            stmt.execute();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReceitaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
