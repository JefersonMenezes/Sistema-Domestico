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
import util.MaskTextField;

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
    private MaskTextField tfValor;
    @FXML
    private ChoiceBox<TipoConta> cbCategoria;
    @FXML
    private CheckBox cbSoma;

    public ContaFXController() {
    }

    public ContaFXController(Usuario user) {
        this.user = user;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listarTipoContas();
        tfValor.setMask("N!.NN");

    }

    public void listarTipoContas() {
        TipoContaDAO dao = new TipoContaDAO();
        tipoContas = dao.listar();

        obsTipoContas = FXCollections.observableArrayList(tipoContas);
        cbCategoria.setItems(obsTipoContas);
    }

    

    public void incluirConta() {
        if (!nome.getText().equals("") && !tfValor.getText().equals("")) {
            if (cbCategoria.getSelectionModel().getSelectedIndex() != -1) {
                Conta conta = new Conta();
                conta.setNome(nome.getText());
                conta.setSaldoInicial(Double.valueOf(tfValor.getText()));
                TipoConta tipo = new TipoConta();

                tipo.setId(cbCategoria.getSelectionModel().getSelectedItem().getId());
                conta.setTipoConta(tipo);
                conta.setIncluiSoma(cbSoma.selectedProperty().getValue());

                conta.setUsuario(user);

                ContaDAO dao = new ContaDAO();
                dao.inserir(conta);

                alerta.getInformacao("Sucesso", "Cabecalho", "Conta " + nome.getText() + "  Cadastrada com sucesso!");
                limpaCampos();
            } else {
                alerta.getAlert("Atenção", "Verifique", "O tipo de Conta é obrigatório!");
            }
        } else {
            alerta.getAlert("Atenção", "Verifique", "O Todos os campos são obrigatório!");

        }
    }

    private void limpaCampos() {
        nome.setText("");
        tfValor.setText("");
        cbCategoria.getSelectionModel().select(-1);
    }
}
