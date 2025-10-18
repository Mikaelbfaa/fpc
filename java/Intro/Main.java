import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main {
    private static int x = 0;
    private static Semaphore sem = new Semaphore(1);

    public static void main(String[] args) {
        Random random = new Random();
        int num_threads = random.nextInt(2,6);
        Thread[] threads = new Thread[num_threads];

        for(int i = 0; i < num_threads; i++) {
            Thread thread = new Thread(new SumRunnable(random.nextInt(100), sem), "Thread " + i);
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

        System.out.println("The final value of X is: " + x);
    }

    public static void incrementX(int value) {
        x += value;
    }
}
