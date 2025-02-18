import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServidorUDP {
    public static void main(String[] args) throws IOException {
        final int PUERTO= 5000;
        boolean conectado = true;
        byte[] buffer = new byte[1024];

        try{
            DatagramSocket socketUDP = new DatagramSocket(PUERTO);
            System.out.println("Servidor UDP lanzado.");

            while (conectado) {
                DatagramPacket paqueteRecibido = new DatagramPacket(buffer, buffer.length);
                socketUDP.receive(paqueteRecibido);

                //recibe el objeto quiniela
                ByteArrayInputStream array = new ByteArrayInputStream(paqueteRecibido.getData());
                ObjectInputStream objectInputStream = new ObjectInputStream(array);
                Quiniela quiniela = (Quiniela) objectInputStream.readObject();

                //Vamos a obtener el hash de la quiniela
                String hashQuiniela = quiniela.getHashQuiniela();

                //Enviamos el hash al cliente de vuelta
                byte[] hashEnBytes = hashQuiniela.getBytes();
                DatagramPacket hashQuinielaEnviado = new DatagramPacket(hashEnBytes, hashEnBytes.length, paqueteRecibido.getAddress(), paqueteRecibido.getPort());
                socketUDP.send(hashQuinielaEnviado);
                System.out.println("Se ha enviado al cliente el hash de la quiniela: "+ quiniela.getHashQuiniela() + "\nPara la quiniela: " +quiniela.getQuiniela());
                
            }
            //Cerramos el socket
            socketUDP.close();
        }catch(ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }
    }
}
