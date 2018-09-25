package thread.threadcommunicate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Class ThreadCommunicateExample
 * @Description: 使用wait/notify时候，wait会释放锁，但是notify不会释放锁
 *               因而t2调用lock.wait()的时候释放锁给t1，然后t1调用lock.notify()的时候并不释放锁
 *               所以会直到t1运行完毕，释放锁喉t2才能继续执行
 *               CountDownLatch并不涉及锁的操作
 * @Author: luozhen
 * @Create: 2018/09/25 13:11
 */
public class ThreadCommunicateExample {

    private volatile List<String> list = new ArrayList<>();

    private void add(String string) {
        list.add(string);
    }

    private int size() {
        return list.size();
    }

    public static void main(String[] args) {

        final ThreadCommunicateExample example = new ThreadCommunicateExample();

        final Object lock = new Object();

        CountDownLatch latch = new CountDownLatch(1);

        Thread t1 = new Thread(() -> {
            try {
                //synchronized (lock) {
                    for (int i = 0; i < 10; i++) {
                        example.add("thread" + i);
                        System.out.println("当前线程：" + Thread.currentThread().getName() + "添加一个元素thread" + i);
                        Thread.sleep(500);
                        if (example.size() == 5) {
                            System.out.println("已经发出通知...");
                            //lock.notify();
                            latch.countDown();
                        }
                    }
                //}
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            //synchronized (lock) {
                if (example.size() != 5) {
                    try {
                        //System.out.println("t2开始执行...");
                        //lock.wait();
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("当前线程：" + Thread.currentThread().getName() + "收到通知，线程停止...");
                throw new RuntimeException();
            //}
        }, "t2");

        /*
          需添加volatile关键字才能实现通信
         */
        Thread t3 = new Thread(() -> {
            while (true) {
                if (example.size() == 5) {
                    System.out.println("当前线程：" + Thread.currentThread().getName() + "收到通知，线程停止...");
                    throw new RuntimeException();
                }
            }
        }, "t3");

        t3.start();
        t2.start();
        t1.start();
    }

}
