package TCPCalculadoraConcurrente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HiloCalculadora implements Runnable{
    private Socket socketCliente;
    private PrintWriter salida;
    private BufferedReader lectorDeEntrada;
    

    public HiloCalculadora(Socket socketCliente2) throws IOException {
        this.socketCliente = socketCliente2;
        this.salida = new PrintWriter(socketCliente.getOutputStream(), true);
        this.lectorDeEntrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
    }

    @Override
    public void run() {
        try {
            salida.println("Bienvenido al Servidor.");

            while (true) {
                //Pedir y leer el primer número
                salida.println("Introduce el primer número:");
                String num1Str = lectorDeEntrada.readLine();
                if (num1Str == null || num1Str.equals(".")) break;
                int num1 = Integer.parseInt(num1Str);

                //Pedir y leer el segundo número
                salida.println("Introduce el segundo número:");
                String num2Str = lectorDeEntrada.readLine();
                if (num2Str == null || num2Str.equals(".")) break;
                int num2 = Integer.parseInt(num2Str);

                //Pedir la operación a realizar
                salida.println("Elige la operación:");
                salida.println("1. Sumar");
                salida.println("2. Restar");
                salida.println("3. Multiplicar");
                salida.println("4. Dividir");

                String opcionStr = lectorDeEntrada.readLine();
                if (opcionStr == null || opcionStr.equals(".")) break;
                int opcion = Integer.parseInt(opcionStr);

                //Crear la calculadora con los números recibidos
                Calculadora calculadora = new Calculadora(num1, num2);
                double resultado = 0;
                String mensajeResultado = "";

                switch (opcion) {
                    case 1:
                        resultado = calculadora.sumar();
                        mensajeResultado = "Resultado de la suma: " + resultado;
                        break;
                    case 2:
                        resultado = calculadora.restar();
                        mensajeResultado = "Resultado de la resta: " + resultado;
                        break;
                    case 3:
                        resultado = calculadora.multiplicar();
                        mensajeResultado = "Resultado de la multiplicación: " + resultado;
                        break;
                    case 4:
                        resultado = calculadora.dividir();
                        mensajeResultado = (num2 == 0) ? "Error: No se puede dividir por cero." : "Resultado de la división: " + resultado;
                        break;
                    default:
                        mensajeResultado = "Opción no válida.";
                        break;
                }

                //Enviar el resultado al cliente
                salida.println(mensajeResultado);

                //Preguntar si quiere continuar
                salida.println("¿Quieres realizar otra operación? (s/n)");
                String continuar = lectorDeEntrada.readLine();
                if (continuar == null || continuar.equalsIgnoreCase("n")) {
                    salida.println("Hasta luego.");
                    break;
                }
            }

            //Cerrar conexión con el cliente
            socketCliente.close();
        } catch (IOException | NumberFormatException e) {
            salida.println("Error: Entrada no válida.");
        }
    }
    
}
