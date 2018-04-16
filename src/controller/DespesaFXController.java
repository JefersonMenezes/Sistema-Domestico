/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.CategoriaDAO;
import dao.ContaDAO;
import dao.DespesaDAO;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import modelo.Categoria;
import modelo.Conta;
import modelo.Despesa;
import modelo.Usuario;
import util.Alertas;
import util.MaskTextField;
/**
 * FXML Controller class
 *
 * @author zion
 */
public class DespesaFXController implements Initializable {

    private List<Conta> contas;
    private List<Categoria> despesas;

    private ObservableList<Conta> obsContas;
    private ObservableList<Categoria> obsDespesas;

    Alertas alerta = new Alertas();
    private Usuario user;

    @FXML
    private DatePicker dpData;
    @FXML
    private TextField tfDescricao;
    @FXML//
    private MaskTextField tfValor;
    @FXML
    private ChoiceBox<Categoria> cbCategoria;
    @FXML
    private ChoiceBox<Conta> cbConta;
    @FXML
    private CheckBox cbPago;
    @FXML
    private Label lVoltar;
    @FXML
    private Label lUsuarioLogado;

    //public static DespesaFXController controller;
    public Usuario getUsuario() {
        return user;
    }

    public void setUsuario(Usuario usuario) {
        user = usuario;
    }

    public DespesaFXController(Usuario usuario) {
        this.user = usuario;
        System.err.println("Usuario: "+this.user.getNome());
    }

    public DespesaFXController() {

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //controller = this;
        listarCategorias();
        listarContas();
        initCampos();
        tfValor.setMask("N!.NN");
        
        //mostraUsuário();
    }

    private void initCampos() {
        dpData.setValue(LocalDate.now());
    }

    public void listarContas() {
        ContaDAO dao = new ContaDAO();
        contas = dao.listaContasUser(user);
        obsContas = FXCollections.observableArrayList(contas);
        cbConta.setItems(obsContas);
    }

    public void listarCategorias() {
        CategoriaDAO dao = new CategoriaDAO();
        despesas = dao.listaDespesas();
        obsDespesas = FXCollections.observableArrayList(despesas);
        cbCategoria.setItems(obsDespesas);
    }

    @FXML
    public void registraDespesa() {
        if (!tfValor.getText().equals("") && !tfDescricao.getText().equals("")) {
            if (cbCategoria.getSelectionModel().getSelectedIndex() != -1 && cbConta.getSelectionModel().getSelectedIndex() != -1) {
                salvaDespesa();
            } else {
                alerta.getAlert("Atenção", "Selecione", "Selecione a Categoria e a Conta!");
            }
        } else {
            alerta.getAlert("Atenção", "Campos obrigatórios", "Campos valor e descrição são obrigatórios!");
        }

    }

    public void salvaDespesa() {

        Despesa despesa = new Despesa();
        despesa.setValor(Double.valueOf(tfValor.getText()));
        despesa.setDescricao(tfDescricao.getText());

        despesa.setData(dpData.getValue());
        System.out.println("Data alocada: " + despesa.getData());
        despesa.setConta(cbConta.getSelectionModel().getSelectedItem());
        despesa.setCategoria(cbCategoria.getSelectionModel().getSelectedItem());
        despesa.setPago(cbPago.selectedProperty().getValue());

        if (despesa.isPago()) {
            System.err.println("Valor : " + despesa.getValor());
            System.out.println("Saldo : " + despesa.getConta().getSaldoInicial());
            if (despesa.getValor() > despesa.getConta().getSaldoInicial()) {
                alerta.getAlert("Atenção", "Saldo insuficiente", "A conta selecionada não posui saldo suficiente para pagamento!");
            } else {
                DespesaDAO dao = new DespesaDAO();
                dao.inserir(despesa);

                ContaDAO c = new ContaDAO();
                c.setSubtraiSaldo(despesa.getValor(), despesa.getConta().getId());
                alerta.getInformacao("Sucesso", "Despesa Salva", "A despesa " + tfDescricao.getText() + " foi salva com sucesso!");
                limpaCampos();
                listarContas();
            }
        } else {
            DespesaDAO dao = new DespesaDAO();
            dao.inserir(despesa);
            alerta.getInformacao("Sucesso", "Despesa Salva", "A despesa " + tfDescricao.getText() + " foi salva com sucesso!");
            limpaCampos();
            listarContas();
        }
    }

    private void limpaCampos() {
        tfValor.setText("");
        tfDescricao.setText("");
        cbCategoria.getSelectionModel().select(-1);
        cbConta.getSelectionModel().select(-1);
    }

    private void mostraUsuário() {
        lUsuarioLogado.setText(user.getNome());
    }

}
