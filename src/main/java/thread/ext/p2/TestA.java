package thread.ext.p2;

/**
 * 轮询：难以保证及时性，资源开销很大
 * 线程间的通信[对数据变化的一个感知]
 *   等待通知机制[对象上的方法]
 *   wait
 *   notify/notifyAll
 *   等待和通知的标准范式[对象的方法，对象.调用，别直接调用了，千万别傻逼]
 *      等待方：
 *          1.获取对象的锁
 *          2.循环判断条件是否满足
 *              不满足调用wait方法
 *          3.条件满足，执行业务逻辑
 *      通知方：
 *          1.获得对象的锁
 *          2.改变条件
 *          3.通知所有等待在对象的线程
 *   notify:唤醒其中一个等待
 *   notifyAll:唤醒所有的等待
 *   notify/notifyAll该使用谁：经量使用notifyAll，因为notify有可能发生信号丢失的情况
 *   等待超时模式
 *      假设等待的时长为t，当前时间now() + t 之后超时
 *          long time = now() + t;
 *          long remain = t;//等待的持续时间
 *          while(result不满足条件 && remain > 0){
 *              wait(remain);
 *              remain = now() - time;等待还剩下的持续时间
 *          }
 *   join()：等待这个线程的执行完成，线程a执行了b.join(),线程a必须等待线程b执行完成了以后，线程a才能继续自己的工作，可能会造成无限循环的情况
 *
 *   调用yield，sleep，wait，notify等方法对锁有何影响
 *      yield：不释放锁
 *      sleep：不释放锁
 *      wait：在调用之前必须持有锁，释放锁
 *      notify：在调用之前必须持有锁，不释放锁
 */
public class TestA {
    private static Express express = new Express(0,Express.CITY);
    private static class CheckKm extends Thread{
        @Override
        public void run() {
            express.waitKm();
        }
    }
    private static class CheckSite extends Thread{
        @Override
        public void run() {
            express.waitSite();
        }
    }
    public static void main(String[] args) {
        for(int i = 0;i < 3;i++){
            new CheckSite().start();
        }
        for(int i = 0;i < 3;i++){
            new CheckKm().start();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        express.changeKm();
    }
}
class Express{
    public final static String CITY = "SHANGHAI";
    private int km;
    private String site;

    public Express(){}

    public Express(int km, String site) {
        this.km = km;
        this.site = site;
    }
    //变化公里数，然后通知处于wait状态并需要处理公里数的线程进行业务处理
    public synchronized void changeKm(){
        this.km = 101;
        /*
        使用notify有可能会导致唤醒改变site的线程，建议使用notifyAll
         */
        notifyAll();
    }
    //变化地点，然后通知处于wait状态并需要处理地点的线程进行业务处理
    public synchronized void changeSite(){
        this.site = "BJ";
        notifyAll();
    }
    public synchronized void waitKm(){
        try {
            while (this.km <= 100) {
                this.wait();
                System.out.println("km " + Thread.currentThread().getId() + " 被唤醒了");
            }
            System.out.println("the km is " + this.km + " , I will change db");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public synchronized void waitSite(){
        try {
            while (this.site.equals(CITY)) {
                this.wait();
                System.out.println("site " + Thread.currentThread().getId() + " 被唤醒了");
            }
            System.out.println("the site is " + this.km + " , I will change db");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
