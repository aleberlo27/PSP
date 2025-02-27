import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;

public class ClienteWeb {
    public static void main(String[] args) throws NoSuchAlgorithmException, UnknownHostException, IOException, ClassNotFoundException {
        Quiniela quiniela = new Quiniela();
        String servidor = "localhost";
        int puertoTCP = 6000;

        //CONECTAMOS CON SERVIDOR TCP
        Socket socketClienteTCP = new Socket(servidor, puertoTCP);

        //Enviamos objeto a TCP
        ObjectOutputStream salidaTCP = new ObjectOutputStream(socketClienteTCP.getOutputStream());
        salidaTCP.writeObject(quiniela);
        salidaTCP.flush(); //Asegura que los datos se envíen antes de cerrar
        
        //Recibimos la salida del TCP servidorQuinielaWeb
        ObjectInputStream entradaTCP = new ObjectInputStream(socketClienteTCP.getInputStream());
        quiniela = (Quiniela) entradaTCP.readObject();
        System.out.println(quiniela.getHashQuiniela());

        socketClienteTCP.close();     
    }
}
