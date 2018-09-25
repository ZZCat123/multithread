package thread.collection;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Class MyBlockingLinkedList
 * @Description: 自定义无界BlockingQueue
 * @Author: luozhen
 * @Create: 2018/09/25 14:10
 */
public class MyBlockingLinkedList<T> {

    // 队列容器LinkedList
    private LinkedList<T> linkedList = new LinkedList<>();

    // 队列计数器
    private AtomicInteger count = new AtomicInteger(0);

    // 队列的最小size
    private final int minSize = 0;

    // 队列的最大size
//    private final int maxSize;

//    public MyBlockingLinkedList(int maxSize) {
//        this.maxSize = maxSize;
//    }

    private final Object lock = new Object();

    public void put(T t) {
//        synchronized (lock) {
//            while (count.get() == this.maxSize) {
//                try {
//                    lock.wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
            linkedList.add(t);
            count.incrementAndGet();
            lock.notify();
            //System.out.println("新加入的元素为：" + t);
//        }
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
            t = linkedList.removeFirst();
            count.decrementAndGet();
            lock.notify();
        }
        return t;
    }

    public int getSize() {
        return this.count.get();
    }

    public static void main(String[] args) {
//        MyBlockingLinkedList<Character> linkedList = new MyBlockingLinkedList<>(5);
        MyBlockingLinkedList<Character> linkedList = new MyBlockingLinkedList<>();

        ExecutorService executorService = Executors.newCachedThreadPool();

//        executorService.execute(() -> {
//            Character temp = 'a';
//            for (int i = 0; i < 10; i++) {
//                linkedList.put(temp);
//                System.out.println("producer produce : " + temp);
//                temp++;
//                System.out.println("--------------------------------");
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        executorService.execute(() -> {
//            for (int i = 0; i < 10; i++) {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("队列长度为 : " + linkedList.getSize());
//                Character c = linkedList.take();
//                System.out.println("consumer consume : " + c);
//                System.out.println("队列长度为 : " + linkedList.getSize());
//            }
//        });

    }

}
