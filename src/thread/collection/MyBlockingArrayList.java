package thread.collection;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Class MyBlockingArrayList
 * @Description: TODO
 * @Author: luozhen
 * @Create: 2018/09/25 14:55
 */
public class MyBlockingArrayList<T> {

    private ArrayList<T> arrayList = new ArrayList<>();

    // 队列计数器
    private AtomicInteger count = new AtomicInteger(0);

    // 队列的最小size
    private final int minSize = 0;

    // 队列的最大size
    private final int maxSize;

    public MyBlockingArrayList(int maxSize) {
        this.maxSize = maxSize;
    }

    private Object lock = new Object();

    public void put(T t) {
        synchronized (lock) {
            while (count.get() == maxSize) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            arrayList.add(t);
            count.incrementAndGet();
            lock.notify();
        }
    }

    public T take() {
        T t = null;
        synchronized (lock) {
            while (count.get() == minSize) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            t = arrayList.remove(0);
            count.decrementAndGet();
            lock.notify();
        }
        return t;
    }

    public int getSize() {
        return this.count.get();
    }

    public static void main(String[] args) {
        MyBlockingArrayList<Character> arrayList = new MyBlockingArrayList<>(5);
        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.execute(() -> {
            Character temp = 'a';
            for (int i = 0; i < 10; i++) {
                arrayList.put(temp);
                System.out.println("producer produce : " + temp);
                temp++;
                System.out.println("--------------------------------");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        executorService.execute(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("队列长度为 : " + arrayList.getSize());
                Character c = arrayList.take();
                System.out.println("consumer consume : " + c);
                System.out.println("队列长度为 : " + arrayList.getSize());
            }
        });
    }

}
