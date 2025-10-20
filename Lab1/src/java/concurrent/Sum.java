import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Sum {
    private static int total = 0;
    private static Semaphore sem = new Semaphore(1);

    public static void sum(FileInputStream fis, String path) throws IOException {
        
	    int byteRead;
        int sum = 0;
        
        while ((byteRead = fis.read()) != -1) {
        	sum += byteRead;
        }

        try {            
            sem.acquire();
            total += sum;
            System.out.println(path + " : " + sum);
            sem.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sum(String path) throws IOException {

        Path filePath = Paths.get(path);
        if (Files.isRegularFile(filePath)) {
       	    FileInputStream fis = new FileInputStream(filePath.toString());
            sum(fis, path);
        } else {
            throw new RuntimeException("Non-regular file: " + path);
        }
    }

    public static void main(String[] args) throws Exception {
        ArrayList<Thread> threads = new ArrayList<Thread>();

        if (args.length < 1) {
            System.err.println("Usage: java Sum filepath1 filepath2 filepathN");
            System.exit(1);
        }

	//many exceptions could be thrown here. we don't care
        for (String path : args) {
            Thread thread = new SumThread(path);
            thread.start();
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.join();
        }
        
        System.out.println("Total: " + total);
    }

    static class SumThread extends Thread {
        private String path;

        public SumThread(String path) {
            this.path = path;
        }

        @Override
        public void run() {
            try {
                Sum.sum(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
