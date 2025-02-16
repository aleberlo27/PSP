package TCPCalculadoraConcurrente.v2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        String host = "localhost";
        int puerto = 6000;

        try {
            Socket socket = new Socket(host, puerto);
            ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.print("Introduce el primer número: ");
                int num1 = sc.nextInt();

                System.out.print("Introduce el segundo número: ");
                int num2 = sc.nextInt();

                System.out.println("Elige la operación:");
                System.out.println("1. Sumar");
                System.out.println("2. Restar");
                System.out.println("3. Multiplicar");
                System.out.println("4. Dividir");
                int operacion = sc.nextInt();

                // Crear el objeto Calculadora y enviarlo al servidor
                Calculadora calculadora = new Calculadora(num1, num2, operacion);
                salida.writeObject(calculadora);

                // Recibir el resultado del servidor
                double resultado = entrada.readDouble();
                System.out.println("Resultado: " + resultado);

                // Preguntar si quiere continuar
                System.out.print("¿Quieres realizar otra operación? (s/n): ");
                String continuar = sc.next();
                if (continuar.equalsIgnoreCase("n")) {
                    break;
                }
            }
            socket.close();
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}