/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author zion
 */
public class MainFXController implements Initializable {
    
    @FXML
    private Label labelOlaMundo;
    
    @FXML
    private void botaoOlaMundo(){
        int i = 0;
        i= i +1;
        System.out.println("Você clicou no botão");
        labelOlaMundo.setText("Parabéns Você clicou pela "+i+"° vez");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
