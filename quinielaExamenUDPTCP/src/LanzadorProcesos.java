public class LanzadorProcesos {
    public static void main(String[] args) throws Exception {
        //Ejecutamos los servidores
        new ProcessBuilder("java", "ServidorUDP").inheritIO().start(); 
        new ProcessBuilder("java", "ServidorTCP").inheritIO().start();

        //Ejecutamos 7 clientes por separado
        for (int i = 0; i < 7; i++) {
            new ProcessBuilder("java", "Cliente").inheritIO().start();
        }
    }
}
