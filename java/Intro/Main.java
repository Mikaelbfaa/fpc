import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main {
    private static int x = 0;
    private static Semaphore sem = new Semaphore(1);

    public static void main(String[] args) {
        Random random = new Random();

        Thread thread1 = new Thread(new SomaRunnable(random.nextInt(100), sem), "Thread 1");
        Thread thread2 = new Thread(new SomaRunnable(random.nextInt(100), sem), "Thread 2");

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("O valor final de X é: " + x);
    }

    public static void incrementaX(int valor) {
        x += valor;
    }
}
