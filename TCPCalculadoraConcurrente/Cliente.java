package TCPCalculadoraConcurrente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        String host = "localhost"; //127.0.0.1
        int puerto = 6000; //Al que nos vamos a conectar

        try {
            Socket socket = new Socket(host, puerto);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

            //Mensajes iniciales del servidor
            System.out.println(entrada.readLine());

            while (true) {
                //Pedir el primer número
                System.out.println(entrada.readLine());
                String num1 = teclado.readLine();
                salida.println(num1);

                //Pedir el segundo número
                System.out.println(entrada.readLine());
                String num2 = teclado.readLine();
                salida.println(num2);

                //Leer todas las líneas con las opciones
                for (int i = 0; i < 5; i++) {  // Leemos 5 líneas: "Elige la operación" + las 4 opciones
                    System.out.println(entrada.readLine());
                }

                //Introducir opción
                String operacion = teclado.readLine();
                salida.println(operacion);

                //Mostrar el resultado de la operación
                System.out.println(entrada.readLine());

                //Preguntar si quiere continuar
                System.out.println(entrada.readLine());
                String continuar = teclado.readLine();
                salida.println(continuar);

                if (continuar.equalsIgnoreCase("n")) {
                    System.out.println(entrada.readLine()); //Mensaje de despedida
                    break;
                }
            }

            //Cerrar conexión
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
