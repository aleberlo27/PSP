import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorQuinielaWeb {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        final int PUERTOTCP= 6000;
        boolean conectado = true;
        ServerSocket servidorTCP = new ServerSocket(PUERTOTCP);
        Socket socketClienteTCP;
        
        System.out.println("Servidor TCP esperando en el puerto "+servidorTCP.getLocalPort());

        while (conectado) {
            //Abrimos servidor para que se conecte el cliente
            socketClienteTCP = servidorTCP.accept();
            System.out.println("Se ha conectado el cliente: "+socketClienteTCP.getInetAddress());

            new HiloServidorQuinielaWeb(socketClienteTCP).start();
        }
    }
}
