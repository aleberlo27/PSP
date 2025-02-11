import java.util.concurrent.Semaphore;

public class Restaurante {
    Semaphore palillos = new Semaphore(5);

    private boolean cogerPalillos() throws InterruptedException{
        if(palillos.tryAcquire(2)){
            System.out.println("El "+Thread.currentThread().getName() + " ha cogido 2 palillos.");
            return true;
        }else{
            System.out.println("El "+Thread.currentThread().getName() + " no ha podido coger los palillos.");
            Thread.sleep(500);
            return false;
        } 
    }

    private void dejarPalillos(){
        palillos.release(2);
        System.out.println("El "+Thread.currentThread().getName() + " ha dejado los palillos.");
    }

    public void comer() throws InterruptedException{
        if(cogerPalillos()){
            System.out.println("El "+Thread.currentThread().getName() + " está comiendo.");
            Thread.sleep(2000);
            dejarPalillos();
        }else{
            System.out.println("El "+Thread.currentThread().getName() + " está esperando.");
        }
    }
}
