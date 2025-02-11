

public class HiloFilosofo implements Runnable{
    private Restaurante restaurante;

    public HiloFilosofo(Restaurante res){
        this.restaurante = res;
    }

    @Override
    public void run() {
        for(int i = 0; i < 50 ; i ++){
            try {
                restaurante.comer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}