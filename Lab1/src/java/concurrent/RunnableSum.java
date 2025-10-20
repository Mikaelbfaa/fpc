public class RunnableSum implements Runnable{
    private String path;

    public RunnableSum(String path) {
        this.path = path;
    }

    @Override
    public void run() {
        try {
            Sum.sum(this.path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
