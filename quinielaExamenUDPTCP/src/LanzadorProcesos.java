public class LanzadorProcesos {
    public static void main(String[] args) throws Exception {
        try {
            String javaHome = System.getProperty("java.home") + "/bin/java";

            //Lanzar Servidor UDP
            ProcessBuilder servidorUDP = new ProcessBuilder(javaHome, "ServidorUDP");
            servidorUDP.start();

            //Lanzar Servidor TCP
            ProcessBuilder servidorTCP = new ProcessBuilder(javaHome, "ServidorTCP");
            servidorTCP.start();

            //Lanzar 7 Clientes
            for (int i = 1; i <= 7; i++) {
                ProcessBuilder cliente = new ProcessBuilder(javaHome, "Cliente");
                cliente.start();
                Thread.sleep(500); 
            }

            System.out.println("Todos los procesos han finalizado.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
