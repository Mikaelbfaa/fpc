import java.util.concurrent.Semaphore;

public class SomaRunnable implements Runnable {
    private int X;
    private Semaphore sem;

    public SomaRunnable(int X, Semaphore sem) {
        this.X = X;
        this.sem = sem;
    }

    @Override
    public void run() {

        try {
            System.out.println(Thread.currentThread().getName() + " vai dormir por " + this.X + " ms.");
            Thread.sleep(this.X);
            System.out.println(Thread.currentThread().getName() + "  Acordou!");
            this.somaX();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void somaX() throws InterruptedException {
        sem.acquire();

        try {
            Main.incrementaX(this.X);
            System.out.println(Thread.currentThread().getName() + " somando " + this.X);
        } finally {
            sem.release();
        }
    }
}