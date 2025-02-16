package TCPCalculadoraConcurrente.v2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HiloCalculadora implements Runnable{
    private Socket socketCliente;
    

    public HiloCalculadora(Socket socketCliente2) throws IOException {
        this.socketCliente = socketCliente2;
    }


    @Override
    public void run() {
         try{
            ObjectInputStream entrada = new ObjectInputStream(socketCliente.getInputStream());
            ObjectOutputStream salida = new ObjectOutputStream(socketCliente.getOutputStream());

            while (true) {
                // Recibir el objeto Calculadora
                Calculadora calculadora = (Calculadora) entrada.readObject();

                // Calcular el resultado y enviarlo de vuelta
                double resultado = calculadora.calcular();
                salida.writeDouble(resultado);
                salida.flush();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
