package TCPCalculadoraConcurrente.v2;

import java.io.Serializable;

public class Calculadora implements Serializable{
    private int num1, num2, operacion;

    public Calculadora (int n, int n2, int op){
        this.num1 = n;
        this.num2 = n2;
        this.operacion = op;
    }

    public int getNum1() {
        return num1;
    }

    public void setNum1(int num1) {
        this.num1 = num1;
    }

    public int getNum2() {
        return num2;
    }

    public void setNum2(int num2) {
        this.num2 = num2;
    }

    public double calcular() {
        switch (operacion) {
            case 1: return sumar();
            case 2: return restar();
            case 3: return multiplicar();
            case 4: return dividir();
            default: return -1;
        }
    }

    public int sumar(){
        return num1 + num2;
    }
    
    public double restar(){
        if (num1 < num2) { 
            int temp = num1;
            num1 = num2;
            num2 = temp;
        }
        return num1 - num2;
    }

    public double multiplicar(){
        return num1 * num2;
    }

    public double dividir(){
        if (num2 == 0) {
            System.out.println("Error: No se puede dividir por cero.");
            return 0;
        }
        return num1 / num2;
    }
}
