package thread.executors;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Class CachedThreadPool
 * @Description: TODO
 * @Author: luozhen
 * @Create: 2018/09/24 15:29
 */
public class CachedThreadPool {

    private int count = 0;

    private synchronized void add() {
        count++;
    }

    private int get() {
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        final int threshold = 1000;
        ExecutorService executorService = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(threshold);
        CachedThreadPool pool = new CachedThreadPool();
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
