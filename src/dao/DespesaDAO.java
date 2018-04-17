/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import modelo.Categoria;
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
            stmt.setDate(2, Date.valueOf(despesa.getData()));
            stmt.setString(3, despesa.getDescricao());
            stmt.setBoolean(4, despesa.isPago());
            stmt.setInt(5, despesa.getConta().getId());
            stmt.setInt(6, despesa.getCategoria().getId());

            stmt.execute();
            stmt.close();

        } catch (SQLException ex) {
            System.out.println("dao.DespesaDAO.inserir()" + ex);
        }

    }

    public List<Despesa> getDespesasCategoria(int idUser, LocalDate inicio, LocalDate fim) {
        String sql = "SELECT it.nome as nome, sum(es.valor) as valor from  usuario as ite inner join categoria as it inner join despesa as es on it.id = es.categoria_id where ite.id = ? and data between ? and ? group by nome";
        try {
            List<Despesa> despesas = new ArrayList<Despesa>();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idUser);
            
            stmt.setDate(2, Date.valueOf(inicio));
            stmt.setDate(3, Date.valueOf(fim));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Despesa despesa = new Despesa();
                despesa.setValor(rs.getDouble("valor"));

                Categoria categoria = new Categoria();
                categoria.setNome(rs.getString("nome"));
                despesa.setCategoria(categoria);

                despesas.add(despesa);
            }
            rs.close();
            stmt.close();
            return despesas;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public double listaSomaDespesas(int idUser, LocalDate inicio, LocalDate fim) {
        String sql = "SELECT sum(valor) as valor FROM financa.despesa JOIN financa.conta ON conta_id = conta.id where usuario_id = ? and data between ? and ?";

        try {
            double total = 0;
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idUser);
            stmt.setDate(2, Date.valueOf(inicio));
            stmt.setDate(3, Date.valueOf(fim));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                total = rs.getDouble("valor");
            }
            rs.close();
            stmt.close();
            return total;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Despesa> getGroupbyData(int idUser, LocalDate ldInicio, LocalDate ldFim) {
        String sql = "SELECT sum(despesa.valor) as soma_valor, data FROM financa.despesa INNER JOIN usuario WHERE usuario.id = ? and data between ? and ? group by data order by data asc";

        try {
            List<Despesa> despesas = new ArrayList<Despesa>();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idUser);
            stmt.setDate(2, Date.valueOf(ldInicio));
            stmt.setDate(3, Date.valueOf(ldFim));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Despesa despesa = new Despesa();
                despesa.setValor(rs.getDouble("soma_valor"));
                Instant instant = Instant.ofEpochMilli(rs.getDate("data").getTime());

                despesa.setData(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate());

                despesas.add(despesa);
            }
            return despesas;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
