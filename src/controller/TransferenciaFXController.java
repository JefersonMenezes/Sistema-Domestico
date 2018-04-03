/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ContaDAO;
import dao.TransferenciaDAO;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import modelo.Conta;
import modelo.Transferencia;
import modelo.Usuario;
import util.Alertas;

/**
 * FXML Controller class
 *
 * @author zion
 */
public class TransferenciaFXController implements Initializable {

    Alertas alerta = new Alertas();
    private Usuario user;
    private List<Conta> contas;
    private ObservableList<Conta> obsContas;

    @FXML
    private DatePicker dpData;

    @FXML
    private TextField tfDescricao;

    @FXML
    private TextField tfValor;

    @FXML
    private ChoiceBox<Conta> cbOrigem;

    @FXML
    private ChoiceBox<Conta> cbDestino;

    @FXML
    private Label lVoltar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listarContas();
    }

    public void listarContas() {
        ContaDAO dao = new ContaDAO();
        user = new Usuario();
        user.setId(1);
        user.setNome("Jeferson Menezes");

        contas = dao.listaContasUser(user);
        obsContas = FXCollections.observableArrayList(contas);
        cbOrigem.setItems(obsContas);
        cbDestino.setItems(obsContas);
    }

    public void registraTransferencia() {
        if (!tfValor.getText().equals("") && !tfDescricao.getText().equals("")) {
            if (cbOrigem.getSelectionModel().getSelectedIndex() != -1 && cbDestino.getSelectionModel().getSelectedIndex() != -1) {
                salvaTransferencia();
            } else {
                alerta.getAlert("Atenção", "Selecione", "Selecione as Contas!");
            }
        } else {
            alerta.getAlert("Atenção", "Campos obrigatórios", "Campos valor e observação são obrigatórios!");
        }
    }

    private void salvaTransferencia() {
        Transferencia acao = new Transferencia();
        acao.setDescricao(tfDescricao.getText());
        acao.setValor(Double.valueOf(tfValor.getText()));
        acao.setData(dpData.getValue());
        acao.setContaOrigem(cbOrigem.getSelectionModel().getSelectedItem());
        acao.setContaDestino(cbDestino.getSelectionModel().getSelectedItem());

        if (acao.getValor() > acao.getContaOrigem().getSaldoInicial()) {
            alerta.getAlert("Atenção", "Saldo insuficiente", "Não posui valor suficiente!");

        } else {
            TransferenciaDAO dao = new TransferenciaDAO();
            dao.inserir(acao);
            
            ContaDAO d = new ContaDAO();
            d.setSubtraiSaldo(acao.getValor(), acao.getContaOrigem().getId());
            d.setSomaSaldo(acao.getValor(), acao.getContaDestino().getId());
            
            alerta.getInformacao("Sucesso", "Transação Efetuada", "Trasferido "+acao.getValor()+" da Conta "+acao.getContaOrigem().getNome()+" para "+acao.getContaDestino().getNome());
            
            limpaCampos();
            listarContas();
        }
    }

    private void limpaCampos() {
        tfValor.setText("");
        tfDescricao.setText("");
        cbDestino.getSelectionModel().select(-1);
        cbOrigem.getSelectionModel().select(-1);
    }

}
