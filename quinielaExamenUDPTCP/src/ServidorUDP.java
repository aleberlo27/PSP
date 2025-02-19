import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServidorUDP {
    public static void main(String[] args) {
        try{
            final int PUERTO = 5000;
            byte[] buffer = new byte[1024];
            
                DatagramSocket socketUDP = new DatagramSocket(PUERTO);
                System.out.println("Servidor UDP esperando en el puerto " + PUERTO);
    
                while (true) {
                    //Recibimos el datagrampaquet del cliente
                    DatagramPacket paqueteRecibido = new DatagramPacket(buffer, buffer.length);
                    socketUDP.receive(paqueteRecibido);
    
                    //Desserializamos el objeto Quiniela
                    ObjectInputStream entradaCliente = new ObjectInputStream(new ByteArrayInputStream(paqueteRecibido.getData()));
                    Quiniela quiniela = (Quiniela) entradaCliente.readObject();
    
                    //Generamos el hash
                    quiniela.setHashQuiniela(quiniela.getQuiniela());
    
                    //Serializamos el objeto Quiniela para enviarlo otra vez
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream salidaCliente = new ObjectOutputStream(baos);
                    salidaCliente.writeObject(quiniela);
                    byte[] datosRespuesta = baos.toByteArray();
    
                    //Enviamos el objeto serializado
                    DatagramPacket paqueteRespuesta = new DatagramPacket(
                        datosRespuesta, datosRespuesta.length, 
                        paqueteRecibido.getAddress(), paqueteRecibido.getPort()
                    );
                    socketUDP.send(paqueteRespuesta);
    
                    System.out.println("Hash enviado al cliente: " + quiniela.getHashQuiniela());
                }
        }catch(Exception e){
            e.printStackTrace();
        }
    } 
}
