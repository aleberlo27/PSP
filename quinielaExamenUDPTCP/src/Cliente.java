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
        byte[] buffer = new byte[1024];
    
      
        //Enviamos la quiniela por el servidor UDP
        DatagramSocket socketUDP = new DatagramSocket();
        Quiniela miQuiniela = new Quiniela();

        ByteArrayOutputStream array = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(array);
        oos.writeObject(miQuiniela);
        byte[] quinielaBytes = array.toByteArray();

        DatagramPacket paqueteUDP = new DatagramPacket(quinielaBytes, quinielaBytes.length, InetAddress.getByName(servidor), puertoUDP);
        socketUDP.send(paqueteUDP);

        //Recibimos el hash de la quiniela que nos devuelve el servidor UDP
        DatagramPacket paqueteRecibido = new DatagramPacket(buffer, buffer.length);
        socketUDP.receive(paqueteRecibido);
        String hashMiQuiniela = new String(paqueteRecibido.getData());
        System.out.println("Se ha recibido el hash de la quiniela: "+ hashMiQuiniela + "\nLa quiniela es: "+miQuiniela.getQuiniela()); 

        //Enviamos el hash para que nos lo valide al servidor TCP
        Socket socketTCP = new Socket(servidor, puertoTCP);
        ObjectOutputStream salidaTCP = new ObjectOutputStream(socketTCP.getOutputStream());
        ObjectInputStream entradaTCP = new ObjectInputStream(socketTCP.getInputStream());
        salidaTCP.writeObject(hashMiQuiniela);

        //Recibimos la respuesta del servidor TCP
        String respuestaTCP = (String) entradaTCP.readObject();
        System.out.println("El servidor TCP valida nuestro hash. \n"+respuestaTCP);

        //Nos devuelve el resultado del hash valido y la quiniela válida
        String resultadoQuinielaValida = (String) entradaTCP.readObject();
        System.out.println(resultadoQuinielaValida);

        //Cerramos el socket de UDP
        socketUDP.close();
        //Cerramos el socket TCP
        socketTCP.close();
    }
}
