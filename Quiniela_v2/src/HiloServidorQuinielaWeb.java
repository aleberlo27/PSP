
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class HiloServidorQuinielaWeb extends Thread{
    private Socket socketCliente;
    int puertoUDP = 5000;
    String servidor = "localhost";
    private ObjectInputStream entrada;
    private ObjectOutputStream salida;

    public HiloServidorQuinielaWeb(Socket socket){
        this.socketCliente = socket;
    }

    public void run(){
        try{
            entrada = new ObjectInputStream(socketCliente.getInputStream());
            Quiniela quiniela = (Quiniela) entrada.readObject(); //Recepciona el objeto quiniela del cliente

            //Serializamos la quiniela para enviarla por bytes  
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            salida = new ObjectOutputStream(baos);
            salida.writeObject(quiniela);

            byte[] quinielaBytes = baos.toByteArray();
            DatagramSocket socketUDP = new DatagramSocket();

            //Enviamos el objeto serializado al servidorUDP
            DatagramPacket paqueteEnvio = new DatagramPacket(quinielaBytes, quinielaBytes.length, InetAddress.getByName(servidor), puertoUDP);
            socketUDP.send(paqueteEnvio);

            System.out.println("Enviada la quiniela al Servidor de registro UDP ...");


            //Recibimos la respuesta del Servidor UDP de registro 
            byte[] buffer = new byte[1024];
            DatagramPacket paqueteRecibido = new DatagramPacket(buffer, buffer.length);
            socketUDP.receive(paqueteRecibido);

            //Deserializamos la respuesta porque llega como un array de bytes y necesitamos el objeto
            ObjectInputStream entradaUDP = new ObjectInputStream(new ByteArrayInputStream(paqueteRecibido.getData()));
            quiniela = (Quiniela) entradaUDP.readObject();
            System.out.println("Quiniela recibida con hash generado:");
            System.out.println("Hash: " + quiniela.getHashQuiniela());
            
            //Enviamos a la quiniela a traves de ClienteWeb
            ObjectOutputStream salidaTCP = new ObjectOutputStream(socketCliente.getOutputStream());
            salidaTCP.writeObject(quiniela);
             
            salidaTCP.close();
            entradaUDP.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }catch(ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }
    }
}
