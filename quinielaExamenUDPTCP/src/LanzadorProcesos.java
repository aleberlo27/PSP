public class LanzadorProcesos {
    public static void main(String[] args) throws Exception {
        //Ejecutamos los servidores
        new ProcessBuilder("java", "C:\\Users\\usuario\\Documents\\2DAM\\PSP\\TEMA 3\\quinielaExamenUDPTCP\\bin\\ServidorUDP").inheritIO().start(); 
        new ProcessBuilder("java", "C:\\Users\\usuario\\Documents\\2DAM\\PSP\\TEMA 3\\quinielaExamenUDPTCP\\bin\\ServidorTCP").inheritIO().start();

        //Ejecutamos 7 clientes por separado
        for (int i = 0; i < 7; i++) {
            new ProcessBuilder("java", "C:\\Users\\usuario\\Documents\\2DAM\\PSP\\TEMA 3\\quinielaExamenUDPTCP\\bin\\Cliente").inheritIO().start();
        }
    }
}
