package thread.ext.utils;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * V call() throws Exception;有返回值有异常[和其他几种创建线程的方式的区别，可以使用这个去获取一些信息，比如向网络上获取一些资源等等]
 *
 * public class FutureTask<V> implements RunnableFuture<V>
 *      public interface RunnableFuture<V> extends Runnable, Future<V>
 *          Future<V>:
 *              V get() throws InterruptedException, ExecutionException;   -- 这是一个阻塞的方法，类似异步，nio机制，也有一个get(time)的超时时间机制
 *
 * isDone()：结束，正常结束还是异常结束，或者自己取消，返回true
 * isCancelled()：任务在完成前被取消，返回true，其他情况返回false
 * cancel(boolean)：终止任务，任务还没开始，返回false，任务已经启动，根据传进来的值，如果传true，尝试中断，中断成功，返回true。如果传false，不会去中断已经运行的任务，任务已经结束，直接返回false
 * 中断在第一节课有上过，记住，中断机制是协作式的
 *
 * FutureTask经常使用在线程池里面
 *
 *
 *
 */
public class CallableTest {
    private static class UseCallable implements Callable<Integer>{
        @Override
        public Integer call() throws Exception {
            int sum = 0;
            while (!Thread.currentThread().isInterrupted()){
                sum = 0;
                System.out.println("callable开始计算");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.out.println("111");
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
                for(int i = 0;i < 5000;i++){
                    sum += i;
                }
                System.out.println("计算已经完成，结果为：" + sum);
            }
            return sum;
        }
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        UseCallable useCallable = new UseCallable();
        FutureTask<Integer> task = new FutureTask<>(useCallable);
        Thread thread = new Thread(task);
        thread.start();
        Thread.sleep(1000);
        Random r = new Random();

        if(r.nextBoolean()){//随机决定是终止任务还是获得结果
            System.out.println("获得结果：" + task.get());
        }else {
            System.out.println("中断：");
            boolean cancel = task.cancel(true);
            System.out.println(cancel);
        }
    }
}
