package thread.unsafe;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @CLass ThreadUnsafeExample
 * @Description: TODO
 * @Author: luozhen
 * @Create: 2018/09/19 12:09
 */
public class ThreadUnsafeExample {

    private volatile int count = 0; // volatile并不保证原子性

    private void add() {
        count++;
    }

    private synchronized void synAdd() {
        count++;
    }

    private int getCount() {
        return count;
    }

    private void setCount(int count) {
        this.count = count;
    }

    public static void main(String[] args) throws InterruptedException {
        final int TOTALTHREAD = 10000;
        ThreadUnsafeExample example = new ThreadUnsafeExample();
        CountDownLatch latch = new CountDownLatch(TOTALTHREAD);
        ExecutorService executorService = Executors.newCachedThreadPool();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < TOTALTHREAD; i++) {
            executorService.execute(() -> {
                example.add();
                latch.countDown();
            });
        }
        latch.await();
        long endTime = System.currentTimeMillis();
        System.out.println("spend time with no synchronized : " + (endTime - startTime) + "ms, count = " + example.getCount());

        CountDownLatch latch1 = new CountDownLatch(TOTALTHREAD);
        example.setCount(0);

        startTime = System.currentTimeMillis();
        for (int i = 0; i < TOTALTHREAD; i++) {
            executorService.execute(() -> {
                example.synAdd();
                latch1.countDown();
            });
        }
        latch1.await();
        endTime = System.currentTimeMillis();
        System.out.println("spend time with synchronized : " + (endTime - startTime) + "ms, count = " + example.getCount());

        executorService.shutdown();
    }
}
