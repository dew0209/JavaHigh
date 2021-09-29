package thread.ext.utils;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * CountDownLatch
 *  作用：是一个线程等待其他的线程完成之后再执行，加强版join()
 *     可以在另一个线程里面进行多次扣减，一旦为0，在主线程里面代表前期的准备工作已经完成
 *      比如线程a需要依赖线程b,c,d，这时候就可以使用CountDownLatch，CountDownLatch设置的时候不一定为3，但是在
 *          b,c,d线程需要自己根据业务逻辑进行扣减，扣除点和线程的个数是没有关系的
 *
 * CyclicBarrier
 *  让一组线程到达某个屏障，然后阻塞，必须这组全部到达，屏障才开放，所有被阻塞的线程继续运行
 *          public CyclicBarrier(int parties, Runnable barrierAction)：barrierAction在屏障开放之后会被执行
 *
 * 一个是等待业务或者配置的装配即可，一个是等待线程
 *  CountDownLatch只能用一次 由外部线程决定主线程
 *  CyclicBarrier可以用多次  由主线程自己控制
 */
public class CountDownLatchCyclicBarrier {

}

class UseCountDownLatch{
    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println("11111");
                    latch.countDown();
                    System.out.println("减少");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(1000);
                    System.out.println("2222");
                    latch.countDown();
                    System.out.println("减少");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        BushThread bushThread = new BushThread();
        new Thread(bushThread).start();
        for(int i = 0;i <= 3;i++){
            Thread t = new Thread(new InitThread());
            t.start();
        }
        latch.await();
        System.out.println("完成~~~~~");
        for(int i = 0;i <= 3;i++){
            Thread t = new Thread(new InitThread());
            t.start();
        }
        latch.await();
        //会先输出，不能重复使用
        System.out.println("11111111");
    }
    static CountDownLatch latch = new CountDownLatch(6);
    private static class InitThread implements Runnable{

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getId() + "...准备工作");
            latch.countDown();
            System.out.println("继续其他工作,代表自己的业务---已经减少一次");
        }
    }
    private static class BushThread implements Runnable{

        @Override
        public void run() {
            try {

                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getId() + "...正式工作");

        }
    }

}

class UseCyclicBarrier{

    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(5,new CollectionThread());

    public static ConcurrentHashMap<String,Long> resultMap = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {
        for(int i = 0;i <= 4;i++){
            Thread t = new Thread(new SubThread());
            t.start();

        }
        Thread.sleep(3000);
        //可以重复使用
        System.out.println("在开一组");
        for(int i = 0;i <= 4;i++){
            Thread t = new Thread(new SubThread());
            t.start();

        }
    }
    //负责屏障开放之后的工作
    private static class CollectionThread implements Runnable{

        @Override
        public void run() {
            StringBuilder result = new StringBuilder();
            for (Map.Entry<String, Long> stringLongEntry : resultMap.entrySet()) {
                result.append("[ " + stringLongEntry.getValue() + "] ");
            }
            System.out.println("result = " + result);
            System.out.println("做其他的事情");
        }
    }
    private static class SubThread implements Runnable {

        @Override
        public void run() {
            long id = Thread.currentThread().getId();
            resultMap.put(Thread.currentThread().getId() + " ",id);
            Random r = new Random();
            if(r.nextBoolean()){
                try {
                    Thread.sleep(1000 + id);
                    System.out.println("线程： " + id + ",做一些事情");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                System.out.println("等待其他的工作线程一起到来");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000 + id);
                System.out.println("线程： " + id + ",做业务事情");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}