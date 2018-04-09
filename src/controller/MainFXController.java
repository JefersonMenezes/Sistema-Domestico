/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ContaDAO;
import dao.DespesaDAO;
import dao.ReceitaDAO;
import fxVisao.DespesaFX;
import fxVisao.ReceitaFX;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import modelo.Despesa;
import modelo.Receita;
import modelo.Usuario;
import util.DateDetails;

/**
 * FXML Controller class
 *
 * @author zion
 */
public class MainFXController implements Initializable {

    @FXML
    private Label lDataAtual;

    @FXML
    private Label lTotalContas;

    @FXML
    private Label lTotalReceitas;

    @FXML
    private Label lTotalDespesas;

    @FXML
    private Label lTotalCartoes;

    @FXML
    private Label labelOlaMundo;

    @FXML
    private Label lSair;

    @FXML
    private PieChart pcDespesas;

    @FXML
    private PieChart pcReceitas;

    @FXML
    private StackPane stack;

    @FXML
    void sair(MouseEvent event) {
        System.exit(0);
    }

    private List<Receita> receitasPiza;
    private List<Despesa> despesasPiza;
    private Usuario user;

    ObservableList<PieChart.Data> pieDespesas;
    ObservableList<PieChart.Data> pieReceitas;

    private double totalContas;
    private double totalDespesas;
    private double totalReceitas;

    private LocalDate dataCorrente;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pegaDataCorrente();
        listaDespesasPiza();
        listaReceitasPiza();
        listaTotais();
    }

    private void listaDespesasPiza() {
        user = new Usuario();
        user.setId(1);
        user.setNome("Jeferson Menezes");

        DespesaDAO dao = new DespesaDAO();
        despesasPiza = dao.getDespesasCategoria(user.getId());

        montaPizaDespesa();
    }

    private void listaReceitasPiza() {
        user = new Usuario();
        user.setId(1);
        user.setNome("Jeferson Menezes");

        ReceitaDAO dao = new ReceitaDAO();
        receitasPiza = dao.getReceitasCategoria(user.getId());

        montaPizaReceita();
    }

    private void montaPizaDespesa() {
        pieDespesas = FXCollections.observableArrayList();
        for (int i = 0; i < despesasPiza.size(); i++) {
            pieDespesas.add(new PieChart.Data(despesasPiza.get(i).getCategoria().getNome(), despesasPiza.get(i).getValor()));
        }
        pcDespesas.setData(pieDespesas);
    }

    private void montaPizaReceita() {
        pieReceitas = FXCollections.observableArrayList();
        for (int i = 0; i < receitasPiza.size(); i++) {
            pieReceitas.add(new PieChart.Data(receitasPiza.get(i).getCategoria().getNome(), receitasPiza.get(i).getValor()));
        }
        pcReceitas.setData(pieReceitas);
    }

    private void listaTotais() {
        user = new Usuario();
        user.setId(1);
        user.setNome("Jeferson Menezes");

        DateDetails dNow = new DateDetails(dataCorrente);
        List<LocalDate> dates = new ArrayList<LocalDate>();
        dates = dNow.getMinMaxMes();

        ContaDAO Cdao = new ContaDAO();
        this.totalContas = Cdao.listaSomaContas(user.getId());

        DespesaDAO Ddao = new DespesaDAO();
        this.totalDespesas = Ddao.listaSomaDespesas(user.getId(), dates.get(0), dates.get(1));

        ReceitaDAO Rdao = new ReceitaDAO();
        this.totalReceitas = Rdao.listaSomaDespesas(user.getId(), dates.get(0), dates.get(1));

        mostraTotais();
    }

    private void mostraTotais() {
        lTotalContas.setText("R$ " + String.valueOf(this.totalContas));
        lTotalDespesas.setText("R$ " + String.valueOf(this.totalDespesas));
        lTotalReceitas.setText("R$ " + String.valueOf(this.totalReceitas));
    }

    private void pegaDataCorrente() {
        LocalDate localDate = LocalDate.now();
        this.dataCorrente = localDate;

        lDataAtual.setText(String.valueOf(localDate));
    }

    private void mostraData() {
        lDataAtual.setText(String.valueOf(this.dataCorrente));
    }

    private void addReceita() throws IOException {
        user = new Usuario();
        user.setId(1);
        user.setNome("Jeferson Menezes");

        Parent root = FXMLLoader.load(getClass().getResource("/fxVisao/DespesaFX.fxml"));

        ///-------------------------------------------------------
    }

    
    @FXML
    void goToDespesa() throws IOException {
        user = new Usuario();
        user.setId(1);
        user.setNome("Jeferson Menezes");
        DespesaFX.setUser(user);
        new DespesaFX().start(new Stage());
    }
     

    @FXML
    void goToReceita() throws IOException {
        user = new Usuario();
        user.setId(1);
        user.setNome("Jeferson Menezes");
        ReceitaFX.setUser(user);
        new ReceitaFX().start(new Stage());
    }

}
