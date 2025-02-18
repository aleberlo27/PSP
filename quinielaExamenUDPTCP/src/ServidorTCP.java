import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;

public class ServidorTCP {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, ClassNotFoundException, InterruptedException {
        final int PUERTO= 6000;
        boolean conectado = true;
        String hashQuinielaValida = Quiniela.getHashQuinielaValida();

        while (conectado) {
            //Abrimos servidor para que se conecte el cliente
            ServerSocket servidorTCP = new ServerSocket(PUERTO);
            Socket socket = servidorTCP.accept();
            System.out.println("Se ha conectado el cliente: "+socket.getInetAddress());

            ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());

            Quiniela quiniela = (Quiniela) entrada.readObject();
            String hashObjeto = quiniela.getHashQuiniela();
            
            //Comparamos hashes para devolverlos
            if(hashObjeto.equals(hashQuinielaValida)){
                salida.writeObject("Quiniela correcta.");
            }else{
                salida.writeObject("Quiniela incorrecta.");
            }

            salida.writeObject(quiniela.toString());

            //Cerramos el servidor
            servidorTCP.close();
        }
    }
}
