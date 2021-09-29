package thread.ext.p2;

/*
注意：面试点
    sleep不释放锁
    notify/notifyAll不释放锁
    wait释放锁，注意wait重新获得锁的时候，不是从头再synchronized一次，而是从wait()方法下一行继续执行[注意点★]
    yield释放锁
 */
public class Compare {
    static Object lock = new Object();
    public static void main(String[] args) throws InterruptedException {
        new Thread(new SleepC()).start();
        Thread.sleep(100);
        new Thread(new UnSleepC()).start();
    }
    static class SleepC implements Runnable{
        @Override
        public void run() {
            synchronized (lock){
                System.out.println("获得了锁  SleepC");
                try {
                    //请在这里进行修改和调式
                    Thread.yield();
                    System.out.println("开始休眠");
                    Thread.sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("休眠结束，释放锁");
            }
        }
    }
    static class UnSleepC implements Runnable{
        @Override
        public void run() {
            System.out.println("启动了");
            synchronized (lock){
                //修改和调式
                lock.notifyAll();
                try {
                    //Thread.sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("获得到了锁");
                System.out.println("释放锁");
            }
        }
    }
}
