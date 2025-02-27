import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;

public class AdministracionLoteria {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        String servidor = "localhost";
        final int puertoUDP = 4999;
        Quiniela quiniela = new Quiniela();
        System.out.println("Administración lotería esperando");

        //Serializamos la quiniela para enviarla por bytes  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream salida = new ObjectOutputStream(baos);
        //enviamos la quiniela
        salida.writeObject(quiniela);
        salida.flush();

        byte[] quinielaBytes = baos.toByteArray();
        DatagramSocket socketUDP = new DatagramSocket();

        //Enviamos el objeto serializado al servidorUDP
        DatagramPacket paqueteEnvio = new DatagramPacket(quinielaBytes, quinielaBytes.length, InetAddress.getByName(servidor), puertoUDP);
        socketUDP.send(paqueteEnvio);

        System.out.println("Enviada la quiniela al Servidor de administración UDP ...");
        socketUDP.close();
    }
}
