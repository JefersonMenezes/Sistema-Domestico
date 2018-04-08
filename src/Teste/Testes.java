/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Teste;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import util.DateDetails;

/**
 *
 * @author zion
 */
public class Testes {

    public static void main(String[] args) {
        LocalDate dataAgora = LocalDate.now();
        DateDetails data = new DateDetails(dataAgora);
        System.out.println("Ano:" + data.getAnoNumero());
        System.out.println("Mes:" + data.getMesNome());
        System.out.println("Ultimo dia do mes:" + data.getDiasMes());
        System.out.println("dia da semana:" + data.getDiaSemanaNome());

        List<LocalDate> datas = new ArrayList<LocalDate>();
        datas = data.getMinMaxMes();
        for (int i = 0; i < datas.size(); i++) {
            System.out.println("Datas......." + datas.get(i));
        }

        System.err.println("Data Inicio" + datas.get(0));
        System.err.println("Data Fim" + datas.get(1));
    }

}
