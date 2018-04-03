/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Teste;

import factory.ConnectionFactory;
import javax.swing.JOptionPane;

/**
 *
 * @author zion
 */
public class Testes {
    
    public static void main(String[]args){
        ConnectionFactory c = new ConnectionFactory();
        c.getConnection();
        JOptionPane.showMessageDialog(null, "Conexão realizda comsucesso!!!");
    }
    
}
