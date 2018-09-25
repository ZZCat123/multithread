package thread.executors;

/**
 * @Class Task
 * @Description: TODO
 * @Author: luozhen
 * @Create: 2018/09/24 16:20
 */
public class Task implements Runnable {

    private int taskId;
    private String taskName;

    public Task(int taskId, String taskName) {
        this.taskId = taskId;
        this.taskName = taskName;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", taskName='" + taskName + '\'' +
                '}';
    }

    @Override
    public void run() {
        try {
            System.out.println("run taskId = " + this.getTaskId() + ", task name = " + this.getTaskName());
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
