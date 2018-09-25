package thread.executors;

import java.util.concurrent.*;

/**
 * @Class ScheduledThreadPool
 * @Description: TODO
 * @Author: luozhen
 * @Create: 2018/09/24 15:50
 */
public class ScheduledThreadPool {

    public static void main(String[] args) {
        Print print = new Print();
        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);

        // 延迟1s初始化，每3s执行一次
        ScheduledFuture<?> scheduledFuture = service.scheduleWithFixedDelay(print, 1, 3, TimeUnit.SECONDS);
    }

}
class Print extends Thread {
    @Override
    public void run() {
        System.out.println("running......");
    }
}
