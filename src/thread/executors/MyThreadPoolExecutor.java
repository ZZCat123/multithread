package thread.executors;

import java.util.concurrent.*;

/**
 * @Class MyThreadPoolExecutor
 * @Description: TODO
 * @Author: luozhen
 * @Create: 2018/09/24 16:31
 */
public class MyThreadPoolExecutor {

    /**
     * 有界队列的线程池
     * @param corePoolSize 核心线程数
     * @param maximumPoolSize 最大线程数
     * @param keepAliveTime 空闲线程存活时间
     * @param timeUnit 空闲线程存活时间的单位
     * @param arrayBlockingQueue 有界队列ArrayBlockingQueue
     * @param handler 拒绝策略
     * @return ThreadPoolExecutor对象
     */
    public static ThreadPoolExecutor MyThreadPoolWithBoundedQueue(
            int corePoolSize,
            int maximumPoolSize,
            int keepAliveTime, TimeUnit timeUnit,
            ArrayBlockingQueue<Runnable> arrayBlockingQueue,
            RejectedExecutionHandler handler) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime, timeUnit,
                arrayBlockingQueue,
                handler
        );
        return pool;
    }

    /**
     * 无界队列的线程池
     * @param corePoolSize 核心线程池大小
     * @param maximumPoolSize 最大线程池大小
     * @param keepAliveTime 空闲线程存活时间
     * @param timeUnit 空闲线程存活时间的单位
     * @param linkedBlockingQueue 无界队列LinkedBlockingQueue
     * @param handler 拒绝策略
     * @return ThreadPoolExecutor对象
     */
    public static ThreadPoolExecutor MyThreadPoolWithUnboundedQueue(
            int corePoolSize,
            int maximumPoolSize,
            int keepAliveTime,
            TimeUnit timeUnit,
            LinkedBlockingQueue<Runnable> linkedBlockingQueue,
            RejectedExecutionHandler handler) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                timeUnit,
                linkedBlockingQueue,
                handler
        );
        return pool;
    }

    public static void main(String[] args) throws InterruptedException {

        MyRejectedPolicy myRejectedPolicy = new MyRejectedPolicy();

        System.out.println(Runtime.getRuntime().availableProcessors());

        ArrayBlockingQueue<Runnable> arrayBlockingQueue = new ArrayBlockingQueue<>(3);
        ThreadPoolExecutor boundedPool = MyThreadPoolExecutor.MyThreadPoolWithBoundedQueue(
                1,   // 核心线程池大小为1
                2,  // 最大线程池大小为2
                60, TimeUnit.SECONDS,  // 空闲线程存活时间60s
                arrayBlockingQueue  // 等待队列为有界队列，大小为3
                //, new ThreadPoolExecutor.DiscardPolicy() // 丢弃无法处理的任务
                //, new ThreadPoolExecutor.DiscardOldestPolicy() // 丢弃等待队列中最老的任务（最近进队，并且未被处理）
                //, new ThreadPoolExecutor.CallerRunsPolicy()
                , myRejectedPolicy   // 自定义拒绝策略
        );

        LinkedBlockingQueue<Runnable> linkedBlockingQueue = new LinkedBlockingQueue<>();
        ThreadPoolExecutor unboundedPool = MyThreadPoolExecutor.MyThreadPoolWithUnboundedQueue(
                5,   // 核心线程池大小为5,无界队列的线程数量最多就是corePoolSize的大小
                10,   // 最大线程池大小为10
                60, TimeUnit.SECONDS,  // 空闲线程存活时间60s
                linkedBlockingQueue,   // 等待队列为误解队列
                myRejectedPolicy   // 自定义拒绝策略
        );

        Task task1 = new Task(1, "任务1");
        Task task2 = new Task(2, "任务2");
        Task task3 = new Task(3, "任务3");
        Task task4 = new Task(4, "任务4");
        Task task5 = new Task(5, "任务5");
        Task task6 = new Task(6, "任务6");

        // 有界队列线程池运行
        /**
         * 运行5个任务时，任务1先被corePoolSize初始化的一个线程执行
         * 任务2，3，4进入到等待队列
         * 任务5到达时等待队列已满，此时maximumPoolSize = 2,创建一个线程执行任务5，
         * 然后任务2，3，4依次出队执行
         * 任务执行顺序为：1，5，2，3，4
         * run taskId = 1, task name = 任务1
         * run taskId = 5, task name = 任务5
         * run taskId = 2, task name = 任务2
         * run taskId = 3, task name = 任务3
         * run taskId = 4, task name = 任务4
         */

        /**
         *运行5个以上任务时，前5个参考以上，
         * 第6个任务到达时，等待队列已满，此时在执行任务的线程数量已经等于maximumPoolSize
         * 所以会执行拒绝策略（构造线程池的时候没指定拒绝策略就会使用JDK默认的拒绝策略AbortPolicy，抛出异常）
         * Exception in thread "main" java.util.concurrent.RejectedExecutionException: Task thread.executors.Task@326de728 rejected from java.util.concurrent.ThreadPoolExecutor@25618e91[Running, pool size = 2, active threads = 2, queued tasks = 3, completed tasks = 0]
         * 	at java.base/java.util.concurrent.ThreadPoolExecutor$AbortPolicy.rejectedExecution(ThreadPoolExecutor.java:2080)
         * 	at java.base/java.util.concurrent.ThreadPoolExecutor.reject(ThreadPoolExecutor.java:832)
         * 	at java.base/java.util.concurrent.ThreadPoolExecutor.execute(ThreadPoolExecutor.java:1365)
         * 	at thread.executors.MyThreadPoolExecutor.main(MyThreadPoolExecutor.java:55)
         * run taskId = 5, task name = 任务5
         * run taskId = 1, task name = 任务1
         * run taskId = 2, task name = 任务2
         * run taskId = 3, task name = 任务3
         * run taskId = 4, task name = 任务4
         */

        boundedPool.execute(task1);
        boundedPool.execute(task2);
        boundedPool.execute(task3);
        boundedPool.execute(task4);
        boundedPool.execute(task5);
        boundedPool.execute(task6);

        boundedPool.shutdown();


        // 无界队列线程池运行
        for (int i = 1; i <= 20; i++) {
            unboundedPool.execute(new Task(i,  "无界队列任务" + i));
        }
        Thread.sleep(1000);
        System.out.println("unbounded queue size : " + linkedBlockingQueue.size());
        Thread.sleep(2000);

        unboundedPool.shutdown();
    }

}
