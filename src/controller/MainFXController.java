/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ContaDAO;
import dao.DespesaDAO;
import dao.ReceitaDAO;
import fxVisao.ContaFX;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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
    private Label lUsuarioLogado;

    @FXML
    private PieChart pcDespesas;

    @FXML
    private PieChart pcReceitas;

    @FXML
    private StackPane stack;

    private List<Receita> receitasPiza;
    private List<Despesa> despesasPiza;
    private Usuario user;

    ObservableList<PieChart.Data> pieDespesas;
    ObservableList<PieChart.Data> pieReceitas;

    private double totalContas;
    private double totalDespesas;
    private double totalReceitas;

    private LocalDate dataCorrente;

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario usuario) {
        this.user = usuario;
    }

    public MainFXController() {
    }

    public MainFXController(Usuario user) {
        this.user = user;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostraUsuario();
        pegaDataCorrente();
        listaDespesasPiza();
        listaReceitasPiza();
        listaTotais();
    }

    private void listaDespesasPiza() {
        DespesaDAO dao = new DespesaDAO();
        despesasPiza = dao.getDespesasCategoria(user.getId());

        montaPizaDespesa();
    }

    private void listaReceitasPiza() {
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
        DateDetails dNow = new DateDetails(dataCorrente);
        List<LocalDate> dates = new ArrayList<LocalDate>();
        dates = dNow.getMinMaxMes();

        ContaDAO Cdao = new ContaDAO();
        this.totalContas = Cdao.listaSomaContas(user.getId());

        DespesaDAO Ddao = new DespesaDAO();
        this.totalDespesas = Ddao.listaSomaDespesas(user.getId(), dates.get(0), dates.get(1));

        ReceitaDAO Rdao = new ReceitaDAO();
        this.totalReceitas = Rdao.listaSomaReceitas(user.getId(), dates.get(0), dates.get(1));

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
        Parent root = FXMLLoader.load(getClass().getResource("/fxVisao/DespesaFX.fxml"));

        ///-------------------------------------------------------
    }

    @FXML
    void goToDespesa() throws IOException {
        DespesaFX.setUser(user);
        new DespesaFX().start(new Stage());
    }

    @FXML
    void goToReceita() throws IOException {
        ReceitaFX.setUser(user);
        new ReceitaFX().start(new Stage());
    }

    @FXML
    void goToConta() throws IOException {
        ContaFX.setUser(user);
        new ContaFX().start(new Stage());
    }

    @FXML
    void sair(MouseEvent event) {
        System.exit(0);
    }

    private void mostraUsuario() {
        lUsuarioLogado.setText(user.getNome());
    }

}
