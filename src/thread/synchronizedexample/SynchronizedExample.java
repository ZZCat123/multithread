package thread.synchronizedexample;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Program multithread
 * @Description: 测试synchronized分别锁不同对象时的作用范围
 * @Author: luozhen
 * @Create: 2018/09/19 10:10
 */
public class SynchronizedExample {

    /**
     * 只作用于同一个对象
     */
    public void func1() {
        synchronized (this) {
            for (int i = 0; i < 10; i++) {
                System.out.print(i + " ");
            }
        }
    }

    /**
     * 只作用于同一个对象
     */
    public synchronized void func2() {
        for (int i = 0; i < 10; i++) {
            System.out.print(i + " ");
        }
    }

    /**
     * 作用于整个类
     * 该类上的不同对象也会进行同步
     */
    public void func3() {
        synchronized (SynchronizedExample.class) {
            for (int i = 0; i < 10; i++) {
                System.out.print(i + " ");
            }
        }
    }

    /**
     * 同步一个静态方法
     * 作用于整个类
     */
    public synchronized static void func4() {
        for (int i = 0; i < 10; i++) {
            System.out.print(i + " ");
        }
    }

    public static void main(String[] args) {
        SynchronizedExample e1 = new SynchronizedExample();
        SynchronizedExample e2 = new SynchronizedExample();
        ExecutorService executorService = Executors.newCachedThreadPool();

//        0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 同一对象，顺序执行
//        executorService.execute(() -> e1.func1());
//        executorService.execute(() -> e1.func1());

//        0 0 1 1 2 3 2 4 3 5 4 6 5 7 8 9 6 7 8 9 不同对象，交替执行
//        executorService.execute(() -> e1.func1());
//        executorService.execute(() -> e2.func1());

//        0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 同一对象，顺序执行
//        executorService.execute(() -> e1.func2());
//        executorService.execute(() -> e1.func2());

//        0 0 1 1 2 2 3 3 4 4 5 5 6 6 7 7 8 8 9 9 不同对象，交替执行
//        executorService.execute(() -> e1.func2());
//        executorService.execute(() -> e2.func2());

//        0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 同一对象，顺序执行
//        executorService.execute(() -> e1.func3());
//        executorService.execute(() -> e1.func3());

//        0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 不同对象，顺序执行
//        executorService.execute(() -> e1.func3());
//        executorService.execute(() -> e2.func3());

//        0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9
//        executorService.execute(() -> SynchronizedExample.func4());
//        executorService.execute(() -> SynchronizedExample.func4());

        executorService.shutdown();
    }

}
