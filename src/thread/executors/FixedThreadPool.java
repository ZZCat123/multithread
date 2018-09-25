package thread.executors;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Class FixedThreadPool
 * @Description: TODO
 * @Author: luozhen
 * @Create: 2018/09/24 15:37
 */
public class FixedThreadPool {

    private volatile int count = 0;

    private void add() {
        count++;
    }

    private int get() {
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        final int threshold = 10000;
        ExecutorService executorService = Executors.newFixedThreadPool(threshold / 10);
        CountDownLatch latch = new CountDownLatch(threshold);
        FixedThreadPool pool = new FixedThreadPool();
        for (int i = 0; i < threshold; i++) {
            executorService.execute(() -> {
                pool.add();
                latch.countDown();
            });
        }
        latch.await();
        System.out.println("end......");
        System.out.println(pool.get());
        executorService.shutdown();
    }

}
