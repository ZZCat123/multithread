package thread.synchronizedexample;

/**
 * @Class VolatileExample
 * @Description: TODO
 * @Author: luozhen
 * @Create: 2018/09/25 10:05
 */
public class VolatileExample extends Thread {

    private volatile boolean isRunning = true;

    private void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    @Override
    public void run() {
        while (isRunning) {
//            System.out.println("running......");
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        System.out.println("end......");
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileExample volatileExample = new VolatileExample();
        volatileExample.start();
        Thread.sleep(3000);
        volatileExample.setRunning(false);
        System.out.println("isRunning = " + volatileExample.isRunning);
    }

}
