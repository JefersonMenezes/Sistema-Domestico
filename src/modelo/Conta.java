/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author zion
 */
public class Conta {
    
    private int id;
    private String nome;
    private double saldoInicial;
    private Usuario usuario;
    private TipoConta tipoConta;
    private boolean incluiSoma;

    public Conta() {
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }

    public boolean isIncluiSoma() {
        return incluiSoma;
    }

    public void setIncluiSoma(boolean incluiSoma) {
        this.incluiSoma = incluiSoma;
    }

    
    @Override
    public String toString() {
        return getNome();
    }
    
    
    
   
}