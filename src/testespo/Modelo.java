package testespo;
 
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mapda
 */
public class Modelo {
    private int m; //numero de restrições
    private int n; //numero de variaveis
    private double A[][]; //matriz de coeficientes das restrições
    private int b[]; //valores das restriçoes
    private int c[]; //coeficiente da função objetivo
    private int sinal[]; //sinal das restriçoes
    private double resultado[]; //resultado das variaveis no braching
    private double Z; // resultado da função objetivo no branching
    private int k[]; //armazena o valor da nova restrição
    private int raux[];//recebe o inteiro dos resultados

    public Modelo(int m, int n, double[][] A, int[] b, int[] c, int[] sinal) {
        this.m = m;
        this.n = n;
        this.A = A;
        this.b = b;
        this.c = c;
        this.sinal = sinal;
        this.resultado = new double[n];
        this.Z = Z;
        this.k = k;
        this.raux = new int[n];
    }
   
    
    public int[] getRaux() {
        return raux;
    }

    public void setRaux(int[] raux) {
        this.raux = raux;
    }

    public int[] getK() {
        return k;
    }

    public void setK(int[] k) {
        this.k = k;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int[] getSinal() {
        return sinal;
    }

    public void setSinal(int[] sinal) {
        this.sinal = sinal;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public double[][] getA() {
        return A;
    }

    public void setA(double[][] A) {
        this.A = A;
    }

    public int[] getB() {
        return b;
    }

    public void setB(int[] b) {
        this.b = b;
    }

    public int[] getC() {
        return c;
    }

    public void setC(int[] c) {
        this.c = c;
    }

    public double[] getResultado() {
        return resultado;
    }

    public void setResultado(double[] resultado) {
        this.resultado = resultado;
    }

    public double getZ() {
        return Z;
    }

    public void setZ(double Z) {
        this.Z = Z;
    }
    
    
    
    
    
}
