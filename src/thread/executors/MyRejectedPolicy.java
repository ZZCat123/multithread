package thread.executors;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Class MyRejectedPolicy
 * @Description: TODO
 * @Author: luozhen
 * @Create: 2018/09/24 16:49
 */
public class MyRejectedPolicy implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        // 自定义拒绝策略的业务逻辑
        System.out.println("自定义拒绝策略, 当前被拒绝任务为 : " + r.toString());
    }
}
