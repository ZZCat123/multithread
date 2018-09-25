package thread.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Class UnsafeAtomicExample
 * @Description: Atomic类只保证单步操作是原子性的，不保证多个原子操作的整体的原子性
 * @Author: luozhen
 * @Create: 2018/09/25 10:46
 */
public class UnsafeAtomicExample {

    private static AtomicInteger count = new AtomicInteger(0);

    private int multiAdd() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        count.addAndGet(1);
        count.addAndGet(2);
        count.addAndGet(3);
        count.addAndGet(4);

        return count.get();
    }

    public static void main(String[] args) throws InterruptedException {
        final UnsafeAtomicExample example = new UnsafeAtomicExample();
        final int threshold = 1000;
        ExecutorService service = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(threshold);
        for (int i = 0; i < threshold; i++) {
            service.execute(() -> {
//                int sum = example.multiAdd();
//                if (sum % 10 != 0) {
//                    System.out.println(sum);
//                }
                System.out.println(example.multiAdd());
                latch.countDown();
            });
        }
        latch.await();
        System.out.println("end......");
        service.shutdown();
    }

}
