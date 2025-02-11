public class App {
    public static void main(String[] args) throws Exception {
        Restaurante restaurante = new Restaurante();

        for(int i = 0; i < 5; i++){
            String cadena = "Filosofo "+ (i+1);
            Thread hilo = new Thread(new HiloFilosofo(restaurante), cadena);
            hilo.start();
        }
    }
}
