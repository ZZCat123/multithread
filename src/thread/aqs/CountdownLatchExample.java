package thread.aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Program multithread
 * @Description: TODO
 * @Author: luozhen
 * @Create: 2018/09/19 10:49
 */
public class CountdownLatchExample {

    public static void main(String[] args) throws Exception {
        final int totalThread = 10;
        CountDownLatch count = new CountDownLatch(totalThread);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> System.out.print("run..."));
            count.countDown();
        }
        count.await();
        System.out.println("end!");
        executorService.shutdown();
    }

}
