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
import fxVisao.TransferenciaFX;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
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
    @FXML
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private LineChart<Integer, Number> lcMensal;
    
    XYChart.Series<Integer, Number> seriesReceitas;
    XYChart.Series<Integer, Number> seriesDespesas;
    private List<Receita> receitasLineDados;
    private List<Despesa> despesasLineDados;
    
    private List<Receita> receitasPiza;
    private List<Despesa> despesasPiza;
    private Usuario user;
    
    ObservableList<PieChart.Data> pieDespesas;
    ObservableList<PieChart.Data> pieReceitas;
    
    private double totalContas;
    private double totalDespesas;
    private double totalReceitas;
    
    private LocalDate dataCorrente;
    private DateDetails dataDetails;
    
    ;

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
        listaValoresLineGrafico();
        
    }
    
    @FXML
    private void atualiza() {
        listaDespesasPiza();
        listaReceitasPiza();
        listaTotais();
        listaValoresLineGrafico();
    }
    
    private void listaDespesasPiza() {
        DateDetails dNow = new DateDetails(dataCorrente);
        List<LocalDate> dates = new ArrayList<LocalDate>();
        dates = dNow.getMinMaxMes();
        
        DespesaDAO dao = new DespesaDAO();
        despesasPiza = dao.getDespesasCategoria(user.getId(), dates.get(0), dates.get(1));
        
        montaPizaDespesa();
    }
    
    private void listaReceitasPiza() {
        DateDetails dNow = new DateDetails(dataCorrente);
        List<LocalDate> dates = new ArrayList<LocalDate>();
        dates = dNow.getMinMaxMes();
        
        ReceitaDAO dao = new ReceitaDAO();
        receitasPiza = dao.getReceitasCategoria(user.getId(), dates.get(0), dates.get(1));
        
        montaPizaReceita();
    }
    
    private void listaValoresLineGrafico() {
        DateDetails dNow = new DateDetails(dataCorrente);
        List<LocalDate> dates = new ArrayList<LocalDate>();
        dates = dNow.getMinMaxMes();
        
        ReceitaDAO r = new ReceitaDAO();
        receitasLineDados = r.getGroupbyData(user.getId(), dates.get(0), dates.get(1));
        
        DespesaDAO d = new DespesaDAO();
        despesasLineDados = d.getGroupbyData(user.getId(), dates.get(0), dates.get(1));
        
        montaGraficoLinha(dNow.getDiasMes());
    }
    
    private void montaGraficoLinha(int diasNoMes) {
        lcMensal.getData().clear();
        seriesDespesas = null;
        seriesReceitas = null;
        
        seriesReceitas = new XYChart.Series<Integer, Number>();
        seriesReceitas.setName("Receitas");
        
        seriesDespesas = new XYChart.Series<Integer, Number>();
        seriesDespesas.setName("Despesas");
        xAxis.setUpperBound(diasNoMes);
        
        for (int i = 0; i < receitasLineDados.size(); i++) {
            seriesReceitas.getData().add(new XYChart.Data(receitasLineDados.get(i).getData().getDayOfMonth(), receitasLineDados.get(i).getValor()));
        }
        
        for (int i = 0; i < despesasLineDados.size(); i++) {
            seriesDespesas.getData().add(new XYChart.Data<Integer, Number>(despesasLineDados.get(i).getData().getDayOfMonth(), despesasLineDados.get(i).getValor()));
        }
        lcMensal.getData().add(seriesReceitas);
        lcMensal.getData().add(seriesDespesas);
        
    }
    
    private void montaPizaDespesa() {
        pieDespesas = FXCollections.observableArrayList();
        for (int i = 0; i < despesasPiza.size(); i++) {
            pieDespesas.add(new PieChart.Data(despesasPiza.get(i).getCategoria().getNome(), despesasPiza.get(i).getValor()));
        }
        pcDespesas.setData(pieDespesas);
        pcDespesas.getData().forEach(this::installTooltip);
    }
    
    private void montaPizaReceita() {
        pieReceitas = FXCollections.observableArrayList();
        for (int i = 0; i < receitasPiza.size(); i++) {
            pieReceitas.add(new PieChart.Data(receitasPiza.get(i).getCategoria().getNome(), receitasPiza.get(i).getValor()));
        }
        pcReceitas.setData(pieReceitas);
        pcReceitas.getData().forEach(this::installTooltip);
        
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
        System.out.println("Listando....");
    }
    
    private void mostraTotais() {
        lTotalContas.setText("R$ " + String.valueOf(this.totalContas));
        lTotalDespesas.setText("R$ " + String.valueOf(this.totalDespesas));
        lTotalReceitas.setText("R$ " + String.valueOf(this.totalReceitas));
    }
    
    private void pegaDataCorrente() {
        LocalDate localDate = LocalDate.now();
        this.dataCorrente = localDate;
        
        DateDetails custom = new DateDetails(this.dataCorrente);
        
        lDataAtual.setText(custom.getDateFormatBR());
    }
    
    private void mostraData() {
        lDataAtual.setText(String.valueOf(this.dataCorrente));
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
    void goToTransferencia() throws IOException {
        TransferenciaFX.setUser(user);
        new TransferenciaFX().start(new Stage());
    }
    
    @FXML
    void sair(MouseEvent event) {
        System.exit(0);
    }
    
    private void mostraUsuario() {
        lUsuarioLogado.setText(user.getNome());
    }
    
    public void installTooltip(PieChart.Data d) {
        
        String msg = String.format("%s : %s", d.getName(), d.getPieValue());
        
        Tooltip tt = new Tooltip(msg);
        tt.setStyle("-fx-background-color: gray; -fx-text-fill: whitesmoke;");
        
        Tooltip.install(d.getNode(), tt);
    }
    
}
