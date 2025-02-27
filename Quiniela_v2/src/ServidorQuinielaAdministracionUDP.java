import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServidorQuinielaAdministracionUDP {
    public static void main(String[] args) throws IOException {
        final int PUERTO = 4999;
        boolean conectado = true;
        DatagramSocket socketUDP = new DatagramSocket(PUERTO);
        System.out.println("Servidor UDP quiniela administración esperando en el puerto " + socketUDP.getLocalPort());

        while (conectado) {
            byte[] buffer = new byte[1024];
            //Recibimos el datagrampaquet de la ADMINISTRACIÓN DE QUINIELAS
            DatagramPacket paqueteRecibido = new DatagramPacket(buffer, buffer.length);
            socketUDP.receive(paqueteRecibido);
            
            new HiloServidorQuinielaAdministracion(paqueteRecibido, socketUDP).start();
        }
    }
}
