package thread.ext.aqs.conditionex;



import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {
    private static Express express = new Express(0, Express.CITY);
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
            new Test.CheckSite().start();
        }
        for(int i = 0;i < 3;i++){
            new Test.CheckKm().start();
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
    private Lock kmLock = new ReentrantLock();
    private Lock siteLock = new ReentrantLock();
    private Condition kmCond = kmLock.newCondition();
    private Condition siteCond = siteLock.newCondition();
    /*
    使用一把锁，然后多个Condition也是可以的，每次newCondition()都是新的Condition
     */


    public Express(){}

    public Express(int km, String site) {
        this.km = km;
        this.site = site;
    }
    //变化公里数，然后通知处于wait状态并需要处理公里数的线程进行业务处理
    public void changeKm(){


        try{
            kmLock.lock();
            this.km = 101;
            kmCond.signal();
        }finally {
            kmLock.unlock();
        }
    }
    //变化地点，然后通知处于wait状态并需要处理地点的线程进行业务处理
    public void changeSite(){
        try{
            siteLock.lock();
            this.site = "BJ";
            siteCond.signal();
        }finally {
            siteLock.unlock();
        }
    }
    public void waitKm(){
        kmLock.lock();
        try {
            while (this.km <= 100) {
                kmCond.await();
                System.out.println("km " + Thread.currentThread().getId() + " 被唤醒了");
            }
            System.out.println("the km is " + this.km + " , I will change db");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            kmLock.unlock();
        }
    }
    public void waitSite(){
        siteLock.lock();
        try {
            while (this.site.equals(CITY)) {
                siteCond.await();
                System.out.println("site " + Thread.currentThread().getId() + " 被唤醒了");
            }
            System.out.println("the site is " + this.km + " , I will change db");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            siteLock.unlock();
        }
    }

}
