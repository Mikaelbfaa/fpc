import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main {
    private static int x = 0;
    private static Semaphore sem = new Semaphore(1);

    public static void main(String[] args) {
        Random random = new Random();
        int num_threads = random.nextInt(2,6);
        Thread[] threads = new Thread[num_threads];
        int r;

        for(int i = 0; i < num_threads; i++) {
            r = random.nextInt(100);

            Thread thread = new Thread(new SomaRunnable(r, sem), "Thread " + i);
            threads[i] = thread;
            thread.start();
        }

        for(int i = 0; i < num_threads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("O valor final de X é: " + x);
    }

    public static void incrementaX(int valor) {
        x += valor;
    }
}
