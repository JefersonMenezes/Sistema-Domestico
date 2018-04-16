/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.CategoriaDAO;
import dao.ContaDAO;
import dao.ReceitaDAO;
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
import modelo.Receita;
import modelo.Usuario;
import util.Alertas;
import util.MaskTextField;

/**
 * FXML Controller class
 *
 * @author zion
 */
public class ReceitaFXController implements Initializable {

    private List<Conta> contas;
    private List<Categoria> receitas;

    private ObservableList<Conta> obsContas;
    private ObservableList<Categoria> obsReceitas;

    Alertas alerta = new Alertas();
    private Usuario user;

    @FXML
    private Label lVoltar;
    @FXML
    private DatePicker dpData;
    @FXML
    private TextField tfDescricao;
    @FXML
    private MaskTextField tfValor;
    @FXML
    private CheckBox cbRecebido;
    @FXML
    private ChoiceBox<Conta> cbConta;
    @FXML
    private ChoiceBox<Categoria> cbCategoria;
    
    public Usuario getUsuario(){
        return user;
    }
    public void setUsuario(Usuario usuario){
        this.user = usuario;
    }

    public ReceitaFXController() {
    }

    public ReceitaFXController(Usuario user) {
        this.user = user;
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listarContas();
        listarCategorias();
        initCampos();
        tfValor.setMask("N!.NN");
    }
    
    private void initCampos(){
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
        receitas = dao.listaReceitas();
        obsReceitas = FXCollections.observableArrayList(receitas);
        cbCategoria.setItems(obsReceitas);
    }

    @FXML
    public void registraReceita() {
        if (!tfValor.getText().equals("") && !tfDescricao.getText().equals("")) {
            if (cbCategoria.getSelectionModel().getSelectedIndex() != -1 && cbConta.getSelectionModel().getSelectedIndex() != -1) {
                salvaReceita();
            } else {
                alerta.getAlert("Atenção", "Selecione", "Selecione a Categoria e a Conta!");
            }
        } else {
            alerta.getAlert("Atenção", "Campos obrigatórios", "Campos valor e descrição são obrigatórios!");
        }

    }

    private void salvaReceita() {
        Receita receita = new Receita();
        receita.setValor(Double.valueOf(tfValor.getText()));
        receita.setDescricao(tfDescricao.getText());

        receita.setData(dpData.getValue());
        System.out.println("Data alocada: " + receita.getData());
        receita.setConta(cbConta.getSelectionModel().getSelectedItem());
        receita.setCategoria(cbCategoria.getSelectionModel().getSelectedItem());
        receita.setRecebido(cbRecebido.selectedProperty().getValue());

        ReceitaDAO dao = new ReceitaDAO();
        dao.inserir(receita);

        if (receita.isRecebido()) {
            ContaDAO c = new ContaDAO();
            c.setSomaSaldo(receita.getValor(), receita.getConta().getId());
        }
        alerta.getInformacao("Sucesso", "Receita Salva", "A receita " + tfDescricao.getText() + " foi salva com sucesso!");
        limpaCampos();
    }

    private void limpaCampos() {
        tfValor.setText("");
        tfDescricao.setText("");
        cbCategoria.getSelectionModel().select(-1);
        cbConta.getSelectionModel().select(-1);
    }

}
