package thread.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Program LockExample
 * @Description: lock锁的的示例
 * @Author: luozhen
 * @Create: 2018/09/19 10:32
 */
public class LockExample {

    private Lock lock = new ReentrantLock();

    public void func() {
        lock.lock();
        try {
            for (int i = 0; i < 10; i++) {
                System.out.print(i + " ");
            }
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        LockExample example = new LockExample();
        ExecutorService executorService = Executors.newCachedThreadPool();

        // 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9
        executorService.execute(() -> example.func());
        executorService.execute(() -> example.func());

        executorService.shutdown();
    }

}
