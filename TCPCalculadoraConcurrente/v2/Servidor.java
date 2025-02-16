package TCPCalculadoraConcurrente.v2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) throws IOException {
        final int PUERTO= 6000;
        boolean conectado = true;

        ServerSocket socketServidor = new ServerSocket(PUERTO);

        while (conectado) {
            Socket socketCliente = socketServidor.accept(); //sicket que le pasas al cliente
            HiloCalculadora hiloCalculadora = new HiloCalculadora(socketCliente);
            hiloCalculadora.run();
        }

        socketServidor.close();
    }
}
