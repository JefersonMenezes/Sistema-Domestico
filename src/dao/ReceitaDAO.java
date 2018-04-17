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
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Categoria;
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
            stmt.setObject(2, Date.valueOf(receita.getData()));
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

    public List<Receita> getReceitasCategoria(int idUser, LocalDate inicio, LocalDate fim) {
        String sql = "SELECT it.nome as nome, sum(es.valor) as valor from  usuario as ite inner join categoria as it inner join receita as es on it.id = es.categoria_id where ite.id = ? and data between ? and ? group by nome";
        try {
            List<Receita> receitas = new ArrayList<Receita>();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idUser);
            stmt.setDate(2, Date.valueOf(inicio));
            stmt.setDate(3, Date.valueOf(fim));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Receita receita = new Receita();
                receita.setValor(rs.getDouble("valor"));

                Categoria categoria = new Categoria();
                categoria.setNome(rs.getString("nome"));
                receita.setCategoria(categoria);

                receitas.add(receita);
            }
            rs.close();
            stmt.close();
            return receitas;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public double listaSomaReceitas(int idUser, LocalDate inicio, LocalDate fim) {
        String sql = "SELECT sum(valor) as valor FROM financa.receita JOIN financa.conta ON conta_id = conta.id where usuario_id = ? and data between ? and ?";

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

    public List<Receita> getGroupbyData(int idUser, LocalDate ldInicio, LocalDate ldFim) {
        String sql = "SELECT sum(receita.valor) as soma_valor, data FROM financa.receita INNER JOIN usuario WHERE usuario.id = ? and data between ? and ? group by data order by data asc";
        try {
            List<Receita> receitas = new ArrayList<Receita>();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idUser);
            stmt.setDate(2, Date.valueOf(ldInicio));
            stmt.setDate(3, Date.valueOf(ldFim));

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Receita receita = new Receita();
                receita.setValor(rs.getDouble("soma_valor"));
                Instant instant = Instant.ofEpochMilli(rs.getDate("data").getTime());

                receita.setData(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate());

                receitas.add(receita);
            }
            return receitas;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

}
