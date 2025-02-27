import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.security.NoSuchAlgorithmException;

public class ServidorRegistroQuiniela {
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
        final int PUERTO = 5000;
        byte[] buffer = new byte[1024];
        boolean conectado = true;
        DatagramSocket socketUDP = new DatagramSocket(PUERTO);
        System.out.println("Servidor UDP esperando en el puerto " + socketUDP.getLocalPort());

        while (conectado) {
            //Recibimos el datagrampaquet del SERVIDOR QUINIELA WEB
            DatagramPacket paqueteRecibido = new DatagramPacket(buffer, buffer.length);
            socketUDP.receive(paqueteRecibido);
            
            new HiloServidorRegistro(paqueteRecibido, socketUDP).start();;
        }
    }
}
