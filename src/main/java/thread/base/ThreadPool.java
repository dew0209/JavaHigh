package thread.base;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 创建线程的方式四：使用线程池
 *  好处：
 *      1.提高响应速度（减少了创建新线程的时间）
 *      2.降低资源消耗（重复利用线程池中线程，不需要每次都创建）
 *      3.便于线程管理
 *          corePoolSize：核心池的大小
 *          maximumPoolSize：最大线程数
 *          keepAliveTime：线程没有任务时最多保持多长时间后会终止
 */
public class ThreadPool {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        ThreadPoolExecutor pool = (ThreadPoolExecutor)service;
        //均可
        /*pool.execute(new NumberThread());
        pool.execute(new NumberThread1());
        pool.shutdown();*/
        service.execute(new NumberThread());
        service.execute(new NumberThread1());

        service.shutdown();
    }
}

class NumberThread implements Runnable{
    @Override
    public void run() {
        for(int i = 0;i <= 100;i++){
            if (i % 2 == 0){
                System.out.println(Thread.currentThread().getName() + "--->" + i);
            }
        }
    }
}

class NumberThread1 implements Runnable{
    @Override
    public void run() {
        for(int i = 0;i <= 100;i++){
            if (i % 2 != 0){
                System.out.println(Thread.currentThread().getName() + "--->" + i);
            }
        }
    }
}