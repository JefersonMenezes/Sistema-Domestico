/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.CategoriaDAO;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import modelo.Categoria;

/**
 * FXML Controller class
 *
 * @author zion
 */
public class CategoriaFXController implements Initializable {

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab abaDespesa;
    @FXML
    private Tab abaReceita;
    
    @FXML
    private ListView<Categoria> lvDespesa;

    @FXML
    private ListView<Categoria> lvReceita;
          
    
    
    private List<Categoria> despesas = new ArrayList<Categoria>();
    private List<Categoria> receitas = new ArrayList<Categoria>();
    
    private ObservableList<Categoria> obsDespesas;
    private ObservableList<Categoria> obsReceitas;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listarCategorias();
    }    
    
    private void listarCategorias(){
        CategoriaDAO dao = new CategoriaDAO();
        
        despesas = dao.listaDespesas();
        receitas = dao.listaReceitas();
        
        obsDespesas = FXCollections.observableArrayList(despesas);
        obsReceitas = FXCollections.observableArrayList(receitas);
        
        lvDespesa.setItems(obsDespesas);
        lvReceita.setItems(obsReceitas);        
    }
    
}
