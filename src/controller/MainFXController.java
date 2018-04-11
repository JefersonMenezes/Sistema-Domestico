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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
