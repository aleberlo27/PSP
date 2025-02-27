import java.io.*;
import java.net.*;
import java.security.NoSuchAlgorithmException;

public class HiloServidorRegistro extends Thread {
    DatagramPacket paqueteRecibido;
    DatagramSocket socketUDP;
    private static final String FILE_NAME = "quinielas_registradas.txt";

    public HiloServidorRegistro(DatagramPacket dp, DatagramSocket ds){
        this.paqueteRecibido = dp;
        this.socketUDP = ds;
    }

    public void run(){
        try{
            //Deserializamos el objeto Quiniela
            ByteArrayInputStream bais = new ByteArrayInputStream(paqueteRecibido.getData());
            ObjectInputStream entradaServidorTCP = new ObjectInputStream(bais);
            Quiniela quiniela = (Quiniela) entradaServidorTCP.readObject();
            
            //Comprobar si es quiniela válida y hacerle el hash a la quiniela
            if(quiniela.esQuinielaValida()){
                System.out.println("Quiniela validada.");
            }else{
                System.out.println("Quiniela no válida.");
            }
            quiniela.setHashQuiniela(quiniela.getQuiniela());
    
            //Escribimos en un único fichero para todas las quinielas
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
                writer.write("Nombre: " + quiniela.getNombre() + "\n");
                writer.write("Hash Quiniela: " + quiniela.getHashQuiniela() + "\n");
                writer.write("-----------------------------\n");
            }
    
            System.out.println("Quiniela de " + quiniela.getNombre() + " registrada en " + FILE_NAME);


            //Serializamos el objeto Quiniela para enviarlo otra vez
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream salidaCliente = new ObjectOutputStream(baos);
            salidaCliente.writeObject(quiniela);

            byte[] datosRespuesta = baos.toByteArray();
            DatagramPacket paqueteRespuesta = new DatagramPacket(
                    datosRespuesta, datosRespuesta.length, 
                    paqueteRecibido.getAddress(), paqueteRecibido.getPort()
                );
            socketUDP.send(paqueteRespuesta);
            System.out.println("Enviado el objeto quiniela al servidor TCP quiniela web.");
            socketUDP.close();

        }catch(IOException ioe){
            ioe.printStackTrace();
        }catch(ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }catch(NoSuchAlgorithmException nsae){
            nsae.printStackTrace();
        }
    }
}
