/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Teste;

import factory.ConnectionFactory;
import java.sql.Connection;
/**
 *
 * @author zion
 */
public class Testes {

    public static void main(String[] args) {
        Connection conexão = new ConnectionFactory().getConnection();
    }

}
