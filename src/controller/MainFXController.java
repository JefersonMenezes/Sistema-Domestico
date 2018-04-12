/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author zion
 */
public class MainFXController implements Initializable {
    
    @FXML
    private JFXTextField tfSaldoContas;

    @FXML
    private JFXTextField tfSaldoReceitas;

    @FXML
    private JFXTextField tfSaldoDespesas;

    @FXML
    private JFXTextField tfSaldoCartao;
    
    private Usuario user;

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
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
        // TODO
    }    
    
}
