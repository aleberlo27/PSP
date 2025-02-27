import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class HiloServidorQuinielaAdministracion extends Thread {
    DatagramPacket paqueteRecibido;
    DatagramSocket socketUDP;
    int puertoUDP = 5000;
    String servidor = "localhost";

    public HiloServidorQuinielaAdministracion(DatagramPacket dp, DatagramSocket ds){
        this.paqueteRecibido = dp;
        this.socketUDP = ds;
    }

    public void run(){
        try{
            //Deserializamos el objeto Quiniela
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(paqueteRecibido.getData()));
            Quiniela quiniela = (Quiniela) objectInputStream.readObject();

            //Serializamos el objeto Quiniela para enviarlo otra vez
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream salidaUDP = new ObjectOutputStream(baos);
            salidaUDP.writeObject(quiniela);
            salidaUDP.flush();

            byte[] datosRespuesta = baos.toByteArray();
            DatagramPacket paqueteRespuesta = new DatagramPacket(
                    datosRespuesta, datosRespuesta.length, 
                    InetAddress.getByName(servidor), puertoUDP
                );
            socketUDP.send(paqueteRespuesta);
            System.out.println("Enviado el objeto quiniela al servidor UDP REGISTRO.");

            salidaUDP.close();
            baos.close();

        }catch(IOException ioe){
            ioe.printStackTrace();
        }catch(ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }
    }
}
