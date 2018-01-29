import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Test {

    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(4);
        ScheduledFuture<?> future = executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello");
            }
        }, 10, 10, TimeUnit.SECONDS);

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        future.cancel(false);
    }
}
