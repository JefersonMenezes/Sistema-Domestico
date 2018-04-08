/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ContaDAO;
import dao.TipoContaDAO;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import modelo.Conta;
import modelo.TipoConta;
import modelo.Usuario;
import util.Alertas;

/**
 * FXML Controller class
 *
 * @author zion
 */
public class ContaFXController implements Initializable {
    
    private List<TipoConta> tipoContas;
    private ObservableList<TipoConta> obsTipoContas;
    private Usuario user;
    Alertas alerta = new Alertas();
    
    @FXML
    private TextField nome;
    
    @FXML
    private TextField SaldoInicial;
    
    @FXML
    private ChoiceBox<TipoConta> cbCategoria;
    
    @FXML
    private CheckBox cbJava;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listarTipoContas();
    }    
    
    public void listarTipoContas() {
        TipoContaDAO dao = new TipoContaDAO();
        tipoContas = dao.listar();
        
        obsTipoContas = FXCollections.observableArrayList(tipoContas);
        cbCategoria.setItems(obsTipoContas);
    }
    
    public int tipoContaSelecionada() {
        TipoConta tipoConta = cbCategoria.getSelectionModel().getSelectedItem();
        System.out.println("Tipo Conta: ID=" + tipoConta.getId() + " - Nome=" + tipoConta.getTipo());
        System.err.println("Acionado: " + cbJava.selectedProperty().getValue());
        return 1;
    }
    
    public void incluirConta() {
        if (!nome.getText().equals("") && !SaldoInicial.getText().equals("")) {
            if (cbCategoria.getSelectionModel().getSelectedIndex() != -1) {
                Conta conta = new Conta();
                conta.setNome(nome.getText());
                conta.setSaldoInicial(Double.valueOf(SaldoInicial.getText()));
                TipoConta tipo = new TipoConta();
                tipo.setId(cbCategoria.getSelectionModel().getSelectedItem().getId());
                conta.setTipoConta(tipo);
                
                user = new Usuario();
                user.setId(1);
                user.setNome("Jeferson Menezes");
        
                conta.setUsuario(user);
                
                ContaDAO dao = new ContaDAO();
                dao.inserir(conta);
                
                alerta.getInformacao("Sucesso","Cabecalho", "Conta "+nome.getText()+"  Cadastrada com sucesso!");
            } else{
                alerta.getAlert("Atenção", "Verifique", "O tipo de Conta é obrigatório!");   
            }
        } else {
            alerta.getAlert("Atenção", "Verifique", "O Todos os campos são obrigatório!");
            
        }
    }
}
