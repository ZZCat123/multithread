package thread.juc;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * @Program multithread
 * @Description: TODO
 * @Author: luozhen
 * @Create: 2018/09/19 11:17
 */
public class ForkJoinExample extends RecursiveTask<Integer> {

    private final int threshold = 5;

    private int first;
    private int last;

    ForkJoinExample(int first, int last) {
        this.first = first;
        this.last = last;
    }

    @Override
    protected Integer compute() {
        int result = 1;
        if (last - first < threshold) {
            for (int i = first; i <= last; i++) {
                result *= i;
            }
        } else {
            int mid = first + (last - first) / 2;
            ForkJoinExample leftTask = new ForkJoinExample(first, mid);
            ForkJoinExample rightTask = new ForkJoinExample(mid + 1, last);
            leftTask.fork();
            rightTask.fork();
            result = leftTask.join() + rightTask.join();
        }
        return result;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start1 = System.currentTimeMillis();
        ForkJoinExample example = new ForkJoinExample(1, 50);
        ForkJoinPool pool = new ForkJoinPool();
        Future result = pool.submit(example);
        System.out.println(result.get());
        long end1 = System.currentTimeMillis();
        System.out.println("ForkJoin耗时：" + (end1 - start1));

        long start2 = System.currentTimeMillis();
        int res = 1;
        for (int i = 1; i <= 50; i++) {
            res *= i;
        }
        System.out.println(res);
        long end2 = System.currentTimeMillis();
        System.out.println("普通计算耗时：" + (end2 - start2));
    }
}
