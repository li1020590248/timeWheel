import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecuteTest {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(6);
        for (int i = 0; i < 5; i++) {
            final int taskIndex = i;
            executor.execute(() -> {
                System.out.println(taskIndex);
                try {
                    Thread.sleep(Long.MAX_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
