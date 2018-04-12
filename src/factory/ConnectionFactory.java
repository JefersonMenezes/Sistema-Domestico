/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author zion
 */
public class ConnectionFactory {
    
    
    public Connection getConnection() {
        System.out.println("Conectando ao banco...");
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost/financa", "root", "root");
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao conectar  ao banco"+ex);
        }
            
    }
    
}
