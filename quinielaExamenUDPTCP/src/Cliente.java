import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;

public class Cliente{
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, ClassNotFoundException {
        String servidor = "localhost";
        int puertoUDP = 5000;
        int puertoTCP = 6000;    
      
        //CONECTAMOS CON UDP
        DatagramSocket socketUDP = new DatagramSocket();
        Quiniela miQuiniela = new Quiniela();
        
        //Serializamos la quiniela para enviarla por bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream salidaUDP = new ObjectOutputStream(baos);
        salidaUDP.writeObject(miQuiniela);
        byte[] quinielaBytes = baos.toByteArray();

        //Enviamos el objeto serializado al servidorUDP
        DatagramPacket paqueteEnvio = new DatagramPacket(quinielaBytes, quinielaBytes.length, InetAddress.getByName(servidor), puertoUDP);
        socketUDP.send(paqueteEnvio);
        System.out.println("Enviada Quiniela al Servidor UDP...");

        //Recibimos la respuesta del Servidor UDP
        byte[] buffer = new byte[1024];
        DatagramPacket paqueteRecibido = new DatagramPacket(buffer, buffer.length);
        socketUDP.receive(paqueteRecibido);

        //Deserializamos la respuesta porque llega como un array de bytes y necesitamos el objeto
        ObjectInputStream entradaUDP = new ObjectInputStream(new ByteArrayInputStream(paqueteRecibido.getData()));
        miQuiniela = (Quiniela) entradaUDP.readObject();
        System.out.println("Quiniela recibida con hash generado:");
        System.out.println("Quiniela: " + miQuiniela.getQuiniela());
        System.out.println("Hash: " + miQuiniela.getHashQuiniela());

        //CONECTAMOS CON TCP
        Socket socketClienteTCP = new Socket(servidor, puertoTCP);

        //Enviamos objeto a TCP
        ObjectOutputStream salidaTCP = new ObjectOutputStream(socketClienteTCP.getOutputStream());
        salidaTCP.writeObject(miQuiniela);

        //Recibimos la salida del TCP
        ObjectInputStream entradaTCP = new ObjectInputStream(socketClienteTCP.getInputStream());
        System.out.println(entradaTCP.readObject());
        System.out.println(entradaTCP.readObject());
        System.out.println(entradaTCP.readObject());

        entradaUDP.close();
        salidaUDP.close();
        socketUDP.close();
        entradaTCP.close();
        salidaTCP.close();
        socketClienteTCP.close();
    }
}
