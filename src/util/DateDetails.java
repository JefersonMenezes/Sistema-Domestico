/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Argon
 */
public class DateDetails {

    private final LocalDate dataLocal;
    private final boolean anoBissexto;
    private final int anoNumero;
    private final int numeroDiasAno;

    private final String mesNome;
    private final int mesNumero;
    private final int diasMes;
    private final int diaDoMes;

    private final String diaSemanaNome;
    private final int diaSemanaNumero;

    public DateDetails(LocalDate localDate) {
        this.dataLocal = localDate;

        this.anoBissexto = localDate.isLeapYear();
        this.anoNumero = localDate.getYear();
        this.numeroDiasAno = localDate.lengthOfYear();
        this.mesNome = localDate.getMonth().name();
        this.mesNumero = localDate.getMonthValue();
        this.diaSemanaNome = localDate.getDayOfWeek().name();
        this.diaSemanaNumero = localDate.getDayOfWeek().getValue();
        this.diasMes = localDate.lengthOfMonth();
        this.diaDoMes = localDate.getDayOfMonth();
        mostraTodos();
    }

    public boolean isAnoBissexto() {
        return anoBissexto;
    }

    public int getAnoNumero() {
        return anoNumero;
    }

    public int getNumeroDiasAno() {
        return numeroDiasAno;
    }

    public String getMesNome() {
        String mes[] = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        return mes[mesNumero - 1];
    }

    public int getMesNumero() {
        return mesNumero;
    }

    public String getDiaSemanaNome() {
        String dia[] = {"Segunda feira", "Terça feira", "Quarta feira", "Quinta feira", "Sexta feira", "Sábado", "Domingo"};
        return dia[diaSemanaNumero];
    }

    public int getDiaSemanaNumero() {
        return diaSemanaNumero;
    }

    public int getDiasMes() {
        return diasMes;
    }

    public int getDiaDoMes() {
        return diaDoMes;
    }

    public List<LocalDate> getMinMaxMes() {
        List<LocalDate> bMes;
        bMes = new ArrayList<LocalDate>();

        LocalDate inicio = LocalDate.of(anoNumero, mesNumero, 1);
        LocalDate fim = LocalDate.of(anoNumero, mesNumero, diasMes);

        bMes.add(inicio);
        bMes.add(fim);
        return bMes;
    }

    public String getDateFormatBR() {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String text = dataLocal.format(formatters);
        return text;
    }

    private void mostraTodos() {

        System.out.println("é bissexto......: " + this.anoBissexto);
        System.out.println("ano.............: " + this.anoNumero);
        System.out.println("numero dias.....: " + this.numeroDiasAno);
        System.out.println("Mes.............: " + this.mesNome);
        System.out.println("Mes numero......: " + this.mesNumero);
        System.err.println("Dia do Mês......: " + this.diaDoMes);
        System.out.println("Semana..........: " + this.diaSemanaNome);
        System.out.println("Semana dia......: " + this.diaSemanaNumero);
        System.out.println("Numero dias Mes.: " + this.diasMes);
        System.out.println("------------------------------------");
    }
}
